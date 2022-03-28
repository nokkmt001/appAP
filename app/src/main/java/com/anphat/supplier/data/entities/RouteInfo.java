package com.anphat.supplier.data.entities;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class RouteInfo {
    private DistanceInfo distance;
    private DurationInfo duration;
    private String endAddress;
    private LatLng endLocation;
    private String startAddress;
    private LatLng startLocation;

    private List<LatLng> points;

    public DistanceInfo getDistance() {
        return distance;
    }

    public void setDistance(DistanceInfo distance) {
        this.distance = distance;
    }

    public DurationInfo getDuration() {
        return duration;
    }

    public void setDuration(DurationInfo duration) {
        this.duration = duration;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    public LatLng getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(LatLng endLocation) {
        this.endLocation = endLocation;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public LatLng getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(LatLng startLocation) {
        this.startLocation = startLocation;
    }

    public List<LatLng> getPoints() {
        return points;
    }

    public void setPoints(List<LatLng> points) {
        this.points = points;
    }
}
