package monilip.tvshowreminder.network;


import android.content.Context;
import android.os.AsyncTask;

import org.w3c.dom.Document;

import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import monilip.tvshowreminder.database.DatabaseHandler;
import monilip.tvshowreminder.database.Episode;
import monilip.tvshowreminder.database.TVShow;
import monilip.tvshowreminder.network.TVDB.*;

/**
 * Created by monilip on 2014-11-09.
 */
public class NetworkManager {
    private Context context;

    public NetworkManager(Context context){
        this.context = context;
    }

    public void getTVShowData(int[] TVDBids) {
        String[] urls = new String[TVDBids.length];

        for(int i = 0; i < TVDBids.length; i++) {
            String urlString = new String("http://thetvdb.com/data/series/" + TVDBids[i] + "/all/");
            urls[i] = urlString;
        }
        LoadTVShowsDataASYNC task = new LoadTVShowsDataASYNC();
        task.execute(urls);
    }

    //loads tv shows data from xml from urls to database
    private class LoadTVShowsDataASYNC extends AsyncTask<String, Void, String> {

        DatabaseHandler db = new DatabaseHandler(context);

        @Override
        protected String doInBackground(String... urls) {

            for(String urlString: urls){
                URL url = null;
                try {
                    url = new URL(urlString);
                    URLConnection conn = url.openConnection();

                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder builder = factory.newDocumentBuilder();
                    Document doc = builder.parse(conn.getInputStream());

                    TVShowParser tvshowParser = new TVShowParser(doc);
                    //add tvshow to database if there is not
                    if (!db.isTVShow(tvshowParser.getTVDBid())) {
                        //add tvshow to database
                        db.addTVShow(tvshowParser.getTVShow());
                    }
                    //add episodes from tvshow
                    db.addEpisodesToTVShow(tvshowParser.getEpisodesDataList(), db.getTVShowByTVDBId(tvshowParser.getTVDBid()).getId());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            //TODO
            //refresh UI
        }
    }
}