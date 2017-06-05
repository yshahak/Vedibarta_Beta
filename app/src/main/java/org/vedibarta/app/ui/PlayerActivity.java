package org.vedibarta.app.ui;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v7.widget.Toolbar;

import org.vedibarta.app.MyApplication;
import org.vedibarta.app.R;
import org.vedibarta.app.model.Par;

import java.util.Random;

import static org.vedibarta.app.PlayService.MEDIA_ID_LAST_SESSION;
import static org.vedibarta.app.PlayService.MEDIA_ID_PARASHA;

public class PlayerActivity extends PlayableActivity {


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
        }
        simpleExoPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources()
                , intArtsArr[new Random().nextInt(intArtsArr.length)]));
    }

    @Override
    void onConnectToPlayService() {
        Par par = getIntent().getParcelableExtra(EXTRA_PARASHA);
        if (getIntent().getBooleanExtra(EXTRA_LAST_SESSION, false)) {
            MediaControllerCompat.getMediaController(PlayerActivity.this)
                    .getTransportControls().playFromMediaId(MEDIA_ID_LAST_SESSION, null);
        } else if (par != null) {
            if (!par.equals(MyApplication.getPlayerManager().getPar())) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(EXTRA_PARASHA, par);
                MediaControllerCompat.getMediaController(PlayerActivity.this)
                        .getTransportControls().playFromMediaId(MEDIA_ID_PARASHA, bundle);
            }
        } else {
            MyApplication.getPlayerManager().omitTitle();
            simpleExoPlayerView.showController();
        }
    }

}
