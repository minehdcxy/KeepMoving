package com.example.raytine.keepmoving.api;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CloudQueryCallback;
import com.avos.avoscloud.SaveCallback;
import com.example.raytine.keepmoving.user.model.User;

import keepInterface.RequestResult;

public class UpdateUserInfoApi {

    private final String TAG = UpdateUserInfoApi.class.getSimpleName();

    public void updateUserInfo(User user, final RequestResult requestResult) {

        AVObject todo = AVObject.createWithoutData("_User", user.getObjectId());
        todo.put("nickname", user.getNickname());
        todo.put("gender", user.getGender());
        todo.put("age", user.getAge());
        todo.put("address", user.getAddress());
        todo.put("card", user.getCard());
        todo.put("wallet", user.getWallet());
        todo.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (null == e) {
                    requestResult.successfully("更新成功", null);
                } else {
                    requestResult.failed(e.toString());
                }
            }
        });
    }

}
