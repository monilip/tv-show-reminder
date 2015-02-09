package monilip.tvshowreminder.controller.tvshows;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import monilip.tvshowreminder.R;
import monilip.tvshowreminder.model.adapter.CustomEpisodeListAdapter;
import monilip.tvshowreminder.model.database.DatabaseHandler;
import monilip.tvshowreminder.model.database.Episode;

/**
 * Created by Monika on 2014-12-07.
 */
public class EpisodesListFragment extends ListFragment {

    Integer tvshowID;
    CustomEpisodeListAdapter adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tvshowID = getArguments().getInt("tvshowID");
        // storing string resources into Array
        List<Episode> episodesList = this.getStringListData();
        //adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.episodes_item, tvshowsList);
        adapter = new CustomEpisodeListAdapter(this.getActivity(), episodesList);
        setListAdapter(adapter);
    }

    private List<Episode> getStringListData() {
        DatabaseHandler db = new DatabaseHandler(getActivity().getApplicationContext());
        return db.getAllEpisodeFromTVShow(tvshowID);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        //TODO
        //new fragment with single episode data
    }
}