package monilip.tvshowreminder;

import android.content.Context;
import android.util.Log;

import java.io.File;
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

    public void crud(Context context) {

        //TODO
        Log.d("TEST", "adding images...");
        String filesPath = context.getFilesDir().toString() + File.separator;
        File folder = new File(filesPath);
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdirs();
        }
        if (success) {
              Log.d("Test","asynctask...");
              //  DownloadTVShowImagesASYNC imgTask = new DownloadTVShowImagesASYNC();
              //   imgTask.execute(ids);
        } else {
            //TODO
            Log.d("Test","Can't create main folder");
        }
        //add images
        //1. Create folder for TVSR on sdcard if does not exist
        //2. Create forlder for tvshow
        //3. DOwnload banner.jpg
        //4. Save it
        Log.d("TEST", "Images added");
    }
}
