package com.bwie.shaowenlong.model;

import com.bwie.shaowenlong.okhttp.OkHttpUtils;
import com.bwie.shaowenlong.utils.NetUtil;

import java.util.Map;

public class ModelImpl implements Imodel {


    @Override
    public void getRequest(String url, Map<String, String> params, Class clazz, final MyCallBack callBack) {
        OkHttpUtils.getmInstance().postEnqueue(url, params, clazz, new MyCallBack() {
            @Override
            public void onsuccess(Object obj) {
                callBack.onsuccess(obj);
            }

            @Override
            public void onfail(Exception e) {
                callBack.onfail(e);
            }
        });
    }
}
