package org.androidtown.myfood;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.androidtown.myfood.item.RestaurantItem;

public class RestaurantActivity extends AppCompatActivity {


    ViewPager viewPager;
    TabLayout tabLayout;
    RestaurantItem restaurantItem;
    Bundle bundle = new Bundle();
    String imgSrc;
    String name;
    String contact;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant);

        Intent intent = getIntent();

        //int raiting;

        restaurantItem = (RestaurantItem)intent.getSerializableExtra("selectedItem");
        name =restaurantItem.name;
        imgSrc = restaurantItem.imgSrc;
        contact = restaurantItem.contact;

        //raiting = restaurantItem.raiting;


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(name);


        Log.d("ResActOncreate",String.valueOf(restaurantItem.id));


        viewPager = (ViewPager)findViewById(R.id.viewpager);
        tabLayout = (TabLayout)findViewById(R.id.tabs);
        Adapter adapter = new Adapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


        ImageView imageView = (ImageView)findViewById(R.id.res_image);
        Log.d("resImg",imgSrc);
        Picasso
                .with(getApplicationContext())
                .load(imgSrc)
                .resize(2000,1100)
                .centerCrop()
                .into(imageView);


        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.call_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("tel:"+contact);
                Log.d("call",uri.toString());
                Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(intent);
            }
        });
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

                f=new RestaurantInfoFragment();

            }else if(position==1){
                bundle.putInt("id",restaurantItem.id);
                f=new RestaurantReivewFragment();
                f.setArguments(bundle);
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



