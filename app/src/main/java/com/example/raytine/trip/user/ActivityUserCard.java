package com.example.raytine.trip.user;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.raytine.keepmoving.R;
import com.example.raytine.trip.application.MyLeanCloudApplication;
import com.example.raytine.trip.user.model.User;

import java.util.ArrayList;
import java.util.List;

public class ActivityUserCard extends ActionBarActivity {
    private ListView listView;
    private List<String> cardList = new ArrayList<>();

    private final String TAG = ActivityUserCard.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_card);
        initViews();
        initDatas();
        CardAdapter cardAdapter = new CardAdapter(this, R.layout.item_card_layout, cardList);
        listView.setAdapter(cardAdapter);
    }

    private void initDatas() {
        User user = MyLeanCloudApplication.getIns().getUser();
        cardList.clear();
        cardList.addAll(user.getCard().getCarList());
    }

    private void initViews() {
        listView = (ListView) findViewById(R.id.lv_myCard);
    }

    class CardAdapter extends ArrayAdapter<String>{

        public CardAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
        }

        @Override
        public int getCount() {
            return cardList.size();
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            View view;
            if(convertView == null){
                holder = new ViewHolder();
                view = LayoutInflater.from(ActivityUserCard.this).inflate(R.layout.item_card_layout, null);
                holder.cdKey = (TextView) view.findViewById(R.id.tv_item_card);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }
            holder.cdKey.setText(cardList.get(position));
            return view;
        }
    }

    class ViewHolder{
        TextView cdKey;
    }
}
