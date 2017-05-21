package com.example.raytine.trip.daily;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.raytine.keepmoving.R;
import com.example.raytine.trip.application.MyLeanCloudApplication;
import com.example.raytine.trip.home.model.TripData;
import com.example.raytine.trip.user.model.User;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by raytine on 2017/5/18.
 */

public class AddDailyTripActivity extends Activity implements DailyContract.View{
    private DailyContract.Presenter presenter;
    private TextView setting;
    private ImageView addImage;
    private CircleImageView userImage;
    private EditText tirpName;
    private TextView selectImage;
    private EditText addDescribe;
    private EditText tripAddress;
    private RelativeLayout dailyLayout;
    private TextView tripCommit;

    private String tirpimagePath;
    private String dailyImagePath;
    private int imageType = 0;
    private List<TripData.DiaryTrip> diaryTripList;
    private ProgressDialog progressDialog;
    private TripData data;
    private String tripObjectId;

    private static final int Choose_Photo = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_daily_trip_layout);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        new DailyPresenter(this);
        initViews();
        initDatas();
    }

    private void initDatas() {
        User user = MyLeanCloudApplication.getIns().getUser();
        if(user.getTripId().equals("")){
            tripCommit.setVisibility(View.VISIBLE);
        }else{
            tripCommit.setVisibility(View.GONE);
            showProgressDialog();
            presenter.updateDaily(user.getTripId());

        }
    }

    private void initViews() {
        dailyLayout = (RelativeLayout) findViewById(R.id.lv_daily);
        tripCommit = (TextView) findViewById(R.id.tv_commit_trip);
        tripAddress = (EditText) findViewById(R.id.tv_daily_trip_address);
        addImage = (ImageView) findViewById(R.id.iv_add_imageView);
        setting = (TextView) findViewById(R.id.iv_daily_setting);
        userImage = (CircleImageView) findViewById(R.id.cv_daily_user_image);
        tirpName = (EditText) findViewById(R.id.tv_daily_trip_name);
        selectImage = (TextView) findViewById(R.id.tv_add_image);
        addDescribe = (EditText) findViewById(R.id.tv_add_describe);
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageType = 1;
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.setType("image/*");
                startActivityForResult(intent, Choose_Photo);
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = MyLeanCloudApplication.getIns().getUser();
                showProgressDialog();
                presenter.updateUserTirp(user.getObjectId());
            }
        });

        tripCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tripCommit.setVisibility(View.GONE);
                tripAddress.setEnabled(false);
                tirpName.setEnabled(false);
                User user  = MyLeanCloudApplication.getIns().getUser();
                data = new TripData();
                data.setUserId(user.getObjectId());
                data.setImageUrl(tirpimagePath);
                data.setAddress(tripAddress.getText().toString());
                data.setTripDescribe(addDescribe.getText().toString());
                data.setTripUserName(user.getNickname());
                data.setTripName(tirpName.getText().toString());
                showProgressDialog();
                try {
                    presenter.updateDailyTrip(data);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = MyLeanCloudApplication.getIns().getUser();
                if(user.getTripId().equals("")){
                    diaryTripList = new ArrayList<>();
                    data = new TripData();
                }else{
                    diaryTripList = data.getDiaryTripList();
                }
                if(diaryTripList == null){
                    diaryTripList = new ArrayList<TripData.DiaryTrip>();
                }
                TripData.DiaryTrip diaryTrip = new TripData.DiaryTrip();
                diaryTrip.setDiaryImageUrl(dailyImagePath);
                diaryTrip.setDiaryContent(addDescribe.getText().toString());
                diaryTripList.add(diaryTrip);
                data.setDiaryTripList(diaryTripList);
                data.setTripId(tripObjectId);
                data.setTripDescribe(addDescribe.getText().toString());
                try {
                    updateTripData(data);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageType = 2;
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.setType("image/*");
                startActivityForResult(intent, Choose_Photo);
            }
        });

    }

    private void updateTripData(TripData data) throws FileNotFoundException {
        showProgressDialog();
        presenter.loadDailyImageFile(data);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Choose_Photo:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT > 19) {
                        handleImageOnKitKat(data);
                    } else {
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;

        }
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void handleImageOnKitKat(Intent data) {
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                if(imageType == 1){
                    dailyImagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
                }else if(imageType == 2){
                    tirpimagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
                }
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                if(imageType == 1){
                    dailyImagePath = getImagePath(contentUri, null);
                }else if(imageType == 2){
                    dailyImagePath = getImagePath(contentUri, null);
                }

            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            if(imageType == 1){
                dailyImagePath = getImagePath(uri, null);
            }else if(imageType == 2){
                tirpimagePath = getImagePath(uri, null);
            }

        }
        if(imageType == 1){
            displayImage(dailyImagePath);
        }else if(imageType == 2){
            displayImage(tirpimagePath);
        }
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            BitmapFactory.Options options=new BitmapFactory.Options();
            options.inSampleSize=4;
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath,options);
            if(imageType == 1){
                addImage.setImageBitmap(bitmap);
            }else if(imageType == 2){
                userImage.setImageBitmap(bitmap);
            }
            Toast.makeText(AddDailyTripActivity.this, imagePath, Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(AddDailyTripActivity.this, "failed", Toast.LENGTH_SHORT).show();
        }
    }

    public String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    @Override
    public void setPresenter(DailyContract.Presenter var) {
        this.presenter = var;
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
    public void loadFileSuccess(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        presenter.updateTrip(data);
    }

    @Override
    public void loadFielFailed(String msg) {
        dismissDialog();
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateTripRecordSuccess(String msg) {
        dismissDialog();
        dailyLayout.setVisibility(View.VISIBLE);
        tripObjectId = msg;
    }

    @Override
    public void updateTripRecordFailed(String msg) {
        dismissDialog();
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateDailySuccess(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        dismissDialog();
        finish();
    }

    @Override
    public void updateDailyFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        dismissDialog();
    }

    @Override
    public void updateSuccess(String msg, TripData data) {
        Picasso.with(this).load(data.getImageUrl()).into(userImage);
        tripAddress.setText(data.getAddress());
        tirpName.setText(data.getTripName());
        dailyLayout.setVisibility(View.VISIBLE);
        tripObjectId = data.getTripId();
        this.data = data;
        dismissDialog();
    }

    @Override
    public void updateFailed(String msg) {
        dismissDialog();
    }
}
