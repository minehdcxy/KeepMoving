package com.example.raytine.keepmoving.filmDetail;

import com.example.raytine.keepmoving.api.FilmDetailApi;
import com.example.raytine.keepmoving.home.model.FilmData;

import java.util.List;

import keepInterface.RequestResult;

/**
 * Created by raytine on 2017/5/2.
 */

public class FilmDetailPresenter implements FilmDetailContract.Presenter{
    FilmDetailContract.View view;
    FilmDetailApi api;
    FilmDetailPresenter(FilmDetailContract.View view){
        this.view = view;
        view.setPresenter(this);
         api = new FilmDetailApi();
    }


    @Override
    public void destroy() {

    }

    @Override
    public void loadFilmDetailMessage(String filmId) {
        api.searchFilmDetail(filmId, new RequestResult() {
            @Override
            public void successfully(String msg, List<Object> objectList) {
                view.loadSuccess((FilmData)objectList.get(0));
            }

            @Override
            public void failed(String msg) {
                view.loadFailed(msg);
            }
        });
    }

    @Override
    public void loadFilmCinemaMessage(List<String> list) {
        api.searchFilmCinema(list, new RequestResult() {
            @Override
            public void successfully(String msg, List<Object> objectList) {
                view.loadCinemaSuccess(objectList);
            }

            @Override
            public void failed(String msg) {
                view.loadCinemaFailed(msg);
            }
        });
    }
}
