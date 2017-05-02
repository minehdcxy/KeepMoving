package com.example.raytine.keepmoving.user.manage;

import java.util.List;

import base.BasePresenter;
import base.BaseView;

public class UserManageContract {

    interface View extends BaseView<Presenter> {
        void querySuccess(List<Object> objectList);
        void queryFailed(String msg);
    }

    interface Presenter extends BasePresenter {
        void queryFilms();
    }

}
