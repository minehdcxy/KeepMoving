package com.example.raytine.trip.tripContent;

import com.example.raytine.trip.daily.DailyContract;

import java.util.List;

import base.BasePresenter;
import base.BaseView;

/**
 * Created by raytine on 2017/5/21.
 */

public interface CommentContract {

    interface View extends BaseView<Presenter>{
        void loadContentSuccess(String msg, List<Object> objectList);
        void loadContentFailed(String msg);
    }

    interface Presenter extends BasePresenter{
        void loadComment(String tripId);
    }
}
