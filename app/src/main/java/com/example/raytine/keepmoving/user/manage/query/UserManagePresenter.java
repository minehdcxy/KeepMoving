package com.example.raytine.keepmoving.user.manage.query;

import com.example.raytine.keepmoving.api.FilmSearchApi;
import com.example.raytine.keepmoving.home.model.FilmData;

import java.util.List;

import keepInterface.RequestResult;

public class UserManagePresenter implements UserManageContract.Presenter{

    private final String TAG = UserManagePresenter.class.getSimpleName();

    private UserManageContract.View view;

    private FilmSearchApi api;

    public UserManagePresenter(UserManageContract.View view) {
        this.view = view;
        view.setPresenter(this);
        api = new FilmSearchApi();
    }

    @Override
    public void queryFilms() {
        api.searchAllFilm(new FilmData(), new RequestResult() {
            @Override
            public void successfully(String msg, List<Object> objectList) {
                view.querySuccess(objectList);
            }

            @Override
            public void failed(String msg) {
                view.queryFailed(msg);
            }
        });
    }

    @Override
    public void destroy() {
        view = null;
    }

}
