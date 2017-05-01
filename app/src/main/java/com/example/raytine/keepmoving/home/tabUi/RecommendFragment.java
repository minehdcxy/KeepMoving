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

public class RecommendFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener{
    private ListView listView;
    private TextView searchButton;
    private EditText searchContent;
    private List<FilmData> filmDataList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_recommend, container, false);
        initViews(view);
        initDatas();
        FilmAdapter adapter = new FilmAdapter(getActivity(), R.layout.film_item, filmDataList);
        listView.setAdapter(adapter);
        return view;
    }

    private void initDatas() {
        for (int i = 0 ;i<10;i++){
            FilmData filmData = new FilmData();
            filmData.setFilmName("致青春2");
            filmData.setFilmIntrodution("这是个凄美的爱情故事，男猪脚的名字叫梁春凤，女猪脚的名字叫马冬梅");
            filmData.setFilmPrice("105元");
            filmDataList.add(filmData);
        }
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
        switch(view.getId()){
            case R.id.tv_recommend_search_button:
                break;
            case R.id.et_recommend_search_content:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(getActivity(), FilmDetailActivity.class);
        startActivity(intent);
    }

    public class FilmAdapter extends ArrayAdapter<FilmData>{
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
            if(convertView == null){
                view = LayoutInflater.from(context).inflate(R.layout.film_item, null);
                holder = new FilmHolder();
                holder.imageView = (CircleImageView) view.findViewById(R.id.film_image);
                holder.filmName = (TextView) view.findViewById(R.id.tv_film_name);
                holder.filmIntrodution = (TextView) view.findViewById(R.id.tv_film_introduction);
                holder.filmPrice = (TextView) view.findViewById(R.id.tv_film_price);
                view.setTag(holder);
            }else{
                view = convertView;
                holder = (FilmHolder) view.getTag();
            }
            holder.filmName.setText(filmDataList.get(position).getFilmName());
            holder.filmIntrodution.setText(filmDataList.get(position).getFilmIntrodution());
            holder.filmPrice.setText(filmDataList.get(position).getFilmPrice());
            return view;
        }
    }
    class FilmHolder{
        CircleImageView imageView;
        TextView filmName;
        TextView filmIntrodution;
        TextView filmPrice;
    }
}
