package com.example.raytine.keepmoving.user.info;

import com.example.raytine.keepmoving.user.model.User;

import base.BasePresenter;
import base.BaseView;

public interface UserInfoContract {

    interface View extends BaseView<Presenter> {
        void updateSuccess();
        void updateFailed();
    }

    interface Presenter extends BasePresenter {
        void update(User user);
    }

}
