package org.vedibarta.app.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import org.vedibarta.app.MyApplication;
import org.vedibarta.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.exo_player)
    SimpleExoPlayerView simpleExoPlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mViewPager.setAdapter(new CustomPagerAdapter(getSupportFragmentManager()));
        simpleExoPlayerView.setPlayer(MyApplication.getPlayerManager().getPlayer());
    }

    @Override
    protected void onResume(){
        super.onResume();
        if (MyApplication.getPlayerManager().getPlayer().getPlaybackState() == ExoPlayer.STATE_READY){
            simpleExoPlayerView.setVisibility(View.VISIBLE);
        } else {
            simpleExoPlayerView.setVisibility(View.GONE);
        }
    }
}
