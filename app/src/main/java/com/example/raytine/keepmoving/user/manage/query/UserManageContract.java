package com.example.raytine.keepmoving.user.manage.query;

import java.util.List;

import base.BasePresenter;
import base.BaseView;

public class UserManageContract {

    interface View extends BaseView<Presenter> {
        void querySuccess(List<Object> objectList);
        void queryFailed(String msg);
        void deleteSuccess(String msg);
        void deleteFailed(String msg);
    }

    interface Presenter extends BasePresenter {
        void queryFilms();
        void deleteFilm(String objectId);
    }

}
