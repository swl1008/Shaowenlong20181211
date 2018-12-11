package com.bwie.shaowenlong.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.shaowenlong.R;
import com.bwie.shaowenlong.bean.PhoneBean;
import com.bwie.shaowenlong.okhttp.Apis;
import com.bwie.shaowenlong.presenter.PresenterImpl;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements Iview,View.OnClickListener{

    private Button btn_login,btn_three_login;
    private EditText edit_login_name,edit_login_pass;
    private TextView text_zhuce;
    private PresenterImpl presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_login = findViewById(R.id.btn_login);
        edit_login_name = findViewById(R.id.edit_login_name);
        edit_login_pass = findViewById(R.id.edit_login_pass);
        text_zhuce = findViewById(R.id.text_zhuce);
        btn_three_login = findViewById(R.id.btn_three_login);
        presenter = new PresenterImpl(this);
        btn_login.setOnClickListener(this);
        text_zhuce.setOnClickListener(this);
        btn_three_login.setOnClickListener(this);
    }


    @Override
    public void success(Object data) {
            if(data instanceof PhoneBean){
            PhoneBean phoneBean= (PhoneBean) data;
            if(phoneBean.getCode().equals("0")){
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }
    }

    @Override
    public void error(String str) {
        Toast.makeText(MainActivity.this,str,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_login:
                    Map<String, String> params = new HashMap<>();
                    String phone = edit_login_name.getText().toString();
                    String pass = edit_login_pass.getText().toString();
                    params.put("mobile",phone);
                    params.put("password",pass);
                    presenter.getRequest(Apis.URL_LOGIN,params,PhoneBean.class);
                    break;
                case R.id.text_zhuce:
                    Intent intent = new Intent(MainActivity.this,ZhuceActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btn_three_login:
                    //获得UMShareAPI实例
                    UMShareAPI umShareAPI =  UMShareAPI.get(MainActivity.this);

                    //开始登录
                    //第一个参数：上下文
                    //第二个参数，登录哪种平台
                    //第三个参数，添加回调
                    umShareAPI.getPlatformInfo(MainActivity.this, SHARE_MEDIA.QQ, new UMAuthListener() {
                        /**
                         * 开始登录回调
                         * @param share_media
                         */
                        @Override
                        public void onStart(SHARE_MEDIA share_media) {
                            Log.i("tag", "UMAuthListener onStart");
                        }

                        /**
                         * 登录完成
                         * @param share_media
                         * @param i
                         * @param map
                         */
                        @Override
                        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                            //头像，昵称
                            Log.i("tag", "UMAuthListener onComplete");

                            //获取名字
                            String name  = map.get("screen_name");
                            //获取头像
                            String img  = map.get("profile_image_url");
                            Toast.makeText(MainActivity.this,""+name,Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                            startActivity(intent);
                            Log.i("tag", "name is "+ name);
                            Log.i("tag", "img is "+ img);
                        }

                        /**
                         * 登录失败
                         * @param share_media
                         * @param i
                         * @param throwable
                         */
                        @Override
                        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                            Log.i("tag", "UMAuthListener onError" + throwable.getLocalizedMessage());
                        }

                        /**
                         * 登录取消
                         * @param share_media
                         * @param i
                         */
                        @Override
                        public void onCancel(SHARE_MEDIA share_media, int i) {
                            Log.i("tag", "UMAuthListener onCancel");
                        }
                    });
                    break;
                default:
                    break;
            }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }
}
