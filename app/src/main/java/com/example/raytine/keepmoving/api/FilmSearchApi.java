package com.example.raytine.keepmoving.api;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.DeleteCallback;
import com.avos.avoscloud.FindCallback;
import com.example.raytine.keepmoving.home.model.FilmData;

import java.util.ArrayList;
import java.util.List;

import keepInterface.RequestResult;

/**
 * Created by raytine on 2017/5/1.
 */

public class FilmSearchApi {
    private RequestResult requestResult;
    private String TAG = "FilmSearchApi";


    public void test(){
        
    }

    public void searchAllFilm(final FilmData Data, RequestResult result){
        this.requestResult = result;
        第二次修改
        final List<Object> filmDataList = new ArrayList<>();
        AVQuery<AVObject> query = new AVQuery<>("Film");
        query.whereExists("objectId");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if(e == null){
                    for(AVObject object : list){
                        FilmData filmData = new FilmData();
                        filmData.setFilmName(object.getString("filmName"));
                        filmData.setFilmPrice(object.getString("filmPrice"));
                        filmData.setFilmIntroduction(object.getString("filmIntroduction"));
                        filmData.setFilmImage(object.getString("filmImage"));
                        filmData.setFilmId(object.getObjectId());
                        filmDataList.add(filmData);
                    }
                    requestResult.successfully("success", filmDataList);
                }
            }
        });

    }

    public void searchAvroadFilm(final FilmData Data, RequestResult result){
        this.requestResult = result;
        final List<Object> filmDataList = new ArrayList<>();
        AVQuery<AVObject> query = new AVQuery<>("Film");
        query.whereEqualTo("isAbroad", "1");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if(e == null){
                    for(AVObject object : list){
                        FilmData filmData = new FilmData();
                        filmData.setFilmName(object.getString("filmName"));
                        filmData.setFilmPrice(object.getString("filmPrice"));
                        filmData.setFilmIntroduction(object.getString("filmIntroduction"));
                        filmData.setFilmImage(object.getString("filmImage"));
                        filmData.setFilmId(object.getObjectId());
                        filmDataList.add(filmData);
                    }
                    requestResult.successfully("success", filmDataList);
                }
            }
        });

    }

    public void searchHotFilm(final FilmData Data, RequestResult result){
        this.requestResult = result;
        final List<Object> filmDataList = new ArrayList<>();
        AVQuery<AVObject> query = new AVQuery<>("Film");
        query.whereEqualTo("isShow", "3");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if(e == null){
                    for(AVObject object : list){
                        FilmData filmData = new FilmData();
                        filmData.setFilmName(object.getString("filmName"));
                        filmData.setFilmPrice(object.getString("filmPrice"));
                        filmData.setFilmIntroduction(object.getString("filmIntroduction"));
                        filmData.setFilmImage(object.getString("filmImage"));
                        filmData.setFilmId(object.getObjectId());
                        filmDataList.add(filmData);
                    }
                    requestResult.successfully("success", filmDataList);
                }
            }
        });

    }

    public void searchNotShowFilm(final FilmData Data, RequestResult result){
        this.requestResult = result;
        final List<Object> filmDataList = new ArrayList<>();
        AVQuery<AVObject> query = new AVQuery<>("Film");
        query.whereEqualTo("isShow", "2");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if(e == null){
                    for(AVObject object : list){
                        FilmData filmData = new FilmData();
                        filmData.setFilmName(object.getString("filmName"));
                        filmData.setFilmPrice(object.getString("filmPrice"));
                        filmData.setFilmIntroduction(object.getString("filmIntroduction"));
                        filmData.setFilmImage(object.getString("filmImage"));
                        filmData.setFilmId(object.getObjectId());
                        filmDataList.add(filmData);
                    }
                    requestResult.successfully("success", filmDataList);
                }
            }
        });

    }


    public void searchFilm(String content, RequestResult result) {
        this.requestResult = result;
        AVQuery<AVObject> query = new AVQuery<>("Film");
        query.whereContains("filmName", content);
        final List<Object> filmDataList = new ArrayList<>();
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(final List<AVObject> list, final AVException e) {
                if(e == null){
                    for(AVObject object : list){
                        FilmData filmData = new FilmData();
                        filmData.setFilmName(object.getString("filmName"));
                        filmData.setFilmPrice(object.getString("filmPrice"));
                        filmData.setFilmIntroduction(object.getString("filmIntroduction"));
                        filmData.setFilmImage(object.getString("filmImage"));
                        filmData.setFilmId(object.getObjectId());
                        filmDataList.add(filmData);
                    }
                    requestResult.successfully("success", filmDataList);
                }
            }
        });

    }

    public void deleteFilm(String objectId, final RequestResult result) {
        AVObject filmFolder = AVObject.createWithoutData("Film", objectId);
        filmFolder.deleteInBackground(new DeleteCallback() {
            @Override
            public void done(AVException e) {
                if (null == e) {
                    result.successfully(null, null);
                }
            }
        });
    }



}
