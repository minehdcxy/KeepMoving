package com.example.raytine.keepmoving.guide;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.raytine.keepmoving.R;
import com.example.raytine.keepmoving.login.LoginAcitivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private PagerAdapter adapter;
    private ViewPager pager;
    private RelativeLayout guide;
    private Button startButton;

    private List<View> viewList = new ArrayList<>();

    private SharedPreferences preferences;

    private String IS_FIRST_INSTALL = "isFirstInstall";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT > 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        initViews();
        preferences = this.getPreferences(MODE_PRIVATE);
        if (preferences.getBoolean(IS_FIRST_INSTALL, false)) {
            guide.setVisibility(View.VISIBLE);
            pager.setVisibility(View.GONE);
            final Intent intent = new Intent(MainActivity.this, LoginAcitivity.class);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        startActivity(intent);
                        finish();
                    }
                }
            }).start();
        } else {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(IS_FIRST_INSTALL, true);
            editor.commit();
            initGuideViews();
            adapter = new PagerAdapter();
            pager.setAdapter(adapter);
            startButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, LoginAcitivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }

    private void initViews() {
        pager = (ViewPager) findViewById(R.id.keep_guide_pager);
        guide = (RelativeLayout) findViewById(R.id.rl_keep_guide);

    }

    private void initGuideViews() {
        LayoutInflater inflater = getLayoutInflater();
        View guideOne = inflater.inflate(R.layout.ll_guide_one, null);
        viewList.add(guideOne);
        View guideTwo = inflater.inflate(R.layout.ll_guide_two, null);
        viewList.add(guideTwo);
        View guideThree = inflater.inflate(R.layout.ll_guide_three, null);
        startButton = (Button) guideThree.findViewById(R.id.bt_guide_start);
        viewList.add(guideThree);
    }

    class PagerAdapter extends android.support.v4.view.PagerAdapter {

        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position));
            return viewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(viewList.get(position));
        }
    }

}
