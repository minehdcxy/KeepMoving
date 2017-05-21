package com.example.raytine.trip.register;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.raytine.keepmoving.R;
import com.example.raytine.trip.login.LoginActivity;

/**
 * Created by raytine on 2017/4/11.
 */

public class RegisterActivity extends Activity implements View.OnClickListener, RegisterContract.View {
    private Button registerButton;
    private Button returnLogin;
    private EditText phoneNumber;
    private EditText code;
    private TextView validate;
    private EditText password;
    private boolean isRunning = true;//计时器线程开关
    private int sec = 30;

    private RegisterContract.Presenter presenter;

    private Handler timeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            validate.setText(msg.obj.toString() + "秒后获取");
            if (msg.obj.toString().equals("0")) {
                validate.setEnabled(true);
                isRunning = false;
                validate.setTextColor(getResources().getColor(R.color.dark_green));
                validate.setText("获取验证码");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_layout);
        new RegisterPresenter(this);
        if (Build.VERSION.SDK_INT > 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        initViews();
    }

    private void initViews() {
        password = (EditText) findViewById(R.id.et_register_password);
        phoneNumber = (EditText) findViewById(R.id.et_register_phone_number);
        code = (EditText) findViewById(R.id.et_register_code);
        validate = (TextView) findViewById(R.id.tv_register_validate);
        registerButton = (Button) findViewById(R.id.bt_register);
        returnLogin = (Button) findViewById(R.id.bt_return_login);
        returnLogin.setOnClickListener(this);
        registerButton.setOnClickListener(this);
        validate.setOnClickListener(this);
        code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.equals("")) {
                    registerButton.setEnabled(false);
                    registerButton.setAlpha((float) 0.4);
                } else {
                    registerButton.setEnabled(true);
                    registerButton.setAlpha(1);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_register_validate:
                isRunning = true;
                presenter.register(phoneNumber.getText().toString(), password.getText().toString());
                validate.setTextColor(getResources().getColor(R.color.avoscloud_feedback_text_gray));
                validate.setEnabled(false);
                countDown();
                break;
            case R.id.bt_return_login:
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.bt_register:
                presenter.validate(phoneNumber.getText().toString(), code.getText().toString());
                break;
        }
    }

    private void countDown() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning) {
                    if (sec != 0) {
                        sec--;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message message = timeHandler.obtainMessage();
                    message.obj = sec;
                    timeHandler.sendMessage(message);
                    if (message.obj.toString().equals("0")) {
                        break;
                    }
                }

            }
        }).start();
    }

    @Override
    public void setPresenter(RegisterContract.Presenter var) {
        this.presenter = var;
    }

    @Override
    public void registerSuccess(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void registerFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
