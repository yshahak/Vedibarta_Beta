package org.vedibarta.app;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import net.alexandroid.shpref.MyLog;
import net.alexandroid.shpref.ShPref;

import org.vedibarta.app.calendar.CalendarHelper;
import org.vedibarta.app.model.Par;
import org.vedibarta.app.network.DownloadManager;

/**
 * Created by e560 on 23/05/17.
 */

public class MyApplication extends Application {

    private static final String TAG = MyApplication.class.getSimpleName();
    private static MyApplication myApplication;
    private static Bitmap bitmapForNotification;
    private PlayerManager playerManager;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        ShPref.init(this, ShPref.APPLY);
        MyLog.setTag("ZAQ");
        myApplication = this;
        playerManager = new PlayerManager(this);
        Par current = ParashotHelper.parList.get(CalendarHelper.getWeekParashaIndex());
        DownloadManager.download(this, current);
    }

    public static PlayerManager getPlayerManager() {
        return myApplication.playerManager;
    }

    public static Bitmap getBitmapForNotification() {
        if (bitmapForNotification == null) {
            bitmapForNotification = BitmapFactory.decodeResource(myApplication.getResources(), R.drawable.comicks);
        }
        return bitmapForNotification;
    }



}
