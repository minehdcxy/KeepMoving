package com.example.raytine.trip.register;

import base.BasePresenter;
import base.BaseView;

/**
 * Created by raytine on 2017/4/12.
 */

public interface RegisterContract {
    interface View extends BaseView<Presenter>{
        void registerSuccess(String msg);
        void registerFailed(String msg);
    }

    interface Presenter extends BasePresenter{
        void register(String phoneNumber, String password);
        void validate(String phoneNumber, String code);
    }
}
