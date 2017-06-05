package org.vedibarta.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.support.v4.media.MediaBrowserCompat;
import android.util.Log;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import net.alexandroid.shpref.ShPref;

import org.vedibarta.app.model.Par;
import org.vedibarta.app.model.Track;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by e560 on 23/05/17.
 */

@SuppressWarnings("WeakerAccess")
@SuppressLint("DefaultLocale")
public class PlayerManager implements ExoPlayer.EventListener {

    private static final int FAST_FORWRD = 10;
    private static final int REWIND = 10;
    private static final String KEY_LAST_PAR_INDEX = "keyLastParIndex";
    private static final String KEY_LAST_PAR_TRACK = "keyLAstParTrack";
    private static final String KEY_LAST_PAR_POSITION = "keyLastParPosition";

    private MediaBrowserCompat mediaBrowser;

    private final SimpleExoPlayer player;
    DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter();
    // Produces DataSource instances through which media data is loaded.
    DataSource.Factory dataSourceFactory;
    // Produces Extractor instances for parsing the media data.
    ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
    // This is the MediaSource representing the media to be played.

    private PublishSubject<String> titleObservable = PublishSubject.create();
    private PublishSubject<Boolean> loadingObservable = PublishSubject.create();
    private PublishSubject<String> pauseObservable = PublishSubject.create();

    private Par par;

    public PlayerManager(final Context context) {
        // 1. Create a default TrackSelector
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory trackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(trackSelectionFactory);
        player = ExoPlayerFactory.newSimpleInstance(context, trackSelector);
        dataSourceFactory = new DefaultDataSourceFactory(context,
                Util.getUserAgent(context, "vedibarta"), defaultBandwidthMeter);
        player.addListener(this);
    }

    public void restoreLastSession(Context context) {
        Par par = ParashotHelper.parList.get(ShPref.getInt(KEY_LAST_PAR_INDEX));
        int lastTrack =  ShPref.getInt(KEY_LAST_PAR_TRACK);
        long lastPosition = ShPref.getLong(KEY_LAST_PAR_POSITION);
        initSession(context, par);
        player.seekTo(lastTrack, lastPosition);
        play();
    }

    public Observable<String> preparePlayer(Context context, Par par) {
        initSession(context, par);
//        play();
        return titleObservable;
    }

    private void initSession(Context context, Par par) {
        this.par = par;
        // Measures bandwidth during playback. Can be null if not required.
        List<MediaSource> sources = new ArrayList<>();
        for (Track track : par.getTrackList()) {
            String source;
            if (ParashotHelper.checkIfTrackDownloaded(context, par.getParTitle(), track.getUrl())) {
                source = ParashotHelper.getTrackSourceFromDevice(context, par.getParTitle(), track.getUrl());
            } else {
                source = ParashotHelper.BASE_URL_MP3
                        + par.getParashUrl()
                        + track.getUrl();
            }
            MediaSource audioSource = new ExtractorMediaSource(Uri.parse(source),
                    dataSourceFactory, extractorsFactory, null, null);
            sources.add(audioSource);
        }
        // Prepare the player with the source.
        ConcatenatingMediaSource concatenatedSource =
                new ConcatenatingMediaSource(sources.toArray(new MediaSource[sources.size()]));
        player.prepare(concatenatedSource);
    }

    public void play() {
        player.setPlayWhenReady(true);
    }

    public void pause() {
        player.setPlayWhenReady(false);
//        pauseObservable.onNext(getTrackTitle());
    }

    public void stop() {
        player.stop();
    }

    public void fastForward() {
        seekTo(player.getCurrentPosition() + 1000 * FAST_FORWRD);
    }

    public void rewind() {
        seekTo(player.getCurrentPosition() - 1000 * FAST_FORWRD);
    }

    public void next() {
        Timeline timeline = player.getCurrentTimeline();
        if (timeline.isEmpty()) {
            return;
        }
        int windowIndex = player.getCurrentWindowIndex();
        if (windowIndex < timeline.getWindowCount() - 1) {
            seekTo(windowIndex + 1, C.TIME_UNSET);
        } else {
            ShPref.put(KEY_LAST_PAR_INDEX, -1);
        }
    }

    public void previous() {
        Timeline timeline = player.getCurrentTimeline();
        if (timeline.isEmpty()) {
            return;
        }
        int windowIndex = player.getCurrentWindowIndex();
//        timeline.getWindow(windowIndex, window);
        if (windowIndex > 0)//&& (player.getCurrentPosition() <= MAX_POSITION_FOR_SEEK_TO_PREVIOUS || (window.isDynamic && !window.isSeekable))) {
            seekTo(windowIndex - 1, C.TIME_UNSET);
    }

    private void seekTo(long positionMs) {
        if (positionMs < 0) {
            seekTo(player.getCurrentWindowIndex(), 0);
        } else if (positionMs > player.getDuration()) {
            next();
        } else {
            seekTo(player.getCurrentWindowIndex(), positionMs);
        }
    }

    private void seekTo(int windowIndex, long positionMs) {
        player.seekTo(windowIndex, positionMs);
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {
    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
        titleObservable.onNext(getTrackTitle());
    }

    @Override
    public void onLoadingChanged(boolean isLoading) {
        Log.d("TAG", "isLoading=" + isLoading);
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        switch (playbackState) {
            case ExoPlayer.STATE_BUFFERING:
                titleObservable.onNext(getTrackTitle());
                loadingObservable.onNext(true);
                break;
            case ExoPlayer.STATE_READY:
                if (playWhenReady) {
                    titleObservable.onNext(getTrackTitle());
                } else {
                    saveLastState();
                    pauseObservable.onNext(getTrackTitle());
                }
                loadingObservable.onNext(false);
                break;
            case ExoPlayer.STATE_ENDED:
                ShPref.put(KEY_LAST_PAR_INDEX, -1);
                break;
            default:
                loadingObservable.onNext(false);
        }
        Log.d("TAG", "onPlayerStateChanged=" + playbackState);
    }

    private void saveLastState() {
        ShPref.put(KEY_LAST_PAR_INDEX, ParashotHelper.parList.indexOf(par));
        ShPref.put(KEY_LAST_PAR_TRACK, player.getCurrentWindowIndex());
        ShPref.put(KEY_LAST_PAR_POSITION, player.getCurrentPosition());
    }

    public int getLastSessionIndex(){
        return ShPref.getInt(KEY_LAST_PAR_INDEX, -1);
    }

    public String getLastSessionTitle(){
        Par par = ParashotHelper.parList.get(ShPref.getInt(KEY_LAST_PAR_INDEX));
        int lastTrack =  ShPref.getInt(KEY_LAST_PAR_TRACK);
        return String.format("?לנגן פרשת %s %d/%d", par.getParTitle(), lastTrack + 1, par.getTrackList().length);
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    private String getTrackTitle() {
        return String.format("%s %d/%d", par.getParTitle(), player.getCurrentWindowIndex() + 1, par.getTrackList().length);
    }

    public Observable<String> getTitleObservable() {
        return titleObservable;
    }

    public PublishSubject<Boolean> getLoadingObservable() {
        return loadingObservable;
    }

    public Par getPar() {
        return par;
    }

    public SimpleExoPlayer getPlayer() {
        return player;
    }

    public Observable<String> getPauseObservable() {
        return pauseObservable;
    }

    public void omitTitle() {
        titleObservable.onNext(getTrackTitle());
    }


}
