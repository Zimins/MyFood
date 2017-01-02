package org.androidtown.myfood.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.androidtown.myfood.item.ListItem;
import org.androidtown.myfood.R;

import java.util.List;

/**
 * Created by Zimincom on 2016. 11. 8..
 */

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.ViewHolder>{



    private static final String TAG = "RestaurantListAdapter";
    private Context context; private List<ListItem> items; private int itemLayout;


    public RestaurantListAdapter(Context context, List<ListItem> items, int itemLayout) {
        this.context = context;
        this.items = items;
        this.itemLayout = itemLayout;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.text.setText(items.get(position).getText());
        holder.image.setImageResource(items.get(position).getImage());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
             public void onClick(View view) {
            Toast.makeText(context, "아이템 클릭" + position, Toast.LENGTH_SHORT).show();
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() { @Override
        public boolean onLongClick(View v) {
            Toast.makeText(context, "아이템 롱 클릭" + position, Toast.LENGTH_SHORT).show();
            return false;
        }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView text;
        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            text = (TextView) itemView.findViewById(R.id.text);
        }
    }
}


