package com.example.raytine.keepmoving.filmDetail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.raytine.keepmoving.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raytine on 2017/4/30.
 */

public class FilmDetailActivity extends ActionBarActivity {
    private ListView cinemaList;
    private List<FilmDetailModel> filmDetailModelList = new ArrayList<>();
    private List<CinemaModel> cinemaModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_detail);
        initViews();
        initDatas();
        CinemaAdapter adapter = new CinemaAdapter(this, R.layout.cinema_item, cinemaModelList);
        cinemaList.setAdapter(adapter);
    }

    private void initDatas() {
        for(int i=0 ;i< 10 ;i++){
            CinemaModel model = new CinemaModel();
            model.setCinemaName("sssss");
            model.setFilmPrice("25元");
            model.setAddress("黑龙江大学音乐厅");
            model.setFilmTime("165分钟");
            cinemaModelList.add(model);
        }

    }

    private void initViews() {
        cinemaList = (ListView) findViewById(R.id.lv_cinema_detail);

    }

    class CinemaAdapter extends ArrayAdapter<CinemaModel> {
        private List<CinemaModel> cinemaModelList = new ArrayList<>();
        private int resource;
        private Context context;

        public CinemaAdapter(Context context, int resource, List<CinemaModel> objects) {
            super(context, resource, objects);
            this.cinemaModelList = objects;
            this.resource = resource;
            this.context = context;
        }

        @Override
        public int getCount() {
            return cinemaModelList.size();
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            FilmDetailHolder holder = null;
            View view;
            if (convertView == null) {
                holder = new FilmDetailHolder();
                view = LayoutInflater.from(context).inflate(R.layout.cinema_item, null);
                holder.cinemaName = (TextView) view.findViewById(R.id.tv_item_cinema_name);
                holder.cinemaAddress = (TextView) view.findViewById(R.id.tv_item_cinema_address);
                holder.cinemaTime = (TextView) view.findViewById(R.id.tv_item_cinema_time);
                holder.filmPrice = (TextView) view.findViewById(R.id.tv_item_film_price);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (FilmDetailHolder) view.getTag();
            }
            holder.cinemaName.setText(cinemaModelList.get(position).getCinemaName());
            holder.cinemaAddress.setText(cinemaModelList.get(position).getAddress());
            holder.cinemaTime.setText(cinemaModelList.get(position).getFilmTime());
            holder.filmPrice.setText(cinemaModelList.get(position).getFilmPrice());
            return view;
        }


    }

    class FilmDetailHolder {
        TextView cinemaName;
        TextView cinemaAddress;
        TextView cinemaTime;
        TextView filmPrice;
    }
}
