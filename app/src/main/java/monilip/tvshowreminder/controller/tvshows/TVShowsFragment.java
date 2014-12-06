package monilip.tvshowreminder.controller.tvshows;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import monilip.tvshowreminder.R;
import monilip.tvshowreminder.model.database.DatabaseHandler;
import monilip.tvshowreminder.model.database.TVShow;

/**
 * Created by monilip on 2014-11-26.
 */
public class TVShowsFragment extends ListFragment {

    ArrayAdapter<String> adapter;
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        // storing string resources into Array
        List<String> tvshowsList = this.getStringListData();
        adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.episodes_item,tvshowsList);
        setListAdapter(adapter);
    }


    private List<String> getStringListData(){
        DatabaseHandler db = new DatabaseHandler(getActivity().getApplicationContext());

        List<TVShow> tvshows = db.getAllTVShows();
        List<String> tvshowsList = new ArrayList<String>();
        if (tvshows.size() == 0){
            tvshowsList.add("No tvshows in database");
        } else {
            for (int i = 0; i < tvshows.size(); i++) {
                tvshowsList.add("TVShow: '" + tvshows.get(i).getTitle() + "' ( " + tvshows.get(i).getYear() + ")");
            }
        }

        return tvshowsList;
    }


    public void reload() {
        List<String> tvshowsList = this.getStringListData();
        adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),R.layout.episodes_item,tvshowsList);
        setListAdapter(adapter);
    }
}
