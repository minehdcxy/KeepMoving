package com.example.raytine.keepmoving.buyTickets;

import com.example.raytine.keepmoving.filmDetail.CinemaModel;
import com.example.raytine.keepmoving.user.model.User;

import base.BasePresenter;
import base.BaseView;

/**
 * Created by raytine on 2017/5/7.
 */

public class BuyTicketsContract {
    interface View extends BaseView<Presenter>{
        void updateSuccess(String msg);
        void updateFailed(String msg);
    }

    interface Presenter extends BasePresenter{
        void updateSeat(String cinemaId, CinemaModel.Ticket ticket);

        void updateUserInfo(User user);
    }
}
