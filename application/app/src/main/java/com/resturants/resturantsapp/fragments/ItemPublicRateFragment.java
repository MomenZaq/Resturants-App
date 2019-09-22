package com.resturants.resturantsapp.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.resturants.resturantsapp.R;

import java.util.ArrayList;
import java.util.List;

import co.lujun.myandroidtagview2.TagContainerLayout;

public class ItemPublicRateFragment extends Fragment {
    View v;
    boolean newCreation = true;
    Activity activity;

    private TextView txvRate;
    private RatingBar rate;
    private TextView txvNeg;
    private TextView txvPos;
    private TagContainerLayout tags;


    public ItemPublicRateFragment() {
    }


    @SuppressLint("ValidFragment")
    public ItemPublicRateFragment(Activity activity) {
        this.activity = activity;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (v == null) {
            newCreation = true;
            v = inflater.inflate(R.layout.fragment_public_rate, container, false);


        } else {
            newCreation = false;

        }

        return v;
    }

    @Override
    public void onViewCreated(@Nullable View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (activity == null) {
            return;
        }
        if (newCreation) {
            txvRate = (TextView) v.findViewById(R.id.txv_rate);
            rate = (RatingBar) v.findViewById(R.id.rate);
            txvNeg = (TextView) v.findViewById(R.id.txv_neg);
            txvPos = (TextView) v.findViewById(R.id.txv_pos);
            tags = (TagContainerLayout) v.findViewById(R.id.tags);


            //set comments tags
            List<String> userWin = new ArrayList<>();
            //just for test
            userWin.add("     جميل     ");
            userWin.add("  رائع   ");
            userWin.add(" لذيذ ");
            tags.setBackgroundColor(Color.TRANSPARENT);
            tags.setTagBackgroundColor(activity.getResources().getColor(R.color.colorAccent));
            tags.setTagBorderColor(activity.getResources().getColor(R.color.colorAccent));
            tags.setTagTextColor(activity.getResources().getColor(R.color.colorWhite));
            tags.setBorderColor(Color.TRANSPARENT);
            tags.setGravity(Gravity.CENTER);
            Typeface typeface = Typeface.createFromAsset(activity.getAssets(), "Changa-SemiBold.ttf");
            tags.setTagTypeface(typeface);
            tags.setTags(userWin);
        }
    }


}
