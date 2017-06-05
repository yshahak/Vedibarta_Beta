package org.vedibarta.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayer;
import com.jakewharton.rxbinding2.view.RxView;

import org.vedibarta.app.MyApplication;
import org.vedibarta.app.ParashotHelper;
import org.vedibarta.app.R;
import org.vedibarta.app.calendar.CalendarHelper;
import org.vedibarta.app.model.Par;

import butterknife.BindView;

public class MainActivity extends PlayableActivity{

    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.par_title)
    TextView parTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        mViewPager.setAdapter(new CustomPagerAdapter(getSupportFragmentManager()));
        btnFullScreen.setVisibility(View.VISIBLE);
        RxView.clicks(btnFullScreen).subscribe(aVoid -> openPlayerActivity(MyApplication.getPlayerManager().getPar()));
        Par current = ParashotHelper.parList.get(CalendarHelper.getWeekParashaIndex());
        if (MyApplication.getPlayerManager().getPlayer().getPlaybackState() == ExoPlayer.STATE_READY) {
            simpleExoPlayerView.setVisibility(View.VISIBLE);
            simpleExoPlayerView.showController();
            MyApplication.getPlayerManager().omitTitle();
        } else {
            showPlayDialog(current);
            simpleExoPlayerView.setVisibility(View.GONE);
        }
    }

    @Override
    void onConnectToPlayService() {

    }

    private void showPlayDialog(Par current) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String content;
        int lastSession = MyApplication.getPlayerManager().getLastSessionIndex();
        if (lastSession != -1){
            builder.setTitle("שחזור שמיעה אחרונה");
            content = MyApplication.getPlayerManager().getLastSessionTitle();
        } else {
            content = String.format("לנגן את פרשת %s?", current.getParTitle());
        }
        builder
                .setMessage(content)
                .setPositiveButton("כן", (dialog, which) -> {
                    if (lastSession != -1) {
                        openLastSession(ParashotHelper.parList.get(lastSession));
                    } else {
                        openPlayerActivity(current);
                    }
                })
                .setNegativeButton("לא", null);
        builder.create().show();
    }



    @Override
    protected void onRestart() {
        super.onRestart();
        if (MyApplication.getPlayerManager().getPlayer().getPlaybackState() == ExoPlayer.STATE_READY) {
            simpleExoPlayerView.setVisibility(View.VISIBLE);
            simpleExoPlayerView.showController();
            MyApplication.getPlayerManager().omitTitle();
        } else {
            simpleExoPlayerView.setVisibility(View.GONE);
        }
    }

    private void openPlayerActivity(Par parasha) {
        Intent intent = new Intent(MainActivity.this, PlayerActivity.class);
        startActivity(intent);
    }

    private void openLastSession(Par parasha) {
        Intent intent = new Intent(MainActivity.this, PlayerActivity.class);
        intent.putExtra(EXTRA_LAST_SESSION, true);
        intent.putExtra(EXTRA_PARASHA, parasha);
        startActivity(intent);
    }


}
