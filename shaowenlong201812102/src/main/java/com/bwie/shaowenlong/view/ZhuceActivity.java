package com.bwie.shaowenlong.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bwie.shaowenlong.R;
import com.bwie.shaowenlong.bean.PhoneBean;
import com.bwie.shaowenlong.okhttp.Apis;
import com.bwie.shaowenlong.presenter.PresenterImpl;

import java.util.HashMap;
import java.util.Map;

public class ZhuceActivity extends AppCompatActivity implements Iview{

    private PresenterImpl presenter;
    private Button btn_zhuce;
    private EditText edit_zhuce_name,edit_zhuce_pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuce);
        presenter = new PresenterImpl(this);
        btn_zhuce = findViewById(R.id.btn_zhuce);
        edit_zhuce_name = findViewById(R.id.edit_zhuce_name);
        edit_zhuce_pass = findViewById(R.id.edit_zhuce_pass);
        btn_zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> params = new HashMap<>();
                String names = edit_zhuce_name.getText().toString();
                String passes = edit_zhuce_pass.getText().toString();
                params.put("mobile",names);
                params.put("password",passes);
                presenter.getRequest(Apis.URL_ZHUCE,params,PhoneBean.class);
            }
        });
    }

    @Override
    public void success(Object data) {
        PhoneBean phonebean= (PhoneBean) data;
        if(phonebean.getCode().equals("0")){
            Toast.makeText(ZhuceActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(ZhuceActivity.this,MainActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(ZhuceActivity.this,"失败",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void error(String str) {
        Toast.makeText(ZhuceActivity.this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }
}
