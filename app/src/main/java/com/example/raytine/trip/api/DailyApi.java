package com.example.raytine.trip.api;

import android.text.TextUtils;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;
import com.example.raytine.trip.home.model.TripData;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import keepInterface.RequestResult;

/**
 * Created by raytine on 2017/5/18.
 */

public class DailyApi {
    private RequestResult requestResult;

    public void updateTripRecord(final TripData data, final RequestResult result) throws FileNotFoundException {
        this.requestResult = result;
        final AVFile file = AVFile.withAbsoluteLocalPath("youth.jpg", data.getImageUrl());
        file.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if(e == null){
                    data.setImageUrl(file.getUrl());
                    requestResult.successfully("文件上传成功", null);
                }else{
                    requestResult.failed("文件上传失败");
                }
            }
        });
    }

    public void loadDailyImageFile(final TripData data, final RequestResult result) throws FileNotFoundException {
        this.requestResult = result;
        final AVFile file = AVFile.withAbsoluteLocalPath("youth.jpg", data.getDiaryTripList().get(data.getDiaryTripList().size()-1).getDiaryImageUrl());
        file.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if(e == null){
                    data.getDiaryTripList().get(data.getDiaryTripList().size() - 1).setDiaryImageUrl(file.getUrl());
                    updateDaily(data, result);
                    requestResult.successfully("文件上传成功", null);
                }else{
                    requestResult.failed("文件上传失败");
                }
            }
        });


    }

    public void updateTrip(final TripData data, final RequestResult result){
        this.requestResult = result;
        final AVObject tripData = new AVObject("trip_daily");
        tripData.put("address", data.getAddress());
        tripData.put("daily", data.getDiaryTripList());
        tripData.put("tirpDescribe", data.getTripDescribe());
        tripData.put("imageUrl", data.getImageUrl());
        tripData.put("userId", data.getUserId());
        tripData.put("tripName", data.getTripName());
        tripData.put("tripUserName","minehdcxy");
        tripData.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    updateUserTrip(tripData.getObjectId(), data.getUserId(), result);
                }
            }
        });// 保存到服务端
    }

    public void updateUserTrip(final String tripId, String userId, final RequestResult result){
        AVObject todo = AVObject.createWithoutData("_User", userId);
        todo.put("tripId", tripId);
        todo.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if(e == null){
                    result.successfully(tripId, null);
                }
            }
        });
    }

    public void updateDaily(TripData data, RequestResult result){
        this.requestResult = result;
        AVObject todo = AVObject.createWithoutData("trip_daily", data.getTripId());
        todo.put("daily", data.getDiaryTripList());
        todo.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if(e == null){
                    requestResult.successfully("保存成功", null);
                }else{
                    requestResult.failed(e.toString());
                }
            }
        });
    }

    public void updateTripDaily(final String tripId, RequestResult result){
        this.requestResult = result;
        final List<Object> trip = new ArrayList<>();
        AVQuery<AVObject> avQuery = new AVQuery<>("trip_daily");
        avQuery.getInBackground(tripId, new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                TripData data = new TripData();
                data.setTripName(avObject.getString("tripName"));
                data.setAddress(avObject.getString("address"));
                data.setImageUrl(avObject.getString("imageUrl"));
                data.setUserId(avObject.getString("userId"));
                data.setDiaryTripList(avObject.getList("daily"));
                data.setTripDate(avObject.getDate("createdAt"));
                data.setTripId(tripId);
                trip.add(data);
                requestResult.successfully("游记拉取成功", trip);
            }
        });

    }
}
