package com.example.raytine.keepmoving.user.manage.edit;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.raytine.keepmoving.R;
import com.example.raytine.keepmoving.home.model.FilmData;

import java.util.ArrayList;
import java.util.Calendar;

public class ActivityManageFilmInfo extends ActionBarActivity implements View.OnClickListener, ManageFilmInfoContract.View{

    private final String TAG = ActivityManageFilmInfo.class.getSimpleName();

    public static final  int CHOOSE_PHOTO = 1;

    private ManageFilmInfoContract.Presenter presenter;

    private ImageView manageFilmBackIv;
    private ImageView manageFilmPictureIv;
    private TextView manageFilmTitleTv;
    private EditText filmNameEt;
    private EditText filmTypeEt;
    private EditText filmVersionEt;
    private EditText filmAddressEt;
    private EditText filmTimeEt;
    private EditText filmDirectorEt;
    private TextView filmStrTv;
    private Button showCinemaBtn;
    private Button showDateBtn;
    private Button showTimeBtn;
    private Button addInfoBtn;
    private EditText filmIntroductionEt;
    private Button commitBtn;

    private int choiceCinema = -1;
    private String choiceDate;
    private String choiceTime;
    private StringBuffer filmStr;

    private boolean isWantAddInfo = false;

    private final String[] items = {"黑龙江大学音乐厅", "哈西万达影城", "学府四嘉兴影城", "乐松万达影城", "中央大街万达影城"};

    private ArrayList<Integer> choiceDeleteInfo = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_film);

        new ManageFilmInfoPresenter(this);
        initViews();
        initData();
    }

    private void initViews() {
        manageFilmBackIv = (ImageView) findViewById(R.id.manage_film_back_iv);
        manageFilmPictureIv = (ImageView) findViewById(R.id.manage_film_picture_iv);
        manageFilmTitleTv = (TextView) findViewById(R.id.manage_film_title_tv);
        filmNameEt = (EditText) findViewById(R.id.manage_film_name_et);
        filmTypeEt = (EditText) findViewById(R.id.manage_film_type_et);
        filmVersionEt = (EditText) findViewById(R.id.manage_film_version_et);
        filmAddressEt = (EditText) findViewById(R.id.manage_film_address_et);
        filmTimeEt = (EditText) findViewById(R.id.manage_film_time_et);
        filmDirectorEt = (EditText) findViewById(R.id.manage_film_director_et);
        filmStrTv = (TextView) findViewById(R.id.manage_film_filmStr_tv);
        showCinemaBtn = (Button) findViewById(R.id.manage_film_show_cinema_btn);
        showDateBtn = (Button) findViewById(R.id.manage_film_show_date_btn);
        showTimeBtn = (Button) findViewById(R.id.manage_film_show_time_btn);
        addInfoBtn = (Button) findViewById(R.id.manage_film_addInfo_btn);
        filmIntroductionEt = (EditText) findViewById(R.id.manage_film_introduction_et);
        commitBtn = (Button) findViewById(R.id.manage_film_commit_btn);

        manageFilmBackIv.setOnClickListener(this);
        manageFilmPictureIv.setOnClickListener(this);
        filmStrTv.setOnClickListener(this);
        showCinemaBtn.setOnClickListener(this);
        showDateBtn.setOnClickListener(this);
        showTimeBtn.setOnClickListener(this);
        addInfoBtn.setOnClickListener(this);
        commitBtn.setOnClickListener(this);
    }

    private void initData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (null != bundle) {
            manageFilmTitleTv.setText("编辑影视信息");
            FilmData film = (FilmData) bundle.get("film");
        } else {
            manageFilmTitleTv.setText("添加影视信息");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.manage_film_back_iv:
                onBackPressed();
                break;
            case R.id.manage_film_picture_iv:
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.setType("image/*");
                startActivityForResult(intent, CHOOSE_PHOTO);
                break;
            case R.id.manage_film_filmStr_tv:
                showDeleteInfoDialog();
                break;
            case R.id.manage_film_show_cinema_btn:
                showChoiceCinemaDialog();
                break;
            case R.id.manage_film_show_date_btn:
                showDateDialog();
                break;
            case R.id.manage_film_show_time_btn:
                showTimeDialog();
                break;
            case R.id.manage_film_addInfo_btn:
                addInfo();
                break;
            case R.id.manage_film_commit_btn:
                commit();
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CHOOSE_PHOTO:
                if (RESULT_OK == resultCode) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitKat(data);
                    } else {
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
        }
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.toString().replaceAll("file://", "");
        }
        Log.i(TAG, imagePath);
        displayImage(imagePath);
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            manageFilmPictureIv.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_LONG).show();
        }
    }

    private void showDeleteInfoDialog() {
        String tmp = filmStrTv.getText().toString();
        if (TextUtils.isEmpty(tmp)) {
            Toast.makeText(ActivityManageFilmInfo.this, "请先添加上映院线信息后再进行查看", Toast.LENGTH_SHORT).show();
        } else {
            String[] info = tmp.split(",");
            boolean[] initChoiceSets = new boolean[info.length];
            choiceDeleteInfo.clear();
            AlertDialog.Builder builder = new AlertDialog.Builder(ActivityManageFilmInfo.this);
            builder.setTitle("选择可以删除院线信息");
            builder.setMultiChoiceItems(info, initChoiceSets,
                    new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                            if (isChecked) {
                                choiceDeleteInfo.add(which);
                            } else {
                                choiceDeleteInfo.remove(Integer.valueOf(which));
                            }
                        }
                    });
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int size = choiceDeleteInfo.size();
                    Log.i(TAG, "size : " + size);
                    for (int i : choiceDeleteInfo) {
                        Log.i(TAG, "i : " + i);
                    }
                }
            });
            builder.show();
        }

    }

    private void showChoiceCinemaDialog() {
        choiceCinema = -1;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择上映影院");
        builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                choiceCinema = which;
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (-1 == choiceCinema) {
                    choiceCinema = 0;
                }
                showCinemaBtn.setText(items[choiceCinema]);
                Toast.makeText(ActivityManageFilmInfo.this, "选择" + items[choiceCinema], Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }

    private void showDateDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                choiceDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                showDateBtn.setText(choiceDate);
                Toast.makeText(ActivityManageFilmInfo.this, "选择日期" + choiceDate, Toast.LENGTH_SHORT).show();
            }
        }, year, month, day).show();
    }

    private void showTimeDialog() {
        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                choiceTime = hourOfDay + ":" + minute + ":00";
                showTimeBtn.setText(choiceTime);
                Toast.makeText(ActivityManageFilmInfo.this, "选择时间" + choiceTime, Toast.LENGTH_SHORT).show();
            }
        }, hours, minute, true).show();
    }

    private void addInfo() {
        if (isWantAddInfo) {
            if (-1 == choiceCinema) {
                Toast.makeText(ActivityManageFilmInfo.this, "请选择上映影院", Toast.LENGTH_SHORT).show();
                return;
            }
            if (null == choiceDate) {
                Toast.makeText(ActivityManageFilmInfo.this, "请选择上映日期", Toast.LENGTH_SHORT).show();
                return;
            }
            if (null == choiceTime) {
                Toast.makeText(ActivityManageFilmInfo.this, "请选择上映时间", Toast.LENGTH_SHORT).show();
                return;
            }
            String tmp = filmStrTv.getText().toString();
            if (!TextUtils.isEmpty(tmp)) {
                tmp += ",";
            }
            String currTemp = items[choiceCinema] + " " + choiceDate + " " + choiceTime;
            filmStrTv.setText(tmp + currTemp);
            Toast.makeText(ActivityManageFilmInfo.this, "添加信息为:" + currTemp, Toast.LENGTH_LONG).show();
            filmStr.append((choiceCinema + 1)).append(",").append(choiceDate).append(" ").append(choiceTime).append("|");
            addInfoBtn.setText("点击开始添加上映院线");
            showCinemaBtn.setEnabled(false);
            showDateBtn.setEnabled(false);
            showTimeBtn.setEnabled(false);
            showCinemaBtn.setText("上映影院");
            showDateBtn.setText("上映日期");
            showTimeBtn.setText("上映时间");
            choiceCinema = -1;
            choiceDate = null;
            choiceTime = null;
            isWantAddInfo = false;
            Log.i(TAG, "filmStr : " + filmStr.toString());
        } else {
            if (null == filmStr) {
                filmStr = new StringBuffer();
            }
            addInfoBtn.setText("点击完成添加上映院线");
            showCinemaBtn.setEnabled(true);
            showDateBtn.setEnabled(true);
            showTimeBtn.setEnabled(true);
            isWantAddInfo = true;
        }
    }

    private void commit() {
        String filmName = filmNameEt.getText().toString();
        String filmType = filmTypeEt.getText().toString();
        String filmVersion = filmVersionEt.getText().toString();
        String filmAddress = filmAddressEt.getText().toString();
        String filmTime = filmTimeEt.getText().toString();
        String filmDirector = filmDirectorEt.getText().toString();
        String filmIntroduction = filmIntroductionEt.getText().toString();
        if (TextUtils.isEmpty(filmName)) {
            Toast.makeText(ActivityManageFilmInfo.this, "影片名称不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(filmType)) {
            Toast.makeText(ActivityManageFilmInfo.this, "影片类型不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(filmVersion)) {
            Toast.makeText(ActivityManageFilmInfo.this, "影片版本不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(filmAddress)) {
            Toast.makeText(ActivityManageFilmInfo.this, "影片地区不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(filmTime)) {
            Toast.makeText(ActivityManageFilmInfo.this, "影片时长不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(filmDirector)) {
            Toast.makeText(ActivityManageFilmInfo.this, "影片导演不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(filmIntroduction)) {
            Toast.makeText(ActivityManageFilmInfo.this, "影片描述不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(filmStr)) {
            Toast.makeText(ActivityManageFilmInfo.this, "影片上映院线不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

    }

    @Override
    public void setPresenter(ManageFilmInfoContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected void onDestroy() {
        presenter.destroy();
        super.onDestroy();
    }
}
