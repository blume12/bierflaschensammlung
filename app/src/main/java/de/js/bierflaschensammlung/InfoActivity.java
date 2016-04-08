package de.js.bierflaschensammlung;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;


public class InfoActivity extends MenuMainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        addToolbar();
    }



}
