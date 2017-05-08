package com.example.raytine.keepmoving.api;

import android.nfc.Tag;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CloudQueryCallback;
import com.avos.avoscloud.DeleteCallback;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.GetDataCallback;
import com.avos.avoscloud.SaveCallback;
import com.example.raytine.keepmoving.application.MyLeanCloudApplication;
import com.example.raytine.keepmoving.filmDetail.CinemaModel;
import com.example.raytine.keepmoving.home.model.FilmData;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import keepInterface.RequestResult;

/**
 * Created by raytine on 2017/5/2.
 */

public class FilmDetailApi {

    private final String TAG = FilmDetailApi.class.getSimpleName();

    private RequestResult result;

    private CinemaModel.Ticket ticket;

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
                    data.setFilmImage(avObject.getString("filmImage"));
                    data.setFilmPrice(avObject.getString("filmPrice"));
                    data.setFilmIntroduction(avObject.getString("filmIntroduction"));
                    filmDataList.add(data);
                    result.successfully("ok", filmDataList);
                } else {
                    result.failed(String.valueOf(e));
                }
            }
        });
    }

    public void queryFilmPicture(String url, final RequestResult result) {
        AVFile file = new AVFile("FilmPicture.jpg", url, new HashMap<String, Object>());
        file.getDataInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] bytes, AVException e) {
                if (null == e) {
                    List<Object> list = new ArrayList<>();
                    Bundle bundle = new Bundle();
                    bundle.putByteArray("picture", bytes);
                    list.add(bundle);
                    result.successfully("ok", list);

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

    public void uploadPicture(String path, final RequestResult result) {
        this.result = result;
        try {
            final AVFile file = AVFile.withAbsoluteLocalPath("FilmPicture.jpg", path);
            file.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (null == e) {
                        Log.i(TAG, file.getUrl());
                        result.successfully(file.getUrl(), null);
                    }
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void updateFilm(FilmData filmData, final RequestResult result) {
        final AVObject filmFolder = AVObject.createWithoutData("Film", filmData.getFilmId());
        filmFolder.put("filmName", filmData.getFilmName());// 设置名称
        filmFolder.put("filmPrice", filmData.getFilmPrice());
        filmFolder.put("filmType", filmData.getFilmType());
        filmFolder.put("filmVersion", filmData.getFilmVersion());
        filmFolder.put("filmAddress", filmData.getFilmAddress());
        filmFolder.put("filmTime", filmData.getFilmTime());
        filmFolder.put("filmDirector", filmData.getFilmDirector());
        filmFolder.put("cinemaStr", filmData.getFilmStr());
        filmFolder.put("filmIntroduction", filmData.getFilmIntroduction());
        if (!TextUtils.isEmpty(filmData.getFilmImage())) {
            filmFolder.put("filmImage", filmData.getFilmImage());
        }
        filmFolder.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (null == e) {
                    result.successfully(filmFolder.getObjectId(), null);
                }
            }
        });// 保存到服务端
    }

    public void uploadFilm(FilmData filmData, final RequestResult result) {
        final AVObject filmFolder = new AVObject("Film");// 构建对象
        filmFolder.put("filmName", filmData.getFilmName());// 设置名称
        filmFolder.put("filmPrice", filmData.getFilmPrice());
        filmFolder.put("filmType", filmData.getFilmType());
        filmFolder.put("filmVersion", filmData.getFilmVersion());
        filmFolder.put("filmAddress", filmData.getFilmAddress());
        filmFolder.put("filmTime", filmData.getFilmTime());
        filmFolder.put("filmDirector", filmData.getFilmDirector());
        filmFolder.put("cinemaStr", filmData.getFilmStr());
        filmFolder.put("filmIntroduction", filmData.getFilmIntroduction());
        if (!TextUtils.isEmpty(filmData.getFilmImage())) {
            filmFolder.put("filmImage", filmData.getFilmImage());
        }
        filmFolder.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (null == e) {
                    result.successfully(filmFolder.getObjectId(), null);
                }
            }
        });// 保存到服务端
    }

    private String filmObjectId;
    private List<String> cinemaIds;
    public void updateCinemaInfo(String filmObjectId, List<String> cinemaIds, RequestResult result) {
        this.filmObjectId = filmObjectId;
        this.cinemaIds = cinemaIds;
        this.result = result;
        updateCinemaInfo();
    }

    int index = 0;
    public void updateCinemaInfo() {
        if (cinemaIds.size() == index) {
            result.successfully(null, null);
        } else {
            searchCinemaById(cinemaIds.get(index), filmObjectId);
        }
    }

    private void searchCinemaById(String cinemaId, final String filmObjectId) {
        String sql = "select * from Cinema where cinemaId = " + cinemaId;
        AVQuery.doCloudQueryInBackground(sql, new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                if (null == e) {
                    AVObject object = avCloudQueryResult.getResults().get(0);
                    ticket = new Gson().fromJson(object.getJSONObject("tickets").toString(), CinemaModel.Ticket.class);
                    updateCinema(object.getObjectId(), filmObjectId);
                }
            }
        });
    }

    private void updateCinema(String cinemaId, String filmObjectId) {
        AVObject cinemaFolder = AVObject.createWithoutData("Cinema", cinemaId);
        Integer[] integers = new Integer[50];
        for (int i = 0; i < integers.length; i++) {
            integers[i] = 1;
        }
        ticket.getTickets().put(filmObjectId, integers);
        cinemaFolder.put("tickets", ticket);
        cinemaFolder.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (null == e) {
                    index ++;
                    updateCinemaInfo();
                }
            }
        });// 保存到服务端
    }

}
