package monilip.tvshowreminder.controller.dashboard;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import monilip.tvshowreminder.R;
import monilip.tvshowreminder.model.adapter.CustomEpisodeListAdapter;
import monilip.tvshowreminder.model.database.DatabaseHandler;
import monilip.tvshowreminder.model.database.Episode;
import monilip.tvshowreminder.model.database.TVShow;

/**
 * Created by monilip on 2014-11-26.
 */
public class DashboardFragment extends Fragment {

    CustomEpisodeListAdapter adapter;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        refresh();

        return view;

    }

    private List<Episode> getNextEpisodesData() {

        DatabaseHandler db = new DatabaseHandler(getActivity().getApplicationContext());

        return db.getNextEpisodesData();
    }


    public void refresh() {
        ListView thisWeekList = (ListView) view.findViewById(R.id.nextEpisodes);
        adapter = new CustomEpisodeListAdapter(this.getActivity(), getNextEpisodesData());
        thisWeekList.setAdapter(adapter);
    }
}
