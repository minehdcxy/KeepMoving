package com.example.raytine.keepmoving.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.raytine.keepmoving.R;
import com.example.raytine.keepmoving.application.MyLeanCloudApplication;
import com.example.raytine.keepmoving.login.LoginActivity;
import com.example.raytine.keepmoving.user.model.User;

public class UserFragment extends Fragment implements View.OnClickListener {

    private final String TAG = UserFragment.class.getSimpleName();

    private User user;

    private LinearLayout userInfoLl;
    private LinearLayout userManageLl;
    private LinearLayout userWalletLl;
    private LinearLayout userCardLl;
    private LinearLayout userAdviseLl;
    private TextView exit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        user = MyLeanCloudApplication.getIns().getUser();
        initViews(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void initViews(View view) {
        userInfoLl = (LinearLayout) view.findViewById(R.id.user_info_ll);
        userManageLl = (LinearLayout) view.findViewById(R.id.user_manage_ll);
        userWalletLl = (LinearLayout) view.findViewById(R.id.user_wallet_ll);
        userCardLl = (LinearLayout) view.findViewById(R.id.user_card_ll);
        userAdviseLl = (LinearLayout) view.findViewById(R.id.user_advise_ll);
        exit = (TextView) view.findViewById(R.id.user_exit_tv);

        switch (user.getType()) {
            case 0:
                userManageLl.setVisibility(View.GONE);
                userWalletLl.setVisibility(View.VISIBLE);
                userCardLl.setVisibility(View.VISIBLE);
                userAdviseLl.setVisibility(View.VISIBLE);

                userWalletLl.setOnClickListener(this);
                userCardLl.setOnClickListener(this);
                userAdviseLl.setOnClickListener(this);
                break;
            case 1:
                userManageLl.setVisibility(View.VISIBLE);
                userWalletLl.setVisibility(View.GONE);
                userCardLl.setVisibility(View.GONE);
                userAdviseLl.setVisibility(View.GONE);

                userManageLl.setOnClickListener(this);
                break;
        }

        userInfoLl.setOnClickListener(this);
        exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_info_ll:
                Intent infoIntent = new Intent(getActivity(), ActivityUserInfo.class);
                startActivity(infoIntent);
                break;
            case R.id.user_manage_ll:
//                Intent manageIntent = new Intent(getActivity(), )
                break;
            case R.id.user_wallet_ll:
                Intent walletIntent = new Intent(getActivity(), ActivityUserWallet.class);
                startActivity(walletIntent);
                break;
            case R.id.user_card_ll:
                Intent cardIntent = new Intent(getActivity(), ActivityUserCard.class);
                startActivity(cardIntent);
                break;
            case R.id.user_advise_ll:
                Intent adviseIntent = new Intent(getActivity(), ActivityUserAdvise.class);
                startActivity(adviseIntent);
                break;
            case R.id.user_exit_tv:
                Intent exitIntent = new Intent(getActivity(), LoginActivity.class);
                startActivity(exitIntent);
                break;
        }
    }

}
