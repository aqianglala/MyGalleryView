package com.example.admin.mygalleryview.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.admin.mygalleryview.R;
import com.example.admin.mygalleryview.global.BaseFragment;
import com.example.admin.mygalleryview.utils.ScreenUtils;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by admin on 2016/3/4.
 */
public class ItemFragment extends BaseFragment{

    private PhotoView mPhotoView;
    private final Handler mHandler = new Handler();
    private Bundle mBundle;
    private int mWidth;
    private int mHeight;
    private float mPositionX;
    private float mPositionY;
    private ProgressBar mProgressBar;
    private ParentFragment mParentFragment;
    private String url;
    private int screenWidth;
    private int screenHeight;
    private PhotoViewAttacher mAttacher;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_item);
        mPhotoView = getViewById(R.id.photo);
        mProgressBar = getViewById(R.id.progressbar);
    }

    @Override
    protected void setListener() {
        mPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.post(new Runnable() {

                    @Override
                    public void run() {
                        enterAnimation();
                    }
                });
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mAttacher = new PhotoViewAttacher(mPhotoView);
        // 获取数据
        getData();
        // 设置图片
        mPhotoView.setLayoutParams(new RelativeLayout.LayoutParams(mWidth,mHeight));
        mPhotoView.setImageDrawable(mDrawable);

        enterAnimation();
    }

    private void getData() {
        mBundle = getArguments();
        url = mBundle.getString("url");

        mParentFragment = (ParentFragment) getParentFragment();
        // 获取起始位置
        mPositionX = mParentFragment.getPositionX();
        mPositionY = mParentFragment.getPositionY();

        // 图片的宽高
        mWidth = mBundle.getInt("width", 0);
        mHeight = mBundle.getInt("height", 0);
    }

    @Override
    protected void onUserVisible() {

    }

    private Drawable mDrawable;
    public void setImageRes(Drawable drawable){
        this.mDrawable = drawable;
    }

    public void enterAnimation(){
        // 计算终止位置
        // 获取屏幕高度
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        int endX = screenWidth / 2 - mWidth / 2;
        int endY =( screenHeight - ScreenUtils.getStatusBarHeight(mActivity))/ 2  - mHeight / 2 ;

//        PropertyValuesHolder pvhX =PropertyValuesHolder.ofFloat("x",mPositionX,endX);
//        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("y", mPositionY,endY);
//        PropertyValuesHolder pvhAlpha = PropertyValuesHolder.ofFloat("alpha", 0.5f, 1f);
//        PropertyValuesHolder pvhScaleX = PropertyValuesHolder.ofFloat("scaleX", 0f,1);
//        PropertyValuesHolder pvhScaleY = PropertyValuesHolder.ofFloat("scaleY", 0f,1);

        Animation translateAnimation = new TranslateAnimation(Animation.ABSOLUTE,mPositionX,Animation
                .ABSOLUTE,endX,Animation
                .ABSOLUTE,mPositionY,Animation.ABSOLUTE,endY);;

        Animation scaleAnimation = new ScaleAnimation(0,1,0,1,0.5f,0.5f);
        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.setDuration(300);
        animationSet.setFillAfter(true);

        mPhotoView.startAnimation(animationSet);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mProgressBar.setVisibility(View.VISIBLE);
                mPhotoView.clearAnimation();
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
                        (RelativeLayout.LayoutParams
                                .MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                mPhotoView.setLayoutParams(params);
                Glide.with(ItemFragment.this).load(url).listener(new RequestListener<String, GlideDrawable>() {

                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable>
                            target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        mProgressBar.setVisibility(View.GONE);
                        mAttacher.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        return (false);
                    }
                }).into(mPhotoView);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
//        final ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(mPhotoView,pvhScaleX,
//                pvhScaleY, pvhX,
//                pvhY).setDuration(300);

//        animator.start();
//        animator.addListener(new AnimatorListenerAdapter(){
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                mProgressBar.setVisibility(View.VISIBLE);
//                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
//                        (RelativeLayout.LayoutParams
//                                .MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
//                mPhotoView.setLayoutParams(params);
//                Glide.with(ItemFragment.this).load(url).listener(new RequestListener<String, GlideDrawable>() {
//
//                    @Override
//                    public boolean onException(Exception e, String model, Target<GlideDrawable>
//                            target, boolean isFirstResource) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                        mProgressBar.setVisibility(View.GONE);
//                        mAttacher.setScaleType(ImageView.ScaleType.FIT_CENTER);
//                        return (false);
//                    }
//                }).into(mPhotoView);
//            }
//        });
    }

    public void runExitAnimation(){
        // 获取图片的宽高
        int measuredWidth = mPhotoView.getMeasuredWidth();
        int measuredHeight = mPhotoView.getMeasuredHeight();
        //初始化
        AnimationSet set = new AnimationSet(false);

        Animation translateAnimation = new TranslateAnimation(Animation.ABSOLUTE,mImageX,Animation
                .ABSOLUTE,mPositionX,Animation
                .ABSOLUTE,mImageY,Animation.ABSOLUTE,mPositionY);
        Animation scaleAnimation = new ScaleAnimation(mImageX,mPositionX,mImageY,mPositionY,Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0.5f);
        //设置动画时间
        set.addAnimation(translateAnimation);
        set.addAnimation(scaleAnimation);
        set.setDuration(1000);
        set.setFillAfter(true);

        mPhotoView.startAnimation(set);
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction trans = manager.beginTransaction();
                manager.popBackStack();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private float mImageX;
    private float mImageY;
    private View.OnTouchListener mTouchListener=new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_UP:
                    mImageX = event.getRawX() - event.getX();
                    mImageY = event.getRawY() - event.getY();
                    break;
            }
            return false;
        }
    };
}
