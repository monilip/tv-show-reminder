package monilip.tvshowreminder.controller.addTVShow;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.w3c.dom.Document;

import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import monilip.tvshowreminder.R;
import monilip.tvshowreminder.model.database.TVShow;
import monilip.tvshowreminder.model.network.ConnectionDetector;
import monilip.tvshowreminder.model.network.TVDB.FindTVShowParser;

/**
 * Created by monilip on 2014-11-26.
 */
public class AddTVShowFragment extends Fragment implements View.OnClickListener {

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_tvshow, container, false);
        Button searchButton = (Button) view.findViewById(R.id.searchTVShowButton);
        searchButton.setOnClickListener(this);
        return view;

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.searchTVShowButton:
                Log.d("TEST", "*clicks*");

                //Getting TVShow name to find
                EditText tvshowName = (EditText) getView().findViewById(R.id.tvshowName);

                //Checks connection
                ConnectionDetector connectionDetector = new ConnectionDetector(getActivity().getApplicationContext());
                if (connectionDetector.isConnected()) {
                    Log.d("TEST", "App is connected to the Internet");
                    //Getting data from TVDB
                    String[] urls = new String[1];
                    String seriesname = tvshowName.getText().toString();
                    seriesname = seriesname.replace(' ','+');
                    urls[0] = "http://thetvdb.com/api/GetSeries.php?seriesname=" + seriesname;

                    LoadTVShowsASYNC task = new LoadTVShowsASYNC();
                    task.execute(urls);
                } else {
                    Log.d("TEST", "App is not connected to the Internet :(");
                }

                break;

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
            Log.d("TEST","I've got tvshows!");
            Bundle bundle = new Bundle();
            bundle.putSerializable("tvshowsResults",(Serializable) tvshows);
            fragmentManager = getFragmentManager();
            if (fragmentManager != null){
                fragmentTransaction = fragmentManager.beginTransaction();

                Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_addtvshow_results);
                // if there is a fragment allready in the container
                if (!(fragment == null)) {
                    fragmentTransaction.remove(fragment);
                }
                Fragment resultsFragment = new AddTVShowResultsFragment();
                resultsFragment.setArguments(bundle);
                fragmentTransaction.add(R.id.fragment_addtvshow_results, resultsFragment);
                fragmentTransaction.addToBackStack(null);

                fragmentTransaction.commit();
            } else {
                Log.d("TEST", "fragmentManager is null :(");
            }
        }

    }

}
