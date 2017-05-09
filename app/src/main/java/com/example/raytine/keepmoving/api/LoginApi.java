package com.example.raytine.keepmoving.api;

import android.util.Log;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.CloudQueryCallback;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.LogInCallback;
import com.example.raytine.keepmoving.application.MyLeanCloudApplication;
import com.example.raytine.keepmoving.user.model.User;
import com.google.gson.Gson;

import java.util.List;

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
                    result.successfully("登录成功", null);
                    getUserInfo(avUser.getObjectId());
                }else{
                    Log.i(TAG,String.valueOf(e));
                }
            }
        });
    }

    private void getUserInfo(final String objectId) {
        AVQuery<AVObject> avQuery = new AVQuery<>("_User");
        avQuery.getInBackground(objectId, new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                Log.i(TAG, avObject.toString());
                User user = new User();
                user.setObjectId(objectId);
                user.setTelephone(avObject.getString("mobilePhoneNumber"));
                user.setType(avObject.getInt("type"));
                user.setNickname(avObject.getString("nickname"));
                user.setGender(avObject.getString("gender"));
                user.setAge(avObject.getInt("age"));
                user.setAddress(avObject.getString("address"));
                user.setWallet((float) avObject.getInt("wallet"));
                User.Card card = new Gson().fromJson(avObject.getJSONObject("card").toString(), User.Card.class);
                user.setCard(card);
                Log.i(TAG, user.toString());
                MyLeanCloudApplication.getIns().setUser(user);
            }
        });
    }



}
