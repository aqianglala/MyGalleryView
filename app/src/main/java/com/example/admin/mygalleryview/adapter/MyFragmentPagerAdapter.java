package com.example.admin.mygalleryview.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.admin.mygalleryview.fragment.ItemFragment;

import java.util.ArrayList;

/**
 * Created by admin on 2016/3/4.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter{
    private ArrayList<ItemFragment> itemFragments;
    public MyFragmentPagerAdapter(FragmentManager fm , ArrayList<ItemFragment> itemFragments) {
        super(fm);
        this.itemFragments=itemFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return itemFragments.get(position);
    }

    @Override
    public int getCount() {
        return itemFragments.size();
    }
}
