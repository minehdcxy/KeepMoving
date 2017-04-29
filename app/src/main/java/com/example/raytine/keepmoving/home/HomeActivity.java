package com.example.raytine.keepmoving.home;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.raytine.keepmoving.R;

public class HomeActivity extends ActionBarActivity{

    private final String TAG = HomeActivity.class.getSimpleName();

    private Toolbar toolbar;
    private ImageView homeIv, userIv;
    private HomeFragment homeFragment;
    private UserFragment userFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initViews();

    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.home_view_toolbar);
        homeIv = (ImageView) findViewById(R.id.home_iv);
        userIv = (ImageView) findViewById(R.id.home_user);

        if (null == homeFragment) {
            homeFragment = new HomeFragment();
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_home, homeFragment)
                .commit();

        homeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeIv.setImageResource(R.drawable.ic_tab_home_blue);
                userIv.setImageResource(R.drawable.ic_tab_my);

                if (null == homeFragment) {
                    homeFragment = new HomeFragment();
                }
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_home, homeFragment)
                        .commit();
            }
        });

        userIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeIv.setImageResource(R.drawable.ic_tab_home);
                userIv.setImageResource(R.drawable.ic_tab_my_blue);

                if (null == userFragment) {
                    userFragment = new UserFragment();
                }
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_home, userFragment)
                        .commit();
            }
        });
    }
}
