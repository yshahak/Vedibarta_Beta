package org.vedibarta.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import org.vedibarta.app.MyApplication;
import org.vedibarta.app.OnTrackChange;
import org.vedibarta.app.ParashotHelper;
import org.vedibarta.app.R;
import org.vedibarta.app.RetrofitHelper;
import org.vedibarta.app.calendar.CalendarHelper;
import org.vedibarta.app.model.Par;
import org.vedibarta.app.model.Track;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import static org.vedibarta.app.ParashotHelper.BASE_URL_MP3;
import static org.vedibarta.app.ui.PlayerActivity.EXTRA_PARASHA;

public class MainActivity extends AppCompatActivity implements OnTrackChange {

    private static final String TAG = MainActivity.class.getSimpleName();
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
        Par current = ParashotHelper.parList.get(CalendarHelper.getWeekParashaIndex());
        download(current);
        showPlayWeekParashaDialog(current);
    }

    private void showPlayWeekParashaDialog(Par current) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage(String.format("Start playing %s?", current.getParTitle()))
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    Intent intent = new Intent(MainActivity.this, PlayerActivity.class);
                    intent.putExtra(EXTRA_PARASHA, current);
                    startActivity(intent);
                })
                .setNegativeButton(android.R.string.cancel, null);
        builder.create().show();


    }

        @Override
        protected void onResume () {
            super.onResume();
            if (MyApplication.getPlayerManager().getPlayer().getPlaybackState() == ExoPlayer.STATE_READY) {
                simpleExoPlayerView.setVisibility(View.VISIBLE);
            } else {
                simpleExoPlayerView.setVisibility(View.GONE);
            }
        }

        @Override
        protected void onDestroy () {
            super.onDestroy();
            titleSubscription.dispose();
        }

        @Override
        public void onTrackChanged (String title){
            getSupportActionBar().setTitle(title);
            simpleExoPlayerView.showController();
        }

    public void download(Par par) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_MP3 + par.getParashUrl())
                .client(client)
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
    }
}
