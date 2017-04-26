package com.example.raytine.keepmoving.register;

import com.example.raytine.keepmoving.api.RegisterApi;

import keepInterface.RequestResult;

/**
 * Created by raytine on 2017/4/12.
 */

class RegisterPresenter implements RegisterContract.Presenter {
    private RegisterContract.View registerView;
    private RegisterApi registerApi;

    RegisterPresenter(RegisterContract.View view) {
        this.registerView = view;
        registerView.setPresenter(this);
        registerApi = new RegisterApi();
    }

    @Override
    public void register(String phoneNumber, String password) {
        RegisterApi.register(phoneNumber, password);
    }

    @Override
    public void validate(String phoneNumber, String code) {
        registerApi.validate(code, new RequestResult() {
            @Override
            public void successfully(String msg) {
                registerView.registerSuccess(msg);
            }

            @Override
            public void failed(String msg) {
                registerView.registerFailed(msg);
            }
        });
    }

    @Override
    public void destory() {

    }

}
