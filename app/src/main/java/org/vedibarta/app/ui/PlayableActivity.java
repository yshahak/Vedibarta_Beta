package org.vedibarta.app.ui;

import android.content.Intent;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import org.vedibarta.app.MyApplication;
import org.vedibarta.app.OnTrackChange;
import org.vedibarta.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

import static org.vedibarta.app.MyApplication.getPlayerManager;

/**
 * Created by e560 on 04/06/17.
 */

public abstract class PlayableActivity extends AppCompatActivity implements OnTrackChange {

    public static final String EXTRA_PARASHA = "EXTRA_PARASHA";
    @BindView(R.id.simple_exo_player)
    SimpleExoPlayerView simpleExoPlayerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.par_title)
    TextView parTitle;
    private Disposable titleSubscription;
    private Disposable loadingSubscription;
    MediaBrowserCompat mediaBrowser;


    void init(){
        ButterKnife.bind(this);
        SimpleExoPlayer player = getPlayerManager().getPlayer();
        simpleExoPlayerView.setPlayer(player);
        Observable<String> titleObservable = MyApplication.getPlayerManager().getTitleObservable();
        titleSubscription = titleObservable.subscribe(this::onTrackChanged);
        loadingSubscription = MyApplication.getPlayerManager().getLoadingObservable()
                .subscribe(loading -> progressBar.setVisibility(loading ? View.VISIBLE : View.GONE));
        setMediaBrowser();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    abstract void setMediaBrowser();

    @Override
    protected void onStart() {
        super.onStart();
        mediaBrowser.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mediaBrowser.disconnect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        titleSubscription.dispose();
        loadingSubscription.dispose();
    }

    @Override
    public void onTrackChanged(String title) {
        simpleExoPlayerView.showController();
        parTitle.setText(title);
    }
}
