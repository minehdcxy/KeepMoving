package com.example.raytine.keepmoving.api;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;

import keepInterface.RequestResult;

/**
 * Created by raytine on 2017/4/12.
 */

public class LoginApi {
    private static String TAG = "LoginApi";
    private RequestResult result;

    public void login(String phoneNumber, String password, final RequestResult requestResult) {
        this.result = requestResult;
        AVUser.logInInBackground(phoneNumber, password, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                if(e == null){
                    result.successfully("登录成功");
                }else{
                    Log.i(TAG,String.valueOf(e));
                }
            }
        });
    }
}
