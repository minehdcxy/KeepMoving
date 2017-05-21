package com.example.raytine.trip.tripContent;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.raytine.keepmoving.R;
import com.example.raytine.trip.home.model.TripData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import adapter.HomeFragmentAdapter;

/**
 * Created by raytine on 2017/5/17.
 */

public class TripContentActivity extends FragmentActivity {
    private ViewPager pager;
    private TripData model;
    private List<Fragment> tripContentFragmentList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_content);

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        initViews();
        initDatas();
        HomeFragmentAdapter adapter = new HomeFragmentAdapter(getSupportFragmentManager(), tripContentFragmentList);
        pager.setAdapter(adapter);
    }

    private void initDatas() {
        Bundle bundle = getIntent().getExtras();
        model = (TripData) bundle.get("model");
        for(int i = 0;i<model.getDiaryTripList().size();i++){
            TripContentFragment tripContentFragment = new TripContentFragment();
            Bundle tripBundle = new Bundle();
            tripBundle.putString("contentDescribe", model.getDiaryTripList().get(i).getDiaryContent());
            tripBundle.putString("contentImageUrl", model.getDiaryTripList().get(i).getDiaryImageUrl());
            tripBundle.putString("tripId", model.getTripId());
            tripContentFragment.setArguments(tripBundle);
            tripContentFragmentList.add(tripContentFragment);
        }
    }

    private void initViews() {
        pager = (ViewPager) findViewById(R.id.vp_content);
    }
}
