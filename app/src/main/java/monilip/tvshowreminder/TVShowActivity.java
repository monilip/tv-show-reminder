package monilip.tvshowreminder;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import monilip.tvshowreminder.database.DatabaseHandler;
import monilip.tvshowreminder.database.Episode;
import monilip.tvshowreminder.database.TVShow;


public class TVShowActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // storing string resources into Array
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());

        TVShow tvshow = db.getTVShowById(1); // "Gotham" (2014)
        List<Episode> episodes = db.getAllEpisodeFromTVShow(tvshow.getId());
        List<String> episodesList = new ArrayList<String>();
        for(int i=0;i < episodes.size();i++){
            episodesList.add("Episode " + episodes.get(i).getEpisodeNumber() + ": '" + episodes.get(i).getTitle() + "', date: " + episodes.get(i).getDate());
        }

        // Binding resources Array to ListAdapter
        this.setListAdapter(new ArrayAdapter<String>(this, R.layout.episodes_item, R.id.label, episodesList));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tvshow, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
