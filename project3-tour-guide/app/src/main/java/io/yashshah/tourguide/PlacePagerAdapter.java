package io.yashshah.tourguide;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by yashshah on 31/12/16.
 */

public class PlacePagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public PlacePagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new HistoricalFragment();
        } else if (position == 1) {
            return new MuseumsFragment();
        } else if (position == 2) {
            return new FoodFragment();
        } else {
            return new OtherFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getResources().getString(R.string.historical);
        } else if (position == 1) {
            return mContext.getResources().getString(R.string.museums);
        } else if (position == 2) {
            return mContext.getResources().getString(R.string.food);
        } else {
            return mContext.getResources().getString(R.string.other);
        }
    }
}
