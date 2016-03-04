package com.example.admin.mygalleryview.adapter;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.admin.mygalleryview.R;
import com.example.admin.mygalleryview.activity.MainActivity;
import com.example.admin.mygalleryview.fragment.ParentFragment;
import com.example.admin.mygalleryview.utils.ScreenUtils;

import java.util.ArrayList;

/**
 * Created by admin on 2016/3/4.
 */
public class MyGridViewAdapter extends MyBaseAdapter<String> {

    private int horizontalSpacing;
    private int verticalSpacing;
    private ArrayList<String> mData;
    private MainActivity mActivity;

    private Bundle mBundle=new Bundle();

    public MyGridViewAdapter(ArrayList<String> data , MainActivity context) {
        super(data);
        mData = data;
        mActivity=context;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid,
                parent, false);
        ImageView imageView= (ImageView) rootView.findViewById(R.id.id_imageView);
        // 加载网络图片
        Glide.with(mActivity).load(mData.get(position)).into(imageView);
        // 设置触摸事件，为了获取图片位置
        imageView.setOnTouchListener(imageTouchListener);
        // 设置点击事件，跳转
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 将小图对象传过去
                mActivity.sendmDrawables();
                FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ParentFragment fragment = (ParentFragment) fragmentManager.findFragmentByTag("multiImage");
                if (fragment == null) {
                    fragment = new ParentFragment();
                    // 点击的位置
                    mBundle.putInt("currentPosition",position);
                    // 将图片的url传递过去
                    mBundle.putStringArrayList("urls",mData);
                    fragment.setArguments(mBundle);
                    fragmentTransaction.add(R.id.fragmentContainer, fragment, "multiImage");
                    fragmentTransaction.addToBackStack("multiImage");
                    fragmentTransaction.commit();
                }
            }
        });
        return rootView;
    }

    public void setSpacing(int horizontalSpacing,int verticalSpacing){
        this.horizontalSpacing = horizontalSpacing;
        this.verticalSpacing = verticalSpacing;
    }

    private View.OnTouchListener imageTouchListener = new View.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            // 获取图片所在的位置
            if (event.getAction() == MotionEvent.ACTION_UP) {
                // 图片的位置
                mBundle.putFloat("positionX", event.getRawX() - event.getX());
                mBundle.putFloat("positionY", event.getRawY() - event.getY() - ScreenUtils
                        .getStatusBarHeight(mActivity));
                // 图片的宽高
                mBundle.putInt("width", v.getWidth());
                mBundle.putInt("height", v.getHeight());
                // gridView的间距
                mBundle.putInt("horizontalSpacing", horizontalSpacing);
                mBundle.putInt("verticalSpacing", verticalSpacing);
            }
            return false;
        }
    };

}
