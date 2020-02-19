package com.ems.dingdong.location;

import android.location.Location;

import androidx.annotation.NonNull;

import com.ems.dingdong.functions.mainhome.address.xacminhdiachi.XacMinhDiaChiFragment;
import com.mapbox.android.core.location.LocationEngineCallback;
import com.mapbox.android.core.location.LocationEngineResult;

import java.lang.ref.WeakReference;

public class LocationChangeListeningActivityLocationCallback implements LocationEngineCallback<LocationEngineResult> {

    private final WeakReference<XacMinhDiaChiFragment> activityWeakReference;

    LocationChangeListeningActivityLocationCallback(XacMinhDiaChiFragment activity) {
        this.activityWeakReference = new WeakReference<>(activity);
    }

    @Override
    public void onSuccess(LocationEngineResult result) {
        XacMinhDiaChiFragment activity = activityWeakReference.get();

        if (activity != null) {
            Location location = result.getLastLocation();

            if (location == null) {
                return;
            }

            // Create a Toast which displays the new location's coordinates
          /*  Toast.makeText(activity, String.format(activity.getString(R.string.new_location),
                    String.valueOf(result.getLastLocation().getLatitude()),
                    String.valueOf(result.getLastLocation().getLongitude())),
                    Toast.LENGTH_SHORT).show();*/

            // Pass the new location to the Maps SDK's LocationComponent
            if (activity.mapboxMap != null && result.getLastLocation() != null) {
                activity.mapboxMap.getLocationComponent().forceLocationUpdate(result.getLastLocation());
            }
        }
    }

    @Override
    public void onFailure(@NonNull Exception exception) {

    }
}
