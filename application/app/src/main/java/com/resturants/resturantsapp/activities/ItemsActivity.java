package com.resturants.resturantsapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.resturants.resturantsapp.R;
import com.resturants.resturantsapp.adapters.MainItemAdapter;
import com.resturants.resturantsapp.model.ItemModel;

import java.util.ArrayList;
import java.util.List;

public class ItemsActivity extends ParentActivity {
    private ConstraintLayout constraintLayout;
    private ImageView imgSetting;
    private ImageView imgLogout;
    private ImageView imgBack;
    private AppCompatEditText edtSearchLeague;
    private TextView txvNear;
    private RecyclerView recycler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);


        constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);
        imgSetting = (ImageView) findViewById(R.id.img_setting);
        imgLogout = (ImageView) findViewById(R.id.img_logout);
        imgBack = (ImageView) findViewById(R.id.img_back);
        edtSearchLeague = (AppCompatEditText) findViewById(R.id.edt_search_league);
        txvNear = (TextView) findViewById(R.id.txv_near);
        recycler = (RecyclerView) findViewById(R.id.recycler);

//set vertical layout manager for recyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutManager);


        //just for test
        List<ItemModel> itemModelList = new ArrayList<>();
        ItemModel itemModel = new ItemModel("المكان 1", 200, "المنطقة 1");
        itemModelList.add(itemModel);
        itemModel = new ItemModel("المكان 2", 300, "المنطقة 2");
        itemModelList.add(itemModel);
        itemModel = new ItemModel("المكان 3", 400, "المنطقة 3");
        itemModelList.add(itemModel);
        itemModel = new ItemModel("المكان 4", 200, "المنطقة 4");
        itemModelList.add(itemModel);
        itemModel = new ItemModel("المكان 5", 100, "المنطقة 5");
        itemModelList.add(itemModel);
        itemModel = new ItemModel("المكان 6", 400, "المنطقة 6");
        itemModelList.add(itemModel);
        itemModel = new ItemModel("المكان 7", 200, "المنطقة 7");
        itemModelList.add(itemModel);

        MainItemAdapter itemAdapter = new MainItemAdapter(itemModelList, this);
        recycler.setAdapter(itemAdapter);
    }

    public void setting(View view) {
        openSetting();
    }

    public void logout(View view) {
        logout();
    }

    public void back(View view) {
        onBackPressed();
    }
}
