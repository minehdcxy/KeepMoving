package com.example.raytine.keepmoving.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;

import com.example.raytine.keepmoving.R;

public class ActivityUserCard extends ActionBarActivity {

    private final String TAG = ActivityUserCard.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_card);
    }
}
