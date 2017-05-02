package com.example.raytine.keepmoving.api;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
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

    public void searchAllFilm(final FilmData Data, RequestResult result){
        this.requestResult = result;
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
                        filmData.setFilmIntrodution(object.getString("filmIntroduction"));
                        filmData.setFilmImage(object.getString("imageUrl"));
                        filmData.setFilmId(object.getObjectId());
                        filmDataList.add(filmData);
                    }
                    requestResult.successfully("success", filmDataList);
                }
            }
        });

    }
}
