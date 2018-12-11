package com.bwie.shaowenlong.model;

public interface MyCallBack<T> {
    void onsuccess(Object obj);
    void onfail(Exception e);
}
