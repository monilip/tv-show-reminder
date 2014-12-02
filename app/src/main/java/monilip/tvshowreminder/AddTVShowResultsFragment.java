package monilip.tvshowreminder;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import monilip.tvshowreminder.database.DatabaseHandler;
import monilip.tvshowreminder.database.TVShow;

/**
 * Created by monilip on 2014-11-26.
 */
public class AddTVShowResultsFragment extends ListFragment {
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
        ArrayAdapter<TVShow> adapter = new ArrayAdapter<TVShow>(getActivity().getApplicationContext(),R.layout.episodes_item,tvshows);

        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        TVShow tvshowClicked = (TVShow) getListAdapter().getItem(position);

        DatabaseHandler db = new DatabaseHandler(getActivity().getApplicationContext());
        db.addTVShow(tvshowClicked);
        //TODO
        //get its episodes
        //add those episodes to db

        Log.d("TEST", "TVshows "+tvshowClicked.getTitle()+ " has been added to db");
    }
}
