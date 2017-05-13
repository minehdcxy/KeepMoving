package com.example.raytine.keepmoving.home.tabUi;

import android.app.TimePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.raytine.keepmoving.R;

import java.util.Calendar;

import sqliteHelper.MySqliteHelper;

/**
 * Created by raytine on 2017/5/11.
 */

public class HotelManageFragment extends Fragment {
    private SQLiteDatabase db;
    private EditText nameEditText;
    private RadioButton radioMan;
    private RadioButton radioWoman;
    private EditText idCardEditText;
    private EditText timeInEditText;
    private EditText timeSumEditText;
    private EditText roomNumEditText;
    private EditText moneyEditText;
    private Button btn01;
    private Button btn02;
    private String result="";
    private MySqliteHelper mySqliteHelper;
    private String choiceTime;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_layout, container, false);
        mySqliteHelper = new MySqliteHelper(getActivity(), "hotelSys.db", null, 1);
        db=mySqliteHelper.getWritableDatabase();
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        btn01 = (Button) view.findViewById(R.id.btn01);
        btn02 = (Button) view.findViewById(R.id.btn02);
        nameEditText = (EditText) view.findViewById(R.id.nameEditText);
        radioMan = (RadioButton) view.findViewById(R.id.man);
        radioWoman = (RadioButton) view.findViewById(R.id.woman);
        idCardEditText = (EditText)view.findViewById(R.id.idCardEditText);
        timeInEditText = (EditText)view.findViewById(R.id.timeInEditText);
        timeSumEditText = (EditText)view.findViewById(R.id.timeSumEditText);
        roomNumEditText = (EditText)view.findViewById(R.id.roomNumEditText);
        moneyEditText = (EditText)view.findViewById(R.id.moneyEditText);
        btn01.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                try {
                    String name = nameEditText.getText().toString();
                    String sex = "";
                    if(radioMan.isChecked()){
                        sex="man";
                    }else if(radioWoman.isChecked()){
                        sex="woman";
                    }
                    String idCard = idCardEditText.getText().toString();
                    String timeIn = timeInEditText.getText().toString();
                    String timeSum = timeSumEditText.getText().toString();
                    String roomNum = roomNumEditText.getText().toString();
                    String money = moneyEditText.getText().toString();

                    db=mySqliteHelper.getReadableDatabase();
                    Cursor query = db.query("hotel", new String[]{"roomNum","name","sex","idCard","timeIn","timeSum","money"}, "roomNum=?", new String[]{roomNum}, null, null, "_id asc");
                    if(!query.moveToFirst()){
                        db.execSQL("insert into hotel(name,sex,idCard,timeIn,timeSum,roomNum,money) values(?,?,?,?,?,?,?)",new String[]{name,sex,idCard,timeIn,timeSum,roomNum,money});
                        Toast.makeText(getActivity(),"插入成功", Toast.LENGTH_SHORT).show();
                        clean();
                    }else{
                        Toast.makeText(getActivity(),"房间已经有人住...", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                db = mySqliteHelper.getReadableDatabase();
                Cursor query = db.query("hotel", new String[]{"name"}, null, null, null, null, "_id asc");
                for (query.moveToFirst();!(query.isAfterLast()); query.moveToNext()) {
                    result = result+query.getString(query.getColumnIndex("name"));
                }
                query.close();
            }

        });

        timeInEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimeDialog();
            }
        });

        btn02.setOnClickListener(new Button.OnClickListener(){

            public void onClick(View v) {
                // TODO Auto-generated method stub
                clean();
            }

        });
    }

    private void showTimeDialog() {
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH) + 1;
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                choiceTime = hourOfDay + ":" + "0"+minute + ":00";
                timeInEditText.setText(String.valueOf(year)+"年"+String.valueOf(month)+"月"+String.valueOf(day)+"日"+choiceTime);
                Toast.makeText( getActivity(), "选择时间" + choiceTime, Toast.LENGTH_SHORT).show();
            }
        }, hours, minute, true).show();
    }


    public void clean(){
        nameEditText.setText("");
        radioWoman.setChecked(true);
        idCardEditText.setText("");
        timeInEditText.setText("");
        timeSumEditText.setText("");
        roomNumEditText.setText("");
        moneyEditText.setText("");


    }
}
