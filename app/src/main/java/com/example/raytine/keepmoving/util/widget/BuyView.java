package com.example.raytine.keepmoving.util.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.raytine.keepmoving.R;
import com.example.raytine.keepmoving.buyTickets.BuyTicketsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raytine on 17/5/8.
 */

public class BuyView extends ViewGroup {

    private int totalWidth;
    private int totalHeight;
    private int gap = 8;
    private int rows;
    private int columns;
    private List<BuyTicketsModel> urlList = new ArrayList<>();

    public BuyView(Context context) {
        super(context);
    }

    public BuyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setDatas(List<BuyTicketsModel> list) {
        if (list.size() == 0 ) {
            return;
        } else {
            urlList.clear();
            for(int i = 0 ;i< list.size();i++){
                urlList.add(list.get(i));
            }
        }
        generateChildrenLayout(list.size());
        totalWidth = getContext().getResources().getDisplayMetrics().widthPixels;
        totalHeight = getContext().getResources().getDisplayMetrics().heightPixels;
        for (int i = 0; i < list.size(); i++) {
            TextView contentView = CreateTextView();
            addView(contentView);
            layoutView();
        }
    }

    private void generateChildrenLayout(int length) {
        if (length <= 3) {
            rows = 1;
            columns = length;
        } else if (length <= 6) {
            rows = 2;
            columns = 3;
        } else {
            rows = 3;
            columns = 3;
        }
    }

    private void layoutView() {
        int singleWidth = (totalWidth - gap * 2) / 3 - 10;
        int singleHeight = 65;
        LayoutParams params = getLayoutParams();
        params.height = (singleHeight + gap) * rows;
        setLayoutParams(params);
        for (int i = 0; i < getChildCount(); i++) {
            TextView contentView = (TextView) getChildAt(i);
            contentView.setText(urlList.get(i).getBuyRow()+"排"+" "+urlList.get(i).getBuyColumn()+"座");
            contentView.setBackgroundResource(R.drawable.bg_green_corner_style);
            contentView.setGravity(Gravity.CENTER);
            int[] position = findPosition(i);
            int left = (singleWidth + gap) * position[1];
            int top = (singleHeight + gap) * position[0];
            int right = left + singleWidth;
            int bottom = top + singleHeight;
            contentView.layout(left, top, right, bottom);
        }
    }

    private int[] findPosition(int childNum) {
        int[] position = new int[2];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if ((i * columns + j) == childNum) {
                    position[0] = i;
                    position[1] = j;
                    break;
                }
            }
        }
        return position;
    }

    private TextView CreateTextView() {
        final TextView view = new TextView(getContext());
        return view;
    }
}


