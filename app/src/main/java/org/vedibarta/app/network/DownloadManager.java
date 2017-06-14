package org.vedibarta.app.network;

import android.content.Context;

import net.alexandroid.shpref.MyLog;

import org.vedibarta.app.ParashotHelper;
import org.vedibarta.app.model.Par;
import org.vedibarta.app.model.Track;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import static org.vedibarta.app.ParashotHelper.BASE_URL_MP3;
import static org.vedibarta.app.network.RetrofitHelper.BASE_URL_VEDIBARTA;

/**
 * Created by e560 on 07/06/17.
 */

public class DownloadManager {

    public static void download(Context context, Par par) {
        if (Utils.isConnected(context)) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_MP3 + par.getParashUrl())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            RetrofitHelper.ApiService service = retrofit.create(RetrofitHelper.ApiService.class);
            for (Track track : par.getTrackList()) {
                if (ParashotHelper.checkIfTrackDownloaded(context, par.getParTitle(), track.getUrl())) {
                    MyLog.d(String.format("The file %s is already exists", track.getUrl()));
                    continue;
                }
                service.download(track.getUrl())
                        .flatMap(resp -> RetrofitHelper.saveFile(context, resp, par.getParTitle(), track.getUrl()))
                        .subscribeOn(Schedulers.io())
                        .subscribe(file -> MyLog.d(file.getAbsolutePath()), Throwable::printStackTrace);
            }
        }
        ParashotHelper.deleteOldParashot(context, par.getParTitle());
    }

    public static Observable<ResponseBody> sendFeedback(String name, String mail, String text) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_VEDIBARTA)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        RetrofitHelper.SendFeedbackService service = retrofit.create(RetrofitHelper.SendFeedbackService.class);
        return service.sendFeedback(name, mail, text);

    }
}
