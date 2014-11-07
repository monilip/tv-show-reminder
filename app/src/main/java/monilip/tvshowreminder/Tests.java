package monilip.tvshowreminder;

import android.util.Log;

import monilip.tvshowreminder.database.DatabaseHandler;
import monilip.tvshowreminder.database.Episode;
import monilip.tvshowreminder.database.TVShow;

/**
 * Created by monilip on 2014-11-07.
 */
public class Tests {
    private static DatabaseHandler db;

    //test for Database functionality
    public static void databaseTest(DatabaseHandler dbHandler){
        //databaseHandler
        db = dbHandler;
        Log.d("TEST","databaseTest");

        //databaseHandler
        TVShow tvshow = addTestTVShow();
        addTestEpisode(tvshow);

        Episode episode = db.getEpisode(tvshow.getId(),1,1);
        Log.d("TEST", tvshow.toString());
        Log.d("TEST",episode.toString());
    }

    private static TVShow addTestTVShow() {
        //TestTVShow
        TVShow testTvshow = new TVShow(12345, "Test",2014);

        db.addTVShow(testTvshow);
        return testTvshow;
    }
    private static void addTestEpisode(TVShow tvshow) {
        //TestEpisode
        Episode testEp = new Episode(tvshow.getId(),1,1,"Pilot","07-11-2014");

        db.addEpisode(testEp);
    }
}
