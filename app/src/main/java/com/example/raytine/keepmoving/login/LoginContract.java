package com.example.raytine.keepmoving.login;

import base.BasePresenter;
import base.BaseView;

/**
 * Created by raytine on 2017/4/13.
 */

public interface LoginContract {
    interface View extends BaseView<Presenter>{
        void loginSuccess(String msg);
        void loginFailed(String msg);
    }
    interface Presenter extends BasePresenter{
        void login(String phoneNumber, String password);
    }
}
