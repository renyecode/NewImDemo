package com.it.newimdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.v2.V2TIMCallback;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMMessage;
import com.tencent.imsdk.v2.V2TIMValueCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = "LoginActivity";
    private AppCompatEditText mEditTextText ;
    private AppCompatButton mButtonSend;
    private AppCompatButton mbtn_quitgroup;
    private AppCompatButton mbtn_logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mEditTextText =  this.findViewById(R.id.et_text);

        mButtonSend = this.findViewById(R.id.btn_send);
        mbtn_quitgroup = this.findViewById(R.id.btn_quitgroup);
        mbtn_logout = this.findViewById(R.id.btn_logout);

        mButtonSend.setOnClickListener(this);


    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        logout();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_send:
                sendMsg();
                break;
            case R.id.btn_logout:

                break;
            case R.id.btn_quitgroup:
                quitgroup();
                break;
        }
    }

    private void sendMsg() {
    String groupId = "@TGS#aK47YKHGY";
    String msg =    mEditTextText.getText().toString();

        byte[] msgCustom = msg.getBytes();

        V2TIMManager.getInstance().sendGroupCustomMessage(msgCustom, groupId, V2TIMMessage.V2TIM_PRIORITY_HIGH, new V2TIMValueCallback<V2TIMMessage>() {
            @Override
            public void onError(int i, String s) {
                Log.i(TAG,"onError=="+i + "     s==="+s);
            }

            @Override
            public void onSuccess(V2TIMMessage v2TIMMessage) {
                Log.i(TAG,"onSuccess=="+v2TIMMessage.toString());
            }
        });
    }

    private void logout(){
        V2TIMManager.getInstance().logout(new V2TIMCallback() {
            @Override
            public void onError(int i, String s) {
                Log.d(TAG, "onError:登出失败 ");
            }

            @Override
            public void onSuccess() {
                Log.d(TAG, "onSuccess: 登出成功");

            }
        });


    }
    private void quitgroup(){
        String groupid=mEditTextText.getText().toString();
        V2TIMManager.getInstance().quitGroup(groupid, new V2TIMCallback() {
            @Override
            public void onError(int i, String s) {
                Log.d(TAG, "onError: 退出失败："+s);

            }

            @Override
            public void onSuccess() {
                Log.d(TAG, "onSuccess: 退群成功");

            }
        });



    }
}
