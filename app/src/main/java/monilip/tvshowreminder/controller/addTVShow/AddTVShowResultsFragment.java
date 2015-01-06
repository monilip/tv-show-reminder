package monilip.tvshowreminder.controller.addTVShow;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import monilip.tvshowreminder.R;
import monilip.tvshowreminder.model.database.DatabaseHandler;
import monilip.tvshowreminder.model.database.TVShow;
import monilip.tvshowreminder.model.network.NetworkManager;

/**
 * Created by monilip on 2014-11-26.
 */
public class AddTVShowResultsFragment extends ListFragment {
    ArrayAdapter<TVShow> adapter;

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        // storing string resources into Array

        List<TVShow> tvshows = (List<TVShow>) getArguments().getSerializable("tvshowsResults");

        List<String> tvshowsList = new ArrayList<String>();
        if (tvshows.size() == 0){
            tvshowsList.add("No tvshow found");
        } else {
            for (int i = 0; i < tvshows.size(); i++) {
                tvshowsList.add("TVShow: '" + tvshows.get(i).getTitle() + "' ( " + tvshows.get(i).getYear() + ")");
            }
        }
        Log.d("TEST", "tvshows.size: " + tvshows.size());
        adapter = new ArrayAdapter<TVShow>(getActivity().getApplicationContext(), R.layout.episodes_item,tvshows);

        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        TVShow tvshowClicked = (TVShow) getListAdapter().getItem(position);

        DatabaseHandler db = new DatabaseHandler(getActivity().getApplicationContext());
        //if there is no tvshow like this in database
        if (db.getTVShow(tvshowClicked.getTitle(),tvshowClicked.getYear()) == null){
            Log.d("TEST","Adding tvshow to database...");
            db.addTVShow(tvshowClicked);

            NetworkManager netManager = new NetworkManager(getActivity().getApplicationContext());
            int[] TVDBids = {tvshowClicked.getTVDBid()};
            Log.d("TEST","Adding tvshow's episodes to database...");
            netManager.getTVShowData(TVDBids);

            Log.d("TEST", "TVshows "+tvshowClicked.getTitle()+ " has been added to db");
        }
        db.close();

    }

}
