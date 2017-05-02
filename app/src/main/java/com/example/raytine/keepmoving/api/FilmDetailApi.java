package com.example.raytine.keepmoving.api;

import android.nfc.Tag;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CloudQueryCallback;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.example.raytine.keepmoving.filmDetail.CinemaModel;
import com.example.raytine.keepmoving.home.model.FilmData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import keepInterface.RequestResult;

/**
 * Created by raytine on 2017/5/2.
 */

public class FilmDetailApi {
    private RequestResult result;
    private String Tag = "FilmDetailApi";

    public void searchFilmDetail(String filmId, final RequestResult result) {
        this.result = result;
        final List<Object> filmDataList = new ArrayList<>();
        AVQuery<AVObject> avQuery = new AVQuery<>("Film");
        avQuery.getInBackground(filmId, new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                if (e == null) {
                    FilmData data = new FilmData();
                    data.setFilmId(avObject.getObjectId());
                    data.setFilmName(avObject.getString("filmName"));
                    data.setFilmAddress(avObject.getString("filmAddress"));
                    data.setFilmTime(avObject.getString("filmTime"));
                    data.setFilmDirector(avObject.getString("filmDirector"));
                    data.setFilmVersion(avObject.getString("filmVersion"));
                    data.setFilmType(avObject.getString("filmType"));
                    data.setFilmStr(avObject.getString("cinemaStr"));
                    filmDataList.add(data);
                    result.successfully("ok", filmDataList);
                } else {
                    result.failed(String.valueOf(e));
                }
            }
        });
    }

    public void searchFilmCinema(List<String> list, final RequestResult result) {
        this.result = result;
        StringBuilder buffer = new StringBuilder("select * from Cinema where ");
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                buffer.append("cinemaId = ").append(list.get(i));
                break;
            }
            buffer.append("cinemaId = ").append(list.get(i)).append(" or ");

        }
        AVQuery.doCloudQueryInBackground(buffer.toString(), new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                if (e == null) {
                    List<Object> cinemaModelList = new ArrayList<>();
                    for (AVObject object : avCloudQueryResult.getResults()) {
                        CinemaModel model = new CinemaModel();
                        model.setAddress(object.getString("cinemaAddress"));
                        model.setCinemaName(object.getString("cinemaName"));
                        model.setCinemaId(String.valueOf(object.getNumber("cinemaId")));
                        cinemaModelList.add(model);
                    }
                    result.successfully("success", cinemaModelList);
                } else {
                    result.failed(String.valueOf(e));
                }

            }
        });
    }

}
