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

import org.androidtown.myfood.adapter.ListAdapter;
import org.androidtown.myfood.item.ListItem;
import org.androidtown.myfood.item.ReviewItem;
import org.androidtown.myfood.remote.RemoteService;
import org.androidtown.myfood.remote.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Zimincom on 2016. 11. 6..
 */

public class RestaurantReivewFragment extends Fragment {

    EditText name;
    EditText review;
    ReviewItem reviewItem;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.restaurant_review,container,false);


        name = (EditText)rootView.findViewById(R.id.name_input);
        review =(EditText)rootView.findViewById(R.id.review_input);
        reviewItem = new ReviewItem();

        //bottom grid view

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.review_list);
        ListAdapter adapter = new ListAdapter(rootView.getContext(), createItemList(), R.layout.categ_item);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(rootView.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        Button button = (Button)rootView.findViewById(R.id.submit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reviewItem.restauranId = 1;
                reviewItem.name = name.getText().toString();
                reviewItem.text = review.getText().toString();

                RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);

                Log.d("reviewItem", reviewItem.toString());
                Call<String> call = remoteService.insertReview(reviewItem);

                Log.d("callstring",call.toString());
                call.enqueue(new Callback<String>() {

                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

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


    private List<ListItem> createItemList() {
        List<ListItem> items = new ArrayList<ListItem>();
        for (int i = 0; i < 20; i++) {
            items.add(new ListItem("아이템 " + i, R.mipmap.ic_launcher));
        }
        return items;
    }



}
