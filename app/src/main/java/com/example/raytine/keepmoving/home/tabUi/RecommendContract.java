package com.example.raytine.keepmoving.home.tabUi;

import java.util.List;

import base.BasePresenter;
import base.BaseView;

/**
 * Created by raytine on 2017/5/1.
 */

public class RecommendContract {
    interface View extends BaseView<Presenter>{
        void successfully(List<Object> objectList);
    }
    interface Presenter extends BasePresenter{
        void searchAllFilm();

    }
}
