package com.bwie.shaowenlong.view.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bwie.shaowenlong.R;
import com.bwie.shaowenlong.presenter.PresenterImpl;
import com.bwie.shaowenlong.view.Iview;
import com.bwie.shaowenlong.view.ScanActivity;
import com.recker.flybanner.FlyBanner;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment implements Iview {
    private FlyBanner banner;
    private PresenterImpl presenter;
    private Button btn_sao;
    private String url = "http://www.zhaoapi.cn/home/getHome";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first,container,false);
        banner = view.findViewById(R.id.banner);
        btn_sao = view.findViewById(R.id.btn_sao);
        List<String> list = new ArrayList<>();
        list.add("http://www.zhaoapi.cn/images/quarter/ad1.png");
        list.add("http://www.zhaoapi.cn/images/quarter/ad2.png");
        list.add("http://www.zhaoapi.cn/images/quarter/ad3.png");
        list.add("http://www.zhaoapi.cn/images/quarter/ad4.png");
        banner.setImagesUrl(list);
        btn_sao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
            }
        });
        return view;
    }
    private void checkPermission() {
            startActivity(new Intent(getActivity(), ScanActivity.class));
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //如果requestCode匹配，切权限申请通过
        if(requestCode == 100 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            startActivity(new Intent(getActivity(), ScanActivity.class));
        }
    }


    @Override
    public void success(Object o) {

    }

    @Override
    public void error(String str) {

    }
}
