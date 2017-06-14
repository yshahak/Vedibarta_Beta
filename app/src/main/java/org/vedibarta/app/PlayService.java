package org.vedibarta.app;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.NotificationCompat;

import org.vedibarta.app.model.Par;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

import static org.vedibarta.app.ui.PlayableActivity.EXTRA_PARASHA;


public class PlayService extends MediaBrowserServiceCompat implements AudioManager.OnAudioFocusChangeListener {

    private static final java.lang.String LOG_TAG = PlayService.class.getSimpleName();
    private static final String MY_MEDIA_ROOT_ID = "VEDIBARTA_ROOT";
    public static final String MEDIA_ID_PARASHA = "parasha";
    public static final String MEDIA_ID_LAST_SESSION = "last_session";
    private static final int NOTIFICATION_ID = 10;
    private final long TIME_OUT_SESSION = TimeUnit.MINUTES.toMillis(1);

    private MediaSessionCompat mediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;
    private Disposable titleSubscription, pauseSubscription;
    private Handler handler;
    private boolean focusPause;

    @Override
    public void onCreate() {
        super.onCreate();
        initMediaSession();
        Observable<String> trackTitleObservable = MyApplication.getPlayerManager().getTitleObservable();
        Observable<String> pauseObservable = MyApplication.getPlayerManager().getPauseObservable();
        titleSubscription = trackTitleObservable.subscribe(this::showPlayNotification);
        pauseSubscription = pauseObservable.subscribe(s -> {
            showPausedNotification(s);
            setMediaPlaybackState(PlaybackStateCompat.STATE_PAUSED);
            handler.postDelayed(killRunnable, TIME_OUT_SESSION);
        });
        handler = new Handler();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MediaButtonReceiver.handleIntent(mediaSession, intent);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        titleSubscription.dispose();
        pauseSubscription.dispose();
    }

    @Nullable
    @Override
    public BrowserRoot onGetRoot(@NonNull String clientPackageName, int clientUid, @Nullable Bundle rootHints) {
        return new BrowserRoot(MY_MEDIA_ROOT_ID, null);
    }

    @Override
    public void onLoadChildren(@NonNull String parentId, @NonNull Result<List<MediaBrowserCompat.MediaItem>> result) {
        result.sendResult(null);
    }

    private boolean successfullyRetrievedAudioFocus() {
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int result = audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        return result == AudioManager.AUDIOFOCUS_GAIN;
    }

    private void showPlayNotification(String title) {
        NotificationCompat.Builder builder = MediaStyleHelper.from(this, mediaSession, MyApplication.getPlayerManager().getPar().getDescription()
                , android.R.drawable.ic_media_pause, getString(R.string.pause), title);
        if( builder == null ) {
            return;
        }
        startForeground(NOTIFICATION_ID, builder.build());
    }

    private void showPausedNotification(String title) {
        NotificationCompat.Builder builder = MediaStyleHelper.from(this, mediaSession, MyApplication.getPlayerManager().getPar().getDescription()
                , android.R.drawable.ic_media_play, getString(R.string.play), title);
        if( builder == null ) {
            return;
        }
        stopForeground(false);
        NotificationManagerCompat.from(this).notify(NOTIFICATION_ID, builder.build());
    }

    private void initMediaSession() {
        mediaSession = new MediaSessionCompat(getApplicationContext(), LOG_TAG);
        mediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(PlaybackStateCompat.ACTION_PLAY
                        | PlaybackStateCompat.ACTION_PLAY_PAUSE
                        | PlaybackStateCompat.ACTION_SKIP_TO_NEXT
                        | PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
                        | PlaybackStateCompat.ACTION_FAST_FORWARD
                        | PlaybackStateCompat.ACTION_REWIND);
        mediaSession.setPlaybackState(mStateBuilder.build());
        mediaSession.setCallback(mMediaSessionCallback);
        Intent mediaButtonIntent = new Intent(Intent.ACTION_MEDIA_BUTTON);
        mediaButtonIntent.setClass(this, MediaButtonReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, mediaButtonIntent, 0);
        mediaSession.setMediaButtonReceiver(pendingIntent);
        // Set the session's token so that client activities can communicate with it.
        setSessionToken(mediaSession.getSessionToken());

    }

    private void setMediaPlaybackState(int state) {
        mStateBuilder.setState(state, PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN, 0);
        mediaSession.setPlaybackState(mStateBuilder.build());
    }

    private MediaSessionCompat.Callback mMediaSessionCallback = new MediaSessionCompat.Callback() {

        @Override
        public void onPlayFromMediaId(String mediaId, Bundle extras) {
            super.onPlayFromMediaId(mediaId, extras);
            switch (mediaId){
                case MEDIA_ID_LAST_SESSION:
                    MyApplication.getPlayerManager().restoreLastSession(getApplicationContext());
                    break;
                case MEDIA_ID_PARASHA:
                    extras.setClassLoader(Par.class.getClassLoader());
                    Par par = extras.getParcelable(EXTRA_PARASHA);
                    if (par == null || par.equals(MyApplication.getPlayerManager().getPar())) {
                        return;
                    }
                    MyApplication.getPlayerManager().preparePlayer(getApplicationContext(), par);
                    break;
            }
            mediaSession.setActive(true);
            startService(new Intent(PlayService.this, PlayService.class));
            if( !successfullyRetrievedAudioFocus() ) {
                return;
            }
            onPlay();
        }

        @Override
        public void onPlay() {
            super.onPlay();
            setMediaPlaybackState(PlaybackStateCompat.STATE_PLAYING);
            MyApplication.getPlayerManager().play();
            handler.removeCallbacks(killRunnable);
            focusPause = false;
        }

        @Override
        public void onPause() {
            super.onPause();
            MyApplication.getPlayerManager().pause();
            setMediaPlaybackState(PlaybackStateCompat.STATE_PAUSED);
            handler.postDelayed(killRunnable, TIME_OUT_SESSION);
        }

        @Override
        public void onStop() {
            super.onStop();
            setMediaPlaybackState(PlaybackStateCompat.STATE_STOPPED);
            MyApplication.getPlayerManager().stop();
            AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            audioManager.abandonAudioFocus(PlayService.this);
            handler.postDelayed(killRunnable, TIME_OUT_SESSION);
        }

        @Override
        public void onSkipToNext() {
            super.onSkipToNext();
            MyApplication.getPlayerManager().next();
        }

        @Override
        public void onSkipToPrevious() {
            super.onSkipToPrevious();
            MyApplication.getPlayerManager().previous();
        }

        @Override
        public void onFastForward() {
            super.onFastForward();
            MyApplication.getPlayerManager().fastForward();
        }

        @Override
        public void onRewind() {
            super.onRewind();
            MyApplication.getPlayerManager().rewind();
        }
    };


    @Override
    public void onAudioFocusChange(int focusChange) {
        int state = mediaSession.getController().getPlaybackState().getState();
        switch( focusChange ) {
            case AudioManager.AUDIOFOCUS_LOSS: {
                if (state == PlaybackStateCompat.STATE_PLAYING) {
                    focusPause = true;
                    mMediaSessionCallback.onStop();
                }
                break;
            }
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT: {
                if (state == PlaybackStateCompat.STATE_PLAYING) {
                    focusPause = true;
                    mMediaSessionCallback.onPause();
                }
                break;
            }
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK: {
                if (state == PlaybackStateCompat.STATE_PLAYING) {
                    MyApplication.getPlayerManager().getPlayer().setVolume(0.5f);
                }
                break;
            }
            case AudioManager.AUDIOFOCUS_GAIN: {
                if (focusPause && state == PlaybackStateCompat.STATE_PAUSED) {
                    mMediaSessionCallback.onPlay();
                } else if (state == PlaybackStateCompat.STATE_PLAYING){
                    MyApplication.getPlayerManager().getPlayer().setVolume(1);
                }
                break;
            }
        }
    }

    private Runnable killRunnable = () -> {
//        mMediaSessionCallback.onStop();
        stopSelf();
    };

}
class MediaStyleHelper {
    /**
     * Build a notification using the information from the given media session. Makes heavy use
     * of {@link MediaMetadataCompat#getDescription()} to extract the appropriate information.
     * @param context Context used to construct the notification.
     * @return A pre-built notification with information from the given media session.
     */
    public static NotificationCompat.Builder from(Context context, MediaSessionCompat mediaSession, MediaDescriptionCompat description
            , int iconId, String action, String title) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder
                .setContentTitle(title)
                .setLargeIcon(MyApplication.getBitmapForNotification())
                .setSmallIcon(android.R.drawable.ic_media_play)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setContentIntent(mediaSession.getController().getSessionActivity())
                .setDeleteIntent(MediaButtonReceiver.buildMediaButtonPendingIntent(context, PlaybackStateCompat.ACTION_STOP))
                .addAction(new NotificationCompat.Action(
                        android.R.drawable.ic_media_previous, context.getString(R.string.previous),
                        MediaButtonReceiver.buildMediaButtonPendingIntent(context,
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS)))
                .addAction(new NotificationCompat.Action(
                        android.R.drawable.ic_media_rew, context.getString(R.string.rewind),
                        MediaButtonReceiver.buildMediaButtonPendingIntent(context,
                                PlaybackStateCompat.ACTION_REWIND)))
                .addAction(new NotificationCompat.Action(iconId, action,
                        MediaButtonReceiver.buildMediaButtonPendingIntent(context,
                                PlaybackStateCompat.ACTION_PLAY_PAUSE)))
                .addAction(new NotificationCompat.Action(
                        android.R.drawable.ic_media_ff, context.getString(R.string.fast_forward),
                        MediaButtonReceiver.buildMediaButtonPendingIntent(context,
                                PlaybackStateCompat.ACTION_FAST_FORWARD)))
                .addAction(new NotificationCompat.Action(
                        android.R.drawable.ic_media_next, context.getString(R.string.next),
                        MediaButtonReceiver.buildMediaButtonPendingIntent(context,
                                PlaybackStateCompat.ACTION_SKIP_TO_NEXT)))
                // Take advantage of MediaStyle features
                .setStyle(new NotificationCompat.MediaStyle()
                        .setMediaSession(mediaSession.getSessionToken()) //support for wear
                        .setShowActionsInCompactView(builder.mActions.size() -1)
                        .setShowCancelButton(true)
                        .setCancelButtonIntent(MediaButtonReceiver.buildMediaButtonPendingIntent(context, PlaybackStateCompat.ACTION_STOP)))
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        return builder;
    }
}