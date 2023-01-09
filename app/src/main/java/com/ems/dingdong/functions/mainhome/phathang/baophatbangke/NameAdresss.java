package com.ems.dingdong.functions.mainhome.phathang.baophatbangke;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NameAdresss {
    public String getNameAderss(Context context, double lat, double lon) {
        String name = "";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocation(lat, lon, 10);
            if (list.size() > 0) {
                for (Address address : list) {
                    if (address.getLocality() != null && address.getLocality().length() != 0) {
                        name = address.getLocality();
                        break;
                    }
                }
            }
        } catch (Exception e) {
        }
        return name;
    }
}
