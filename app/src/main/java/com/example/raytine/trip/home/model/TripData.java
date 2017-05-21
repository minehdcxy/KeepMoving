package com.example.raytine.trip.home.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by raytine on 2017/5/15.
 */

public class TripData implements Serializable {
    private String tripDescribe;
    private Date tripDate;
    private int tripUpvote;
    private String tripUserName;
    private String tripName;
    private String address;
    private String imageUrl;
    private String tripId;
    private String userId;
    private ArrayList<DiaryTrip> diaryTripList;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }




    public static class DiaryTrip implements Serializable{
        private String diaryContent;
        private String diaryDate;
        private String diaryImageUrl;
        private Date date;


        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public String getDiaryContent() {
            return diaryContent;
        }

        public void setDiaryContent(String diaryContent) {
            this.diaryContent = diaryContent;
        }


        public String getDiaryDate() {
            return diaryDate;
        }

        public void setDiaryDate(String diaryDate) {
            this.diaryDate = diaryDate;
        }

        public String getDiaryImageUrl() {
            return diaryImageUrl;
        }

        public void setDiaryImageUrl(String diaryImageUrl) {
            this.diaryImageUrl = diaryImageUrl;
        }
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTripDescribe() {
        return tripDescribe;
    }

    public void setTripDescribe(String tripDescribe) {
        this.tripDescribe = tripDescribe;
    }

    public Date getTripDate() {
        return tripDate;
    }

    public void setTripDate(Date tripDate) {
        this.tripDate = tripDate;
    }

    public int getTripUpvote() {
        return tripUpvote;
    }

    public void setTripUpvote(int tripUpvote) {
        this.tripUpvote = tripUpvote;
    }

    public String getTripUserName() {
        return tripUserName;
    }

    public void setTripUserName(String tripUserName) {
        this.tripUserName = tripUserName;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public List<DiaryTrip> getDiaryTripList() {
        return diaryTripList;
    }

    public void setDiaryTripList(List<DiaryTrip> diaryTripList) {
        this.diaryTripList = (ArrayList<DiaryTrip>) diaryTripList;
    }


}
