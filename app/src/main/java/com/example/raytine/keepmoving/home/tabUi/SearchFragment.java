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
import android.widget.TextView;
import android.widget.Toast;

import com.example.raytine.keepmoving.R;

import sqliteHelper.MySqliteHelper;

/**
 * Created by raytine on 2017/5/12.
 */

public class SearchFragment extends Fragment {
    private SQLiteDatabase db;

    private String SearchResult="";
    private Button search;
    private TextView resultTextView;
    private EditText searchEditText;
    private MySqliteHelper mySqliteHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_layout, container, false);
        mySqliteHelper = new MySqliteHelper(getActivity(), "hotelSys.db", null, 1);
        db=mySqliteHelper.getWritableDatabase();
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        search = (Button) view.findViewById(R.id.searchBtn);
        resultTextView = (TextView) view.findViewById(R.id.resultTestView);
        searchEditText = (EditText) view.findViewById(R.id.searchEditText);
        search.setOnClickListener(new Button.OnClickListener(){

            public void onClick(View v) {

                String roomNum = searchEditText.getText().toString();
                db=mySqliteHelper.getReadableDatabase();
                Cursor query01 = db.query("hotel", new String[]{"roomNum","name","sex","idCard","timeIn","timeSum","money"}, "roomNum=?", new String[]{roomNum}, null, null, "_id asc");
                if(query01.moveToFirst()){
                    for (query01.moveToFirst(); !query01.isAfterLast(); query01.moveToNext()) {
                        SearchResult +="房间号："+ query01.getString(query01.getColumnIndex("roomNum"))+"\n";
                        SearchResult +="姓　名："+ query01.getString(query01.getColumnIndex("name"))+"\n";
                        SearchResult +="性　别："+ query01.getString(query01.getColumnIndex("sex"))+"\n";
                        SearchResult +="身份证："+ query01.getString(query01.getColumnIndex("roomNum"))+"\n";
                        SearchResult +="入住时间："+ query01.getString(query01.getColumnIndex("roomNum"))+"\n";
                        SearchResult +="入住天数："+ query01.getString(query01.getColumnIndex("roomNum"))+"\n";
                        SearchResult +="总钱数："+ query01.getString(query01.getColumnIndex("roomNum"));
                    }
                }else{
                    Toast.makeText(getActivity(),"没有相关信息", Toast.LENGTH_SHORT).show();
                }

                query01.close();
                resultTextView.setText(SearchResult);
                SearchResult="";
            }

        });
    }
}
