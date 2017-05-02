package com.example.raytine.keepmoving.home.tabUi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.raytine.keepmoving.R;
import com.example.raytine.keepmoving.filmDetail.FilmDetailActivity;
import com.example.raytine.keepmoving.home.model.FilmData;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by raytine on 2017/4/30.
 */

public class RecommendFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, RecommendContract.View {
    private ListView listView;
    private TextView searchButton;
    private EditText searchContent;
    private List<FilmData> filmDataList = new ArrayList<>();

    private RecommendContract.Presenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_recommend, container, false);
        new RecommendPresenter(this);
        initViews(view);
        initDatas();

        return view;
    }

    private void initDatas() {
        presenter.searchAllFilm();
    }

    private void initViews(View view) {
        listView = (ListView) view.findViewById(R.id.lv_recommend);
        searchButton = (TextView) view.findViewById(R.id.tv_recommend_search_button);
        searchContent = (EditText) view.findViewById(R.id.et_recommend_search_content);
        searchButton.setOnClickListener(this);
        searchContent.setOnClickListener(this);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_recommend_search_button:
                break;
            case R.id.et_recommend_search_content:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(getActivity(), FilmDetailActivity.class);
        intent.putExtra("filmId", filmDataList.get(i).getFilmId());
        startActivity(intent);
    }

    @Override
    public void setPresenter(RecommendContract.Presenter var) {
        this.presenter = var;
    }

    @Override
    public void successfully(List<Object> objectList) {
        for (int i = 0; i < objectList.size(); i++) {
            filmDataList.add(i, (FilmData)objectList.get(i));
        }
        FilmAdapter adapter = new FilmAdapter(getActivity(), R.layout.film_item, filmDataList);
        listView.setAdapter(adapter);
    }

    public class FilmAdapter extends ArrayAdapter<FilmData> {
        private Context context;
        private List<FilmData> filmDataList;

        public FilmAdapter(Context context, int resource, List<FilmData> objects) {
            super(context, resource, objects);
            this.context = context;
            filmDataList = objects;
        }

        @Override
        public int getCount() {
            return filmDataList.size();
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            FilmHolder holder;
            if (convertView == null) {
                view = LayoutInflater.from(context).inflate(R.layout.film_item, null);
                holder = new FilmHolder();
                holder.imageView = (CircleImageView) view.findViewById(R.id.film_image);
                holder.filmName = (TextView) view.findViewById(R.id.tv_film_name);
                holder.filmIntrodution = (TextView) view.findViewById(R.id.tv_film_introduction);
                holder.filmPrice = (TextView) view.findViewById(R.id.tv_film_price);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (FilmHolder) view.getTag();
            }
            holder.filmName.setText(filmDataList.get(position).getFilmName());
            holder.filmIntrodution.setText(filmDataList.get(position).getFilmIntrodution());
            holder.filmPrice.setText(filmDataList.get(position).getFilmPrice());
            return view;
        }
    }

    class FilmHolder {
        CircleImageView imageView;
        TextView filmName;
        TextView filmIntrodution;
        TextView filmPrice;
    }
}
