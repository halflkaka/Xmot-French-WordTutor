package com.example.shicanjie.xmot.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.example.shicanjie.xmot.Class.DBManager;
import com.example.shicanjie.xmot.R;

public class WelcomeActivity extends AppCompatActivity {

    public DBManager dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        final View view = View.inflate(this, R.layout.activity_welcome,null);
        setContentView(view);

        Animation aa = new AlphaAnimation(0.3f,1.0f);
        aa.setDuration(2000);
        view.startAnimation(aa);
        aa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                redirectTo();
            }
        });
    }
    private void redirectTo(){
        dbHelper = new DBManager(this);
        dbHelper.openDatabase();
        dbHelper.closeDatabase();

        Intent intent = new Intent(this, IndexActivity.class);
        startActivity(intent);
        finish();
    }
}
