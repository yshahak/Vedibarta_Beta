package org.vedibarta.app.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import org.vedibarta.app.MyApplication;
import org.vedibarta.app.OnTrackChange;
import org.vedibarta.app.PlayService;
import org.vedibarta.app.R;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

import static org.vedibarta.app.MyApplication.getPlayerManager;

public class PlayerActivity extends AppCompatActivity implements OnTrackChange {

    public static final String EXTRA_PARASHA = "EXTRA_PARASHA";
    @BindView(R.id.simple_exo_player)
    SimpleExoPlayerView simpleExoPlayerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    private Disposable titleSubscription;
    private Disposable loadingSubscription;
    private MediaBrowserCompat mediaBrowser;

    private int[] intArtsArr = {R.drawable.comm1, R.drawable.comm2, R.drawable.comm3};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SimpleExoPlayer player = getPlayerManager().getPlayer();
        simpleExoPlayerView.setPlayer(player);
//        Par parasha = getIntent().getParcelableExtra(EXTRA_PARASHA);
        Observable<String> titleObservable = MyApplication.getPlayerManager().getTitleObservable();
        titleSubscription = titleObservable.subscribe(this::onTrackChanged);
//        getSupportActionBar().setTitle(parasha.getParTitle());
        loadingSubscription = MyApplication.getPlayerManager().getLoadingObservable()
                .subscribe(loading -> progressBar.setVisibility(loading ? View.VISIBLE : View.GONE));
        simpleExoPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources()
                , intArtsArr[new Random().nextInt(intArtsArr.length)]));
        setMediaBrowser();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    private void setMediaBrowser() {
        mediaBrowser = new MediaBrowserCompat(this,
                new ComponentName(this, PlayService.class),
                mConnectionCallbacks,
                null); // optional Bundle
    }


    private void setController() {
        MediaControllerCompat mediaController = MediaControllerCompat.getMediaController(this);
        // Display the initial state
        MediaMetadataCompat metadata = mediaController.getMetadata();
        PlaybackStateCompat pbState = mediaController.getPlaybackState();
        // Register a Callback to stay in sync
        mediaController.registerCallback(controllerCallback);
    }

    private void onPauseClicked(){
        int pbState = MediaControllerCompat.getMediaController(this).getPlaybackState().getState();
        if (pbState == PlaybackStateCompat.STATE_PLAYING) {
            MediaControllerCompat.getMediaController(this).getTransportControls().pause();
        } else {
            MediaControllerCompat.getMediaController(this).getTransportControls().play();
        }
    }

    MediaControllerCompat.Callback controllerCallback =
            new MediaControllerCompat.Callback() {
                @Override
                public void onMetadataChanged(MediaMetadataCompat metadata) {
                }

                @Override
                public void onPlaybackStateChanged(PlaybackStateCompat state) {
                }
            };

    MediaBrowserCompat.ConnectionCallback mConnectionCallbacks =
            new MediaBrowserCompat.ConnectionCallback() {
                @Override
                public void onConnected() {
                    // Get the token for the MediaSession
                    MediaSessionCompat.Token token = mediaBrowser.getSessionToken();
                    // Create a MediaControllerCompat
                    try {
                        MediaControllerCompat mediaController =
                                new MediaControllerCompat(PlayerActivity.this, token);
                        MediaControllerCompat.setMediaController(PlayerActivity.this, mediaController);
                        setController();
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(EXTRA_PARASHA, getIntent().getParcelableExtra(EXTRA_PARASHA));

                        MediaControllerCompat.getMediaController(PlayerActivity.this)
                                .getTransportControls().playFromMediaId("parashs", bundle);

                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

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


    @Override
    protected void onStart() {
        super.onStart();
        mediaBrowser.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // (see "stay in sync with the MediaSession")
        if (MediaControllerCompat.getMediaController(this) != null) {
            MediaControllerCompat.getMediaController(this).unregisterCallback(controllerCallback);
        }
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
        getSupportActionBar().setTitle(title);
        simpleExoPlayerView.showController();
    }
}
