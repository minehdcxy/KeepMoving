package com.example.raytine.keepmoving.user.manage.edit;

import android.os.Bundle;
import android.util.Log;

import com.example.raytine.keepmoving.api.FilmDetailApi;
import com.example.raytine.keepmoving.home.model.FilmData;

import java.util.ArrayList;
import java.util.List;

import keepInterface.RequestResult;

public class ManageFilmInfoPresenter implements ManageFilmInfoContract.Presenter{

    private final String TAG = ManageFilmInfoPresenter.class.getSimpleName();

    private ManageFilmInfoContract.View view;

    private FilmDetailApi api;

    private FilmData filmData;

    private int type;

    public ManageFilmInfoPresenter(ManageFilmInfoContract.View view) {
        this.view = view;
        view.setPresenter(this);
        api = new FilmDetailApi();
    }

    @Override
    public void queryFilmInfo(String filmId) {
        view.showDialog("正在加载...");
        api.searchFilmDetail(filmId, new RequestResult() {
            @Override
            public void successfully(String msg, List<Object> objectList) {
                if ("ok".equalsIgnoreCase(msg)) {
                    filmData = (FilmData) objectList.get(0);
                    queryFilmPicture(filmData.getFilmImage());
                }
            }

            @Override
            public void failed(String msg) {

            }
        });
    }

    private void queryFilmPicture(String url) {
        api.queryFilmPicture(url, new RequestResult() {
            @Override
            public void successfully(String msg, List<Object> objectList) {
                view.hideDialog();
                Bundle bundle = (Bundle) objectList.get(0);
                byte[] bytes = bundle.getByteArray("picture");
                view.initData(filmData, bytes);
            }

            @Override
            public void failed(String msg) {

            }
        });
    }

    @Override
    public void updateFilmInfo(FilmData filmData, String path) {
        this.filmData = filmData;
        type = 1;
        view.showDialog("正在更新...");
        if (null == path) {
            updateFilm();
        } else {
            uploadPicture(path);
        }
    }

    @Override
    public void uploadFilmInfo(FilmData filmData, String path) {
        this.filmData = filmData;
        type = 2;
        view.showDialog("正在上传...");
        if (null == path) {
            filmData.setFilmImage("http://ac-pqum8xdd.clouddn.com/0f1c5abc6e3b1a4bee35.jpg");
            uploadFilm();
        } else {
            uploadPicture(path);
        }
    }

    private void uploadPicture(String path) {
        api.uploadPicture(path, new RequestResult() {
            @Override
            public void successfully(String objectId, List<Object> objectList) {
                Log.i(TAG, "照片上传成功");
                filmData.setFilmImage(objectId);
                switch (type) {
                    case 1:
                        updateFilm();
                        break;
                    case 2:
                        uploadFilm();
                        break;
                }
            }

            @Override
            public void failed(String msg) {

            }
        });
    }

    private void updateFilm() {
        api.updateFilm(filmData, new RequestResult() {
            @Override
            public void successfully(String filmObjectId, List<Object> objectList) {
                updateCinemaInfo(filmObjectId);
            }

            @Override
            public void failed(String msg) {

            }
        });
    }

    private void uploadFilm() {
        api.uploadFilm(filmData, new RequestResult() {
            @Override
            public void successfully(String filmObjectId, List<Object> objectList) {
                updateCinemaInfo(filmObjectId);
            }

            @Override
            public void failed(String msg) {

            }
        });
    }

    private void updateCinemaInfo(String filmObjectId) {
        String filmStr = filmData.getFilmStr();
        String[] str = filmStr.split("\\|");
        List<String> list = new ArrayList<>();
        for (String s : str) {
            String cinemaId = s.split(",")[0];
            list.add(cinemaId);
        }
        api.updateCinemaInfo(filmObjectId, list, new RequestResult() {
            @Override
            public void successfully(String msg, List<Object> objectList) {
                view.complete();
            }

            @Override
            public void failed(String msg) {

            }
        });
    }

    @Override
    public void destroy() {
        view = null;
    }


}
