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
import monilip.tvshowreminder.model.database.DatabaseHandler;
import monilip.tvshowreminder.model.database.Episode;
import monilip.tvshowreminder.model.database.TVShow;

/**
 * Created by monilip on 2014-11-26.
 */
public class DashboardFragment extends Fragment {

    ArrayAdapter<String> adapter;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        refresh();

        return view;

    }

    private List<String> getEpisodesThisWeekData() {

        DatabaseHandler db = new DatabaseHandler(getActivity().getApplicationContext());

        List<String> episodesThisWeek = new ArrayList<String>();
        for(Episode ep : db.getEpisodesFromThisWeek()){
             episodesThisWeek.add(ep.getDate() + ": " + db.getTVShowFromEpisode(ep).getTitle() + " " + ep.getSeasonNumber() + "x" + ep.getEpisodeNumber() + " " + ep.getTitle());
        }

        return episodesThisWeek;
    }

    private List<String> getEpisodesNextWeekData() {

        DatabaseHandler db = new DatabaseHandler(getActivity().getApplicationContext());

        List<String> episodes = new ArrayList<String>();
        for(Episode ep : db.getEpisodesFromNextWeek()){
            episodes.add(ep.getDate() + ": " + db.getTVShowFromEpisode(ep).getTitle() + " " + ep.getSeasonNumber() + "x" + ep.getEpisodeNumber() + " " + ep.getTitle());
        }

        return episodes;
    }

    private List<String> getEpisodesRestOfMonthData() {

        DatabaseHandler db = new DatabaseHandler(getActivity().getApplicationContext());

        List<String> episodes = new ArrayList<String>();
        for(Episode ep : db.getEpisodesFromRestOfMonth()){
            episodes.add(ep.getDate() + ": " + db.getTVShowFromEpisode(ep).getTitle() + " " + ep.getSeasonNumber() + "x" + ep.getEpisodeNumber() + " " + ep.getTitle());
        }

        return episodes;
    }

    private List<String> getEpisodesNextMonthData() {

        DatabaseHandler db = new DatabaseHandler(getActivity().getApplicationContext());

        List<String> episodes = new ArrayList<String>();
        for(Episode ep : db.getEpisodesFromNextMonth()){
            episodes.add(ep.getDate() + ": " + db.getTVShowFromEpisode(ep).getTitle() + " " + ep.getSeasonNumber() + "x" + ep.getEpisodeNumber() + " " + ep.getTitle());
        }

        return episodes;
    }


    public void refresh() {
        //add ListViews
        //thisWeek
        ListView thisWeekList = (ListView) view.findViewById(R.id.episodes_thisWeek);
        adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.tvshow_item,getEpisodesThisWeekData());
        thisWeekList.setAdapter(adapter);
        //nextWeek
        ListView nextWeekList = (ListView) view.findViewById(R.id.episodes_nextWeek);
        adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.tvshow_item,getEpisodesNextWeekData());
        nextWeekList.setAdapter(adapter);
        //restOfMonth
        ListView restOfMonthList = (ListView) view.findViewById(R.id.episodes_restOfMonth);
        adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.tvshow_item,getEpisodesRestOfMonthData());
        restOfMonthList.setAdapter(adapter);
        //nextMonth
        ListView nextMonthList = (ListView) view.findViewById(R.id.episodes_nextMonth);
        adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.tvshow_item,getEpisodesNextMonthData());
        nextMonthList.setAdapter(adapter);
    }
}
