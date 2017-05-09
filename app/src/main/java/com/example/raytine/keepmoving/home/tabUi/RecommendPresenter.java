package com.example.raytine.keepmoving.home.tabUi;

import com.example.raytine.keepmoving.api.FilmSearchApi;
import com.example.raytine.keepmoving.home.model.FilmData;

import java.util.List;

import keepInterface.RequestResult;

/**
 * Created by raytine on 2017/5/1.
 */

public class RecommendPresenter implements RecommendContract.Presenter{
    private RecommendContract.View view;
    private FilmSearchApi api;

    public RecommendPresenter(RecommendContract.View view){
        this.view = view;
        view.setPresenter(this);
        api = new FilmSearchApi();
    }


    @Override
    public void searchAllFilm() {
        api.searchAllFilm(new FilmData(), new RequestResult() {
            @Override
            public void successfully(String msg, List<Object> objectList) {

                view.successfully(objectList);
            }

            @Override
            public void failed(String msg) {

            }
        });
    }

    @Override
    public void searchAbroadFilm() {
        api.searchAvroadFilm(new FilmData(), new RequestResult() {
            @Override
            public void successfully(String msg, List<Object> objectList) {
                view.successfully(objectList);
            }

            @Override
            public void failed(String msg) {
            }
        });
    }

    @Override
    public void searchHotFilm() {
        api.searchHotFilm(new FilmData(), new RequestResult() {
            @Override
            public void successfully(String msg, List<Object> objectList) {
                view.successfully(objectList);
            }

            @Override
            public void failed(String msg) {
            }
        });
    }

    @Override
    public void searchNotShowFilm() {
        api.searchNotShowFilm(new FilmData(), new RequestResult() {
            @Override
            public void successfully(String msg, List<Object> objectList) {
                view.successfully(objectList);
            }

            @Override
            public void failed(String msg) {
            }
        });
    }

    @Override
    public void searchFilm(String content) {
        api.searchFilm(content, new RequestResult() {
            @Override
            public void successfully(String msg, List<Object> objectList) {
                view.successfully(objectList);
            }

            @Override
            public void failed(String msg) {

            }
        });

    }

    @Override
    public void destroy() {

    }
}
