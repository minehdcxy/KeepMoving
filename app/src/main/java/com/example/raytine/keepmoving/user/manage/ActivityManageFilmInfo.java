package com.example.raytine.keepmoving.user.manage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.raytine.keepmoving.R;
import com.example.raytine.keepmoving.home.model.FilmData;

public class ActivityManageFilmInfo extends ActionBarActivity{

    private final String TAG = ActivityManageFilmInfo.class.getSimpleName();

    private ImageView manageFilmBackIv;
    private TextView manageFilmTitleTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_film);

        initViews();
        initData();
    }

    private void initViews() {
        manageFilmBackIv = (ImageView) findViewById(R.id.manage_film_back_iv);
        manageFilmTitleTv = (TextView) findViewById(R.id.manage_film_title_tv);
    }

    private void initData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (null != bundle) {
            manageFilmTitleTv.setText("编辑影视信息");
            FilmData film = (FilmData) bundle.get("film");
        } else {
            manageFilmTitleTv.setText("添加影视信息");
        }
    }
}
