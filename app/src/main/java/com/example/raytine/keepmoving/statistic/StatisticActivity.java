package com.example.raytine.keepmoving.statistic;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.raytine.keepmoving.R;
import com.example.raytine.keepmoving.home.tabUi.MovementFragment;

import java.util.ArrayList;
import java.util.List;

import adapter.HomeFragmentAdapter;

/**
 * Created by raytine on 2017/5/4.
 */

public class StatisticActivity extends FragmentActivity {
    private TabLayout tabLayout;
    private ViewPager pager;
    private List<Fragment> viewList = new ArrayList<>();
    private List<String> tabList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic_layout);
        initViews();
        initViewList();
        initTabTitle();
        HomeFragmentAdapter adapter = new HomeFragmentAdapter(getSupportFragmentManager(), viewList, tabList);
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
        tabLayout.setTabsFromPagerAdapter(adapter);
    }
    private void initViewList() {
        viewList.add(new MovementFragment());
        viewList.add(new MovementFragment());
        viewList.add(new MovementFragment());
    }

    private void initTabTitle() {
        tabList.add(0, this.getString(R.string.tab_title_recommend));
        tabList.add(1, this.getString(R.string.tab_title_receive));
        tabList.add(2, this.getString(R.string.tab_title_reflect));
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.addTab(tabLayout.newTab().setText(tabList.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(tabList.get(1)));
        tabLayout.addTab(tabLayout.newTab().setText(tabList.get(2)));
    }

    private void initViews() {
        tabLayout = (TabLayout) findViewById(R.id.tl_statistic);
        pager = (ViewPager) findViewById(R.id.vp_statistic);
        pager.setOffscreenPageLimit(2);
    }


}
