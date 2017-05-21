package com.example.raytine.trip.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.raytine.keepmoving.R;
import com.example.raytine.trip.application.MyLeanCloudApplication;
import com.example.raytine.trip.login.LoginActivity;
import com.example.raytine.trip.user.info.ActivityUserInfo;
import com.example.raytine.trip.user.model.User;

/**
 * Created by raytine on 2017/5/18.
 */

public class UserActivity extends Activity implements View.OnClickListener{

    private final String TAG = UserFragment.class.getSimpleName();

    private User user;

    private TextView userNameTv;
    private LinearLayout userInfoLl;
    private LinearLayout userManageLl;
    private LinearLayout userWalletLl;
    private TextView userWalletTv;
    private LinearLayout userCardLl;
    private LinearLayout userAdviseLl;
    private TextView exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user);
        user = MyLeanCloudApplication.getIns().getUser();
        initViews();
    }

    private void initViews() {

        userNameTv = (TextView) findViewById(R.id.user_name_tv);
        userInfoLl = (LinearLayout) findViewById(R.id.user_info_ll);
        userManageLl = (LinearLayout) findViewById(R.id.user_manage_ll);
        userWalletLl = (LinearLayout) findViewById(R.id.user_wallet_ll);
        userWalletTv = (TextView) findViewById(R.id.user_wallet_tv);
        userCardLl = (LinearLayout) findViewById(R.id.user_card_ll);
        userAdviseLl = (LinearLayout) findViewById(R.id.user_advise_ll);
        exit = (TextView) findViewById(R.id.user_exit_tv);

        switch (user.getType()) {
            case 0:
                userManageLl.setVisibility(View.GONE);
                userWalletLl.setVisibility(View.VISIBLE);
                userWalletTv.setText(String.valueOf(user.getWallet()));
                userCardLl.setVisibility(View.VISIBLE);
                userAdviseLl.setVisibility(View.VISIBLE);
                userWalletLl.setOnClickListener(this);
                userCardLl.setOnClickListener(this);
                userAdviseLl.setOnClickListener(this);
                break;
            case 1:
                userManageLl.setVisibility(View.VISIBLE);
                userWalletLl.setVisibility(View.GONE);
                userCardLl.setVisibility(View.GONE);
                userAdviseLl.setVisibility(View.GONE);

                userManageLl.setOnClickListener(this);
                break;
        }

        userInfoLl.setOnClickListener(this);
        exit.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(user.getType() == 0)
            userWalletTv.setText(String.valueOf(user.getWallet()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_info_ll:
                Intent infoIntent = new Intent(this, ActivityUserInfo.class);
                startActivity(infoIntent);
                break;
            case R.id.user_manage_ll:
                break;
            case R.id.user_card_ll:
                Intent cardIntent = new Intent(this, ActivityUserCard.class);
                startActivity(cardIntent);
                break;
            case R.id.user_advise_ll:
                Intent adviseIntent = new Intent(this, ActivityUserAdvise.class);
                startActivity(adviseIntent);
                break;
            case R.id.user_exit_tv:
                MyLeanCloudApplication.getIns().setUser(null);
                Intent exitIntent = new Intent(this, LoginActivity.class);
                startActivity(exitIntent);
                break;
        }
    }
}
