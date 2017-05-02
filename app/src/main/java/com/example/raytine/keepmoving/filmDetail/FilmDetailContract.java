package com.example.raytine.keepmoving.filmDetail;

import com.example.raytine.keepmoving.home.model.FilmData;

import java.util.List;

import base.BasePresenter;
import base.BaseView;

/**
 * Created by raytine on 2017/5/2.
 */

public interface FilmDetailContract {
    interface View extends BaseView<Presenter> {
        void loadSuccess(FilmData data);
        void loadFailed(String message);
        void loadCinemaSuccess(List<Object> list);
        void loadCinemaFailed(String message);
    }
    interface Presenter extends BasePresenter{
        void loadFilmDetailMessage(String filmId);
        void loadFilmCinemaMessage(List<String> list);
    }
}
