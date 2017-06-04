package org.vedibarta.app.ui;

import android.content.ComponentName;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.widget.Toolbar;

import org.vedibarta.app.OnTrackChange;
import org.vedibarta.app.PlayService;
import org.vedibarta.app.R;
import org.vedibarta.app.model.Par;

import java.util.Random;

public class PlayerActivity extends PlayableActivity implements OnTrackChange {

    public static final String EXTRA_PARASHA = "EXTRA_PARASHA";

    private int[] intArtsArr = {R.drawable.comm1, R.drawable.comm2, R.drawable.comm3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        init();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Par parasha = getIntent().getParcelableExtra(EXTRA_PARASHA);
        if (parasha != null) {
            getSupportActionBar().setTitle(String.format("פרשת '%s'", parasha.getParTitle()));
        } else {
            simpleExoPlayerView.showController();
        }
        simpleExoPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources()
                , intArtsArr[new Random().nextInt(intArtsArr.length)]));
    }

    void setMediaBrowser() {
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
                    MediaSessionCompat.Token token = PlayerActivity.this.mediaBrowser.getSessionToken();
                    // Create a MediaControllerCompat
                    try {
                        MediaControllerCompat mediaController =
                                new MediaControllerCompat(PlayerActivity.this, token);
                        MediaControllerCompat.setMediaController(PlayerActivity.this, mediaController);
                        setController();
                        Par par = getIntent().getParcelableExtra(EXTRA_PARASHA);
                        if (par != null) {
                            Bundle bundle = new Bundle();
                            bundle.putParcelable(EXTRA_PARASHA, par);
                            MediaControllerCompat.getMediaController(PlayerActivity.this)
                                    .getTransportControls().playFromMediaId("parasha", bundle);
                        }

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
    public void onTrackChanged(String title) {
        simpleExoPlayerView.showController();
        parTitle.setText(title);
    }
}
