package org.vedibarta.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.support.v4.media.MediaBrowserCompat;
import android.util.Log;

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

import org.vedibarta.app.model.Par;
import org.vedibarta.app.model.Track;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by e560 on 23/05/17.
 */

public class PlayerManager implements ExoPlayer.EventListener {

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
        player.setPlayWhenReady(true);
        player.addListener(this);
    }

    public Observable<String> preparePlayer(Par par) {
        this.par = par;
        // Measures bandwidth during playback. Can be null if not required.
        List<MediaSource> sources = new ArrayList<>();
        for (Track track : par.getTrackList()) {
            MediaSource audioSource = new ExtractorMediaSource(Uri.parse(ParashotHelper.BASE_URL_MP3
                    + par.getParashUrl()
                    + track.getUrl()),
                    dataSourceFactory, extractorsFactory, null, null);
            sources.add(audioSource);
        }
        // Prepare the player with the source.
        ConcatenatingMediaSource concatenatedSource =
                new ConcatenatingMediaSource(sources.toArray(new MediaSource[sources.size()]));
        player.prepare(concatenatedSource);
        return titleObservable;
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {
    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
        for (int i = 0; i < trackSelections.length; i++) {
            Log.d("TAG", trackSelections.toString());
        }
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
            default:
                loadingObservable.onNext(false);
        }
        Log.d("TAG", "onPlayerStateChanged=" + playbackState);
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

    @SuppressLint("DefaultLocale")
    private String getTrackTitle() {
        return String.format("%s %d/%d",par.getParTitle(),  player.getCurrentWindowIndex() + 1, par.getTrackList().length);
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

}
