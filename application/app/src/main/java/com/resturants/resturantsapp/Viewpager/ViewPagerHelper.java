package com.resturants.resturantsapp.Viewpager;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.duolingo.open.rtlviewpager.RtlViewPager;
import com.resturants.resturantsapp.R;

import java.util.List;

public class ViewPagerHelper implements TabSelectInterface, ViewPager.OnPageChangeListener {
    public RecyclerView recyclerViewTabs;
    private ViewPagerFragmentAdapter viewPagerFragmentAdapter;
    private Lifecycle lifecycle;
    private byte lastStateForOrientation = 0;
    private Activity activity;
    private RtlViewPager viewPager2;
    private List<Fragment> fragmentList;
    private List<String> tabList;
    private ViewPagerTabsAdapter viewPagerTabsAdapter;
    private FragmentManager fragmentManager;

    public ViewPagerHelper(Lifecycle lifecycle, FragmentManager fragmentManager, Activity activity, List<Fragment> fragmentList, List<String> tabList) {
        this.lifecycle = lifecycle;
        this.fragmentManager = fragmentManager;
        this.activity = activity;
        this.fragmentList = fragmentList;
        this.tabList = tabList;
        setupViewPager();

    }

    private void setupViewPager() {
        recyclerViewTabs = activity.findViewById(R.id.recycle_tabs);
        viewPager2 = activity.findViewById(R.id.viewpager);


        LinearLayoutManager linearLayoutManagerTabs = new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false);

        recyclerViewTabs.setLayoutManager(linearLayoutManagerTabs);

        recyclerViewTabs.setNestedScrollingEnabled(false);
        ViewCompat.setNestedScrollingEnabled(recyclerViewTabs, false);
        recyclerViewTabs.setHasFixedSize(true);
        recyclerViewTabs.setItemViewCacheSize(20);
        recyclerViewTabs.setDrawingCacheEnabled(true);
        recyclerViewTabs.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerViewTabs.setDrawingCacheEnabled(false);
        setTabsAdapter();
        setFragmentsAdapter();


        viewPager2.setOnPageChangeListener(this);

  }


    private void setTabsAdapter() {
        int currentSelected = 0;
        if (viewPagerTabsAdapter != null) {
            currentSelected = getCurrentTabIndex();
        }
        System.out.println("SETTABSADAPTERNOW!");
        viewPagerTabsAdapter = new ViewPagerTabsAdapter(activity, tabList, this, recyclerViewTabs);
        recyclerViewTabs.setAdapter(viewPagerTabsAdapter);

        viewPagerTabsAdapter.setSelectedTab(currentSelected);


    }

    private void setFragmentsAdapter() {
        viewPagerFragmentAdapter = new ViewPagerFragmentAdapter(fragmentManager, fragmentList);
        viewPager2.setAdapter(viewPagerFragmentAdapter);

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //        super.onPageScrolled(position, positionOffset, positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
//        super.onPageSelected(position);
        if (position <= -1) {
            return;
        }
        Log.e("Selected_Page", String.valueOf(position));
        viewPagerTabsAdapter.setSelectedTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
//        super.onPageScrollStateChanged(state);
    }

    @Override
    public void onTabSelected(int position) {
        viewPager2.setCurrentItem(position, true);

    }


    public int getCurrentTabIndex() {
        return viewPager2.getCurrentItem();
    }


}
