package org.androidtown.myfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import org.androidtown.myfood.adapter.RestaurantListAdapter;
import org.androidtown.myfood.item.RestaurantItem;
import org.androidtown.myfood.remote.RemoteService;
import org.androidtown.myfood.remote.ServiceGenerator;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantListByKey extends AppCompatActivity {


    RestaurantListAdapter adapter;

    ArrayList<RestaurantItem> items = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_by_tag);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        Intent intent = getIntent();


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list_tag);
        adapter = new RestaurantListAdapter(this,items,R.layout.restaurant_item);

        recyclerView.setAdapter(adapter);

        selectListByTag(intent.getStringExtra("category"));

        StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy( StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }


    public  void selectListByTag(String keyword){
        RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);
        Call<ArrayList<RestaurantItem>> call = remoteService.getRestaurantByKey(keyword);
        Log.d("test",call.toString());
        final Intent intent = new Intent(getApplicationContext(),RestaurantListByKey.class);

        call.enqueue(new Callback<ArrayList<RestaurantItem>>() {
            @Override
            public void onResponse(Call<ArrayList<RestaurantItem>> call,
                                   Response<ArrayList<RestaurantItem>> response) {
                ArrayList<RestaurantItem> list = response.body();
                if(response.isSuccessful()) {
                    Log.d("test",list.toString());
                    adapter.setItems(list);
                }else{
                    Log.d("test","not success");
                }
                Log.d("test","send good");
            }

            @Override
            public void onFailure(Call<ArrayList<RestaurantItem>> call, Throwable t) {
                Log.d("test","no connection");
                t.printStackTrace();
            }
        });
    }

}
