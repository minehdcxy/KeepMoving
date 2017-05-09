package com.example.raytine.keepmoving.user.manage.query;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVFile;
import com.example.raytine.keepmoving.R;
import com.example.raytine.keepmoving.home.model.FilmData;
import com.example.raytine.keepmoving.user.manage.edit.ActivityManageFilmInfo;
import com.example.raytine.keepmoving.util.widget.ManageDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityUserManage extends ActionBarActivity implements View.OnClickListener,
        AdapterView.OnItemClickListener, UserManageContract.View {

    private final String TAG = ActivityUserManage.class.getSimpleName();
    private final static int REQUEST_CODE = 0;

    private ImageView manageBackIv;
    private ListView listView;
    private Button addNewFilmBtn;
    private ManageDialog dialog;
    private FilmAdapter adapter;

    private UserManageContract.Presenter presenter;
    private List<FilmData> filmDataList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manage);

        new UserManagePresenter(this);
        initViews();
        initData();
    }

    private void initViews() {
        manageBackIv = (ImageView) findViewById(R.id.user_manage_back_iv );
        listView = (ListView) findViewById(R.id.user_manage_film_lv);
        addNewFilmBtn = (Button) findViewById(R.id.user_manage_add_btn);

        manageBackIv.setOnClickListener(this);
        listView.setOnItemClickListener(this);
        addNewFilmBtn.setOnClickListener(this);

    }

    private void initData() {
        presenter.queryFilms();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_manage_back_iv:
                onBackPressed();
                break;
            case R.id.user_manage_add_btn:
                Intent manageIntent = new Intent(ActivityUserManage.this, ActivityManageFilmInfo.class);
                startActivityForResult(manageIntent, REQUEST_CODE);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final FilmData film = filmDataList.get(position);
        dialog = new ManageDialog(this, new ManageDialog.DialogOnClickListener() {
            @Override
            public void clickPosition(int position) {
                switch (position) {
                    case 0:
                        Intent intent = new Intent(ActivityUserManage.this, ActivityManageFilmInfo.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("film", film);
                        intent.putExtras(bundle);
                        startActivityForResult(intent, REQUEST_CODE);
                        break;
                    case 1:
                        presenter.deleteFilm(film.getFilmId());
                        break;
                }
            }
        });
        dialog.show();
    }

    @Override
    public void querySuccess(List<Object> objectList) {
        for (int i = 0; i < objectList.size(); i++) {
            filmDataList.add(i, (FilmData)objectList.get(i));
        }
        adapter = new FilmAdapter(ActivityUserManage.this, R.layout.film_item, filmDataList);
        listView.setAdapter(adapter);
    }

    @Override
    public void queryFailed(String msg) {
    }

    @Override
    public void deleteSuccess(String msg) {
        reloadFilm();
        Toast.makeText(ActivityUserManage.this, "删除成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void deleteFailed(String msg) {

    }

    @Override
    public void setPresenter(UserManageContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (RESULT_OK == resultCode) {
                    reloadFilm();
                } else if (RESULT_CANCELED == resultCode) {

                }
                break;
        }
    }

    private void reloadFilm() {
        adapter.clear();
        adapter.notifyDataSetChanged();
        initData();
    }

    @Override
    protected void onDestroy() {
        presenter.destroy();
        super.onDestroy();
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view;
            final FilmHolder holder;
            if (convertView == null) {
                view = LayoutInflater.from(context).inflate(R.layout.film_item, null);
                holder = new FilmHolder();
                holder.imageView = (CircleImageView) view.findViewById(R.id.film_image);
                holder.filmName = (TextView) view.findViewById(R.id.tv_film_name);
                holder.filmIntroduction = (TextView) view.findViewById(R.id.tv_film_introduction);
                holder.filmPrice = (TextView) view.findViewById(R.id.tv_film_price);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (FilmHolder) view.getTag();
            }

            new Thread(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AVFile file = new AVFile("test.jpg", filmDataList.get(position).getFilmImage(), new HashMap<String, Object>());
                            Picasso.with(ActivityUserManage.this).load(file.getThumbnailUrl(true, 200, 200)).into(holder.imageView);
                        }
                    });
                }
            }).start();
            holder.filmName.setText(filmDataList.get(position).getFilmName());
            holder.filmIntroduction.setText(filmDataList.get(position).getFilmIntroduction());
            holder.filmPrice.setText(filmDataList.get(position).getFilmPrice());
            return view;
        }
    }

    class FilmHolder {
        CircleImageView imageView;
        TextView filmName;
        TextView filmIntroduction;
        TextView filmPrice;
    }

}
