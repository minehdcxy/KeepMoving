package com.example.raytine.keepmoving.user.manage.edit;

public class ManageFilmInfoPresenter implements ManageFilmInfoContract.Presenter{

    private ManageFilmInfoContract.View view;

    public ManageFilmInfoPresenter(ManageFilmInfoContract.View view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void destroy() {
        view = null;
    }
}
