package monilip.tvshowreminder.controller.tvshows;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import monilip.tvshowreminder.R;
import monilip.tvshowreminder.model.database.DatabaseHandler;
import monilip.tvshowreminder.model.database.TVShow;

/**
 * Created by Monika on 2014-12-07.
 */
public class SingleTVShowFragment extends Fragment implements View.OnClickListener {

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Integer tvshowID;

    @Override
    public void onCreate(Bundle save)
    {
        super.onCreate(save);
        setRetainInstance(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tvshows_single, container, false);


        Button searchButton = (Button) view.findViewById(R.id.tvshowEpisodes);
        searchButton.setOnClickListener(this);
        tvshowID = getArguments().getInt("tvshowID");
        DatabaseHandler db = new DatabaseHandler(getActivity().getApplicationContext());
        TVShow tvshow = db.getTVShowById(tvshowID);
        db.close();


        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(tvshow.getTitle());

        TextView year = (TextView) view.findViewById(R.id.releaseYear);
        year.setText(tvshow.getYear().toString());

        TextView description = (TextView) view.findViewById(R.id.description);
        description.setText(tvshow.getDescription());

        ImageView banner = (ImageView) view.findViewById(R.id.banner);
        //TODO
        //get banner per tvshow
        banner.setImageResource(R.drawable.banner);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvshowEpisodes:
                //loadTVShowsSingleFragment into fragment_tvshow_content
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                Fragment listFragment = new EpisodesListFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("tvshowID",tvshowID);
                listFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragment_tvshows_content, listFragment);
                fragmentTransaction.addToBackStack(null);

                fragmentTransaction.commit();
        }
    }
}