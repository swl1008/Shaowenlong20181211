package com.bwie.shaowenlong.view;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UnCachHandler implements Thread.UncaughtExceptionHandler{
    private static UnCachHandler unCachHandler;
    private Context context;
    public static UnCachHandler getInstance(Context context) {
        if(unCachHandler == null){
            synchronized (UnCachHandler.class) {
                unCachHandler = new UnCachHandler(context);
            }
        }
        return unCachHandler;
    }
    private UnCachHandler(Context context) {
        init(context);
    }
    public void init(Context context) {
        Thread.setDefaultUncaughtExceptionHandler(this);
        context = context.getApplicationContext();
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        try {
            Log.i("1607C", e.getLocalizedMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void saveSD(Throwable throwable) throws Exception {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
            return;
        }
        PackageManager pm = context.getPackageManager();
        PackageInfo inFo = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);

        String versionName = inFo.versionName;
        int versionCode = inFo.versionCode;

        int version_code = Build.VERSION.SDK_INT;

        String release = Build.VERSION.RELEASE;

        String mobile = Build.MODEL;

        String mobileName = Build.MANUFACTURER;

        String path = Environment.getExternalStorageDirectory().getAbsolutePath();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        String time = simpleDateFormat.format(new Date());

        File f = new File(path, "exception");
        f.mkdirs();

        File file = new File(f.getAbsolutePath(), "exception" + time + ".txt");

        if (!file.exists()) {
            file.createNewFile();
        }

        String data = "\nMobile型号：" + mobile + "\nMobileName：" + mobileName + "\nSDK版本：" + version_code +
                "\n版本名称：" + versionName + "\n版本号：" + versionCode + "\n异常信息：" + throwable.toString();

        Log.i("dj", data);

        byte[] buffer = data.trim().getBytes();
        FileOutputStream fileOutputStream = new FileOutputStream(file);

        fileOutputStream.write(buffer, 0, buffer.length);
        fileOutputStream.flush();
        fileOutputStream.close();

        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
