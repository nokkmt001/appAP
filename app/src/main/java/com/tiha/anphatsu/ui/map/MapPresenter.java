package com.tiha.anphatsu.ui.map;

import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;
import com.tiha.anphatsu.data.entities.DistanceInfo;
import com.tiha.anphatsu.data.entities.DurationInfo;
import com.tiha.anphatsu.data.entities.RouteInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MapPresenter implements MapContract.Presenter {
    private static final String DIRECTION_URL_API = "https://maps.googleapis.com/maps/api/directions/json?";
    private static final String GOOGLE_API_KEY = "AIzaSyBu86Xy64zcwMiKCLbsDbjPHQ9iG_5TBh0";

    MapContract.View view;

    public MapPresenter(MapContract.View view) {
        this.view = view;
    }


    @Override
    public void DowloadDataMap(String origin, String destination) {
        try {
            String URL = createUrl(origin, destination);
            new DownloadRawData().execute(URL);
        } catch (UnsupportedEncodingException e) {
            view.onDowloadDataMapError(e.getMessage());
        }
    }

    private class DownloadRawData extends AsyncTask<String, Void, String> {
        String link;
        @Override
        protected String doInBackground(String... params) {
            String link = params[0];
            try {
                this.link = link;
                URL url = new URL(link);
                InputStream is = url.openConnection().getInputStream();
                StringBuffer buffer = new StringBuffer();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                return buffer.toString();

            } catch (MalformedURLException e) {
//                e.printStackTrace();
            } catch (IOException e) {
//                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String res) {
            try {
                view.onDowloadDataMapSuccess(parseJSon(res), link);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private String createUrl(String origin, String destination) throws UnsupportedEncodingException {
        //Chuyen sang utf-8
        String urlOrigin = URLEncoder.encode(origin, "utf-8");
        String urlDestination = URLEncoder.encode(destination, "utf-8");
        return DIRECTION_URL_API + "origin=" + urlOrigin + "&destination=" + urlDestination + "&key=" + GOOGLE_API_KEY;
    }

    private List<RouteInfo> parseJSon(String data) throws JSONException {
        if (data == null)
            return null;

        List<RouteInfo> routes = new ArrayList<RouteInfo>();
        JSONObject jsonData = new JSONObject(data);
        JSONArray jsonRoutes = jsonData.getJSONArray("routes");
        for (int i = 0; i < jsonRoutes.length(); i++) {
            JSONObject jsonRoute = jsonRoutes.getJSONObject(i);
            RouteInfo route = new RouteInfo();

            JSONObject overview_polylineJson = jsonRoute.getJSONObject("overview_polyline");
            JSONArray jsonLegs = jsonRoute.getJSONArray("legs");
            JSONObject jsonLeg = jsonLegs.getJSONObject(0);
            JSONObject jsonDistance = jsonLeg.getJSONObject("distance");
            JSONObject jsonDuration = jsonLeg.getJSONObject("duration");
            JSONObject jsonEndLocation = jsonLeg.getJSONObject("end_location");
            JSONObject jsonStartLocation = jsonLeg.getJSONObject("start_location");

            route.setDistance(new DistanceInfo(jsonDistance.getString("text"), jsonDistance.getInt("value")));
            route.setDuration(new DurationInfo(jsonDuration.getString("text"), jsonDuration.getInt("value")));
            route.setEndAddress(jsonLeg.getString("end_address"));
            route.setStartAddress(jsonLeg.getString("start_address"));
            route.setStartLocation(new LatLng(jsonStartLocation.getDouble("lat"), jsonStartLocation.getDouble("lng")));
            route.setEndLocation(new LatLng(jsonEndLocation.getDouble("lat"), jsonEndLocation.getDouble("lng")));
            route.setPoints(decodePolyLine(overview_polylineJson.getString("points")));

            routes.add(route);
        }

        return routes;
    }

    private List<LatLng> decodePolyLine(final String poly) {
        int len = poly.length();
        int index = 0;
        List<LatLng> decoded = new ArrayList<LatLng>();
        int lat = 0;
        int lng = 0;

        while (index < len) {
            int b;
            int shift = 0;
            int result = 0;
            do {
                b = poly.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = poly.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            decoded.add(new LatLng(
                    lat / 100000d, lng / 100000d
            ));
        }

        return decoded;
    }
}
