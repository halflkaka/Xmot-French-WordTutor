package com.example.shicanjie.xmot.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.shicanjie.xmot.R;

public class UserInforActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_searchword:
                    Intent intent_index = new Intent(UserInforActivity.this, IndexActivity.class);
                    startActivity(intent_index);
//                    finish();
                    return true;
                case R.id.navigation_reviewword:
                    Intent intent_wordmode = new Intent(UserInforActivity.this, WordModeActivity.class);
                    startActivity(intent_wordmode);
//                    finish();
                    return true;
                case R.id.navigation_user:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_infor);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.getMenu().getItem(2).setChecked(true);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);



    }
}
