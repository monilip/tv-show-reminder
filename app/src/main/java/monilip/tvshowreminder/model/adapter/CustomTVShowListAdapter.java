package monilip.tvshowreminder.model.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import monilip.tvshowreminder.R;
import monilip.tvshowreminder.model.database.TVShow;

/**
 * Created by monilip on 2015-01-27.
 */
public class CustomTVShowListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<TVShow> tvshowsList;

    public CustomTVShowListAdapter(Activity activity, List<TVShow> tvshowsList) {
        super();
        this.tvshowsList = tvshowsList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return tvshowsList.size();
    }

    @Override
    public Object getItem(int i) {
        return tvshowsList.get(i);
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
            view = inflater.inflate(R.layout.list_item_tvshow, null);
        }
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView description = (TextView) view.findViewById(R.id.description);
        TextView year = (TextView) view.findViewById(R.id.releaseYear);

        //Getting tvshow data for the row
        TVShow tvshow = tvshowsList.get(i);

        //Title
        title.setText(tvshow.getTitle());
        //Description
        description.setText(tvshow.getDescription());
        //Year
        year.setText(tvshow.getYear().toString());

        return view;
    }
}
