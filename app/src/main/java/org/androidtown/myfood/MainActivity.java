package org.androidtown.myfood;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.stetho.Stetho;
import com.google.android.gms.common.api.GoogleApiClient;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.androidtown.myfood.adapter.BottomDrawerAdapter;
import org.androidtown.myfood.adapter.ViewPagerAdapter;
import org.androidtown.myfood.item.LocationItem;
import org.androidtown.myfood.item.RestaurantItem;
import org.androidtown.myfood.remote.RemoteService;
import org.androidtown.myfood.remote.ServiceGenerator;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.androidtown.myfood.R.id.content_in;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private SlidingUpPanelLayout mLayout;

    ArrayList<RestaurantItem> nearRestaurantItems = new ArrayList<RestaurantItem>();
    Handler handler = new Handler();
    Timer timer;
    ViewPager pager;
    Bitmap profileBitmap;

    int page = 0;

    public GoogleApiClient mGoogleApiClient;
    double userLatitude;
    double userLongtitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);


        //toolbar code
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mLayout = (SlidingUpPanelLayout) findViewById(content_in);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //navigation code
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setProfileBitmap();


        getPermissions();

        //buildGoogleApiClient();

        //hot pager
        pager = (ViewPager) findViewById(R.id.hot_pager);
        ViewPagerAdapter hotAdapter = new ViewPagerAdapter(this);
        pager.setAdapter(hotAdapter);
        pageSwitcher(3);


        //검색바 트랜지션
        TransitionInflater tf = TransitionInflater.from(this);
        Transition t = tf.inflateTransition(R.transition.search_show);

        getWindow().setSharedElementExitTransition(t);
        EditText editText = (EditText)findViewById(R.id.food_search);


        //새 액티비티 등장시 포커스 맞출것.
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText et = (EditText) findViewById(R.id.food_search);

                ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this,et,"shared_editText");

                startActivity(new Intent(MainActivity.this,SearchActivity.class),compat.toBundle());
            }
        });



        startLocationService();

        createBotItemList();

        //initBottomDrawer();

    }



    //bot item init
    private void createBotItemList() {


        RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);
        Call<ArrayList<RestaurantItem>> call = remoteService.getListDistanceOrder(LocationItem.knownLatitude, LocationItem.knownLongitude);

        ArrayList<RestaurantItem> list = nearRestaurantItems;

        call.enqueue(new Callback<ArrayList<RestaurantItem>>() {


            @Override
            public void onResponse(Call<ArrayList<RestaurantItem>> call, Response<ArrayList<RestaurantItem>> response) {
                nearRestaurantItems = response.body();
                initBottomDrawer();
                return;
            }

            @Override
            public void onFailure(Call<ArrayList<RestaurantItem>> call, Throwable t) {
                t.printStackTrace();
            }


        });

//                items.add(new RestaurantItem(1,"중화민족", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcReuHcbXLNSxyyfUyQHgpfkXgy04vRsF354l007A3orC26cR3WIqg"));
//                items.add(new RestaurantItem(2,"후쿠함바그", "http://cfile2.uf.tistory.com/image/2267D637554E462606FA65"));
//                items.add(new RestaurantItem(3,"베라베라","http://webzinepro.com/_upload/content/images/1348198806_baskin-logo.jpg"));
//                items.add(new RestaurantItem(4,"정글포차","http://foodmoa.co.kr/data/file/food/1410/BRT0264211_1738550_FFpKw1JwzKq.jpg"));

    }



    @Override
    public void onBackPressed() {
        //하드웨어 버튼 동작
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else if(mLayout != null &&
                (mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED ||
                        mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
            mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        }else{
            Snackbar askQuit = Snackbar.make(findViewById(R.id.content_in),
                    "종료하시겠습니까?",Snackbar.LENGTH_LONG).setAction("그래꺼져",new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

            askQuit.show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //오른쪽 상단 메뉴의 레이아웃을 인플레이션
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //옵션 선택
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(getApplicationContext(),SettingActivity.class);
            startActivity(intent);// 추후 결과 받아오기로 바꾸어야 할듯 혹으 메서드로 처리할 것

            return true;
        }else if(id==R.id.action_info){
            Toast.makeText(getApplicationContext(),"버전 1.0.0", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        String name = item.getTitle().toString();

        Intent intent = new Intent(getApplicationContext(),RestaurantListByTag.class);

         if(id == R.id.category_korea) {
             intent.putExtra("category",name);
             startActivity(intent);
         }else if (id == R.id.category_japan) {
             intent.putExtra("category",name);
             startActivity(intent);
         }else if (id == R.id.category_china) {
             intent.putExtra("category",name);
             startActivity(intent);
         }else if (id == R.id.category_west) {
             intent.putExtra("category",name);
             startActivity(intent);
         }else if (id == R.id.category_italy) {
             intent.putExtra("category",name);
             startActivity(intent);
         }else if (id == R.id.category_cafe) {
             intent.putExtra("category",name);
             startActivity(intent);
         }else if (id == R.id.category_icecream) {
             intent.putExtra("category",name);
             startActivity(intent);
         }else if (id == R.id.category_etc) {
             intent.putExtra("category",name);
             startActivity(intent);
         }else if(id==R.id.account){
             intent = new Intent(getApplicationContext(),ProfilePage.class);
             startActivity(intent);
         }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void pageSwitcher(int seconds) {
        timer = new Timer();
        timer.scheduleAtFixedRate(new RemindTask(), 0, seconds * 1000);
    }



    class RemindTask extends TimerTask {

        @Override
        public void run() {

            // 페이지 자동 넘김
            // As the TimerTask run on a seprate thread from UI thread we have
            // to call runOnUiThread to do work on UI thread.
            runOnUiThread(new Runnable() {
                public void run() {

                    if (page > 4) { // In my case the number of pages are 5
                        timer.cancel();
                        // Showing a toast for just testing purpose
                        Toast.makeText(getApplicationContext(), "Timer stoped",
                                Toast.LENGTH_LONG).show();
                    } else {
                        pager.setCurrentItem(page++);
                    }
                }
            });

        }
    }

    public void setProfileBitmap(){
        Profile profile = Profile.getCurrentProfile();
        final String link = profile.getProfilePictureUri(400, 400).toString();

        Log.i("profile","b thread");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    URL url = new URL(link);
                    InputStream is =url.openStream();
                    profileBitmap= BitmapFactory.decodeStream(is);
                    Log.i("profile","get");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        });

        thread.start();
        try{
            thread.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        //네이게이션 뷰 참조 , 프로필 원형으로 변화
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);
        ImageView profileImg = (ImageView)hView.findViewById(R.id.profile_image);
        RoundedBitmapDrawable rProfile = RoundedBitmapDrawableFactory.create(getResources(),profileBitmap);
        rProfile.setCornerRadius(Math.max(profileBitmap.getWidth(), profileBitmap.getHeight()) / 2.0f);
        rProfile.setAntiAlias(true);
        profileImg.setImageDrawable(rProfile);
        TextView idView = (TextView)hView.findViewById(R.id.user_id);
        idView.setText(profile.getName());

    }



    public void getPermissions(){

        int callPermissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);

        if(callPermissionCheck == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},1);
        }
    }

    public void initBottomDrawer(){

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.bot_list);
        BottomDrawerAdapter adapter = new BottomDrawerAdapter(this, nearRestaurantItems, R.layout.bottom_item,MainActivity.this);
        recyclerView.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }


    public void startLocationService(){
        LocationManager locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        Location location = null;
        int result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

         if (result == PackageManager.PERMISSION_GRANTED) {
             Log.d("location","permisson granted");
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
         }

        if (location != null) {
            Log.d("location","location is not null");
            LocationItem.knownLatitude = location.getLatitude();
            LocationItem.knownLongitude = location.getLongitude();

        } else {
            //서울 설정
            LocationItem.knownLatitude = 37.566229;
            LocationItem.knownLongitude = 126.977689;
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


}
