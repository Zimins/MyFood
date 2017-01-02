package org.androidtown.myfood;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.androidtown.myfood.adapter.RestaurantListAdapter;
import org.androidtown.myfood.item.RestaurantItem;
import org.androidtown.myfood.remote.RemoteService;
import org.androidtown.myfood.remote.ServiceGenerator;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    RestaurantListAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<RestaurantItem> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
            }
        });


        final EditText editText =(EditText)findViewById(R.id.food_search);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                switch (actionId) {
                    case EditorInfo.IME_ACTION_SEARCH:
                        searchBy(editText.getText().toString());
                        editText.setText("");
                        Toast.makeText(getApplicationContext(), "검색", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), "기본", Toast.LENGTH_LONG).show();
                        return false;
                }
                return true;
            }
        });


        recyclerView = (RecyclerView) findViewById(R.id.search_result_list);

        Log.d("oncreate",recyclerView.toString());
        adapter = new RestaurantListAdapter(this,items,R.layout.restaurant_item);
        recyclerView.setAdapter(adapter);

//        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy( StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    private void searchBy(String keyword){
        RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);


        Call<ArrayList<RestaurantItem>> call = remoteService.getRestaurantByKey(keyword);

        Log.d("callstring",call.toString());


        call.enqueue(new Callback<ArrayList<RestaurantItem>>() {
            @Override
            public void onResponse(Call<ArrayList<RestaurantItem>> call, Response<ArrayList<RestaurantItem>> response) {

                items = response.body();

                if (response.isSuccessful()) {

                    adapter.setItems(items);
                } else {
                    Log.d("onresponse", "not success");
                }

            }

            @Override
            public void onFailure(Call<ArrayList<RestaurantItem>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getApplicationContext(),"send faild",Toast.LENGTH_LONG).show();
            }
        });
    }

}
