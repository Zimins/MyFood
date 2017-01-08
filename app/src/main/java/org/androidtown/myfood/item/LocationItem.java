package org.androidtown.myfood.item;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Zimincom on 2017. 1. 2..
 */

public class LocationItem {

    public static double knownLatitude;
    public static double knownLongitude;

    public static LatLng getKnwonLocation() {
        if (knownLatitude == 0 || knownLongitude == 0) {
            return new LatLng(37.566229, 126.977689);
        } else {
            return new LatLng(knownLatitude, knownLongitude);
        }
    }
}
