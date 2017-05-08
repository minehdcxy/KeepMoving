package com.example.raytine.keepmoving.user.manage.edit;

import com.example.raytine.keepmoving.home.model.FilmData;

import base.BasePresenter;
import base.BaseView;

public class ManageFilmInfoContract {

    interface View extends BaseView<Presenter> {
        void initData(FilmData filmData, byte[] picture);
        void showDialog(String msg);
        void hideDialog();
        void complete();
    }

    interface Presenter extends BasePresenter {
        void queryFilmInfo(String filmId);
        void updateFilmInfo(FilmData filmData, String path);
        void uploadFilmInfo(FilmData filmData, String path);
    }

}
