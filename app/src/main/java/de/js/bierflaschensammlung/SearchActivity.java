package de.js.bierflaschensammlung;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Array;
import java.util.ArrayList;

import de.js.bierflaschensammlung.config.Config;
import de.js.bierflaschensammlung.json.JsonParser;

public class SearchActivity extends AppCompatActivity {

    private ArrayAdapter<String> listAdapter;
    private ListView resultList;

    private SearchActivity context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button sendSearch = (Button) findViewById(R.id.search_button);

        // load the listview for the beers
        resultList = (ListView) findViewById(R.id.result_list);

        // add a adapter for the List
        listAdapter = new ArrayAdapter<String>(this, R.layout.row, new ArrayList<String>());

        resultList.setAdapter(listAdapter);


        sendSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //load the data from Rest-Api
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        new AsyncTaskParseJson().execute();
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_beer_new:
                startActivity(new Intent(this, NewBeerActivity.class));
                return true;

            case R.id.home:
                //TODO: Back-Button
                startActivity(new Intent(this, MainActivity.class));
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    private class AsyncTaskParseJson extends AsyncTask<String, String, String> {

        final String TAG = "AsyncTaskParseJson";
        private final String URL = Config.getRestUrl() + "get-beer-list-user.php";

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
                listAdapter.clear();
                // get json string from url
                json = jParser.getJSONFromUrl(URL);

                Log.d(TAG, json.toString());
                // get the array of the beers
                dataJsonArr = json.getJSONArray("beer_kind");
                // loop through all kinds of beers
                Log.d(TAG, "TEST: "+dataJsonArr.toString());
                for (int i = 0; i < dataJsonArr.length(); i++) {
                    JSONObject c = dataJsonArr.getJSONObject(i);
                    Log.d(TAG, c.toString());
                    // Storing each json item in variable
                    String beer_kind = c.getString("beer_kind");
                   // String beer = c.getString("beer_name");
                    String count = c.getString("count");

                    // show the values in our logcat
                    Log.d(TAG, "beer_kind: " + beer_kind + ", count: " + count);
                    // add to the listView
                    listAdapter.add("" + count + " " + beer_kind); // space for Kinds of Beers
                }
                listAdapter.setNotifyOnChange(true);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String strFromDoInBg) {
            Log.d(TAG, "onPostExecute");
            listAdapter.notifyDataSetChanged();
        }
    }

}
