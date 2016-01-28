package de.js.bierflaschensammlung;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONException;
import org.json.JSONObject;

import de.js.bierflaschensammlung.json.JsonTransmitter;

public class NewBeerActivity extends AppCompatActivity {

    private static final String TAG = "NewBeerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_beer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_save, menu);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        return true;
    }

    private void saveBeer() {
        EditText beerName = (EditText) findViewById(R.id.editTextBeerName);
        EditText beerCompany = (EditText) findViewById(R.id.editTextBeerCompany);
        Spinner beerCountry = (Spinner) findViewById(R.id.editTextBeerCountry);
        EditText promille = (EditText) findViewById(R.id.editTextPromille);
        Spinner bottleColor = (Spinner) findViewById(R.id.editViewBottleColor);
        EditText bottleSize = (EditText) findViewById(R.id.editViewBottleSize);

        try {
            JSONObject toSend = new JSONObject();
            toSend.put("beerName", beerName.getText().toString());
            toSend.put("beerCompany", beerCompany.getText().toString());
            toSend.put("beerCountry", "1");
            toSend.put("beerKind", "1");
            toSend.put("promille", promille.getText().toString());
            toSend.put("bottleColor", "" + bottleColor.getSelectedItemId());
            toSend.put("bottleSize", bottleSize.getText().toString());
            Log.d(TAG, "save" + toSend.toString());

            JsonTransmitter transmitter = new JsonTransmitter();
            transmitter.execute(new JSONObject[]{toSend});

        } catch (JSONException e) {
            e.printStackTrace();
        }

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

            case R.id.action_save:
                saveBeer();
                return true;

            case R.id.home:
                //TODO: Back-Button
                startActivity(new Intent(this, MainActivity.class));
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

}
