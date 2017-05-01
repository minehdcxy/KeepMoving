package com.example.raytine.keepmoving.user.info;

import com.example.raytine.keepmoving.api.UpdateUserInfoApi;
import com.example.raytine.keepmoving.user.model.User;

import java.util.List;

import keepInterface.RequestResult;

public class UserInfoPresenter implements UserInfoContract.Presenter {

    private UserInfoContract.View view;
    private UpdateUserInfoApi api;

    public UserInfoPresenter(UserInfoContract.View view) {
        this.view = view;
        view.setPresenter(this);
        api = new UpdateUserInfoApi();
    }

    @Override
    public void update(User user) {
        api.updateUserInfo(user, new RequestResult() {
            @Override
            public void successfully(String msg, List<Object> objectList) {
                view.updateSuccess();
            }

            @Override
            public void failed(String msg) {
                view.updateFailed();
            }
        });
    }

    @Override
    public void destroy() {
        view = null;
    }
}
