package org.androidtown.myfood.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.androidtown.myfood.R;
import org.androidtown.myfood.RestaurantActivity;
import org.androidtown.myfood.item.RestaurantItem;

import java.util.List;

/**
 * Created by Zimincom on 2016. 11. 2..
 */

public class BottomDrawerAdapter extends RecyclerView.Adapter<BottomDrawerAdapter.ViewHolder>{


    public static final String TAG ="MYADAPTER";

    private Context context;
    private List<RestaurantItem> items;
    private int itemLayout;
    private Activity activity;
    private int lastposition=-1;

    public BottomDrawerAdapter(Context context, List<RestaurantItem> items, int itemLayout, Activity activity){

        this.context = context;
        this.items = items;
        this.itemLayout = itemLayout;
        this.activity = activity;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout,parent,false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final RestaurantItem item = items.get(position);
        holder.text.setText(item.getName());

        Picasso
                .with(context)
                .load(item.imgSrc)
                .into(holder.image);

        setAnimation(holder.itemView,position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ImageView imageView = (ImageView)view.findViewById(R.id.image);
                ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,imageView,"shared_image");

                Intent intent = new Intent(context,RestaurantActivity.class);
                intent.putExtra("selectedItem",item);
                String resName=(String)holder.text.getText();
                Log.i("itemclick",resName);
                context.startActivity(intent,compat.toBundle());


            }
        });

    }

    //아이템 등장 애니메이션
    private void setAnimation(View viewAnimate,int position){
        if(position>lastposition){
            Animation animation = AnimationUtils.loadAnimation(context,android.R.anim.slide_in_left);
            viewAnimate.startAnimation(animation);
            lastposition=position;
        }
    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView image;
        public TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView)itemView.findViewById(R.id.image);
            text = (TextView)itemView.findViewById(R.id.text);

        }

    }
}
