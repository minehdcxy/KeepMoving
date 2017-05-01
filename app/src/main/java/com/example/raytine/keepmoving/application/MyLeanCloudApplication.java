package com.example.raytine.keepmoving.application;

import android.app.Application;

import com.avos.avoscloud.AVCloud;
import com.avos.avoscloud.AVOSCloud;
import com.example.raytine.keepmoving.user.model.User;

/**
 * Created by raytine on 2017/4/12.
 */

public class MyLeanCloudApplication extends Application {

    public static boolean isDebug = true;

    private User user;

    private static MyLeanCloudApplication ins;

    public static MyLeanCloudApplication getIns() {
        return ins;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ins = this;
        AVOSCloud.initialize(this, "PQum8XdDaQ7iPrqbvrGASvIN-gzGzoHsz", "syxBPIEAY0t5H1y6RqS4DgaE");
        AVOSCloud.useAVCloudCN();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
