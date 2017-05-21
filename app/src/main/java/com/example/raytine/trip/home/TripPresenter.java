package com.example.raytine.trip.home;


import com.example.raytine.trip.api.TripApi;

import java.util.List;

import keepInterface.RequestResult;

/**
 * Created by raytine on 2017/5/17.
 */

public class TripPresenter implements TripContract.Presenter {
    private TripContract.View view;
    private TripApi api;

    TripPresenter(TripContract.View view){
        this.view = view;
        view.setPresenter(this);
        api = new TripApi();
    }
    @Override
    public void updateTrip() {
        api.LoadTripRecord(new RequestResult() {
            @Override
            public void successfully(String msg, List<Object> objectList) {
                view.successfully(msg, objectList);
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
