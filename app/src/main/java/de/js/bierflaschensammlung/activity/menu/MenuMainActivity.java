package de.js.bierflaschensammlung.activity.menu;

import android.support.v7.app.ActionBar;
import android.view.Menu;

import de.js.bierflaschensammlung.R;

abstract public class MenuMainActivity extends MenuActivity {

    private final static String TAG = "MenuMainActivity";

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
}
