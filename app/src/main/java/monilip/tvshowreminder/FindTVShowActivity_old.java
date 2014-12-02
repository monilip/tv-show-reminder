package monilip.tvshowreminder;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.w3c.dom.Document;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import monilip.tvshowreminder.database.TVShow;
import monilip.tvshowreminder.network.TVDB.FindTVShowParser;


public class FindTVShowActivity_old extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("TEST","FindTVShowActivity");
        super.onCreate(savedInstanceState);
      /*  setContentView(R.layout.activity_search_tvshow);

        Button btnFindTVShow = (Button) findViewById(R.id.btnFindTVShow);
        //Listening to button event
        btnFindTVShow.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                //Getting TVShow name to find
                EditText tvshowName = (EditText) findViewById(R.id.tvshowName);

                //Getting data from TVDB
                String[] urls = new String[1];
                String seriesname = tvshowName.getText().toString();
                seriesname.replace(" ","+");
                urls[0] = "http://thetvdb.com/api/GetSeries.php?seriesname=" + seriesname;

                LoadTVShowsASYNC task = new LoadTVShowsASYNC();
                task.execute(urls);
}
        });
        */
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_tvshow, menu);
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

                    FindTVShowParser findTVShowsParser = new FindTVShowParser(doc);
                    for(int i = 0;i< findTVShowsParser.getTVShowsNumber();i++){
                        tvshows.add(findTVShowsParser.getTVShowData(i));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            /*TextView resultsTextView = (TextView) findViewById(R.id.TempResults);
            resultsTextView.setText("Got " + tvshows.size() + " tvshows");
            for(TVShow tvshow : tvshows){
                resultsTextView.append(tvshow.getTitle()+"("+tvshow.getYear()+")");
            }*/
            //TODO
            //make a fancy UI for results
        }

    }
}
