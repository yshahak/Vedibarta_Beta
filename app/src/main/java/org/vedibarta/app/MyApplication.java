package org.vedibarta.app;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by e560 on 23/05/17.
 */

public class MyApplication extends Application {

    private static MyApplication myApplication;
    private static Bitmap bitmapForNotification;
    private PlayerManager playerManager;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        playerManager = new PlayerManager(this);
    }

    public static PlayerManager getPlayerManager(){
        return myApplication.playerManager;
    }

    public static Bitmap getBitmapForNotification() {
        if (bitmapForNotification == null) {
            bitmapForNotification = BitmapFactory.decodeResource(myApplication.getResources(), R.drawable.comicks);
        }
        return bitmapForNotification;
    }
}
