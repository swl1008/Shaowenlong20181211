package com.bwie.shaowenlong.presenter;

import com.bwie.shaowenlong.model.Imodel;
import com.bwie.shaowenlong.model.ModelImpl;
import com.bwie.shaowenlong.model.MyCallBack;
import com.bwie.shaowenlong.view.Iview;

import java.util.Map;

public class PresenterImpl implements Ipresenter {
    private Iview iview;
    private Imodel imodel;

    public PresenterImpl(Iview iView) {
        iview = iView;
        imodel = new ModelImpl();
    }

    @Override
    public void getRequest(String dataUrl, Map<String, String> map, Class clazz) {
        imodel.getRequest(dataUrl, map, clazz, new MyCallBack() {
            @Override
            public void onsuccess(Object obj) {
                iview.success(obj);
            }

            @Override
            public void onfail(Exception e) {

            }
        });
    }

    public void onDetach(){
        if (imodel != null){
            imodel = null;
        }
        if (iview != null){
            iview = null;
        }
    }


}
