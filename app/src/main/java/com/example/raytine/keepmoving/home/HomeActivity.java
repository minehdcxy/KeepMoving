package com.example.raytine.keepmoving.home;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.example.raytine.keepmoving.R;

public class HomeActivity extends ActionBarActivity {

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

    public void setSelectPager(int select) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideFragment(transaction);
        switch (select) {
            case 0:
                if (null == homeFragment) {
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.fl_home, homeFragment);
                } else {
                    transaction.show(homeFragment);
                }
                break;
            case 1:
                if (null == userFragment) {
                    userFragment = new UserFragment();
                    transaction.add(R.id.fl_home, userFragment);
                } else {
                    transaction.show(userFragment);
                }
                break;
        }
        transaction.commit();
    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.home_view_toolbar);
        homeIv = (ImageView) findViewById(R.id.home_iv);
        userIv = (ImageView) findViewById(R.id.home_user);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (null == homeFragment) {
            homeFragment = new HomeFragment();
            transaction.add(R.id.fl_home, homeFragment);
        } else {
            transaction.show(homeFragment);
        }

        homeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeIv.setImageResource(R.drawable.ic_tab_home_blue);
                userIv.setImageResource(R.drawable.ic_tab_my);
                setSelectPager(0);
            }
        });

        userIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeIv.setImageResource(R.drawable.ic_tab_home);
                userIv.setImageResource(R.drawable.ic_tab_my_blue);
                setSelectPager(1);
            }
        });
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if (homeFragment != null) {
            fragmentTransaction.hide(homeFragment);
        }
        if (userFragment != null) {
            fragmentTransaction.hide(userFragment);
        }
    }


}
