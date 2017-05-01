package com.example.raytine.keepmoving.api;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CloudQueryCallback;
import com.example.raytine.keepmoving.user.model.User;

import keepInterface.RequestResult;

public class UpdateUserInfoApi {

    private final String TAG = UpdateUserInfoApi.class.getSimpleName();

    public void updateUserInfo(User user, final RequestResult requestResult) {
        String sql = "update _User set nickname = '" + user.getNickname() + "', " +
                "gender = '" + user.getGender() + "', " +
                "age = " + user.getAge() + ", " +
                "address = '" + user.getAddress() + "' " +
                "where objectId = '" + user.getObjectId() + "'";
        AVQuery.doCloudQueryInBackground(sql, new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                if (null == e) {
                    requestResult.successfully(null, null);
                } else {
                    requestResult.failed(e.toString());
                }
            }
        });
    }

}
