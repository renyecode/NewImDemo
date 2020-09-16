package com.it.newimdemo;

import android.app.Application;
import android.util.Log;

import com.tencent.imsdk.v2.V2TIMAdvancedMsgListener;
import com.tencent.imsdk.v2.V2TIMCallback;
import com.tencent.imsdk.v2.V2TIMGroupListener;
import com.tencent.imsdk.v2.V2TIMGroupMemberInfo;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMMessage;
import com.tencent.imsdk.v2.V2TIMMessageManager;
import com.tencent.imsdk.v2.V2TIMMessageManagerImpl;
import com.tencent.imsdk.v2.V2TIMMessageReceipt;
import com.tencent.imsdk.v2.V2TIMOfflinePushInfo;
import com.tencent.imsdk.v2.V2TIMSDKConfig;
import com.tencent.imsdk.v2.V2TIMSDKListener;
import com.tencent.imsdk.v2.V2TIMSendCallback;
import com.tencent.imsdk.v2.V2TIMSimpleMsgListener;
import com.tencent.imsdk.v2.V2TIMUserFullInfo;
import com.tencent.imsdk.v2.V2TIMUserInfo;
import com.tencent.imsdk.v2.V2TIMValueCallback;

import java.util.List;

public class MyApplication extends Application {
    private int sdkAppID = 1400336007;

    private String TAG = "MyApplication";
    @Override
    public void onCreate() {
        super.onCreate();

        initIm();

    }


    private void initIm(){

        // 1. 从 IM 控制台获取应用 SDKAppID，详情请参考 SDKAppID。
        // 2. 初始化 config 对象
        V2TIMSDKConfig sdkConfig = new V2TIMSDKConfig();
        // 3. 指定 log 输出级别，详情请参考 SDKConfig。
        sdkConfig.setLogLevel(V2TIMSDKConfig.V2TIM_LOG_INFO);

        // 4. 初始化 SDK 并设置 V2TIMSDKListener 的监听对象。
        // initSDK 后 SDK 会自动连接网络，网络连接状态可以在 V2TIMSDKListener 回调里面监听。
        V2TIMManager.getInstance().initSDK(this, sdkAppID, sdkConfig, new V2TIMSDKListener() {
            // 5. 监听 V2TIMSDKListener 回调
            @Override
            public void onConnecting() {
                // 正在连接到腾讯云服务器
                Log.i(TAG,"onConnecting......");
            }
            @Override
            public void onConnectSuccess() {
                // 已经成功连接到腾讯云服务器
                Log.i(TAG,"onConnectSuccess......");
            }
            @Override
            public void onConnectFailed(int code, String error) {
                // 连接腾讯云服务器失败
                Log.i(TAG,"onConnectFailed......"+code+"   error=="+error);

            }

            @Override
            public void onKickedOffline() {
                super.onKickedOffline();
                Log.i(TAG,"onKickedOffline......");
            }

            @Override
            public void onUserSigExpired() {
                super.onUserSigExpired();
                Log.i(TAG,"onUserSigExpired......");
            }

            @Override
            public void onSelfInfoUpdated(V2TIMUserFullInfo info) {
                super.onSelfInfoUpdated(info);
                Log.i(TAG,"onSelfInfoUpdated......");
            }
        });




        V2TIMManager.getInstance().setGroupListener(new V2TIMGroupListener() {
            @Override
            public void onMemberEnter(String groupID, List<V2TIMGroupMemberInfo> memberList) {
                super.onMemberEnter(groupID, memberList);
                Log.i(TAG,"onMemberEnter......"+groupID);
            }

            @Override
            public void onMemberLeave(String groupID, V2TIMGroupMemberInfo member) {
                super.onMemberLeave(groupID, member);
                Log.i(TAG,"onMemberLeave......"+groupID);
            }

            @Override
            public void onReceiveRESTCustomData(String groupID, byte[] customData) {
                super.onReceiveRESTCustomData(groupID, customData);
                Log.i(TAG,"onMemberLeave......"+groupID+ " customData =="+new String(customData));

            }
        });

        V2TIMManager.getMessageManager().addAdvancedMsgListener(new V2TIMAdvancedMsgListener() {
            @Override
            public void onRecvNewMessage(V2TIMMessage msg) {
                super.onRecvNewMessage(msg);
                Log.i(TAG,"onRecvNewMessage......"+msg.getElemType());
            }

            @Override
            public void onRecvC2CReadReceipt(List<V2TIMMessageReceipt> receiptList) {
                super.onRecvC2CReadReceipt(receiptList);
                Log.i(TAG,"onRecvC2CReadReceipt......");
            }

            @Override
            public void onRecvMessageRevoked(String msgID) {
                super.onRecvMessageRevoked(msgID);
                Log.i(TAG,"onRecvMessageRevoked......");

            }
        });




        V2TIMManager.getInstance().addSimpleMsgListener(new V2TIMSimpleMsgListener() {
            @Override
            public void onRecvC2CTextMessage(String msgID, V2TIMUserInfo sender, String text) {
                super.onRecvC2CTextMessage(msgID, sender, text);

                Log.i(TAG,"onRecvC2CTextMessage...msgID..."+msgID);
                Log.i(TAG,"onRecvC2CTextMessage....text.."+text);
                Log.i(TAG,"onRecvC2CTextMessage...sendergetFaceUrl...."+sender.getFaceUrl());
                Log.i(TAG,"onRecvC2CTextMessage....getNickName.."+sender.getNickName());
                Log.i(TAG,"onRecvC2CTextMessage...getUserID..."+sender.getUserID());
            }

            @Override
            public void onRecvC2CCustomMessage(String msgID, V2TIMUserInfo sender, byte[] customData) {
                super.onRecvC2CCustomMessage(msgID, sender, customData);

                Log.i(TAG,"onRecvC2CCustomMessage...msgID..."+msgID);
                Log.i(TAG,"onRecvC2CCustomMessage....customData.."+new String(customData));
                Log.i(TAG,"onRecvC2CCustomMessage...sendergetFaceUrl...."+sender.getFaceUrl());
                Log.i(TAG,"onRecvC2CCustomMessage....getNickName.."+sender.getNickName());
                Log.i(TAG,"onRecvC2CCustomMessage...getUserID..."+sender.getUserID());
            }

            @Override
            public void onRecvGroupTextMessage(String msgID, String groupID, V2TIMGroupMemberInfo sender, String text) {
                super.onRecvGroupTextMessage(msgID, groupID, sender, text);

                Log.i(TAG,"onRecvGroupTextMessage...msgID..."+msgID);
                Log.i(TAG,"onRecvGroupTextMessage....text.."+text);
                Log.i(TAG,"onRecvGroupTextMessage....groupID.."+groupID);
                Log.i(TAG,"onRecvGroupTextMessage...sendergetFaceUrl...."+sender.getFaceUrl());
                Log.i(TAG,"onRecvGroupTextMessage....getNickName.."+sender.getNickName());
                Log.i(TAG,"onRecvGroupTextMessage...getUserID..."+sender.getUserID());
                Log.i(TAG,"onRecvGroupTextMessage...getUserID..."+sender.getUserID());

            }

            @Override
            public void onRecvGroupCustomMessage(String msgID, String groupID, V2TIMGroupMemberInfo sender, byte[] customData) {
                super.onRecvGroupCustomMessage(msgID, groupID, sender, customData);

                Log.i(TAG,"onRecvGroupCustomMessage...msgID..."+msgID);
                Log.i(TAG,"onRecvGroupCustomMessage....text.."+new String(customData));
                Log.i(TAG,"onRecvGroupCustomMessage....groupID.."+groupID);
                Log.i(TAG,"onRecvGroupCustomMessage...sendergetFaceUrl...."+sender.getFaceUrl());
                Log.i(TAG,"onRecvGroupCustomMessage....getNickName.."+sender.getNickName());
                Log.i(TAG,"onRecvGroupCustomMessage...getUserID..."+sender.getUserID());
                Log.i(TAG,"onRecvGroupCustomMessage...getUserID..."+sender.getUserID());

            }
        });
    }
}
