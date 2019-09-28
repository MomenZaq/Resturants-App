package com.resturants.resturantsapp.activities;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;

import com.resturants.resturantsapp.R;
import com.resturants.resturantsapp.Viewpager.ViewPagerHelper;
import com.resturants.resturantsapp.fragments.ItemDetailsFragment;
import com.resturants.resturantsapp.fragments.ItemPublicRateFragment;
import com.resturants.resturantsapp.fragments.ItemRateFragment;

import java.util.ArrayList;
import java.util.List;

public class ItemProfileActivity extends ParentActivity {
    ItemDetailsFragment itemDetailsFragment;
    ItemPublicRateFragment itemPublicRateFragment;
    ItemRateFragment itemRateFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_profile);


        setTabs();


    }

    private void setTabs() {

        List<Fragment> fragmentList = new ArrayList<>();
        List<String> tabsList = new ArrayList<>();

        itemDetailsFragment = new ItemDetailsFragment(this);
        itemRateFragment = new ItemRateFragment(this);
        itemPublicRateFragment = new ItemPublicRateFragment(this);

        tabsList.add(0, getBaseContext().getResources().getString(R.string.details));
        fragmentList.add(0, itemDetailsFragment);


        tabsList.add(1, getBaseContext().getResources().getString(R.string.rate));
        fragmentList.add(1, itemRateFragment);


        tabsList.add(2, getBaseContext().getResources().getString(R.string.public_rate));
        fragmentList.add(2, itemPublicRateFragment);


        ViewPagerHelper viewpagerHelpter = new ViewPagerHelper(getLifecycle(),
                getSupportFragmentManager(),
                this,
                fragmentList, tabsList);


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
