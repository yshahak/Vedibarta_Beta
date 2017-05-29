package org.vedibarta.app.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import org.vedibarta.app.MyApplication;
import org.vedibarta.app.OnTrackChange;
import org.vedibarta.app.R;
import org.vedibarta.app.RetrofitHelper;
import org.vedibarta.app.model.Par;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import static org.vedibarta.app.ParashotHelper.BASE_URL_ZIP;

public class MainActivity extends AppCompatActivity implements OnTrackChange {

    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.exo_player)
    SimpleExoPlayerView simpleExoPlayerView;
    private Disposable titleSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mViewPager.setAdapter(new CustomPagerAdapter(getSupportFragmentManager()));
        simpleExoPlayerView.setPlayer(MyApplication.getPlayerManager().getPlayer());
        Observable<String> titleObservable = MyApplication.getPlayerManager().getTitleObservable();
        titleSubscription = titleObservable.subscribe(this::onTrackChanged);
//        Par current = ParashotHelper.parList.get(CalendarHelper.getWeekParashaIndex());
//        download(current.getZipFiles()[1], current)
//                .subscribeOn(Schedulers.io())
//                .subscribe(file -> RetrofitHelper.unzipIcon(this, file, current.getParTitle()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MyApplication.getPlayerManager().getPlayer().getPlaybackState() == ExoPlayer.STATE_READY) {
            simpleExoPlayerView.setVisibility(View.VISIBLE);
        } else {
            simpleExoPlayerView.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        titleSubscription.dispose();
    }

    @Override
    public void onTrackChanged(String title) {
        getSupportActionBar().setTitle(title);
        simpleExoPlayerView.showController();
    }

    public Observable<File> download(String path, Par par) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_ZIP)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        RetrofitHelper.ApiService service = retrofit.create(RetrofitHelper.ApiService.class);
        return service.download(path)
                .flatMap(resp -> RetrofitHelper.saveFile(resp, par));
    }
}
