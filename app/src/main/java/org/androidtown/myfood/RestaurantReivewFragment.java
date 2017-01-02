package org.androidtown.myfood;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.androidtown.myfood.adapter.ReviewAdapter;
import org.androidtown.myfood.item.ReviewItem;
import org.androidtown.myfood.remote.RemoteService;
import org.androidtown.myfood.remote.ServiceGenerator;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Zimincom on 2016. 11. 6..
 */

public class RestaurantReivewFragment extends Fragment {

    int restaurantId;
    EditText name;
    EditText review;
    ArrayList<ReviewItem> reviewItems = new ArrayList<>();
    RecyclerView recyclerView;
    ReviewAdapter adapter;
    ReviewItem reviewItem;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.restaurant_review,container,false);


        name = (EditText)rootView.findViewById(R.id.name_input);
        review =(EditText)rootView.findViewById(R.id.review_input);
        Log.d("reiveOncreate",getArguments().toString());
        restaurantId = getArguments().getInt("id");
        reviewItem = new ReviewItem();
        setReviewItems(restaurantId);


        recyclerView = (RecyclerView) rootView.findViewById(R.id.review_list);
        adapter = new ReviewAdapter(rootView.getContext(), reviewItems, R.layout.review_item);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(rootView.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        Button button = (Button)rootView.findViewById(R.id.submit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reviewItem.restaurant_Id = restaurantId;
                reviewItem.name = name.getText().toString();
                reviewItem.text = review.getText().toString();

                RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);

                Log.d("reviewItem", reviewItem.toString());
                Call<String> call = remoteService.insertReview(reviewItem);

                Log.d("callstring",call.toString());
                call.enqueue(new Callback<String>() {

                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        setReviewItems(restaurantId);
                        Toast.makeText(rootView.getContext(),"send done",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(rootView.getContext(),"send failed",Toast.LENGTH_LONG).show();
                    }
                });

                name.setText("");
                review.setText("");

            }

        });
        return rootView;
    }


    public void setReviewItems(int id){

        RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);
        Call<ArrayList<ReviewItem>> call = remoteService.getReviewById(id);

        call.enqueue(new Callback<ArrayList<ReviewItem>>() {
            @Override
                public void onResponse(Call<ArrayList<ReviewItem>> call, Response<ArrayList<ReviewItem>> response) {

                if(response.isSuccessful()) {
                    Log.d("setReviewItems",response.body().toString());
                    reviewItems = response.body();
                    adapter.setItems(reviewItems);
                    recyclerView.setAdapter(adapter);

                    Log.d("setReiew","success");
                }else{
                    Log.d("setReiew","not success");
                }
                Log.d("setReview","send good");
            }

            @Override
            public void onFailure(Call<ArrayList<ReviewItem>> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }


}
