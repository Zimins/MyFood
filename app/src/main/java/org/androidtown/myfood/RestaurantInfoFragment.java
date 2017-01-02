package org.androidtown.myfood;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static org.androidtown.myfood.R.id.map;

/**
 * Created by Zimincom on 2016. 11. 6..
 */

public class RestaurantInfoFragment extends Fragment implements OnMapReadyCallback {

    RatingBar ratingBar;
    int rating =1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.restaurant_info,container,false);


        FragmentManager fm = getChildFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment)getFragmentManager().findFragmentById(map);
        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);


        ratingBar = (RatingBar)rootView.findViewById(R.id.rating);


        return rootView;

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng latLng = new LatLng(37.401962, 127.107140);


        Marker somePlace = googleMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title("밥먹는 곳")
                .snippet("3000won")
        );
        somePlace.showInfoWindow();

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                getActivity();

            }
        });

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));




    }


}
