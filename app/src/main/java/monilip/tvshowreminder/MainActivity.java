package monilip.tvshowreminder;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import monilip.tvshowreminder.database.DatabaseHandler;
import monilip.tvshowreminder.database.Episode;
import monilip.tvshowreminder.database.TVShow;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //for testing
        if (id == R.id.action_test) {
            this.test();
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //For testing
    private void test() {
        //All backend functionality should be tested here
        Log.d("TEST","Test start");

        //databaseHandler
        DatabaseHandler db = new DatabaseHandler(this);

        TVShow tvshow = this.addTestTVShow();
        this.addTestEpisode(tvshow);

        Episode episode = db.getEpisode(tvshow.getId(),1,1);
        Log.d("TEST",tvshow.toString());
        Log.d("TEST",episode.toString());

        Log.d("TEST","Test stop");
    }

    private TVShow addTestTVShow() {
        //databaseHandler
        DatabaseHandler db = new DatabaseHandler(this);

        //TestTVShow
        TVShow testTvshow = new TVShow(123, "Test",2014);

        db.addTVShow(testTvshow);
        return testTvshow;
    }

    private void addTestEpisode(TVShow tvshow) {
        //databaseHandler
        DatabaseHandler db = new DatabaseHandler(this);

        //TestEpisode
        Episode testEp = new Episode(tvshow.getId(),1,1,"Pilot","07-11-2014");

        db.addEpisode(testEp);
    }


}
