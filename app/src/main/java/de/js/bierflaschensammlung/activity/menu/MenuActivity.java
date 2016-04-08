package de.js.bierflaschensammlung.activity.menu;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import de.js.bierflaschensammlung.activity.ContactActivity;
import de.js.bierflaschensammlung.activity.InfoActivity;
import de.js.bierflaschensammlung.activity.NewBeerActivity;
import de.js.bierflaschensammlung.R;
import de.js.bierflaschensammlung.activity.SearchActivity;

abstract public class MenuActivity extends AppCompatActivity {

    private final static String TAG = "MenuActivity";

    protected void addToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        Log.d(TAG, "id: " + item.getIcon());
        switch (id) {
            case R.id.action_beer_new:
                startActivity(new Intent(this, NewBeerActivity.class));
                return true;

            case android.R.id.home:
                Log.d(TAG, "Back button ");
                NavUtils.navigateUpFromSameTask(this);
                return true;

            case R.id.action_info:
                startActivity(new Intent(this, InfoActivity.class));
                return true;

            case R.id.action_search:
                startActivity(new Intent(this, SearchActivity.class));
                return true;

            case R.id.action_contact:
                startActivity(new Intent(this, ContactActivity.class));
                return true;

        }

        return super.onOptionsItemSelected(item);
    }
}
