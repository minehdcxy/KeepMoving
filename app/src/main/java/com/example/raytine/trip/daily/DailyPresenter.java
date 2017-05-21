package com.example.raytine.trip.daily;

import com.example.raytine.trip.api.DailyApi;
import com.example.raytine.trip.application.MyLeanCloudApplication;
import com.example.raytine.trip.guide.MainActivity;
import com.example.raytine.trip.home.model.TripData;

import java.io.FileNotFoundException;
import java.util.List;

import keepInterface.RequestResult;

/**
 * Created by raytine on 2017/5/18.
 */

public class DailyPresenter implements DailyContract.Presenter {

    private DailyContract.View view;
    private DailyApi api;
    DailyPresenter(DailyContract.View view){
        this.view = view;
        view.setPresenter(this);
        api = new DailyApi();
    }


    @Override
    public void destroy() {

    }

    @Override
    public void updateDailyTrip(TripData data) throws FileNotFoundException {
        api.updateTripRecord(data, new RequestResult() {
            @Override
            public void successfully(String msg, List<Object> objectList) {
                view.loadFileSuccess(msg);
            }

            @Override
            public void failed(String msg) {
                view.loadFielFailed(msg);
            }
        });
    }

    @Override
    public void updateTrip(TripData data) {
        api.updateTrip(data, new RequestResult() {
            @Override
            public void successfully(String msg, List<Object> objectList) {
                view.updateTripRecordSuccess(msg);
            }

            @Override
            public void failed(String msg) {
                view.updateTripRecordFailed(msg);

            }
        });
    }

    @Override
    public void loadDailyImageFile(TripData data) throws FileNotFoundException {
        api.loadDailyImageFile(data, new RequestResult() {
            @Override
            public void successfully(String msg, List<Object> objectList) {
                view.updateDailySuccess(msg);
            }

            @Override
            public void failed(String msg) {
                view.updateDailyFailed(msg);
            }
        });
    }


    @Override
    public void updateDaily(String tripId) {
        api.updateTripDaily(tripId, new RequestResult() {
            @Override
            public void successfully(String msg, List<Object> objectList) {
                TripData data = (TripData) objectList.get(0);
                view.updateSuccess(msg, data);
            }

            @Override
            public void failed(String msg) {
                view.updateFailed(msg);
            }
        });
    }

    @Override
    public void updateUserTirp(String userId) {
        api.updateUserTrip("", userId, new RequestResult() {
            @Override
            public void successfully(String msg, List<Object> objectList) {
                view.updateDailySuccess(msg);
            }

            @Override
            public void failed(String msg) {
                view.updateDailyFailed(msg);
            }
        });
    }
}
