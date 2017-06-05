package org.vedibarta.app;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import net.alexandroid.shpref.MyLog;
import net.alexandroid.shpref.ShPref;

import org.vedibarta.app.calendar.CalendarHelper;
import org.vedibarta.app.model.Par;
import org.vedibarta.app.model.Track;

import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import static org.vedibarta.app.ParashotHelper.BASE_URL_MP3;

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
        ShPref.init(this, ShPref.APPLY);
        MyLog.setTag("ZAQ");
        myApplication = this;
        playerManager = new PlayerManager(this);
        Par current = ParashotHelper.parList.get(CalendarHelper.getWeekParashaIndex());
        download(current);
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

    public void download(Par par) {
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
//        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_MP3 + par.getParashUrl())
//                .client(new OkHttpClient.Builder().build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        RetrofitHelper.ApiService service = retrofit.create(RetrofitHelper.ApiService.class);
        for (Track track : par.getTrackList()) {
            if (ParashotHelper.checkIfTrackDownloaded(getApplicationContext(), par.getParTitle(), track.getUrl())) {
                Log.d(TAG, String.format("The file %s is already exists", track.getUrl()));
                continue;
            }
            service.download(track.getUrl())
                    .flatMap(resp -> RetrofitHelper.saveFile(getApplicationContext(), resp, par.getParTitle(), track.getUrl()))
                    .subscribeOn(Schedulers.io())
                    .subscribe(file -> Log.d(TAG, file.getAbsolutePath()));
        }
        ParashotHelper.deleteOldParashot(this, par.getParTitle());
    }
}
