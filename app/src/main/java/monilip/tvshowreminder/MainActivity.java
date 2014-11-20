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

import monilip.tvshowreminder.database.DatabaseHandler;
import monilip.tvshowreminder.network.ConnectionDetector;
import monilip.tvshowreminder.network.NetworkManager;

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

        Button btnFindTVShow = (Button) findViewById(R.id.btnFindTVShow);
        //Listening to button event
        btnFindTVShow.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                ConnectionDetector connectionDetector = new ConnectionDetector(getApplicationContext());
                if (connectionDetector.isConnected()){

                    //Starting a new Intent
                    Intent findTVShowResults = new Intent(getApplicationContext(), FindTVShowActivity.class);

                    //Getting TVShow name to find
                   // EditText tvshowName = (EditText) findViewById(R.id.tvshowName);
                    //Sending data to FindTVSHowResultsActivity
                    startActivity(findTVShowResults);
                }

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
        Tests.networkTest(getApplicationContext());

        //Test stop
        Log.d("TEST","Test stop");
    }

}
