package monilip.tvshowreminder;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.w3c.dom.Document;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import monilip.tvshowreminder.database.DatabaseHandler;
import monilip.tvshowreminder.database.Episode;
import monilip.tvshowreminder.database.TVShow;
import monilip.tvshowreminder.network.ConnectionDetector;
import monilip.tvshowreminder.network.NetworkManager;
import monilip.tvshowreminder.network.TVDB.TVShowsParser;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnGetTVShow = (Button) findViewById(R.id.btnGetTVShow);
        //Listening to button event
        btnGetTVShow.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                ConnectionDetector connectionDetector = new ConnectionDetector(getApplicationContext());
                if (connectionDetector.isConnected()){
                    NetworkManager netManager = new NetworkManager(getApplicationContext());
                    int[] TVDBids = {274431}; //"Gotham" (2014)
                    netManager.getTVShowData(TVDBids);
                }

            }
        });

        Button btnTVShowScreen = (Button) findViewById(R.id.btnTVShowScreen);
        //Listening to button event
        btnTVShowScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                //Starting a new Intent
                Intent tvshowScreen = new Intent(getApplicationContext(), TVShowActivity.class);

                //Sending data to another Activity
                tvshowScreen.putExtra("tvshowId", 1);  // "Gotham" (2014)

                startActivity(tvshowScreen);

            }
        });
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
        //Test start
        Log.d("TEST","Test start");


        //databaseHandler
        DatabaseHandler db = new DatabaseHandler(this);

        //Database test
       // Tests.databaseTest(db);

        //Network test
       // Tests.networkTest(getApplicationContext());
        this.searchTVShow("Supernatural");

        //Test stop
        Log.d("TEST","Test stop");
    }



    private void searchTVShow(String TVShowName){

        Log.d("TEST","networkTest");

        ConnectionDetector connectionDetector = new ConnectionDetector(getApplicationContext());
        if (connectionDetector.isConnected()){
            Log.d("TEST","App is connected to the Internet");

            Log.d("TEST","Searching tvshow in TVDB...");

            String[] urls = new String[1];
            urls[0] = "http://thetvdb.com/api/GetSeries.php?seriesname=" + TVShowName;

            LoadTVShowsASYNC task = new LoadTVShowsASYNC();
            task.execute(urls);
        } else {
            Log.d("TEST","App is not connected to the Internet");
        }


    }

    //loads tv shows from xml from urls
    private class LoadTVShowsASYNC extends AsyncTask<String, Void, String> {
        List<TVShow> tvshows = new ArrayList<TVShow>();

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

                    TVShowsParser tvshowsParser = new TVShowsParser(doc);
                    for(int i = 0;i< tvshowsParser.getTVShowsNumber();i++){
                        tvshows.add(tvshowsParser.getTVShowData(i));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("TEST","Get "+tvshows.size()+ " tvshows");
            //TODO
            //updateUI
        }

    }
}
