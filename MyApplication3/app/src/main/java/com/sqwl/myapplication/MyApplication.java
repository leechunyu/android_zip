package com.sqwl.myapplication;

import android.app.Application;

import com.liulishuo.filedownloader.FileDownloader;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FileDownloader.setup(getApplicationContext());
    }
}
