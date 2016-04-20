package de.js.bierflaschensammlung.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import de.js.bierflaschensammlung.R;
import de.js.bierflaschensammlung.config.Config;
import de.js.bierflaschensammlung.json.JsonParser;
import de.js.bierflaschensammlung.activity.menu.MenuMainActivity;

public class SearchActivity extends MenuMainActivity {

    private ArrayAdapter<String> listAdapter;
    private ListView resultList;
    private TextView noResultView;
    private static String TAG = "SearchActivity";

    private String searchValue = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        addToolbar();

        Button sendSearch = (Button) findViewById(R.id.search_button);

        // load the listview for the beers
        resultList = (ListView) findViewById(R.id.result_list);
        noResultView = (TextView) findViewById(R.id.text_no_result);

        // add a adapter for the List
        listAdapter = new ArrayAdapter<String>(this, R.layout.row, new ArrayList<String>());

        resultList.setAdapter(listAdapter);

        sendSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //load the data from Rest-Api
                EditText searchRequest = (EditText) findViewById(R.id.searchText);
                searchValue = searchRequest.getText().toString();
                Log.d(TAG, "SearchValue: " + searchValue);
                new AsyncTaskParseJson().execute();
            }
        });
    }

    private class AsyncTaskParseJson extends AsyncTask<String, String, String> {

        final String TAG = "AsyncTaskParseJson";
        private final String URL = Config.getRestUrl() + "get-beer-list.php?searchValue=" + searchValue;

        JSONArray beersOwnJsonArr = null;
        JSONArray beersOtherJsonArr = null;
        JSONObject json = null;

        @Override
        protected String doInBackground(String... arg0) {
            try {
                // instantiate our json parser
                JsonParser jParser = new JsonParser();
                // get json string from url
                json = jParser.getJSONFromUrl(URL);
                if (json != null) {
                    Log.d(TAG, json.toString());
                    // get the array of the beers
                    beersOwnJsonArr = json.getJSONArray("beers_own");
                    beersOtherJsonArr = json.getJSONArray("beers_other");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String strFromDoInBg) {
            Log.d(TAG, "onPostExecute");

            if (beersOwnJsonArr != null) {
                listAdapter.clear();
                if (beersOwnJsonArr.length() <= 0) {
                    resultList.setVisibility(View.INVISIBLE);
                    noResultView.setVisibility(View.VISIBLE);
                } else {
                    resultList.setVisibility(View.VISIBLE);
                    noResultView.setVisibility(View.INVISIBLE);
                }

                addItemsToList(beersOwnJsonArr, R.string.text_own_result);
                addItemsToList(beersOtherJsonArr, R.string.text_other_result);
            }
        }

        private void addItemsToList(JSONArray dataJsonArray, int textReference) {
            try {
                if (dataJsonArray != null) {
                    // loop through all kinds of beers
                    Log.d(TAG, "Data: " + dataJsonArray.toString());
                    listAdapter.add(getString(textReference));


                    for (int i = 0; i < dataJsonArray.length(); i++) {
                        JSONObject c = dataJsonArray.getJSONObject(i);
                        Log.d(TAG, c.toString());
                        // Storing each json item in variable
                        String beer = c.getString("beer_name");

                        // show the values in our logcat
                        Log.d(TAG, "beer_name: " + beer);
                        // add to the listView
                        listAdapter.add(beer); // space for Kinds of Beers
                    }
                    listAdapter.setNotifyOnChange(true);
                    listAdapter.notifyDataSetChanged();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
