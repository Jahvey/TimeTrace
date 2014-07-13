package com.atomu.timetrace.location;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * Created by Atomu on 2014/7/10.
 * this class request gps service from system
 */
public class LocationInfoProvider {
    final static public String _LOCATION_PROVIDER = LocationManager.GPS_PROVIDER;
    final static public long _LOCATION_MIN_TIME = 10 * 60 * 1000;
    final static public float _LOCATION_MIN_DISTANCE = 10;

    private Context context;

    private LocationManager locationManager = null;
    private Location location = null;
    private LocationInfo locationInfo = null;

    public LocationInfoProvider(Context c) {
        setContext(c);

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationInfoProvider._LOCATION_PROVIDER,
                LocationInfoProvider._LOCATION_MIN_TIME, LocationInfoProvider._LOCATION_MIN_DISTANCE,
                new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        LocationInfoProvider.this.location = location;
                        updateLocationInfo();
                    }

                    @Override
                    public void onStatusChanged(String s, int i, Bundle bundle) {

                    }

                    @Override
                    public void onProviderEnabled(String s) {
                        location = locationManager.getLastKnownLocation(s);
                        updateLocationInfo();
                    }

                    @Override
                    public void onProviderDisabled(String s) {

                    }
                }
        );

        locationInfo = new LocationInfo();
    }

    public LocationInfo getLocationInfo() {
        return locationInfo;
    }

    public LocationInfo updateLocationInfo() {
        if (location != null) {
            locationInfo.setAccuracy(location.getAccuracy());
            locationInfo.setAltitude(location.getAltitude());
            locationInfo.setBearing(location.getBearing());
            locationInfo.setLatitude(location.getLatitude());
            locationInfo.setLongitude(location.getLongitude());
            locationInfo.setSpeed(location.getSpeed());
        }

        return locationInfo;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
