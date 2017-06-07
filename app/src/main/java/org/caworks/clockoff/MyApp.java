package org.caworks.clockoff;

import android.app.Application;

/**
 * Created by Gallon on 2017/5/31.
 */

public class MyApp extends Application {

    public static Application APP;

    @Override
    public void onCreate() {
        super.onCreate();
        APP = this;
    }
}
