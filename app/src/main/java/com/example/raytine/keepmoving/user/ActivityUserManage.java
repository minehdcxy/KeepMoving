package com.example.raytine.keepmoving.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;

import com.example.raytine.keepmoving.R;

public class ActivityUserManage extends ActionBarActivity{

    private final String TAG = ActivityUserManage.class.getSimpleName();

    private ImageView manageBackIv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manage);

        initViews();
    }

    private void initViews() {
        manageBackIv = (ImageView) findViewById(R.id.user_manage_back_iv);
    }

}
