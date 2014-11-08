package monilip.tvshowreminder;

import android.content.Context;
import android.util.Log;

import monilip.tvshowreminder.database.DatabaseHandler;
import monilip.tvshowreminder.database.Episode;
import monilip.tvshowreminder.database.TVShow;
import monilip.tvshowreminder.network.ConnectionDetector;

/**
 * Created by monilip on 2014-11-07.
 */
public class Tests {

    //tests for Database functionality
    public static void databaseTest(DatabaseHandler dbHandler){
        //databaseHandler
        Log.d("TEST","databaseTest");

        //databaseHandler
        TVShow tvshow = addTestTVShow(dbHandler);
        addTestEpisode(dbHandler, tvshow);

        Episode episode = dbHandler.getEpisode(tvshow.getId(),1,1);
        Log.d("TEST", tvshow.toString());
        Log.d("TEST",episode.toString());
    }
    private static TVShow addTestTVShow(DatabaseHandler db) {
        //TestTVShow
        TVShow testTvshow = new TVShow(12345, "Test",2014);

        db.addTVShow(testTvshow);
        return testTvshow;
    }
    private static void addTestEpisode(DatabaseHandler db, TVShow tvshow) {
        //TestEpisode
        Episode testEp = new Episode(tvshow.getId(),1,1,"Pilot","07-11-2014");

        db.addEpisode(testEp);
    }

    //tests for Network functionallty
    public static void networkTest(Context appContext){
        Log.d("TEST","networkTest");

        ConnectionDetector connectionDetector = new ConnectionDetector(appContext);
        if (connectionDetector.isConnected()){
            Log.d("TEST","App is connected to the Internet");
        } else {
            Log.d("TEST","App is not connected to the Internet");
        }
    }
}
