
package monilip.tvshowreminder.controller;


import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import monilip.tvshowreminder.R;
import monilip.tvshowreminder.Tests;
import monilip.tvshowreminder.model.adapter.TabsPagerAdapter;
import monilip.tvshowreminder.model.database.DatabaseHandler;

/**
 * Created by monilip on 2014-11-26.
 */
public class MainActivity extends FragmentActivity  implements ActionBar.TabListener {

    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;
    // Tab titles
    private String[] tabs = { "Dashboard", "TVShows", "Add TVShow"};


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initilization
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }
        /**
         * on swiping the viewpager make respective tab selected
         * */
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                actionBar.setSelectedNavigationItem(position);

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        // on tab selected
        // show respected fragment view
        viewPager.setCurrentItem(tab.getPosition());
        //if tvshowsFragment checked for update
        if (tab.getPosition() == 1) {
            mAdapter.getTvshows().loadTVShowsList();
        }

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
        if (tab.getPosition() == 0) {
            mAdapter.getDashboard().refresh();
        } else if (tab.getPosition() == 1) {
            mAdapter.getTvshows().loadTVShowsList();
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_clear_db:
                getApplicationContext().deleteDatabase("tvshowsManager");
                Log.d("TEST", "database was deleted");
                return true;
            case R.id.action_tests:
                tests();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void tests() {
        Tests tests = new Tests();

        tests.dashboardTest(getApplicationContext());

    }
}
