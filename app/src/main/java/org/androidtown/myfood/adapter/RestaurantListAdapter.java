package org.androidtown.myfood.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.androidtown.myfood.R;
import org.androidtown.myfood.RestaurantActivity;
import org.androidtown.myfood.item.RestaurantItem;

import java.util.ArrayList;

/**
 * Created by Zimincom on 2016. 11. 8..
 */

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.ViewHolder>{

    private Context context;
    private ArrayList<RestaurantItem> items;
    private int itemLayout;


    public RestaurantListAdapter(Context context, int itemLayout) {
        this.context = context;
        this.itemLayout = itemLayout;
    }


    public RestaurantListAdapter(Context context, ArrayList<RestaurantItem> items, int itemLayout) {
        this.context = context;
        this.items =items;
        this.itemLayout = itemLayout;
        Log.d("test","constructor");
    }

    public void setItems(ArrayList<RestaurantItem> restaurantItems){
        this.items = restaurantItems;
        notifyDataSetChanged();
        Log.d("test",items.toString());
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        Log.d("test","oncreateViewHoler");
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        Log.d("test","itemset");
        final RestaurantItem item = items.get(position);

        holder.image.setImageResource(R.drawable.tangsu);

        Picasso
                .with(context)
                .load(item.imgSrc)
                .into(holder.image);

        holder.text.setText(item.name);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, RestaurantActivity.class);
                Toast.makeText(v.getContext(),holder.text.getText().toString(),Toast.LENGTH_LONG).show();
                Log.i("items", String.valueOf(items.get(position).id));

                intent.putExtra("selectedItem",item);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        Log.d("test","itemcount");
        return this.items.size();
    }




    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            text = (TextView) itemView.findViewById(R.id.text);
            Log.d("test","find views");
        }


    }

}


