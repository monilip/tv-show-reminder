package monilip.tvshowreminder.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import monilip.tvshowreminder.AddTVShowFragment;
import monilip.tvshowreminder.DashboardFragment;
import monilip.tvshowreminder.TVShowsFragment;



/**
 * Created by monilip on 2014-11-26.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Dashboard fragment activity
                return new DashboardFragment();
            case 1:
                // TVshows fragment activity
                return new TVShowsFragment();
            case 2:
                // Add tvshow fragment activity
                return  new AddTVShowFragment();

        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }

}