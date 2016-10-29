package com.example.sherman.sbook.config;

import android.app.Application;
import android.os.SystemClock;

import java.util.concurrent.TimeUnit;

/**
 * Created by VõVân on 10/29/2016.
 */
public class App extends Application{
        @Override
        public void onCreate() {
            super.onCreate();
            // Don't do this! This is just so cold launches take some time
            SystemClock.sleep(TimeUnit.SECONDS.toMillis(3));
        }
}
