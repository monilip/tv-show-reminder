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
    private DashboardFragment dashboard;
    private TVShowsFragment tvshows;
    private AddTVShowFragment addtvshow;


    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
        dashboard = new DashboardFragment();
        tvshows = new TVShowsFragment();
        addtvshow = new AddTVShowFragment();
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Dashboard fragment
                return dashboard;
            case 1:
                // TVshows fragment
                return tvshows;
            case 2:
                // Add tvshow fragment
                return addtvshow;
        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }

    public DashboardFragment getDashboard() {
        return dashboard;
    }

    public TVShowsFragment getTvshows() {
        return tvshows;
    }

    public AddTVShowFragment getAddtvshow() {
        return addtvshow;
    }
}