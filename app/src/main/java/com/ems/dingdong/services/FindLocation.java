package com.ems.dingdong.services;

import static android.content.Context.LOCATION_SERVICE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

public class FindLocation {
    private LocationManager locManager;
    private LocationListener locListener;
    private Location mobileLocation;
    private String provider;
    @SuppressLint("MissingPermission")
    public FindLocation(Context ctx){
        locManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
        locListener = new LocationListener() {
            @Override
            public void onStatusChanged(String provider, int status,
                                        Bundle extras) {
            }
            @Override
            public void onProviderEnabled(String provider) {
            }
            @Override
            public void onProviderDisabled(String provider) {
            }
            @Override
            public void onLocationChanged(Location location) {
                System.out.println("mobile location is in listener="+location);
                mobileLocation = location;
            }
        };
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locListener);
        if (mobileLocation != null) {
            locManager.removeUpdates(locListener);
            String londitude = "Londitude: " + mobileLocation.getLongitude();
            String latitude = "Latitude: " + mobileLocation.getLatitude();
            String altitiude = "Altitiude: " + mobileLocation.getAltitude();
            String accuracy = "Accuracy: " + mobileLocation.getAccuracy();
            String time = "Time: " + mobileLocation.getTime();
            Toast.makeText(ctx, "Latitude is = "+latitude +"Longitude is ="+londitude, Toast.LENGTH_LONG).show();
        } else {
            System.out.println("in find location 4");
            Toast.makeText(ctx, "Sorry location is not determined", Toast.LENGTH_LONG).show();
        }
    }


}


