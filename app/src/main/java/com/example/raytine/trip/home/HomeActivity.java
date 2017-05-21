package com.example.raytine.trip.home;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.raytine.keepmoving.R;
import com.example.raytine.trip.daily.AddDailyTripActivity;
import com.example.raytine.trip.home.model.TripData;
import com.example.raytine.trip.tripContent.TripContentActivity;
import com.example.raytine.trip.user.UserActivity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends ActionBarActivity implements TripContract.View, AdapterView.OnItemClickListener, View.OnClickListener{

    private final String TAG = HomeActivity.class.getSimpleName();

    private Toolbar toolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    private TripContract.Presenter presenter;
    private ProgressDialog progressDialog;
    private DrawerLayout mDrawerLayout;
    private ListView tripList;
    private List<TripData> tripDatas = new ArrayList<>();
    private SliderLayout banner;
    private PagerIndicator indicator;
    private LinearLayout leftUser;
    private FloatingActionButton floatingActionButton;
    private int[] imgurl = new int[]{R.drawable.image_one,
            R.drawable.image_two, R.drawable.image_three};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        new TripPresenter(this);
        initViews();
        initDatas();
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void initDatas() {
        showProgressDialog();
        presenter.updateTrip();
    }

    private void initViews() {
        floatingActionButton = (FloatingActionButton) findViewById(R.id.float_button);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.home_dr_left);
        toolbar = (Toolbar) findViewById(R.id.home_view_toolbar);
        tripList = (ListView) findViewById(R.id.lv_home_trip_record);
        View bannerView = LayoutInflater.from(this).inflate(R.layout.home_banner, null);
        indicator = (PagerIndicator) bannerView.findViewById(R.id.ind_pager);
        banner = (SliderLayout) bannerView.findViewById(R.id.slider);
        progressDialog = new ProgressDialog(this);
        leftUser = (LinearLayout) findViewById(R.id.left_lv_user);
        for (int url : imgurl) {
            TextSliderView customSliderView = new TextSliderView(this);
            customSliderView
                    .image(url)
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            banner.addSlider(customSliderView);
        }
        banner.setPresetTransformer(SliderLayout.Transformer.ZoomOut);
        banner.setCustomAnimation(new DescriptionAnimation());
        banner.setDuration(2000);
        TripAdapter adapter = new TripAdapter(this, R.layout.item_trip, tripDatas);
        tripList.addHeaderView(bannerView);
        tripList.setAdapter(adapter);
        tripList.setOnItemClickListener(this);
        leftUser.setOnClickListener(this);
        floatingActionButton.setOnClickListener(this);

    }


    @Override
    public void successfully(String msg, List<Object> tripDataList) {
        dismissDialog();
        for (int i = 0; i < tripDataList.size(); i++) {
            tripDatas.add((TripData) tripDataList.get(i));
        }
    }

    @Override
    public void failed(String msg) {

    }

    @Override
    public void setPresenter(TripContract.Presenter var) {
        this.presenter = var;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this, TripContentActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("model", tripDatas.get(i-1));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.left_lv_user:
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }finally {
                            Intent intent = new Intent(HomeActivity.this, UserActivity.class);
                            startActivity(intent);
                        }
                    }
                }).start();

                break;
            case R.id.float_button:
                Intent intent = new Intent(this, AddDailyTripActivity.class);
                startActivity(intent);
                break;
        }
    }

    class TripAdapter extends ArrayAdapter<TripData> {
        private List<TripData> list = new ArrayList<>();
        public TripAdapter(Context context, int resource, List<TripData> objects) {
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
            final ViewHolder holder;
            if(convertView == null){
                holder  = new ViewHolder();
                view = LayoutInflater.from(HomeActivity.this).inflate(R.layout.item_trip, null);
                holder.tripDescribe = (TextView) view.findViewById(R.id.item_trip_describe);
                holder.tripDate = (TextView) view.findViewById(R.id.item_trip_date);
                holder.tripAuthor = (TextView) view.findViewById(R.id.item_trip_author);
                holder.layout = (RelativeLayout) view.findViewById(R.id.ly_item);
                holder.upOvte = (TextView) view.findViewById(R.id.tv_item_upovte);
                holder.itemBackground = (RelativeLayout) view.findViewById(R.id.rl_item_trip);
                view.setTag(holder);
            }else{
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }
            holder.layout.setBackgroundResource(R.drawable.bg_trip_style);
            holder.tripDescribe.setText(list.get(position).getAddress()+"-"+list.get(position).getTripDescribe());
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            holder.tripDate.setText(df.format(list.get(position).getTripDate()));
            holder.tripAuthor.setText("by"+"-"+list.get(position).getTripUserName());
            Picasso.with(HomeActivity.this).load(list.get(position).getImageUrl()).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    holder.itemBackground.setBackground(new BitmapDrawable(getResources(),bitmap));
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });
            holder.upOvte.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.updateTrip();
                }
            });
            return view;
        }
    }

    class ViewHolder{
        TextView tripDescribe;
        TextView tripDate;
        TextView tripAuthor;
        RelativeLayout layout;
        TextView upOvte;
        RelativeLayout itemBackground;
    }

    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(HomeActivity.this);
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
