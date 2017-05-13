package com.example.raytine.keepmoving.home.tabUi;


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
import android.widget.Toast;


import com.example.raytine.keepmoving.R;

import sqliteHelper.MySqliteHelper;


/**
 * Created by raytine on 2017/5/12.
 */

public class CheckoutFragment extends Fragment {
    private SQLiteDatabase db;
    private MySqliteHelper mySqliteHelper;
    private Button dropBtn;
    private EditText usernameEditText;
    private EditText roomNum01EditText;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkout_layout, container, false);
        mySqliteHelper = new MySqliteHelper(getActivity(), "hotelSys.db", null, 1);
        db=mySqliteHelper.getWritableDatabase();
        initViews(view);

        return view;
    }

    private void initViews(View view) {
        dropBtn = (Button) view.findViewById(R.id.dropBtn);
        usernameEditText = (EditText) view.findViewById(R.id.dropUsernameEditText);
        roomNum01EditText = (EditText) view.findViewById(R.id.dropRoomNumEditText);
        dropBtn.setOnClickListener(new Button.OnClickListener(){

            public void onClick(View v) {

                String username = usernameEditText.getText().toString();
                String roomNum = roomNum01EditText.getText().toString();
                Cursor query=null;
                try {
                    query = db.query("hotel", new String[]{"_id"},"name=? and roomNum=?" , new String[]{username,roomNum}, null, null, null);
                    if(query.moveToFirst()){
                        db.delete("hotel", " name=? and roomNum=?", new String[]{username,roomNum});
                        Toast.makeText(getActivity(),"删除成功", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getActivity(),"数据不存在", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    query.close();
                    e.printStackTrace();
                }

            }

        });
    }


}
