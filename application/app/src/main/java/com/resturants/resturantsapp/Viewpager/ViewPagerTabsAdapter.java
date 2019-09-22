package com.resturants.resturantsapp.Viewpager;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.resturants.resturantsapp.R;
import com.resturants.resturantsapp.utils.Methods;

import java.util.HashMap;
import java.util.List;


public class ViewPagerTabsAdapter extends RecyclerView.Adapter<ViewPagerTabsAdapter.OrderHolder> {
    private Activity context;
    private List<String> list;
    private int selectedTab = 0;
    private int numberOfTried = 0;
    private TabSelectInterface tabSelectInterface;

    ViewPagerTabsAdapter(Activity context, List<String> list, TabSelectInterface tabSelectInterface, RecyclerView recyclerViewTabs) {
        this.context = context;
        this.list = list;
        this.tabSelectInterface = tabSelectInterface;
    }

    @Nullable
    @Override
    public OrderHolder onCreateViewHolder(@Nullable ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_tab, parent, false);


        //set the tab width
        int newTabMinWidth = Methods.getScreenWidth(context) / list.size();
        System.out.println("THEtabMinWidth: " + newTabMinWidth);
            rowView.getLayoutParams().width =  newTabMinWidth ;



        return new OrderHolder(rowView);
    }

    @Override
    public void onBindViewHolder(@Nullable OrderHolder holder, int position) {

        if (holder == null) {
            return;
        }
        holder.name.setText(list.get(position));


        //change the selected tab text color
        if (selectedTab == position) {
            holder.name.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
            holder.selectedView.setVisibility(View.VISIBLE);
        } else {
            holder.name.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
            holder.selectedView.setVisibility(View.INVISIBLE);
        }


        if (!holder.itemView.hasOnClickListeners()) {
            holder.itemView.setOnClickListener(view -> {
                if (holder.getAdapterPosition() <= -1) {
                    return;
                }
                System.out.println("ONCLICKNOW: " + holder.getAdapterPosition());
                tabSelectInterface.onTabSelected(holder.getAdapterPosition());
            });
        }
        System.out.println("CURRENTPOS: " + position);
        System.out.println("CURRENTPOS3: " + numberOfTried);


    }




    @Override
    public int getItemCount() {
        return list.size();
    }


    void setSelectedTab(int selected) {
        int currentSelectedTab = selectedTab;
        if (currentSelectedTab != selected) {
            selectedTab = selected;

            notifyDataSetChanged();
        }

    }


    public class OrderHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView name;
        private View selectedView;


        OrderHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView);
            selectedView = itemView.findViewById(R.id.selected_view);

        }

        @Override
        public void onClick(View view) {

        }
    }

}
