package com.example.raytine.keepmoving.user.info;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.raytine.keepmoving.R;
import com.example.raytine.keepmoving.application.MyLeanCloudApplication;
import com.example.raytine.keepmoving.user.model.User;

public class ActivityUserInfo extends ActionBarActivity implements View.OnClickListener, UserInfoContract.View {

    private final String TAG = ActivityUserInfo.class.getSimpleName();

    private ImageView userInfoBackIv;
    private TextView userInfoEditTv;
    private EditText userNicknameEt;
    private EditText userGenderEt;
    private EditText userAgeEt;
    private EditText userAddressEt;
    private boolean isEditing;

    private UserInfoContract.Presenter presenter;
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        new UserInfoPresenter(this);
        initViews();
        initData();
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

    private void initData() {
        User user = MyLeanCloudApplication.getIns().getUser();
        userNicknameEt.setText(user.getNickname());
        userGenderEt.setText(user.getGender());
        userAgeEt.setText(String.valueOf(user.getAge()));
        userAddressEt.setText(user.getAddress());
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
                    user = new User();
                    user.setObjectId(MyLeanCloudApplication.getIns().getUser().getObjectId());
                    user.setNickname(userNicknameEt.getText().toString());
                    user.setGender(userGenderEt.getText().toString());
                    user.setAge(Integer.parseInt(userAgeEt.getText().toString()));
                    user.setAddress(userAddressEt.getText().toString());
                    presenter.update(user);
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

    @Override
    public void updateSuccess() {
        Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
        User currUser = MyLeanCloudApplication.getIns().getUser();
        currUser.setNickname(user.getNickname());
        currUser.setGender(user.getGender());
        currUser.setAge(user.getAge());
        currUser.setAddress(user.getAddress());
        MyLeanCloudApplication.getIns().setUser(currUser);
    }

    @Override
    public void updateFailed() {
        Toast.makeText(this, "修改失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(UserInfoContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected void onDestroy() {
        presenter.destroy();
        super.onDestroy();
    }
}
