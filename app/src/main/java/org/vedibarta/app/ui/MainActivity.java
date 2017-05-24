package org.vedibarta.app.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import org.vedibarta.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mViewPager.setAdapter(new CustomPagerAdapter(getSupportFragmentManager()));
//        if (MyApplication.getPlayerManager().getPlayer().getPlaybackState() == ExoPlayer.STATE_READY){
//            Intent intent = new Intent(this, PlayerActivity.class);
//            intent.addFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
//            intent.putExtra(EXTRA_PARASHA, MyApplication.getPlayerManager().getPar());
//            startActivity(intent);
//        }
    }

    @Override
    protected void onResume(){
        super.onResume();
    }
}
