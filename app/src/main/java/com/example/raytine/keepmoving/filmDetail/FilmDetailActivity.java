package com.example.raytine.keepmoving.filmDetail;

import android.app.ProgressDialog;
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
import com.example.raytine.keepmoving.home.model.FilmData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raytine on 2017/4/30.
 */

public class FilmDetailActivity extends ActionBarActivity implements FilmDetailContract.View {
    private ListView cinemaList;
    private List<CinemaModel> cinemaModelList = new ArrayList<>();

    private TextView filmName;
    private TextView filmType;
    private TextView filmAddress;
    private TextView filmTime;
    private TextView filmDirector;
    private TextView filmVersion;
    private ProgressDialog progressDialog;

    private FilmData filmData;

    private FilmDetailContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_detail);
        new FilmDetailPresenter(this);
        initViews();
        initDatas();

    }

    private void initDatas() {
        String filmId = getIntent().getStringExtra("filmId");
        showProgressDialog();
        presenter.loadFilmDetailMessage(filmId);
        for (int i = 0; i < 10; i++) {
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
        filmName = (TextView) findViewById(R.id.tv_detail_film_name);
        filmAddress = (TextView) findViewById(R.id.tv_detail_film_address);
        filmTime = (TextView) findViewById(R.id.tv_detail_film_time);
        filmDirector = (TextView) findViewById(R.id.tv_detail_film_director);
        filmType = (TextView) findViewById(R.id.tv_detail_film_type);
        filmVersion = (TextView) findViewById(R.id.tv_detail_film_version);

    }

    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    private void dismissDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }


    @Override
    public void setPresenter(FilmDetailContract.Presenter var) {
        this.presenter = var;
    }

    @Override
    public void loadSuccess(FilmData data) {
        filmData = data;
        filmName.setText("片名:" + data.getFilmName());
        filmDirector.setText("导演:" + data.getFilmDirector());
        filmTime.setText("片长:" + data.getFilmTime() + "分钟");
        filmType.setText("类型:" + data.getFilmType());
        filmAddress.setText("发行地:" + data.getFilmAddress());
        filmVersion.setText("观影版本:" + data.getFilmVersion());
        String str = data.getFilmStr();
        String[] strArrays = str.split("\\|");
        List<String> list = new ArrayList<>();
        for (int i = 0; i < strArrays.length; i++) {
            list.add(strArrays[i].split(",")[0]);
        }
        presenter.loadFilmCinemaMessage(list);
    }

    @Override
    public void loadFailed(String message) {
        dismissDialog();
    }

    @Override
    public void loadCinemaSuccess(List<Object> list) {
        cinemaModelList.clear();
        for (int i = 0; i < list.size(); i++) {
            cinemaModelList.add(i, (CinemaModel) list.get(i));
        }
        CinemaAdapter adapter = new CinemaAdapter(this, R.layout.cinema_item, cinemaModelList);
        cinemaList.setAdapter(adapter);
        dismissDialog();
    }

    @Override
    public void loadCinemaFailed(String message) {
        dismissDialog();
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
            holder.cinemaName.setText("片名:"+cinemaModelList.get(position).getCinemaName());
            holder.cinemaAddress.setText("影院地址:"+cinemaModelList.get(position).getAddress());

            holder.filmPrice.setText(filmData.getFilmPrice());
            String str = filmData.getFilmStr();
            String[] strArrays = str.split("\\|");
            List<String> list = new ArrayList<>();
            for (int i = 0; i < strArrays.length; i++) {
                if(cinemaModelList.get(position).getCinemaId().equals(strArrays[i].split(",")[0])){
                    holder.cinemaTime.setText("观影时间:"+strArrays[i].split(",")[1]);
                }
            }
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
