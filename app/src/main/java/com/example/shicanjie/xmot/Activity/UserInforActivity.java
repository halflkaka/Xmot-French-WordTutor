package com.example.shicanjie.xmot.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.shicanjie.xmot.Class.UserInformation;
import com.example.shicanjie.xmot.R;

public class UserInforActivity extends AppCompatActivity {

    Button btn_logout;
    UserInformation user_infor = new UserInformation();
    TextView user_name;

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

        btn_logout = findViewById(R.id.logout);
        user_name = findViewById(R.id.username);

        user_name.setText(user_infor.get_Username());

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_infor.reset_all();
                Intent intent_index = new Intent(UserInforActivity.this, UserActivity.class);
                startActivity(intent_index);
                finish();
            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.getMenu().getItem(2).setChecked(true);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);



    }
}
