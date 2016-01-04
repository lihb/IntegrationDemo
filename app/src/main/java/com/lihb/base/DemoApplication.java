package com.lihb.base;

import android.app.Application;

import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.facebook.stetho.Stetho;
import com.lihb.action.SingleOkHttpClient;

import java.io.File;
import java.io.InputStream;

/**
 * Created by lihb on 16/1/2.
 */
public class DemoApplication extends Application{

    private static DemoApplication instance = null;

    public static File baseDir = null;

    private DemoApplication(){}

    public static DemoApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        baseDir = instance.getCacheDir();
        //Stetho
        Stetho.initializeWithDefaults(this);
        //Glide与OkHttpClient集成
        Glide.get(this)
                .register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(SingleOkHttpClient.getInstance()));
    }
}
