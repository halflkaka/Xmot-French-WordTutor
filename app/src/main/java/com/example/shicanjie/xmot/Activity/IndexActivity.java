package com.example.shicanjie.xmot.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.shicanjie.xmot.Class.DBManager;
import com.example.shicanjie.xmot.R;

public class IndexActivity extends AppCompatActivity {

    public DBManager dbHelper;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_searchword:
                    return true;
                case R.id.navigation_reviewword:
                    Intent intent_wordmode = new Intent(IndexActivity.this, WordModeActivity.class);
                    startActivity(intent_wordmode);
                    finish();
                    return true;
                case R.id.navigation_user:
                    Intent intent_user = new Intent(IndexActivity.this, UserActivity.class);
                    startActivity(intent_user);
                    finish();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        dbHelper = new DBManager(this);
        dbHelper.openDatabase();
        dbHelper.closeDatabase();
    }

}
