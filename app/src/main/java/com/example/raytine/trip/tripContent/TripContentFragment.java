package com.example.raytine.trip.tripContent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.raytine.keepmoving.R;
import com.example.raytine.trip.application.MyLeanCloudApplication;
import com.example.raytine.trip.home.model.TripData;
import com.example.raytine.trip.user.model.User;
import com.squareup.picasso.Picasso;

/**
 * Created by raytine on 2017/5/17.
 */

public class TripContentFragment extends Fragment {
    private  TripData.DiaryTrip diaryTrip;
    private ImageView contentImage;
    private TextView contentDescribe;
    private String contentImageUrl;
    private String content;
    private String tripId;
    private ImageView comment;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_trip_content, container, false);
        initViews(view);
        initDatas();
        return view;
    }

    private void initViews(View view) {
        comment = (ImageView) view.findViewById(R.id.iv_trip_comment);
        contentImage = (ImageView) view.findViewById(R.id.iv_content_trip);
        contentDescribe = (TextView) view.findViewById(R.id.tv_trip_describe);
        final User user = MyLeanCloudApplication.getIns().getUser();
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CommentActivity.class);
                intent.putExtra("userId", user.getObjectId());
                intent.putExtra("tripId", tripId);
                startActivity(intent);
            }
        });
    }

    private void initDatas() {
        Bundle bundle = this.getArguments();
        contentImageUrl = bundle.getString("contentImageUrl");
        content = bundle.getString("contentDescribe");
        tripId = bundle.getString("tripId");
        contentDescribe.setText(content);
        Picasso.with(getActivity()).load(contentImageUrl).into(contentImage);

    }
}
