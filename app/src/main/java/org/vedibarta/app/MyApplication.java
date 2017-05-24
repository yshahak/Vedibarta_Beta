package org.vedibarta.app;

import android.app.Application;

/**
 * Created by e560 on 23/05/17.
 */

public class MyApplication extends Application {

    private static MyApplication myApplication;
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
}
