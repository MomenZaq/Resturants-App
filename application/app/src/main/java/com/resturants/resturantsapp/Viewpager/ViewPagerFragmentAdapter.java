package com.resturants.resturantsapp.Viewpager;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;


public class ViewPagerFragmentAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> arrayList;
//    private int counter = 1;


    ViewPagerFragmentAdapter(@Nullable FragmentManager fragmentManager, List<Fragment> arrayList) {
        super(fragmentManager, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.arrayList = arrayList;

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                counter=arrayList.size();
//                notifyDataSetChanged();
//            }
//        },300);


    }

    @Nullable
    @Override
    public Fragment getItem(int position) {
        return arrayList.get(position);
    }


    @Override
    public int getCount() {
        return arrayList.size();
//        return counter;
    }
}