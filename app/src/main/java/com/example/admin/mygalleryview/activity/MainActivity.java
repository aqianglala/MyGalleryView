package com.example.admin.mygalleryview.activity;

import android.app.ActionBar;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.admin.mygalleryview.R;
import com.example.admin.mygalleryview.adapter.MyGridViewAdapter;
import com.example.admin.mygalleryview.global.BaseActivity;
import com.example.admin.mygalleryview.global.BaseApplication;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    private MyGridViewAdapter mAdapter;
    private GridView mGridView;

    private ArrayList<String>mUrls=new ArrayList<>();
    private ActionBar mActionBar;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        mGridView = getViewById(R.id.gridView);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        // 初始化假数据
        initData();
        mAdapter = new MyGridViewAdapter(mUrls,this);
        mGridView.setAdapter(mAdapter);
        // 将gridView的间距传过去
        int horizontalSpacing = mGridView.getHorizontalSpacing();
        int verticalSpacing = mGridView.getVerticalSpacing();
        mAdapter.setSpacing(horizontalSpacing,verticalSpacing);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        for(int i=0;i<9;i++){
            mUrls.add("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1457055188&di=958f65df0979efe1de377b316a38c805&src=http://img.hb.aicdn.com/d2024a8a998c8d3e4ba842e40223c23dfe1026c8bbf3-OudiPA_fw580");
        }
    }

    /**
     * 发送小图列表，只发送一次
     */
    private ArrayList<Drawable>mDrawables=new ArrayList<>();
    public void sendmDrawables(){
        BaseApplication application= (BaseApplication) getApplication();

        mDrawables.clear();
        int count = mAdapter.getCount();
        for(int i=0;i<count;i++){
            ImageView childAt = (ImageView) mGridView.getChildAt(i);
            mDrawables.add(childAt.getDrawable());
        }
        application.setmDrawables(mDrawables);
    }

}
