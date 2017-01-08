package org.androidtown.myfood;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.androidtown.myfood.item.RestaurantItem;

import static org.androidtown.myfood.R.id.map;

/**
 * Created by Zimincom on 2016. 11. 6..
 */

public class RestaurantInfoFragment extends Fragment implements OnMapReadyCallback {


    RestaurantItem restaurantItem;
    RatingBar ratingBar;
    double raiting;
    float latitude;
    float longitude;
    TextView restaurant_summary;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.restaurant_info,container,false);
        restaurantItem = (RestaurantItem)getArguments().getSerializable("restaurantItem");

        Log.d("location",String.valueOf(restaurantItem.latitude));
        restaurant_summary = (TextView)rootView.findViewById(R.id.restaurant_summary);

        restaurant_summary.setText(restaurantItem.summary);
        ImageView transparent = (ImageView)rootView.findViewById(R.id.imagetrans);
        final NestedScrollView nestedScrollView = (NestedScrollView)rootView.findViewById(R.id.info_page) ;

        FragmentManager fm = getChildFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment)getFragmentManager().findFragmentById(map);
        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(map, mapFragment).commit();
        }


        mapFragment.getMapAsync(this);

        ratingBar = (RatingBar)rootView.findViewById(R.id.rating);
        ratingBar.setNumStars(Math.round(restaurantItem.rating));
        ratingBar.setRating(restaurantItem.rating);


        transparent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        nestedScrollView.requestDisallowInterceptTouchEvent(true);
                        // Disable touch on transparent view
                        return false;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        nestedScrollView.requestDisallowInterceptTouchEvent(false);
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        nestedScrollView.requestDisallowInterceptTouchEvent(true);
                        return false;

                    default:
                        return true;
                }
            }
        });

        return rootView;

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {


        //LatLng latLng = new LatLng(restaurantItem.getLatitude(), restaurantItem.getLongitude());


        Marker somePlace = googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(restaurantItem.getLatitude(), restaurantItem.getLongitude()))
                .title(restaurantItem.getName())
                .snippet("hello")
        );
        somePlace.showInfoWindow();

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                getActivity();

            }
        });

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(restaurantItem.getLatitude(), restaurantItem.getLongitude()), 15));





    }


}
