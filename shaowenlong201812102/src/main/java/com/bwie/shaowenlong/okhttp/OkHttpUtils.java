package com.bwie.shaowenlong.okhttp;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.bwie.shaowenlong.model.MyCallBack;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpUtils {
    private static volatile OkHttpUtils mInstance;

    private OkHttpClient mClient;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    /**
     * 第一步,写单例，
     */
    public static OkHttpUtils getmInstance(){
        if (mInstance == null){
            synchronized (OkHttpUtils.class){
                mInstance = new OkHttpUtils();
            }
        }
        return mInstance;
    }
    /**
     * 构造方法，OkHttpClient
     */
    private OkHttpUtils(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        /**
         * 使用构造者模式，优点，简单方便，
         * 设置连接超时
         * 读超时
         * 写超时
         * 添加拦截器
         */
        mClient = new OkHttpClient.Builder()
                .connectTimeout(10,TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();
    }
    /**
     * 异步post
     */
    public void postEnqueue(String url, Map<String,String> params,final Class clazz,final MyCallBack callBack){
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }

        RequestBody body = builder.build();
        Request request = new Request.Builder()
                .post(body)
                .url(url)
                .build();

        Call call = mClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onfail(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Gson gson = new Gson();
                final Object o = gson.fromJson(result, clazz);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onsuccess(o);
                    }
                });
            }
        });
    }



    private static String byte2String(byte[] bytes){
        return new String (bytes);
    }


}
