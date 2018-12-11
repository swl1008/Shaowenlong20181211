package com.bwie.shaowenlong.utils;

import android.annotation.SuppressLint;
import android.os.Message;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.security.auth.callback.Callback;

public class NetUtil {


    private static NetUtil instance;
    private Gson gson;

    @SuppressLint("HandlerLeak")
    private android.os.Handler handler = new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    public NetUtil() {
        gson = new Gson();
    }

    public static NetUtil getInstance() {
        if (instance == null){
            instance = new NetUtil();
        }
        return instance;
    }
    public String getRequest(String strUrl){
        String result="";
        try {
            URL url = new URL(strUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == 200){
                result = stream2String(urlConnection.getInputStream());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    public interface Callback<E>{
        void onSuccess(E e);
    }
    public <E> E getRequest(String strUrl,Class clazz){
        return (E) gson.fromJson(getRequest(strUrl),clazz);
    }

    private String stream2String(InputStream inputStream) throws IOException {
        StringBuilder builder = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        for (String tmp = br.readLine();tmp != null;tmp=br.readLine()){
            builder.append(tmp);
        }
        return builder.toString();
    }
    public void getRequest(final String strUrl, final Class clazz, final Callback callback){
        new Thread(){
            @Override
            public void run() {
                super.run();
                final Object o = getRequest(strUrl,clazz);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(o);
                    }
                });
            }
        }.start();
    }

}
