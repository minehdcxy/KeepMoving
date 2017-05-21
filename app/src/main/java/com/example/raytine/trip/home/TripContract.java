package com.example.raytine.trip.home;

import com.example.raytine.trip.home.model.TripData;

import java.util.List;

import base.BasePresenter;
import base.BaseView;
import keepInterface.RequestResult;

/**
 * Created by raytine on 2017/5/17.
 */

public interface TripContract {
    interface View extends BaseView<Presenter>{
        void successfully(String msg, List<Object> tripDataList);
        void failed(String msg);

    }

    interface Presenter extends BasePresenter{
        void updateTrip();
    }

}
