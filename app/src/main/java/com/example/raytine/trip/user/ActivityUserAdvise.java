package com.example.raytine.trip.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.raytine.keepmoving.R;

public class ActivityUserAdvise extends ActionBarActivity implements View.OnClickListener{

    private final String TAG = ActivityUserAdvise.class.getSimpleName();

    private ImageView adviseBackIv;
    private EditText adviseContentEt;
    private Button adviseCommitBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_advise);

        initViews();
    }

    private void initViews() {
        adviseBackIv = (ImageView) findViewById(R.id.user_advise_back_iv);
        adviseContentEt = (EditText) findViewById(R.id.user_advise_content);
        adviseCommitBtn = (Button) findViewById(R.id.user_advise_commit);

        adviseBackIv.setOnClickListener(this);
        adviseCommitBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_advise_back_iv:
                onBackPressed();
                break;
            case R.id.user_advise_commit:
                if (TextUtils.isEmpty(adviseContentEt.getText())) {
                    Toast.makeText(this, "内容为空，请填写后再提交！", Toast.LENGTH_SHORT).show();
                    return;
                }
                adviseContentEt.setText("");
                Toast.makeText(this, "提交成功，非常感谢！", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
