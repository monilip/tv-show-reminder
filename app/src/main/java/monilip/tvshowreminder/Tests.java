package monilip.tvshowreminder;

import android.content.Context;
import android.util.Log;

import java.util.List;

import monilip.tvshowreminder.model.database.DatabaseHandler;
import monilip.tvshowreminder.model.database.Episode;
import monilip.tvshowreminder.model.database.TVShow;
import monilip.tvshowreminder.model.network.ConnectionDetector;
import monilip.tvshowreminder.model.network.NetworkManager;

/**
 * Created by monilip on 2014-11-07.
 */
public class Tests {


    //tests for Network functionallty
    public static void networkTest(Context appContext){
        Log.d("TEST","networkTest");

        //appContext.deleteDatabase("tvshowsManager");
        DatabaseHandler db = new DatabaseHandler(appContext);


        ConnectionDetector connectionDetector = new ConnectionDetector(appContext);
        if (connectionDetector.isConnected()){
            Log.d("TEST","App is connected to the Internet");
            NetworkManager netManager = new NetworkManager(appContext);
            int[] TVDBids = {274431}; //"Gotham" (2014)
            Log.d("TEST","Adding tvshow to database...");
            netManager.getTVShowData(TVDBids);

            Log.d("TEST","Getting tvshow from database...");


            TVShow tvshow = db.getTVShowByTVDBId(274431); // "Gotham" (2014)
            if (tvshow !=null) {
                Log.d("TEST", "Show title: " + tvshow.getTitle());

                List<Episode> episodes = db.getAllEpisodeFromTVShow(tvshow.getId());
                for(int i=0;i < episodes.size();i++){
                    Log.d("TEST","Episode " + episodes.get(i).getEpisodeNumber() + ": '" + episodes.get(i).getTitle() + "', date: " + episodes.get(i).getDate());
                }
            } else {
                Log.d("TEST","TVShow is still downloading, try later");
            }
        } else {
            Log.d("TEST","App is not connected to the Internet");
        }
    }

    public void dashboardTest(Context appContext){
        DatabaseHandler db = new DatabaseHandler(appContext);

        List<Episode> episodes;

        episodes = db.getNextEpisodesData();
        Log.d("TEST","Number of episodes from this week: "+episodes.size());
        for(Episode ep : episodes){
            Log.d("TEST",ep.getDate() + ": " + db.getTVShowFromEpisode(ep).getTitle() + " " + ep.getSeasonNumber() + "x" + ep.getEpisodeNumber() + " " + ep.getTitle());
        }


    }
}
