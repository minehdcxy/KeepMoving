package com.example.raytine.trip.api;

import com.alibaba.fastjson.JSONArray;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.example.raytine.trip.home.model.TripData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import keepInterface.RequestResult;

/**
 * Created by raytine on 2017/5/17.
 */

public class TripApi {
    private RequestResult requestResult;

    public void LoadTripRecord(RequestResult result){
        this.requestResult = result;
        final List<Object> tripRecordList = new ArrayList<>();
        AVQuery<AVObject> query = new AVQuery<>("trip_daily");
        query.whereExists("objectId");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if(e == null){
                    for(AVObject object : list){
                        TripData data = new TripData();
                        data.setAddress(object.getString("address"));
                        data.setTripDescribe(object.getString("tirpDescribe"));
                        data.setTripDate(object.getDate("createdAt"));
                        data.setTripUpvote(object.getInt("upovte"));
                        data.setImageUrl(object.getString("imageUrl"));
                        data.setTripId(object.getObjectId());
                        data.setTripUserName(object.getString("tripUserName"));
                        org.json.JSONArray jsonArray = object.getJSONArray("daily");
                        List<TripData.DiaryTrip> diaryTripList = new ArrayList<>();
                        for(int i = 0;i<jsonArray.length();i++){
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                TripData.DiaryTrip diaryTrip = new TripData.DiaryTrip();
                                diaryTrip.setDiaryImageUrl(jsonObject.getString("diaryImageUrl"));
                                diaryTrip.setDiaryContent(jsonObject.getString("diaryContent"));
                                diaryTripList.add(diaryTrip);
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                        data.setDiaryTripList(diaryTripList);
                        tripRecordList.add(data);
                    }
                    requestResult.successfully("success", tripRecordList);
                }
            }
        });
    }
}
