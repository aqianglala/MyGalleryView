package com.example.admin.mygalleryview.global;

import android.app.Application;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;

/**
 * Created by admin on 2016/1/26.
 */
public class BaseApplication extends Application{

    private static BaseApplication sInstance;
    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;
//        initFresco();

    }

    private ArrayList<Drawable> mDrawables;

    public ArrayList<Drawable> getmDrawables() {
        return mDrawables;
    }

    public void setmDrawables(ArrayList<Drawable> mDrawables) {
        this.mDrawables = mDrawables;
    }

    //    private void initFresco() {
//        Fresco.initialize(this);
//        OkHttpUtils instance = OkHttpUtils.getInstance();
//        instance.setConnectTimeout(10000, TimeUnit.MILLISECONDS);
//    }

    public static BaseApplication getInstance() {
        return sInstance;
    }
}
