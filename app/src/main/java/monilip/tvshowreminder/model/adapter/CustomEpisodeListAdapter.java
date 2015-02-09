package monilip.tvshowreminder.model.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import monilip.tvshowreminder.R;
import monilip.tvshowreminder.model.database.DatabaseHandler;
import monilip.tvshowreminder.model.database.Episode;

/**
 * Created by monilip on 2015-01-27.
 */
public class CustomEpisodeListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<Episode> episodeList;

    public CustomEpisodeListAdapter(Activity activity, List<Episode> episodeList) {
        super();
        this.episodeList = episodeList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return episodeList.size();
    }

    @Override
    public Object getItem(int i) {
        return episodeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = inflater.inflate(R.layout.list_item_episode, null);
        }
        TextView tvshowTitle = (TextView) view.findViewById(R.id.tvshowTitle);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView date = (TextView) view.findViewById(R.id.date);


        //Getting tvshow data for the row
        Episode episode = episodeList.get(i);

        DatabaseHandler db = new DatabaseHandler(activity.getApplicationContext());
        //TV-show Title
        tvshowTitle.setText(db.getTVShowById(episode.getTVShowId()).getTitle());
        //Title
        title.setText(episode.getTitle());
        //Date
        date.setText(episode.getDate());

        return view;
    }
}
