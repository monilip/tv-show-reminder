package monilip.tvshowreminder.model.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import monilip.tvshowreminder.R;
import monilip.tvshowreminder.model.database.TVShow;

/**
 * Created by monilip on 2015-01-27.
 */
public class SmallCustomTVShowListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<TVShow> tvshowsList;

    public SmallCustomTVShowListAdapter(Activity activity, List<TVShow> tvshowsList) {
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
            view = inflater.inflate(R.layout.list_small_item_tvshow, null);
        }

        ImageView banner = (ImageView) view.findViewById(R.id.banner);

        File imgFile = new  File(activity.getApplicationContext().getFilesDir().toString() + File.separator + "banner" + tvshowsList.get(i).getTVDBid() + ".jpg");

        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            banner.setImageBitmap(myBitmap);
        } else {
            Log.d("Test", "File does not exist :(");
        }



        return view;
    }
}
