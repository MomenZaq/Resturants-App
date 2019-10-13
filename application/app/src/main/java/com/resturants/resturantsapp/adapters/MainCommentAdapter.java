package com.resturants.resturantsapp.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.resturants.resturantsapp.R;
import com.resturants.resturantsapp.model.RateModel;

import java.util.List;

public class MainCommentAdapter extends RecyclerView.Adapter<MainCommentAdapter.OrderHolder> {
    private List<RateModel> list;
    private Activity activity;
    private int lastPosition = -1;

    public MainCommentAdapter(List<RateModel> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    public void setList(List<RateModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_comment, parent, false);
        return new OrderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderHolder holder, final int position) {
        RateModel itemModel = list.get(position);
        holder.txvPersonName.setText(itemModel.getUserName());
        holder.txvComment.setText(itemModel.getComment());


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

        private TextView txvPersonName;
        private TextView txvComment;


        OrderHolder(View v) {
            super(v);



            txvPersonName = (TextView) v.findViewById(R.id.txv_person_name);
            txvComment = (TextView) v.findViewById(R.id.txv_comment);



        }

        @Override
        public void onClick(View view) {

        }
    }


}
