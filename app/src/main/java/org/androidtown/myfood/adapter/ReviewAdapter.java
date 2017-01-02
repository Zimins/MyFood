package org.androidtown.myfood.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.androidtown.myfood.R;
import org.androidtown.myfood.item.ReviewItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zimincom on 2016. 11. 8..
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder>{


    private Context context;
    private List<ReviewItem> items;
    private int itemLayout;


    public ReviewAdapter(Context context, List<ReviewItem> items, int itemLayout) {
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
        holder.name.setText(items.get(position).name+"님");
        holder.text.setText(items.get(position).text);

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

    public void setItems(ArrayList<ReviewItem> items){
        this.items = items;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView text;
        public ViewHolder(View itemView) {
            super(itemView);
            name= (TextView) itemView.findViewById(R.id.name);
            text = (TextView) itemView.findViewById(R.id.text);
        }
    }
}


