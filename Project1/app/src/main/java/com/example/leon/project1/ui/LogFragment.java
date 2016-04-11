package com.example.leon.project1.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class LogFragment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.leon.project1.R.layout.activity_log_fragment);
        Toolbar toolbar = (Toolbar) findViewById(com.example.leon.project1.R.id.toolbar);
        setSupportActionBar(toolbar);
        //create animated buttons
        FloatingActionButton fab = (FloatingActionButton) findViewById(com.example.leon.project1.R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
