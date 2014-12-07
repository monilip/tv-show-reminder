package monilip.tvshowreminder.controller.tvshows;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import monilip.tvshowreminder.R;

/**
 * Created by monilip on 2014-11-26.
 */
public class TVShowsFragment extends Fragment {

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tvshows, container, false);

        //load TVShowsList
        loadTVShowsList();
        return view;
    }

    public void loadTVShowsList() {
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        Fragment listFragment = new TVShowsListFragment();
        fragmentTransaction.replace(R.id.fragment_tvshows_content, listFragment);
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();
    }
}
