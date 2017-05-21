package com.example.raytine.trip.tripContent;

import com.example.raytine.trip.api.CommentApi;

import java.util.List;

import keepInterface.RequestResult;

/**
 * Created by raytine on 2017/5/21.
 */

public class CommentPresenter implements CommentContract.Presenter {
    private CommentContract.View view;
    private CommentApi api;
    CommentPresenter(CommentContract.View view){
        this.view = view;
        view.setPresenter(this);
        api = new CommentApi();
    }

    @Override
    public void loadComment() {
        api.loadComment(new RequestResult() {
            @Override
            public void successfully(String msg, List<Object> objectList) {
                view.loadContentSuccess(msg, objectList);
            }

            @Override
            public void failed(String msg) {
                view.loadContentFailed(msg);
            }
        });
    }


    @Override
    public void destroy() {

    }


}
