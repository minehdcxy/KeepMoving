package com.example.raytine.trip.daily;


import com.example.raytine.trip.home.model.TripData;

import java.io.FileNotFoundException;

import base.BasePresenter;
import base.BaseView;

/**
 * Created by raytine on 2017/5/18.
 */

public interface DailyContract {
    interface View extends BaseView<Presenter>{
        void loadFileSuccess(String msg);
        void loadFielFailed(String msg);
        void updateTripRecordSuccess(String msg);
        void updateTripRecordFailed(String msg);
        void updateDailySuccess(String msg);
        void updateDailyFailed(String msg);
        void updateSuccess(String msg, TripData data);
        void updateFailed(String msg);
    }

    interface Presenter extends BasePresenter{
        void updateDailyTrip(TripData data) throws FileNotFoundException;
        void updateTrip(TripData data);
        void loadDailyImageFile(TripData data) throws FileNotFoundException;
        void updateDaily(String tripId);
        void updateUserTirp(String userId);
    }
}
