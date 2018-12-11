package com.bwie.shaowenlong.app;

import android.app.Application;

import com.bwie.shaowenlong.view.UnCachHandler;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        UMConfigure.init(this,"5a12384aa40fa3551f0001d1","umeng",UMConfigure.DEVICE_TYPE_PHONE,"");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");

        UnCachHandler.getInstance(getApplicationContext()).init(getApplicationContext());
    }
}
