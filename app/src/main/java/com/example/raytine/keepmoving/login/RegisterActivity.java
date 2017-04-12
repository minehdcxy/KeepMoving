package com.example.raytine.keepmoving.login;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.raytine.keepmoving.R;

/**
 * Created by raytine on 2017/4/11.
 */

public class RegisterActivity extends Activity implements View.OnClickListener {
    private Button registerButton;
    private Button returnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_layout);
        if (Build.VERSION.SDK_INT > 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        initViews();
    }

    private void initViews() {
        registerButton = (Button) findViewById(R.id.bt_register);
        returnLogin = (Button) findViewById(R.id.bt_return_login);
        returnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_register:
                break;
            case R.id.bt_return_login:
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
