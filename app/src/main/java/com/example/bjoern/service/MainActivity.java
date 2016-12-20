package com.example.bjoern.service;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import fllog.Log;

public class MainActivity extends AppCompatActivity {

    private TodoFragment todoFrag;
    private TwoPaneFragment twoPaneFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.init(true, true);
        todoFrag = new TodoFragment();
        twoPaneFrag = TwoPaneFragment.newInstance("", "");

        getFragmentManager().beginTransaction()
                .replace(R.id.main_fragment_container, twoPaneFrag)
                .add(R.id.todo_fragment_container, todoFrag)
                .add(R.id.log_fragment_container, Log.getFragment())
                .commit();
        getFragmentManager().executePendingTransactions();
    }
}