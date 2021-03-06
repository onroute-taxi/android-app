package com.onroute.android.data.models;


import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;

import com.google.gson.annotations.Expose;
import com.onroute.android.data.models.base.BaseModel;

import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;


/**
 * This class will represent a tablet.
 * <p/>
 * TODO: add updates for battery %
 */
public class TabletModel extends BaseModel {
    @Expose @Getter public TabletStatus status = new TabletStatus();
    @Expose @Setter String device;
    @Expose @Setter String mac;
    @Expose @Setter String model;
    @Expose @Setter String product;
    @Expose @Setter int sdk_version;


    @Inject
    public TabletModel(Context context) {
        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();

        device = Build.DEVICE;
        product = Build.PRODUCT;
        model = Build.MODEL;
        sdk_version = Build.VERSION.SDK_INT;
        mac = info.getMacAddress();
    }


    public void initializeLocationListener(Context context) {
        LocationManager locationManager = (LocationManager)
                context.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new TabletLocationListener();
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 5000, 0, locationListener);
    }


    public class TabletStatus {
        @Expose @Setter @Getter double latitude;
        @Expose @Setter @Getter double longitude;
        @Expose @Setter double battery_percent;
        @Expose @Setter boolean is_charging;
    }


    private class TabletLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location loc) {
            status.setLatitude(loc.getLatitude());
            status.setLongitude(loc.getLongitude());

            // Update the map in the journey fragment.. remove this in production
//            JourneyFragment.addMarker(loc.getLatitude(), loc.getLongitude());
        }


        @Override
        public void onProviderDisabled(String provider) {
        }


        @Override
        public void onProviderEnabled(String provider) {
        }


        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }
}