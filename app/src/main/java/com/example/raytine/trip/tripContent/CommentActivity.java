package com.example.raytine.trip.tripContent;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.raytine.keepmoving.R;
import com.example.raytine.trip.daily.DailyContract;
import com.example.raytine.trip.home.HomeActivity;
import com.example.raytine.trip.home.model.TripData;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by raytine on 2017/5/21.
 */

public class CommentActivity extends Activity implements CommentContract.View {
    private CommentContract.Presenter presenter;
    private TextView commit;
    private EditText content;
    private ListView contentListView;
    private String tripId;
    private String userId;
    private ProgressDialog progressDialog;
    private List<CommentModel> commentModelList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_layout);
        new CommentPresenter(this);
        initDatas();
        initViews();

    }

    private void initDatas() {
        tripId = getIntent().getStringExtra("tripId");
        userId = getIntent().getStringExtra("userId");
        showProgressDialog();
        presenter.loadComment();
    }

    private void initViews() {
        contentListView = (ListView) findViewById(R.id.lv_comment);
        commit = (TextView) findViewById(R.id.tv_comment_commit);
        content = (EditText) findViewById(R.id.et_comment_content);
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    @Override
    public void setPresenter(CommentContract.Presenter var) {
        this.presenter = var;
    }

    @Override
    public void loadContentSuccess(String msg, List<Object> objectList) {
        for(int i = 0; i<objectList.size();i++){
            commentModelList.add((CommentModel) objectList.get(i));
        }
        dismissDialog();
    }

    @Override
    public void loadContentFailed(String msg) {
        dismissDialog();
    }

    class CommentAdapter extends ArrayAdapter<TripData> {
        private List<TripData> list = new ArrayList<>();
        public CommentAdapter(Context context, int resource, List<TripData> objects) {
            super(context, resource, objects);
            this.list = objects;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Nullable
        @Override
        public TripData getItem(int position) {
            return list.get(position);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            ViewHolder holder;
            if(convertView == null){
                holder  = new ViewHolder();
                view = LayoutInflater.from(CommentActivity.this).inflate(R.layout.item_trip, null);
                holder.tripDescribe = (TextView) view.findViewById(R.id.item_trip_describe);
                view.setTag(holder);
            }else{
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }

            return view;
        }
    }

    class ViewHolder{
        TextView tripDescribe;
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


}
