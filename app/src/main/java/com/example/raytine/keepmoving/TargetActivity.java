package com.example.raytine.keepmoving;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by raytine on 2017/5/4.
 */

public class TargetActivity extends Activity {
    private TextView commit;
    private TextView ok;
    private TextView target;
    private EditText setTarget;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target_layout);
        initViews();
    }

    private void initViews() {
        commit = (TextView) findViewById(R.id.tv_target_commit);
        ok = (TextView) findViewById(R.id.bt_target_ok);
        target = (TextView) findViewById(R.id.tv_target);
        setTarget = (EditText) findViewById(R.id.et_target);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                target.setText(setTarget.getText().toString());
            }
        });
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("Target", target.getText().toString());
                setResult(1, intent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
