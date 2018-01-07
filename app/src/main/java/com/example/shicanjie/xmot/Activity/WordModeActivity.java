package com.example.shicanjie.xmot.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.example.shicanjie.xmot.R;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class WordModeActivity extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_searchword:
                    Intent intent_index = new Intent(WordModeActivity.this, IndexActivity.class);
                    startActivity(intent_index);
                    finish();
                    return true;
                case R.id.navigation_reviewword:
                    return true;
                case R.id.navigation_user:
                    Intent intent_user = new Intent(WordModeActivity.this, UserActivity.class);
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
        setContentView(R.layout.activity_word_mode);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.getMenu().getItem(1).setChecked(true);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        ImageButton rocketMode = (ImageButton) findViewById(R.id.rocketButton);
        ImageButton normalMode = (ImageButton) findViewById(R.id.normalButton);

        rocketMode.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent_rocket = new Intent(WordModeActivity.this, RocketModeActivity.class);
                startActivity(intent_rocket);
//                finish();
            }
        });

        normalMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_normal = new Intent(WordModeActivity.this, NormalModeActivity.class);
                startActivityForResult(intent_normal, 1);
//                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode){
            case 1:
                if(resultCode == RESULT_OK) {
                    Intent intent_normal = new Intent(WordModeActivity.this, NormalModeActivity.class);
                    startActivityForResult(intent_normal, 1);
                    String returnedData = data.getStringExtra("data_return");
                    Log.d("WordModeActivity", returnedData);
                    break;
                }else if(resultCode == RESULT_CANCELED){
//                    Intent
                    break;
                }
            default:
        }
    }

}
