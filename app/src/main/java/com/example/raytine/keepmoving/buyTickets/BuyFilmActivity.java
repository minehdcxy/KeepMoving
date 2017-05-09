package com.example.raytine.keepmoving.buyTickets;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.raytine.keepmoving.R;
import com.example.raytine.keepmoving.application.MyLeanCloudApplication;
import com.example.raytine.keepmoving.filmDetail.CinemaModel;
import com.example.raytine.keepmoving.filmDetail.FilmDetailActivity;
import com.example.raytine.keepmoving.user.model.User;
import com.example.raytine.keepmoving.util.widget.BuyView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by raytine on 2017/5/7.
 */

public class BuyFilmActivity extends Activity implements BuyTicketsContract.View {
    private GridView seatView;
    private TextView okOrder;
    private TextView commit;
    private List<Integer> list = new ArrayList<>();

    private BuyTicketsContract.Presenter presenter;

    private CinemaModel model;

    private String filmId;
    private String myCdKey;

    private static int STATE_SALE = 3;
    private static int STATE_SELECT = 2;
    private static int STATE_CAN_SELECT = 1;

    private float filmMoney;

    private BuyView buyView;
    private List<BuyTicketsModel> buyTicketsModelList = new ArrayList<>();

    private ProgressDialog progressDialog;

    private String column;
    private String row;

    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_film);
        new BuyTicketsPresenter(this);
        initViews();
        initDatas();
        SeatGridAdapter adapter = new SeatGridAdapter();
        seatView.setAdapter(adapter);
    }

    private void initDatas() {
        Bundle bundle = getIntent().getExtras();
        filmId = bundle.getString(FilmDetailActivity.FILM_ID);
        filmMoney = bundle.getFloat(FilmDetailActivity.USER_MONEY);
        model = (CinemaModel) bundle.getSerializable(FilmDetailActivity.CINEMA_MODEL);
        for(int i = 0 ;i< model.getTicket().getTickets().get(filmId).length ; i++){
            list.add(1);
        }

    }

    private void initViews() {
        seatView = (GridView) findViewById(R.id.gv_seat);
        okOrder = (TextView) findViewById(R.id.tv_ok_order);
        buyView = (BuyView) findViewById(R.id.bv_buy_tickets);
        commit = (TextView) findViewById(R.id.tv_buy_commit);
        commit.setEnabled(false);
        okOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder rowBuilder = new StringBuilder();
                for(int i = 0 ;i < model.getTicket().getTickets().get(filmId).length; i++){
                    if(model.getTicket().getTickets().get(filmId)[i] == STATE_SELECT){
                        BuyTicketsModel ticketsModel = new BuyTicketsModel();
                        ticketsModel.setBuyRow((i+1)/9+1);
                        ticketsModel.setBuyColumn((i+1)%9);
                        rowBuilder.append(String.valueOf((i+1)/9+1)).append(String.valueOf((i+1)%9));
                        buyTicketsModelList.add(ticketsModel);
                        model.getTicket().getTickets().get(filmId)[i] = STATE_SALE;
                        MyLeanCloudApplication.getIns().getUser().setWallet(MyLeanCloudApplication.getIns().getUser().getWallet() - filmMoney);
                    }
                }
                showProgressDialog();
                presenter.updateUserInfo(MyLeanCloudApplication.getIns().getUser());
                myCdKey = rowBuilder.toString();
                buyView.setDatas(buyTicketsModelList);
                okOrder.setText("已选座位");
                commit.setEnabled(true);
                commit.setBackgroundResource(R.drawable.bg_green_soild_style);
            }
        });
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgressDialog();
                presenter.updateSeat(model.getCinemaObjectId(), model.getTicket());
                User user = MyLeanCloudApplication.getIns().getUser();
                myCdKey = user.getTelephone() + myCdKey;
                user.getCard().getCarList().add(myCdKey);
                presenter.updateUserInfo(user);
                finish();


            }
        });
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
    public void setPresenter(BuyTicketsContract.Presenter var) {
        this.presenter = var;

    }

    @Override
    public void updateSuccess(String msg) {
        dismissDialog();
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateFailed(String msg) {
        dismissDialog();
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }

    class SeatGridAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup viewGroup) {
            ViewHolder holder = null;
            View view;
            if(convertView == null){
                holder = new ViewHolder();
                view = LayoutInflater.from(BuyFilmActivity.this).inflate(R.layout.seat_item_layout, null);
                holder.seatView = (TextView) view.findViewById(R.id.tv_seat_item);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }
            final ViewHolder finalHolder = holder;
            if(model.getTicket().getTickets().get(filmId)[position] == STATE_CAN_SELECT){
                holder.seatView.setBackgroundResource(R.drawable.bg_green_corner_style);
            } else if (model.getTicket().getTickets().get(filmId)[position] == STATE_SALE){
                holder.seatView.setBackgroundResource(R.drawable.bg_red_soild_style);
            }
            holder.seatView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(model.getTicket().getTickets().get(filmId)[position] == STATE_CAN_SELECT){
                        if(counter >= 4){
                            Toast.makeText(BuyFilmActivity.this,"超出购票范围",Toast.LENGTH_SHORT).show();
                        } else {
                            counter ++;
                            finalHolder.seatView.setBackgroundResource(R.drawable.bg_green_soild_style);
                            model.getTicket().getTickets().get(filmId)[position] = STATE_SELECT;
                        }
                    } else if(model.getTicket().getTickets().get(filmId)[position] == STATE_SELECT){
                        counter --;
                        finalHolder.seatView.setBackgroundResource(R.drawable.bg_green_corner_style);
                        model.getTicket().getTickets().get(filmId)[position] = STATE_CAN_SELECT;
                    }
                }
            });
            return view;
        }
    }

    class ViewHolder{
        TextView seatView;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
