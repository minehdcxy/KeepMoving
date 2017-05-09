package com.example.raytine.keepmoving.buyTickets;

import com.example.raytine.keepmoving.api.BuyTicketsApi;
import com.example.raytine.keepmoving.api.UpdateUserInfoApi;
import com.example.raytine.keepmoving.filmDetail.CinemaModel;
import com.example.raytine.keepmoving.user.model.User;

import java.util.List;

import keepInterface.RequestResult;

/**
 * Created by raytine on 2017/5/7.
 */

public class BuyTicketsPresenter implements BuyTicketsContract.Presenter {
    private BuyTicketsContract.View view;
    private BuyTicketsApi api;
    private UpdateUserInfoApi userInfoApi;


    BuyTicketsPresenter(BuyTicketsContract.View view){
        this.view = view;
        view.setPresenter(this);
        api = new BuyTicketsApi();
        userInfoApi = new UpdateUserInfoApi();

    }

    @Override
    public void updateSeat(String cinemaId, CinemaModel.Ticket ticket) {
        api.update(cinemaId, ticket, new RequestResult() {
            @Override
            public void successfully(String msg, List<Object> objectList) {
                view.updateSuccess(msg);
            }

            @Override
            public void failed(String msg) {
                view.updateFailed(msg);
            }
        });
    }

    @Override
    public void updateUserInfo(User user) {
        userInfoApi.updateUserInfo(user, new RequestResult() {
            @Override
            public void successfully(String msg, List<Object> objectList) {
                view.updateSuccess(msg);
            }

            @Override
            public void failed(String msg) {
                view.updateFailed(msg);
            }
        });
    }

    @Override
    public void destroy() {

    }




}
