package monilip.tvshowreminder.model.network;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.w3c.dom.Document;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import monilip.tvshowreminder.model.database.DatabaseHandler;
import monilip.tvshowreminder.model.network.TVDB.TVShowParser;

/**
 * Created by monilip on 2014-11-09.
 */
public class NetworkManager {
    private Context context;
    private Activity activiy;

    public NetworkManager(Context context, Activity activiy) {
        this.context = context;
        this.activiy = activiy;
    }

    public void getTVShowData(int[] TVDBids) {
        String[] urls = new String[TVDBids.length];
        String[] ids = new String[TVDBids.length];

        for (int i = 0; i < TVDBids.length; i++) {
            String urlString = new String("http://thetvdb.com/api/BBFB91B2F4736182/series/" + TVDBids[i] + "/all/");
            urls[i] = urlString;
            ids[i] = TVDBids[i] + "";
        }
        LoadTVShowDataASYNC task = new LoadTVShowDataASYNC();
        task.execute(urls);

        String filesPath = context.getFilesDir().toString() + File.separator;
        File folder = new File(filesPath);
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdirs();
        }
        if (success) {
            DownloadTVShowImagesASYNC imgTask = new DownloadTVShowImagesASYNC();
            imgTask.execute(ids);
        } else {
            Log.d("Test","Can't create main folder");
        }
    }

    //loads tv shows data from xml from urls to database
    private class LoadTVShowDataASYNC extends AsyncTask<String, Void, String> {
        DatabaseHandler db = new DatabaseHandler(context);
        ProgressDialog progressDialog;

        @Override

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(activiy, "Please wait", "Getting tvshow's data from database", true);
        }

        ;

        @Override
        protected String doInBackground(String... urls) {

            for (String urlString : urls) {
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
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            db.close();
            progressDialog.dismiss();

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            db.close();
        }
    }

    //downloads tv shows images
    private class DownloadTVShowImagesASYNC extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        protected void onPreExecute()
        {
            progressDialog = ProgressDialog.show(activiy, "Please wait","Getting tvshow's images from database", true);
        };

        @Override
        protected String doInBackground(String... ids) {

            int count;
            for(String id: ids){
                URL url = null;
                try {
                    url = new URL("http://thetvdb.com/banners/graphical/"+id+"-g.jpg");
                    URLConnection conn = url.openConnection();

                    conn.connect();
                    // getting file length
                    int lenghtOfFile = conn.getContentLength();

                    // input stream to read file - with 8k buffer
                    InputStream input = new BufferedInputStream(url.openStream(), 8192);

                    // Output stream to write file
                    FileOutputStream output = null;
                    output = context.openFileOutput("banner"+id+".jpg", Context.MODE_PRIVATE);

                    byte data[] = new byte[1024];

                    long total = 0;

                    while ((count = input.read(data)) != -1) {
                        total += count;
                        output.write(data, 0, count);
                    }

                    // flushing output
                    output.flush();

                    // closing streams
                    output.close();
                    input.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
        }
    }
}
