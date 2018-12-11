package com.bwie.shaowenlong.model;


import java.util.Map;

public interface Imodel {
    void getRequest(String url, Map<String, String> params, Class clazz, MyCallBack callBack);
}
