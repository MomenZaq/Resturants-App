package com.resturants.resturantsapp.activities;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.resturants.resturantsapp.R;
import com.resturants.resturantsapp.Viewpager.ViewPagerHelper;
import com.resturants.resturantsapp.fragments.ItemDetailsFragment;
import com.resturants.resturantsapp.fragments.ItemPublicRateFragment;
import com.resturants.resturantsapp.fragments.ItemRateFragment;
import com.resturants.resturantsapp.model.ItemModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ItemProfileActivity extends ParentActivity {
    public static final String EXTRA_SELECTED_ITEM_EXTRA = "selected_item";
    private ImageView imgview;
    private TextView txvName;

    public  ItemDetailsFragment itemDetailsFragment;
    public   ItemPublicRateFragment itemPublicRateFragment;
    public   ItemRateFragment itemRateFragment;
    ItemModel itemModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_profile);
        imgview = (ImageView) findViewById(R.id.imgview);
        txvName = (TextView) findViewById(R.id.txv_name);

        String videoObjectString = getIntent().getStringExtra(EXTRA_SELECTED_ITEM_EXTRA);
        Gson gson = new Gson();
        Type type2 = new TypeToken<ItemModel>() {
        }.getType();
        itemModel = gson.fromJson(videoObjectString, type2);

        Glide.with(getApplicationContext()).
                load(itemModel.getImgUrl())
                .into(imgview);

        txvName.setText(itemModel.getItemName());
        setTabs();


    }

    private void setTabs() {

        List<String> tabsList = new ArrayList<>();
        List<Fragment> fragmentList = new ArrayList<>();

        itemDetailsFragment = new ItemDetailsFragment(this, itemModel);
        itemRateFragment = new ItemRateFragment(this, itemModel);
        itemPublicRateFragment = new ItemPublicRateFragment(this, itemModel);

        tabsList.add(0, getBaseContext().getResources().getString(R.string.details));
        fragmentList.add(0, itemDetailsFragment);


        tabsList.add(1, getBaseContext().getResources().getString(R.string.rate));
        fragmentList.add(1, itemRateFragment);


        tabsList.add(2, getBaseContext().getResources().getString(R.string.public_rate));
        fragmentList.add(2, itemPublicRateFragment);


        ViewPagerHelper viewpagerHelpter = new ViewPagerHelper(getLifecycle(),
                getSupportFragmentManager(),
                this,
                fragmentList,
                tabsList);


    }

    public void back(View view) {
        onBackPressed();
    }

    public void logout(View view) {
        logout();
    }

    public void setting(View view) {
        openSetting();
    }
}
