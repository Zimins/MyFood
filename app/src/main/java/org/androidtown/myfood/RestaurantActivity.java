package org.androidtown.myfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.maps.GoogleMap;

public class RestaurantActivity extends AppCompatActivity {


    ViewPager viewPager;
    TabLayout tabLayout;
    GoogleMap googleMap;

    Bundle bundle = new Bundle();


    BottomSheetBehavior behavior;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant);

        Intent intent = getIntent();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(intent.getExtras().toString());


        bundle.putInt("id", 1);

        viewPager = (ViewPager)findViewById(R.id.viewpager);
        tabLayout = (TabLayout)findViewById(R.id.tabs);
        Adapter adapter = new Adapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


        ImageView imageView = (ImageView)findViewById(R.id.res_image);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public class Adapter extends FragmentStatePagerAdapter {

        public Adapter(FragmentManager fm){
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            Fragment f = null;
            if(position==0){

                f=new RestInfoFragment();

            }else if(position==1){
                f=new RestReivewFragment();
            }
            return f;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String name = null;
            if(position==0){
                name="소개";
            }else if(position==1){
                name="리뷰";
            }

            return name;
        }
    }
}



