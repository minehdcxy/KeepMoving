package com.example.raytine.trip.api;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.feedback.Comment;
import com.example.raytine.trip.tripContent.CommentModel;

import java.util.ArrayList;
import java.util.List;

import keepInterface.RequestResult;

/**
 * Created by raytine on 2017/5/21.
 */

public class CommentApi {
    private RequestResult requestResult;

    public void loadComment(String tripId, RequestResult result){
        this.requestResult = result;
        final List<Object> commentList = new ArrayList<>();
        AVQuery<AVObject> query = new AVQuery<>("comment");
        query.whereEqualTo("tripId", tripId);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if(e == null){
                    for(AVObject object : list){
                        CommentModel model = new CommentModel();
                        model.setContent(object.getString("content"));
                        commentList.add(model);
                    }
                    requestResult.successfully("success", commentList);
                }else{
                    requestResult.failed(e.toString());
                }
            }
        });

    }
}
