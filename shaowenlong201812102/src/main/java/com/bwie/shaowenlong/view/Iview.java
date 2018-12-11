package com.bwie.shaowenlong.view;

public interface Iview<T> {
    void success(Object data);
    void error(String str);
}
