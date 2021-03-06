package com.example.raytine.keepmoving.login;

import com.example.raytine.keepmoving.api.LoginApi;

import java.util.List;

import keepInterface.RequestResult;

/**
 * Created by raytine on 2017/4/12.
 */

class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View view;
    private LoginApi api;
    LoginPresenter(LoginContract.View view) {
        this.view = view;
        view.setPresenter(this);
        api = new LoginApi();
    }


    @Override
    public void login(String phoneNumber, String password) {
        api.login(phoneNumber, password, new RequestResult() {
            @Override
            public void successfully(String msg, List<Object> objectList) {
                view.loginSuccess(msg);
            }

            @Override
            public void failed(String msg) {
                view.loginFailed(msg);
            }
        });
    }


    @Override
    public void destroy() {

    }
}
