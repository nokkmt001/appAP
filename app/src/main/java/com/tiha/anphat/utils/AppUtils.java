package com.tiha.anphat.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppUtils {

    private static final String TAG = "AppUtils";

    private AppUtils() {
        // This class is not publicly instantiable
    }

//    public static void openPlayStoreForApp(Context context) {
//        final String appPackageName = context.getPackageName();
//        try {
//            context.startActivity(new Intent(Intent.ACTION_VIEW,
//                    Uri.parse(context
//                            .getResources()
//                            .getString(R.string.app_market_link) + appPackageName)));
//        } catch (android.content.ActivityNotFoundException e) {
//            context.startActivity(new Intent(Intent.ACTION_VIEW,
//                    Uri.parse(context
//                            .getResources()
//                            .getString(R.string.app_google_play_store_link) + appPackageName)));
//        }
//    }

    @SuppressLint("all")
    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static boolean isValidateUsername(String userName){
        return userName.length() >= 5;
    }

    public static boolean isEmailValid(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean ValidateEmail(String email){
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@" + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?" + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\." + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?" + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|" + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static String loadJSONFromAsset(Context context, String jsonFileName)
            throws IOException {

        AssetManager manager = context.getAssets();
        InputStream is = manager.open(jsonFileName);

        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();

        return new String(buffer, "UTF-8");
    }

    public static String getTimeStamp() {
        return new SimpleDateFormat(AppConstants.TIMESTAMP_FORMAT, Locale.US).format(new Date());
    }

    public static DecimalFormat formatNumber(String loai) {
        DecimalFormat decimalFormat = null;
        decimalFormat = (DecimalFormat) NumberFormat.getInstance(new Locale("vi", "VN"));
        decimalFormat.setDecimalSeparatorAlwaysShown(true);
        loai = loai.toUpperCase();
        switch (loai) {
            case "N0":
                decimalFormat.applyPattern("#,###,###,###,###");
                break;
            case "N1":
                decimalFormat.applyPattern("#,###,###,##0.#");
                break;
            case "N2":
                decimalFormat.applyPattern("#,###,###,##0.##");
                break;
            case "N3":
                decimalFormat.applyPattern("#,###,###,##0.###");
                break;
            case "N4":
                decimalFormat.applyPattern("#,###,###,##0.####");
                break;
            case "N5":
                decimalFormat.applyPattern("#,###,###,##0.#####");
                break;
            default:
                decimalFormat.applyPattern("#,###,###,###,###");
                break;
        }
        return decimalFormat;
    }

    public static String formatDateLongTime(String sdate) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a", Locale.US);
        try {
            Date a = sdf.parse(sdate);
            sdf = new SimpleDateFormat("dd/MM/yy HH:mm ", Locale.US);
            return sdf.format(a);
        } catch (ParseException e) {
            return "";
        }
    }

    public static String fomartDateShortTime(String date, String format) {
        String convertDate = "";
        Timestamp tDate = Timestamp.valueOf(date);
        SimpleDateFormat dt1 = new SimpleDateFormat(format);
        convertDate = dt1.format(tDate).substring(0, 10);
        return convertDate;
    }

    public static String formatDateToDateRequest(String dateRequest, String formatOut) {
        try {
            if (dateRequest.length() > 20)
                dateRequest = dateRequest.substring(0, 20);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
            Date formatted;
            formatted = simpleDateFormat.parse(dateRequest);
            simpleDateFormat = new SimpleDateFormat(formatOut, Locale.US);
            return simpleDateFormat.format(formatted);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String formatDateToString(java.util.Date date, String formatOut) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatOut);
            return simpleDateFormat.format(date);
        } catch (Exception e) {
            return "";
        }
    }

    public static Date formatStringToDateUtil(String strDate, String formatIn) {
        try {
            SimpleDateFormat sdf1 = new SimpleDateFormat(formatIn);
            return sdf1.parse(strDate);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String formatDateToDateRequestSQL(String dateRequest, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        Date formatted = null;
        try {
            formatted = simpleDateFormat.parse(dateRequest);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        simpleDateFormat = new SimpleDateFormat(format, Locale.US);
        String result = simpleDateFormat.format(formatted);
        return result;
    }

    public static java.sql.Date formatStringToDateSQL(String strDate, String formatIn) {
        java.sql.Date KQ = null;
        try {
            SimpleDateFormat sdf1 = new SimpleDateFormat(formatIn);
            Date dateObj = sdf1.parse(strDate);
            KQ = new java.sql.Date(dateObj.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return KQ;
    }


    public static String formatBitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public static Bitmap formatStringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public static Bitmap ByTeToBitMap(byte[] image) {
        if (image == null || image.length == 0) return null;
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }


    public static String chuyenCoDauThanhKhongDau(String value) {
        String result = "";
        try {
            String temp = Normalizer.normalize(value, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
//        .replaceAll(" ", "-")
            result = pattern.matcher(temp).replaceAll("").toLowerCase().replaceAll("đ", "d");
        } catch (Exception e) {
            result = "";
        }
        return result;
    }

    @SuppressLint("DefaultLocale")
    public static String MD5Password(String username, String password)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        if (password.equals("")) {
            return "";
        }
        byte[] pwBytes = new String(
                (username.toUpperCase() + password).getBytes(), "UTF-8")
                .getBytes();
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        String strMD5Pass = "";
        byte byteData[] = messageDigest.digest(pwBytes);
        for (int i = 0; i < byteData.length; i++) {
            strMD5Pass += "-" + Integer.toString((byteData[i] & 0xff) + 0x100, 16).toString().toUpperCase().substring(1);
        }
        return strMD5Pass.substring(1);
    }

    // convert from bitmap to byte array
    public static byte[] BitMapToByte(Bitmap bitmap, Bitmap.CompressFormat format) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(format, 0, stream);
        return stream.toByteArray();
    }

    public static String GetDiaChi(String So, String Duong, String KhuPho, String Phuong, String Quan, String Tinh) {
        So = TextUtils.isEmpty(So) ? "" : So;
        Duong = TextUtils.isEmpty(Duong) ? "" : Duong;
        KhuPho = TextUtils.isEmpty(KhuPho) ? "" : KhuPho;
        Phuong = TextUtils.isEmpty(Phuong) ? "" : Phuong;
        Quan = TextUtils.isEmpty(Quan) ? "" : Quan;
        Tinh = TextUtils.isEmpty(Tinh) ? "" : Tinh;
        String DiaChi = "";
        if (TextUtils.isEmpty(Quan)) {
            if (TextUtils.isEmpty(Tinh)) {
                if (TextUtils.isEmpty(Phuong)) {
                    DiaChi = So + " " + Duong + " " + KhuPho;
                    DiaChi = DiaChi.trim();
                } else {
                    DiaChi = So + " " + Duong + " " + KhuPho;
                    DiaChi = DiaChi.trim() + ", " + Phuong;
                }
            } else {
                if (TextUtils.isEmpty(Phuong)) {
                    DiaChi = So + " " + Duong + " " + KhuPho;
                    DiaChi = DiaChi.trim() + ", " + Tinh;
                } else {
                    DiaChi = So + " " + Duong + " " + KhuPho;
                    DiaChi = DiaChi.trim() + ", " + Phuong + ", " + Tinh;
                }
            }
        } else {
            if (TextUtils.isEmpty(Tinh)) {
                if (Phuong.isEmpty()) {
                    DiaChi = So + " " + Duong + " " + KhuPho;
                    DiaChi = DiaChi.trim() + ", " + Quan;
                    ;
                } else {
                    DiaChi = So + " " + Duong + " " + KhuPho;
                    DiaChi = DiaChi.trim() + ", " + Phuong + ", " + Quan;
                }
            } else {
                if (TextUtils.isEmpty(Phuong)) {
                    DiaChi = So + " " + Duong + " " + KhuPho;
                    DiaChi = DiaChi.trim() + ", " + Quan + ", " + Tinh;

                } else {
                    DiaChi = So + " " + Duong + " " + KhuPho;
                    DiaChi = DiaChi.trim() + ", " + Phuong + ", " + Quan + ", " + Tinh;
                }
            }
        }
        return DiaChi;
    }

    public static String GetSoDienThoai(String phone, String DTDD, String soDienThoai1, String soDienThoai2) {
        String soDienThoai = phone;
        if (!TextUtils.isEmpty(DTDD)) {
            soDienThoai += ", " + DTDD;
        }
        if (!TextUtils.isEmpty(soDienThoai1)) {
            soDienThoai += ", " + soDienThoai1;
        }
        if (!TextUtils.isEmpty(soDienThoai2)) {
            soDienThoai += ", " + soDienThoai2;
        }
        return soDienThoai;
    }

    public static String getMessageVolleyError(VolleyError error) {
        String message = "";
        String responseBody = null;
//        try {
//            if (error instanceof NetworkError) {
//                return AppConstants.Error_KhongCoInternet;
//            } else if (error instanceof ServerError) {
//                return AppConstants.Error_KetNoiServer;
//            } else if (error instanceof AuthFailureError) {
//                return AppConstants.Error_KhongCoInternet;
//            } else if (error instanceof ParseError) {
//                return AppConstants.Error_KetNoiServer;
//            } else if (error instanceof NoConnectionError) {
//                return AppConstants.Error_KhongCoInternet;
//            } else if (error instanceof TimeoutError) {
//                return AppConstants.Error_KetNoiServer;
//            }
//        if (//error instanceof ServerError
//                error instanceof AuthFailureError
////                    || error instanceof ParseError
//                        || error instanceof NoConnectionError
//                        || error instanceof TimeoutError) {
//            return AppConstants.Error_KetNoiServer;
//        }
        if (error instanceof AuthFailureError) {
            return AppConstants.Error_KetNoiServer + "\nError: AuthFailureError";
        } else if (error instanceof NoConnectionError) {
            return AppConstants.Error_KetNoiServer + "\nError: NoConnectionError";
        } else if (error instanceof TimeoutError) {
            return AppConstants.Error_KetNoiServer + "\nError: TimeoutError";
        }

//            if (error.toString().equals("com.android.volley.TimeoutError")
//                    || error.toString().equals("com.android.volley.NoConnectionError: java.net.NoRouteToHostException: No route to host")) {
//                return AppConstants.Error_KetNoiServer;
//            }
        try {
            if (error.networkResponse != null) {
                responseBody = new String(error.networkResponse.data, "utf-8");
            } else {
                message = error.getMessage();
                if (message.contains(AppConstants.URL_SERVER)) {
                    return AppConstants.Error_KetNoiServer;
                }
                return error.getMessage();
            }
        } catch (UnsupportedEncodingException e) {
            return "";
        }

        try {
            JSONObject jsonObject = new JSONObject(responseBody);
            message = jsonObject.getString("Message");
        } catch (JSONException e) {

            try {
                Document doc = Jsoup.parse(responseBody);
//                    Elements element = doc.getAllElements();
//                    for(Element element1: element)
//                    {
//                        Elements str = element1.getElementsByTag("head");
//                        for(Element el: str)
//                        {
//                            String title = el.attr("title");
//                            return title;
//                        }
//                    }
                return doc.title();
            } catch (Exception e1) {
                return e.getMessage();
            }
        }
//        } catch (JSONException e) {
//            return "";
//        }
        return message;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static Bitmap resizeBitmap(Bitmap image, int maxWidth, int maxHeight) {
        if (maxHeight > 0 && maxWidth > 0) {
            int width = image.getWidth();
            int height = image.getHeight();
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > ratioBitmap) {
                finalWidth = (int) ((float) maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float) maxWidth / ratioBitmap);
            }
            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
            return image;
        } else {
            return image;
        }
    }

    public static Double convertTextNumericToDouble(EditText et) {
        double doubleResult = 0;
        try {
            doubleResult = Double.parseDouble(et.getText().toString().replace(".", ""));
        } catch (NumberFormatException e) {
        }
        return doubleResult;
    }
}
