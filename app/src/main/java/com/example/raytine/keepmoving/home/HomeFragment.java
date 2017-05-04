package com.example.raytine.keepmoving.home;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ProviderInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import com.coder.circlebar.CircleBar;
import com.example.raytine.keepmoving.R;
import com.example.raytine.keepmoving.TargetActivity;
import com.example.raytine.keepmoving.statistic.StatisticActivity;

import java.text.SimpleDateFormat;
import java.util.Date;


public class HomeFragment extends Fragment {
    private static String MOVEMENT = "MOVEMENT";
    private static String TARGET = "TARGET";
    private static String STEP = "STEP";
    private CircleBar progressBar;
    private TextView target;
    private TextView myMovement;
    private TextView todayCounter;
    private TextView settingTarget;

    private ProgressDialog progressDialog;

    private SensorManager sensorManager;

    private int counter = 0;
    private int setTarget = 0;



    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_counter_layout, container, false);
        initViews(view);
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(sensorEventListener, sensor, sensorManager.SENSOR_DELAY_NORMAL);
        sharedPreferences = getActivity().getSharedPreferences(MOVEMENT, Context.MODE_PRIVATE);
        if(sharedPreferences.getString("date", DateToStr(new Date())).equals(DateToStr(new Date()))){
            todayCounter.setText(sharedPreferences.getString(STEP, "0"));
            settingTarget.setText("目标:"+String.valueOf(sharedPreferences.getInt(TARGET, 0)));
            counter = Integer.valueOf(sharedPreferences.getString(STEP, "0"));
            progressBar.update(Integer.parseInt(sharedPreferences.getString(STEP, "0")), 5000);
            int maxStep = sharedPreferences.getInt(TARGET, 0);
            if(maxStep == 0){
                progressBar.setMaxstepnumber(100);
            }else{
                progressBar.setMaxstepnumber(maxStep);
            }
        } else{
            sharedPreferences.edit().putString("date", DateToStr(new Date()));
            AVObject todoFolder = new AVObject("movement");
            todoFolder.put("step", counter);
            todoFolder.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if(e == null){
                        dismissDialog();
                    }
                }
            });// 保存到服务端
            counter = 0;
            setTarget = 0;
            todayCounter.setText(counter);
            settingTarget.setText(setTarget);
            showProgressDialog();
        }
        return view;
    }


    private void initViews(View view) {
        target = (TextView) view.findViewById(R.id.tv_home_target_setting);
        myMovement = (TextView) view.findViewById(R.id.tv_my_movement);
        todayCounter = (TextView) view.findViewById(R.id.tv_counter_today);
        progressBar = (CircleBar) view.findViewById(R.id.cb_home_counter_bar);
        settingTarget = (TextView) view.findViewById(R.id.tv_home_target);
        progressBar.setMaxstepnumber(100);
        progressBar.setColor(0xff568951);
        int[] mColors = new int[]{0xFF123456, 0xFF369852, 0xFF147852};
        progressBar.setShaderColor(mColors);
        progressBar.update(0, 1000);

        target.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TargetActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        myMovement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), StatisticActivity.class);
                startActivity(intent);
            }
        });
    }

    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float xValue = Math.abs(sensorEvent.values[0]);
            float yValue = Math.abs(sensorEvent.values[1]);
            float zValue = Math.abs(sensorEvent.values[2]);
            if(xValue >10 || yValue > 10 || zValue > 14){
                counter ++;
                todayCounter.setText(String.valueOf(counter));
                progressBar.update(counter, 5000);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == 1){
                setTarget = Integer.parseInt(data.getStringExtra("Target"));
                progressBar.setMaxstepnumber(setTarget);
                settingTarget.setText("目标:"+String.valueOf(setTarget));
                sharedPreferences.edit().putInt(TARGET, setTarget).putString(STEP, String.valueOf(counter)).commit();
            }

        }
    }

    public static String DateToStr(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("正在初始化新的一天..");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    private void dismissDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
