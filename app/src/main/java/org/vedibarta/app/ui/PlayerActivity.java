package org.vedibarta.app.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import org.vedibarta.app.MyApplication;
import org.vedibarta.app.OnTrackChange;
import org.vedibarta.app.ParashotHelper;
import org.vedibarta.app.R;
import org.vedibarta.app.model.Par;

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
    private Par par;
    private Disposable trackSubscription;
    private Disposable loadingSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SimpleExoPlayer player = getPlayerManager().getPlayer();
        simpleExoPlayerView.setPlayer(player);
        Par parasha = getIntent().getParcelableExtra(EXTRA_PARASHA);
        Observable<String> trackObservable = MyApplication.getPlayerManager().preparePlayer(parasha);
        trackSubscription = trackObservable.subscribe(this::onTrackChanged);
        this.par = (parasha != null) ? parasha : ParashotHelper.parList.get(0);
        getSupportActionBar().setTitle(par.getParTitle());
        loadingSubscription = MyApplication.getPlayerManager().getLoadingObservable()
                .subscribe(loading -> progressBar.setVisibility(loading ? View.VISIBLE: View.GONE));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        trackSubscription.dispose();
        loadingSubscription.dispose();
    }

    @Override
    public void onTrackChanged(String track) {
        getSupportActionBar().setTitle(par.getParTitle() + " " + track);
    }
}
