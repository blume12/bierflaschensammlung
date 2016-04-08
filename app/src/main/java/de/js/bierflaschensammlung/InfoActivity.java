package de.js.bierflaschensammlung;

import android.os.Bundle;

import de.js.bierflaschensammlung.menu.MenuMainActivity;

public class InfoActivity extends MenuMainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        addToolbar();
    }

}
