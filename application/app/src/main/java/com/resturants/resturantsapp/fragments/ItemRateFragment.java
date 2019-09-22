package com.resturants.resturantsapp.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.resturants.resturantsapp.R;
import com.resturants.resturantsapp.adapters.MainCommentAdapter;
import com.resturants.resturantsapp.database.FirebaseUtility;
import com.resturants.resturantsapp.model.RateModel;

import java.util.ArrayList;
import java.util.List;

public class ItemRateFragment extends Fragment {
    View v;
    boolean newCreation = true;
    Activity activity;

    private ConstraintLayout constraintContainer;
    private TextView txvNoData;
    private EditText edtContent;
    private ImageButton btnSend;
    private RecyclerView recycle;

    public ItemRateFragment() {
    }


    @SuppressLint("ValidFragment")
    public ItemRateFragment(Activity activity) {
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
            v = inflater.inflate(R.layout.fragment_item_rate, container, false);


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


            constraintContainer = (ConstraintLayout) v.findViewById(R.id.constraint_container);
            txvNoData = (TextView) v.findViewById(R.id.txv_no_data);
            edtContent = (EditText) v.findViewById(R.id.edt_content);
            btnSend = (ImageButton) v.findViewById(R.id.btn_send);
            recycle = (RecyclerView) v.findViewById(R.id.recycle);


//set vertical layout manager for recyclerView
            LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
            recycle.setLayoutManager(layoutManager);


            //just for test
            List<RateModel> itemModelList = new ArrayList<>();
            RateModel itemModel = new RateModel("أحمد", "ahmed@gmail.com", "المطعم رقم 1", "جميل ولذيذ");
            itemModelList.add(itemModel);
//just for test, add some comments to database
            FirebaseUtility.addRates(activity,  itemModel);

            itemModel = new RateModel("محمد", "mohammed@gmail.com", "المطعم رقم 1", "جميل وطيب");
            itemModelList.add(itemModel);
//just for test, add some comments to database
            FirebaseUtility.addRates(activity,  itemModel);


            itemModel = new RateModel("محمود", "mahmoud@gmail.com", "المطعم رقم 1", "رائع ");
            itemModelList.add(itemModel);
//just for test, add some comments to database
            FirebaseUtility.addRates(activity,  itemModel);


            MainCommentAdapter itemAdapter = new MainCommentAdapter(itemModelList, activity);
            recycle.setAdapter(itemAdapter);


        }
    }


}
