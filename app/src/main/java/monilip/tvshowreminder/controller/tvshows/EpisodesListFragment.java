package monilip.tvshowreminder.controller.tvshows;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import monilip.tvshowreminder.R;
import monilip.tvshowreminder.model.database.DatabaseHandler;
import monilip.tvshowreminder.model.database.Episode;

/**
 * Created by Monika on 2014-12-07.
 */
public class EpisodesListFragment extends ListFragment {

    Integer tvshowID;
    ArrayAdapter<String> adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tvshowID = getArguments().getInt("tvshowID");
        // storing string resources into Array
        List<String> tvshowsList = this.getStringListData();
        adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.episodes_item, tvshowsList);
        setListAdapter(adapter);
    }

    private List<String> getStringListData() {
        DatabaseHandler db = new DatabaseHandler(getActivity().getApplicationContext());

        List<Episode> episodes = db.getAllEpisodeFromTVShow(tvshowID);
        List<String> episodesList = new ArrayList<String>();
        if (episodes.size() == 0) {
            episodesList.add("No episodes in database");
        } else {
            for (int i = 0; i < episodes.size(); i++) {
                episodesList.add("Episode " + episodes.get(i).getSeasonNumber() + "x" + episodes.get(i).getEpisodeNumber() + ": '" + episodes.get(i).getTitle() + "' ( " + episodes.get(i).getDate() + ")");
            }
        }
        db.close();
        return episodesList;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        //TODO
        //new fragment with single episode data
    }
}