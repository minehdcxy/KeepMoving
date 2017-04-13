package com.example.raytine.keepmoving.application;

import android.app.Application;

import com.avos.avoscloud.AVCloud;
import com.avos.avoscloud.AVOSCloud;

/**
 * Created by raytine on 2017/4/12.
 */

public class MyLeanCloudApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AVOSCloud.initialize(this, "PQum8XdDaQ7iPrqbvrGASvIN-gzGzoHsz", "syxBPIEAY0t5H1y6RqS4DgaE");
        AVOSCloud.useAVCloudCN();
    }
}
