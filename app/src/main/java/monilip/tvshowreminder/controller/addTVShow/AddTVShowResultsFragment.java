package monilip.tvshowreminder.controller.addTVShow;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import monilip.tvshowreminder.model.adapter.CustomTVShowListAdapter;
import monilip.tvshowreminder.model.database.DatabaseHandler;
import monilip.tvshowreminder.model.database.TVShow;
import monilip.tvshowreminder.model.network.NetworkManager;

/**
 * Created by monilip on 2014-11-26.
 */
public class AddTVShowResultsFragment extends ListFragment {
    CustomTVShowListAdapter adapter;

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

        adapter = new CustomTVShowListAdapter(this.getActivity(), tvshows);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        TVShow tvshowClicked = (TVShow) getListAdapter().getItem(position);

        DatabaseHandler db = new DatabaseHandler(getActivity().getApplicationContext());
        //if there is no tvshow like this in database
        if (db.getTVShow(tvshowClicked.getTitle(),tvshowClicked.getYear()) == null){
            db.addTVShow(tvshowClicked);

            NetworkManager netManager = new NetworkManager(getActivity().getApplicationContext(),getActivity());
            int[] TVDBids = {tvshowClicked.getTVDBid()};
            netManager.getTVShowData(TVDBids);
        }
        db.close();

    }

}
