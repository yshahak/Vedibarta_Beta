package org.vedibarta.app.ui;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import org.vedibarta.app.MyApplication;
import org.vedibarta.app.OnTrackChange;
import org.vedibarta.app.PlayService;
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
    public static final String EXTRA_LAST_SESSION = "EXTRA_LAST_SESSION";

    @BindView(R.id.simple_exo_player)
    SimpleExoPlayerView simpleExoPlayerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.par_title)
    TextView parTitle;
    @BindView(R.id.btn_full_screen)
    ImageView btnFullScreen;
    private Disposable titleSubscription;
    private Disposable loadingSubscription;
    MediaBrowserCompat mediaBrowser;


    void init() {
        ButterKnife.bind(this);
        SimpleExoPlayer player = getPlayerManager().getPlayer();
        simpleExoPlayerView.setPlayer(player);
        Observable<String> titleObservable = MyApplication.getPlayerManager().getTitleObservable();
        titleSubscription = titleObservable.subscribe(this::onTrackChanged);
        loadingSubscription = MyApplication.getPlayerManager().getLoadingObservable()
                .subscribe(loading -> progressBar.setVisibility(loading ? View.VISIBLE : View.GONE));
        setMediaBrowser();
        forceRTLIfSupported();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void forceRTLIfSupported() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    void setMediaBrowser() {

        MediaBrowserCompat.ConnectionCallback mConnectionCallbacks =
                new MediaBrowserCompat.ConnectionCallback() {
                    @Override
                    public void onConnected() {
                        // Get the token for the MediaSession
                        MediaSessionCompat.Token token = PlayableActivity.this.mediaBrowser.getSessionToken();
                        // Create a MediaControllerCompat
                        try {
                            MediaControllerCompat mediaController =
                                    new MediaControllerCompat(PlayableActivity.this, token);
                            MediaControllerCompat.setMediaController(PlayableActivity.this, mediaController);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        onConnectToPlayService();
                    }

                    @Override
                    public void onConnectionSuspended() {
                        // The Service has crashed. Disable transport controls until it automatically reconnects
                    }

                    @Override
                    public void onConnectionFailed() {
                        // The Service has refused our connection
                    }
                };
        mediaBrowser = new MediaBrowserCompat(this,
                new ComponentName(this, PlayService.class),
                mConnectionCallbacks,
                null); // optional Bundle
    }

    abstract void onConnectToPlayService();

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
