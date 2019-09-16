package com.resturants.resturantsapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.resturants.resturantsapp.R;
import com.resturants.resturantsapp.model.ItemModel;

import java.util.ArrayList;
import java.util.List;

public class MainItemAdapter extends RecyclerView.Adapter<MainItemAdapter.OrderHolder> {
    private List<ItemModel> list;
    private Activity activity;
    private int lastPosition=-1;

    public MainItemAdapter(List<ItemModel> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        return new OrderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderHolder holder, final int position) {
        ItemModel itemModel = list.get(position);
        holder.txvItemName.setText(itemModel.getItemName());
        holder.txvItemDistance.setText(itemModel.getItemDistance() + " م");
        holder.txvItemArea.setText(itemModel.getItemDistance());


        //create simple animation
        Animation animation = AnimationUtils.loadAnimation(activity,
                (position > lastPosition) ? R.anim.upfrombottom
                        : R.anim.downfromtop);
        holder.itemView.startAnimation(animation);
        lastPosition = position;
    }


    @Override
    public int getItemCount() {
//        Toast.makeText(context, ""+mOrders.size(), Toast.LENGTH_SHORT).show();
        return list.size();
    }


    public class OrderHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txvItemName;
        private TextView txvItemDistance;
        private TextView txvItemArea;

        OrderHolder(View v) {
            super(v);


            txvItemName = (TextView) v.findViewById(R.id.txv_item_name);
            txvItemDistance = (TextView) v.findViewById(R.id.txv_item_distance);
            txvItemArea = (TextView) v.findViewById(R.id.txv_item_area);


        }

        @Override
        public void onClick(View view) {

        }
    }


}
