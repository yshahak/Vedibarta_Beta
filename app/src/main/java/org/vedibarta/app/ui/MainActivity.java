package org.vedibarta.app.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayer;
import com.jakewharton.rxbinding2.view.RxView;

import net.alexandroid.shpref.ShPref;

import org.vedibarta.app.MyApplication;
import org.vedibarta.app.ParashotHelper;
import org.vedibarta.app.R;
import org.vedibarta.app.calendar.CalendarHelper;
import org.vedibarta.app.model.Par;

import butterknife.BindView;

public class MainActivity extends PlayableActivity {

    public static final String KEY_ASK_LAST_PLAY = "keyAskLastPlay";
    public static final String KEY_PLAY_WEEK_PAR = "keyPlayWeekPar";

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
            if (savedInstanceState == null) {
                showPlayDialog(current);
            }
            simpleExoPlayerView.setVisibility(View.GONE);
        }
    }

    @Override
    void onConnectToPlayService() {

    }

    private void showPlayDialog(Par current) {
        int lastSession = MyApplication.getPlayerManager().getLastSessionIndex();
        if (lastSession != -1 && ShPref.getBoolean(KEY_ASK_LAST_PLAY, true)) {
            showLastPlayDialog();
        } else {
            showCurrentParDialog(current);
        }
    }

    @SuppressLint("InflateParams")
    private void showLastPlayDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        int lastSession = MyApplication.getPlayerManager().getLastSessionIndex();
        CheckBox checkBox = (CheckBox) LayoutInflater.from(this).inflate(R.layout.check_box_dont_ask, null);
        builder.setTitle("שחזור שמיעה אחרונה")
                .setView(checkBox)
                .setMessage(MyApplication.getPlayerManager().getLastSessionTitle())
                .setPositiveButton("כן", (dialog, which) -> openLastSession(ParashotHelper.parList.get(lastSession)))
                .setNegativeButton("לא", (dialog, which) -> ShPref.put(KEY_ASK_LAST_PLAY, !checkBox.isChecked()));
        builder.create().show();
    }

    private void showCurrentParDialog(Par current) {
        if (ShPref.getBoolean(KEY_PLAY_WEEK_PAR, true)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            CheckBox checkBox = (CheckBox) LayoutInflater.from(this).inflate(R.layout.check_box_dont_ask, null);
            builder.setTitle("ניגון פרשת השבוע")
                    .setView(checkBox)
                    .setMessage(String.format("לנגן את פרשת %s?", current.getParTitle()))
                    .setPositiveButton("כן", (dialog, which) -> openPlayerActivity(current))
                    .setNegativeButton("לא", (dialog, which) -> ShPref.put(KEY_PLAY_WEEK_PAR, !checkBox.isChecked()));
            builder.create().show();
        }
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
        intent.putExtra(EXTRA_PARASHA, parasha);
        startActivity(intent);
    }

    private void openLastSession(Par parasha) {
        Intent intent = new Intent(MainActivity.this, PlayerActivity.class);
        intent.putExtra(EXTRA_LAST_SESSION, true);
        intent.putExtra(EXTRA_PARASHA, parasha);
        startActivity(intent);
    }


}
