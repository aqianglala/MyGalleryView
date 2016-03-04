package com.example.admin.mygalleryview.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.example.admin.mygalleryview.R;
import com.example.admin.mygalleryview.adapter.MyFragmentPagerAdapter;
import com.example.admin.mygalleryview.global.BaseApplication;
import com.example.admin.mygalleryview.global.BaseFragment;

import java.util.ArrayList;

/**
 * Created by admin on 2016/3/4.
 */
public class ParentFragment extends BaseFragment{

    private ArrayList<Drawable> mDrawables;
    private float mPositionY;
    private float mPositionX;
    private int mWidth;
    private int mHeight;
    private int mCurrentPosition;
    private int mHorizontalSpacingAndWidth;
    private int mVerticalSpacingAndHeight;
    private ArrayList<ItemFragment> mFragmentsList;
    private ArrayList<String> mUrls;
    private ViewPager mViewpager;
    private MyFragmentPagerAdapter mAdapter;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_parent);
        mViewpager = getViewById(R.id.viewpager);
    }

    @Override
    protected void setListener() {
        mViewpager.setOnPageChangeListener(mPageChangeListener);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        // 获取传过来的数据
        getData();
        // 初始化itemfragment列表
        initItemFragments();

        mAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), mFragmentsList);
        mViewpager.setAdapter(mAdapter);
        mViewpager.setCurrentItem(mCurrentPosition);
    }

    private void initItemFragments() {
        Bundle itemBundle = new Bundle();

        itemBundle.putInt("width",mWidth);
        itemBundle.putInt("height",mHeight);

        mFragmentsList = new ArrayList<>();
        for (int i = 0; i < mDrawables.size(); i++) {
            ItemFragment itemFragment = new ItemFragment();
            itemFragment.setImageRes(mDrawables.get(i));
            itemBundle.putString("url",mUrls.get(i));
            itemFragment.setArguments(itemBundle);
            mFragmentsList.add(itemFragment);
        }
    }

    private void getData() {
        BaseApplication application = (BaseApplication) mActivity.getApplication();
        mDrawables =application.getmDrawables();

        Bundle bundle = getArguments();
        // 获取当前位置
        mCurrentPosition = bundle.getInt("currentPosition",-1);
        // 获取图片链接列表
        mUrls = bundle.getStringArrayList("urls");

        // 获取当前位置所在小图的坐标
        mPositionY = bundle.getFloat("positionY", 0);
        mPositionX = bundle.getFloat("positionX", 0);

        // 获取小图的宽高
        mWidth = bundle.getInt("width");
        mHeight = bundle.getInt("height");

        mHorizontalSpacingAndWidth = bundle.getInt("horizontalSpacing") + mWidth;
        mVerticalSpacingAndHeight = bundle.getInt("verticalSpacing") + mHeight;
    }

    private ViewPager.OnPageChangeListener mPageChangeListener=new ViewPager.OnPageChangeListener() {


        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int seletedPage) {
            if(seletedPage < mCurrentPosition && (seletedPage ==2 ||seletedPage ==5
                    ||seletedPage ==8 )){
                mPositionX += 2 * mHorizontalSpacingAndWidth;
                mPositionY -=mVerticalSpacingAndHeight;
            }else if(seletedPage > mCurrentPosition && (seletedPage ==3 ||seletedPage ==6 )){
                mPositionX -= 2 * mHorizontalSpacingAndWidth;
                mPositionY +=mVerticalSpacingAndHeight;
            }else if(seletedPage > mCurrentPosition){
                mPositionX += mHorizontalSpacingAndWidth;
            }else if(seletedPage < mCurrentPosition){
                mPositionX -= mHorizontalSpacingAndWidth;
            }
            mCurrentPosition = seletedPage;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    // 更新 position
    public float getPositionX() {
        return mPositionX;
    }

    public float getPositionY() {
        return mPositionY;
    }


    @Override
    protected void onUserVisible() {

    }
}
