package com.example.raytine.keepmoving.api;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import com.example.raytine.keepmoving.filmDetail.CinemaModel;

import keepInterface.RequestResult;

/**
 * Created by raytine on 2017/5/7.
 */

public class BuyTicketsApi {
    private RequestResult requestResult;

    public void update(String cinemaId, CinemaModel.Ticket ticket, final RequestResult result){
        this.requestResult = result;
        AVObject todo = AVObject.createWithoutData("Cinema", cinemaId);
        todo.put("tickets", ticket);
        todo.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if(e == null){
                    result.successfully("更新成功", null);
                } else {
                    result.failed(e.toString());
                }
            }
        });



    }
}
