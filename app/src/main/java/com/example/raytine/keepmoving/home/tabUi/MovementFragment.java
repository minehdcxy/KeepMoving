package com.example.raytine.keepmoving.home.tabUi;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.example.raytine.keepmoving.R;
import com.example.raytine.keepmoving.home.model.KeepModel;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;


/**
 * Created by raytine on 2017/5/2.
 */

public class MovementFragment extends Fragment {
    private ColumnChartView columnChartView;
    private ColumnChartData data;
    private ProgressDialog progressDialog;
    private List<KeepModel> keepModelslist = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View  view  = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_monement_layout, null);
        columnChartView = (ColumnChartView) view.findViewById(R.id.week_charView);
        columnChartView.setZoomEnabled(true);//设置是否支持缩放
        //columnChartView.setOnValueTouchListener(ColumnChartOnValueSelectListener touchListener);//为图表设置值得触摸事件
        columnChartView.setInteractive(true);//设置图表是否可以与用户互动
        columnChartView.setValueSelectionEnabled(true);//设置图表数据是否选中进行显示
        showProgressDialog();
        AVQuery<AVObject> query = new AVQuery<>("movement");
        query.whereExists("objectId");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if(e == null){
                    int i = 1;
                    for(AVObject object : list){
                        KeepModel model = new KeepModel();
                        model.setDayu((float) object.getInt("step"));
                        model.setLable("周"+String.valueOf(i));
                        keepModelslist.add(model);
                        generateData(keepModelslist);
                    }
                    dismissDialog();
                }
            }
        });
        return view;
    }

    private void generateData(List<KeepModel> list){
        //每个集合显示几条柱子
        int numSubcolumns = 1;
        //显示多少个集合
        int numColumns = list.size();
        //保存所有的柱子
        List<Column> columns = new ArrayList<Column>();
        //保存每个竹子的值
        List<SubcolumnValue> values;
        List<AxisValue> axisXValues = new ArrayList<AxisValue>();
        //对每个集合的柱子进行遍历
        for (int i = 0; i < numColumns; ++i) {

            values = new ArrayList<SubcolumnValue>();
            //循环所有柱子（list）
            for (int j = 0; j < numSubcolumns; ++j) {
                //创建一个柱子，然后设置值和颜色，并添加到list中
                values.add(new SubcolumnValue(list.get(i).getDayu(), ChartUtils.pickColor()));
                //设置X轴的柱子所对应的属性名称
                axisXValues.add(new AxisValue(i).setLabel(list.get(i).getLable()));
            }
            //将每个属性的拥有的柱子，添加到Column中
            Column column = new Column(values);
            //是否显示每个柱子的Lable
            column.setHasLabels(true);
            //设置每个柱子的Lable是否选中，为false，表示不用选中，一直显示在柱子上
            column.setHasLabelsOnlyForSelected(false);
            //将每个属性得列全部添加到List中
            columns.add(column);
        }
        //设置Columns添加到Data中
        data = new ColumnChartData(columns);
        //设置X轴显示在底部，并且显示每个属性的Lable，字体颜色为黑色，X轴的名字为“学历”，每个柱子的Lable斜着显示，距离X轴的距离为8
        data.setAxisXBottom(new Axis(axisXValues).setHasLines(true).setTextColor(Color.BLACK).setName("时间段").setHasTiltedLabels(true).setMaxLabelChars(8));
        //属性值含义同X轴
        data.setAxisYLeft(new Axis().setHasLines(true).setName("步数").setTextColor(Color.BLACK).setMaxLabelChars(5));
        //最后将所有值显示在View中
        columnChartView.setColumnChartData(data);

    }

    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("正在更新...");
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
