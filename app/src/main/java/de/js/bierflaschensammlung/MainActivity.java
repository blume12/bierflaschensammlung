package de.js.bierflaschensammlung;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.js.bierflaschensammlung.config.Config;
import de.js.bierflaschensammlung.json.JsonParser;

public class MainActivity extends AppCompatActivity {

    private ArrayAdapter<String> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // load the listview for the beers
        ListView beerList = (ListView) findViewById(R.id.beer_list);

        // add a adapter for the List
        listAdapter = new ArrayAdapter<String>(this, R.layout.row, new ArrayList<String>());
        beerList.setAdapter(listAdapter);

        //load the data from Rest-Api
        new AsyncTaskParseJson().execute();

    }

    private class AsyncTaskParseJson extends AsyncTask<String, String, String> {

        final String TAG = "AsyncTaskParseJson";
        private final String URL = Config.getRestUrl()+"get-beer-list-user.php";

        JSONArray dataJsonArr = null;
        JSONObject json = null;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... arg0) {

            try {

                // instantiate our json parser
                JsonParser jParser = new JsonParser();

                // get json string from url
                json = jParser.getJSONFromUrl(URL);

                Log.d(TAG, json.toString());
                // get the array of the beers
                int countBeers = json.getJSONArray("beers").length();
                listAdapter.add(countBeers + " Biere");

                dataJsonArr = json.getJSONArray("beer_kind");
                // loop through all kinds of beers
                for (int i = 0; i < dataJsonArr.length(); i++) {
                    JSONObject c = dataJsonArr.getJSONObject(i);
                    // Storing each json item in variable
                    String beer_kind = c.getString("beer_kind");
                    String count = c.getString("count");

                    // show the values in our logcat
                    Log.e(TAG, "beer_kind: " + beer_kind + ", count: " + count);
                    // add to the listView
                    listAdapter.add("      " + count + " " + beer_kind); // space for Kinds of Beers
                }
                listAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String strFromDoInBg) {
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayUseLogoEnabled(true);
        }
        return true;
    }

    public void startSearchActivity(View view) {
        startActivity( new Intent(this, SearchActivity.class));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_beer_new) {
            startActivity( new Intent(this, NewBeerActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
