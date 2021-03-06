package org.vedibarta.app.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by e560 on 14/05/17.
 */

public class CustomPagerAdapter extends FragmentPagerAdapter {

    public CustomPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return FragmentParashot.newInstance();
            case 1:
                return new FragmentFeedback();
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "פרשות";
            case 1:
                return "משוב";
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
