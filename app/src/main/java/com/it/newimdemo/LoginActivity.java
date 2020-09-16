package com.it.newimdemo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.v2.V2TIMCallback;
import com.tencent.imsdk.v2.V2TIMManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = "LoginActivity";
    private AppCompatEditText mEditTextPhone ;
    private AppCompatButton mButtonLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEditTextPhone =  this.findViewById(R.id.et_phone);

        mButtonLogin = this.findViewById(R.id.btn_login);

        mButtonLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login :
                loginIM();
                break;
        }
    }

    private void loginIM() {
        String phone =   mEditTextPhone.getText().toString();


        V2TIMManager.getInstance().login(phone, GenerateTestUserSig.genTestUserSig(phone), new V2TIMCallback() {
            @Override
            public void onError(int i, String s) {
                Log.i(TAG,"onError......"+i + "     ss==="+s);
            }

            @Override
            public void onSuccess() {
                Log.i(TAG,"onSuccess......");

                Intent intent = new Intent(LoginActivity.this , MainActivity.class);

                startActivity(intent);


            }
        });


    }
}
