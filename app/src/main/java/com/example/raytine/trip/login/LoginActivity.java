package com.example.raytine.trip.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.raytine.keepmoving.R;
import com.example.raytine.trip.application.MyLeanCloudApplication;
import com.example.raytine.trip.home.HomeActivity;
import com.example.raytine.trip.register.RegisterActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, LoginContract.View {
    private Button quicklyRegister;
    private Button login;
    private LoginContract.Presenter presenter;
    private EditText phoneNumber;
    private EditText password;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_acitivity);
        new LoginPresenter(this);
        preferences = this.getPreferences(MODE_PRIVATE);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        initViews();
    }

    private void initViews() {
        phoneNumber = (EditText) findViewById(R.id.et_login_phone_number);
        password = (EditText) findViewById(R.id.et_login_password);
        login = (Button) findViewById(R.id.bt_login);
        quicklyRegister = (Button) findViewById(R.id.bt_quickly_register);
        quicklyRegister.setOnClickListener(this);
        login.setOnClickListener(this);

        if (MyLeanCloudApplication.isDebug) {
            phoneNumber.setText("18246181829");
            password.setText("123456");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_quickly_register:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.bt_login:
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("isHaveAccount", true);
                editor.putString("username", phoneNumber.getText().toString());
                editor.putString("password", password.getText().toString());
                editor.commit();
                presenter.login(phoneNumber.getText().toString(), password.getText().toString());
                break;
        }
    }

    @Override
    public void setPresenter(LoginContract.Presenter var) {
        this.presenter = var;
    }

    @Override
    public void loginSuccess(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void loginFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
