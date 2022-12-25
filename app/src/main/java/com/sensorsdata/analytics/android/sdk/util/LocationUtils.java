package com.sensorsdata.analytics.android.sdk.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import java.util.List;
import java.util.Locale;

/* loaded from: classes3.dex */
public class LocationUtils {
    private static final float METER_POSITION = 0.0f;
    private static final long REFRESH_TIME = 5000;
    private static LocationListener listener = new MyLocationListener();
    private static ILocationListener mLocationListener;

    /* loaded from: classes3.dex */
    public interface ILocationListener {
        void onSuccessLocation(Location location);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class MyLocationListener implements LocationListener {
        @Override // android.location.LocationListener
        public void onProviderDisabled(String str) {
        }

        @Override // android.location.LocationListener
        public void onProviderEnabled(String str) {
        }

        @Override // android.location.LocationListener
        public void onStatusChanged(String str, int i, Bundle bundle) {
        }

        private MyLocationListener() {
        }

        @Override // android.location.LocationListener
        public void onLocationChanged(Location location) {
            if (LocationUtils.mLocationListener != null) {
                LocationUtils.mLocationListener.onSuccessLocation(location);
            }
        }
    }

    @SuppressLint({"MissingPermission"})
    public static List<Address> getNetWorkLocation(Context context) {
        LocationManager locationManager = getLocationManager(context);
        return getAddress(context, locationManager.isProviderEnabled("network") ? locationManager.getLastKnownLocation("network") : null);
    }

    @SuppressLint({"MissingPermission"})
    public static List<Address> getBestLocation(Context context, Criteria criteria) {
        LocationManager locationManager = getLocationManager(context);
        if (criteria == null) {
            criteria = new Criteria();
        }
        String bestProvider = locationManager.getBestProvider(criteria, true);
        if (TextUtils.isEmpty(bestProvider)) {
            return getNetWorkLocation(context);
        }
        return getAddress(context, locationManager.getLastKnownLocation(bestProvider));
    }

    public static void addLocationListener(Context context, String str, ILocationListener iLocationListener) {
        addLocationListener(context, str, REFRESH_TIME, 0.0f, iLocationListener);
    }

    @SuppressLint({"MissingPermission"})
    public static void addLocationListener(Context context, String str, long j, float f, ILocationListener iLocationListener) {
        if (iLocationListener != null) {
            mLocationListener = iLocationListener;
        }
        if (listener == null) {
            listener = new MyLocationListener();
        }
        getLocationManager(context).requestLocationUpdates(str, j, f, listener);
    }

    @SuppressLint({"MissingPermission"})
    public static void unRegisterListener(Context context) {
        if (listener != null) {
            getLocationManager(context).removeUpdates(listener);
        }
    }

    private static LocationManager getLocationManager(Context context) {
        return (LocationManager) context.getSystemService("location");
    }

    public static List<Address> getAddress(Context context, Location location) {
        if (location != null) {
            try {
                return new Geocoder(context, Locale.getDefault()).getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }
}
