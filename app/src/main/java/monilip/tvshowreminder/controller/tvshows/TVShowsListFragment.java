package monilip.tvshowreminder.controller.tvshows;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import monilip.tvshowreminder.R;
import monilip.tvshowreminder.model.adapter.CustomTVShowListAdapter;
import monilip.tvshowreminder.model.adapter.SmallCustomTVShowListAdapter;
import monilip.tvshowreminder.model.database.DatabaseHandler;
import monilip.tvshowreminder.model.database.TVShow;

/**
 * Created by monilip on 2014-11-26.
 */
public class TVShowsListFragment extends ListFragment {

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    SmallCustomTVShowListAdapter adapter;
    //CustomTVShowListAdapter adapter;

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        List<TVShow> tvshowsList = this.getListData();
       adapter = new SmallCustomTVShowListAdapter(this.getActivity(), tvshowsList);
       //adapter = new CustomTVShowListAdapter(this.getActivity(), tvshowsList);

        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        TVShow tvshowClicked = (TVShow) getListAdapter().getItem(position);

        //loadTVShowsSingleFragment into fragment_tvshow_content
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        Fragment listFragment = new SingleTVShowFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("tvshowID", tvshowClicked.getId());
        listFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragment_tvshows_content, listFragment);

        fragmentTransaction.commit();
    }

    private List<TVShow> getListData(){
        DatabaseHandler db = new DatabaseHandler(getActivity().getApplicationContext());

        List<TVShow> tvshows = db.getAllTVShows();
        db.close();
        return tvshows;
    }

}
