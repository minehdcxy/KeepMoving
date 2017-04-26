package com.example.raytine.keepmoving.api;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;

import keepInterface.RequestResult;

/**0
 * Created by raytine on 2017/4/12.
 */

public class RegisterApi {
    private static String Tag = "RegisterApi";
    private static String MOBILE_PHONE_NUMBER = "mobilePhoneNumber";
    private  RequestResult result;




    public static void register(String phoneNumber, String password) {
        AVUser user = new AVUser();
        user.setUsername(phoneNumber);
        user.setPassword(password);
        user.put(MOBILE_PHONE_NUMBER, phoneNumber);
        user.signUpInBackground(new SignUpCallback() {
            public void done(AVException e) {
                if (e == null) {
                    Log.i(Tag, String.valueOf(e));
                } else {
                    Log.i(Tag, String.valueOf(e));
                }
            }
        });
    }

    public  void validate(String code, final RequestResult result){
        this.result = result;
        AVUser.verifyMobilePhoneInBackground(code, new AVMobilePhoneVerifyCallback() {
            @Override
            public void done(AVException e) {
                if(e == null){
                    result.successfully("验证成功");

                } else {
                    result.failed(String.valueOf(e));
                }
            }
        });
    }

}

