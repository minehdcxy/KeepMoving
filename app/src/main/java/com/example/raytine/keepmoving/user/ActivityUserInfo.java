package com.example.raytine.keepmoving.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.raytine.keepmoving.R;

public class ActivityUserInfo extends ActionBarActivity implements View.OnClickListener{

    private final String TAG = ActivityUserInfo.class.getSimpleName();

    private ImageView userInfoBackIv;
    private TextView userInfoEditTv;
    private EditText userNicknameEt;
    private EditText userGenderEt;
    private EditText userAgeEt;
    private EditText userAddressEt;
    private boolean isEditing;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        initViews();
    }

    private void initViews() {
        userInfoBackIv = (ImageView) findViewById(R.id.user_info_back_iv);
        userInfoEditTv = (TextView) findViewById(R.id.user_info_edit_tv);
        userNicknameEt = (EditText) findViewById(R.id.user_info_nickname_et);
        userGenderEt = (EditText) findViewById(R.id.user_info_gender_et);
        userAgeEt = (EditText) findViewById(R.id.user_info_age_et);
        userAddressEt = (EditText) findViewById(R.id.user_info_address_et);

        userInfoBackIv.setOnClickListener(this);
        userInfoEditTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_info_back_iv:
                onBackPressed();
                break;
            case R.id.user_info_edit_tv:
                if (isEditing) {
                    userNicknameEt.setEnabled(false);
                    userGenderEt.setEnabled(false);
                    userAgeEt.setEnabled(false);
                    userAddressEt.setEnabled(false);
                    isEditing = false;
                    userInfoEditTv.setText("编辑");
                } else {
                    userNicknameEt.setEnabled(true);
                    userGenderEt.setEnabled(true);
                    userAgeEt.setEnabled(true);
                    userAddressEt.setEnabled(true);
                    isEditing = true;
                    userInfoEditTv.setText("完成");
                }
                break;
        }
    }
}
