package de.js.bierflaschensammlung.activity;

import android.os.Bundle;

import de.js.bierflaschensammlung.R;
import de.js.bierflaschensammlung.activity.menu.MenuMainActivity;

public class InfoActivity extends MenuMainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        addToolbar();
    }

}
