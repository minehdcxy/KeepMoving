package com.example.raytine.keepmoving.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.raytine.keepmoving.R;
import com.example.raytine.keepmoving.home.tabUi.RecommendFragment;

import java.util.ArrayList;
import java.util.List;

import adapter.HomeFragmentAdapter;


public class HomeFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager pager;
    private List<Fragment> viewList = new ArrayList<>();
    private List<String> tabList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initViews(view);
        initViewList();
        initTabTitle();
        HomeFragmentAdapter adapter = new HomeFragmentAdapter(getChildFragmentManager(), viewList, tabList);
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
        tabLayout.setTabsFromPagerAdapter(adapter);
        return view;
    }
    private void initViewList() {
        viewList.add(new RecommendFragment());
        viewList.add(new RecommendFragment());
        viewList.add(new RecommendFragment());
        viewList.add(new RecommendFragment());
    }

    private void initTabTitle() {
        tabList.add(0, this.getString(R.string.tab_title_recommend));
        tabList.add(1, this.getString(R.string.tab_title_receive));
        tabList.add(2, this.getString(R.string.tab_title_reflect));
        tabList.add(3, this.getString(R.string.tab_title_abroad));
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.addTab(tabLayout.newTab().setText(tabList.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(tabList.get(1)));
        tabLayout.addTab(tabLayout.newTab().setText(tabList.get(2)));
        tabLayout.addTab(tabLayout.newTab().setText(tabList.get(3)));
    }

    private void initViews(View view) {
        tabLayout = (TabLayout) view.findViewById(R.id.tl_home);
        pager = (ViewPager) view.findViewById(R.id.vp_home);
        pager.setOffscreenPageLimit(3);
    }


}
