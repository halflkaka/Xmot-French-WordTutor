package com.example.shicanjie.xmot.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shicanjie.xmot.Class.DBManager;
import com.example.shicanjie.xmot.Class.MyTCPSocket;
import com.example.shicanjie.xmot.R;

public class IndexActivity extends AppCompatActivity {

    private SQLiteDatabase database;
    Button search;
    EditText text;
    TextView band_local;
    TextView band_Internet;
    TextView text_meaning;
    TextView text_InternetMeaning;
    ImageView image_Bonjour;
    private MyTCPSocket socket_helper;

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

    private String searchForMeaning(){
        Log.d("IndexActivity", text.getText().toString());
        Cursor cur = database.rawQuery("SELECT word,meaning FROM words WHERE word Like \'" + text.getText().toString() + "%\'", null);
        if (cur != null) {
            if(cur.moveToFirst()) {
                Log.d("IndexActivity", cur.getString(cur.getColumnIndex("meaning")));
                return cur.getString(cur.getColumnIndex("word")) + ":\n" + cur.getString(cur.getColumnIndex("meaning"));
            }
            return "词库中无此词，请重新输入。";
        }
        else {
            Log.d("IndexActivity", "NO meaning");
            return "No find. Please try again.";
        }
    }

    class Connect_Thread extends Thread implements Runnable//继承Thread
    {
        public void run()
        {
            try {
                socket_helper.getsocket();
                text_InternetMeaning.setText(socket_helper.sendMessage("/demand " + text.getText())+"\n\n\n\n");
            }
            catch (Exception ex){
                Log.d("IndexActivity", "run: " + ex.getMessage());
            }
        }
    }

    private void display(String meaning){
        image_Bonjour.setVisibility(View.GONE);

        text_meaning.setText(meaning);

        IndexActivity.Connect_Thread connect_Thread = new IndexActivity.Connect_Thread();
        connect_Thread.start();

        text_meaning.setVisibility(View.VISIBLE);
        text_InternetMeaning.setVisibility(View.VISIBLE);
        band_local.setVisibility(View.VISIBLE);
        band_Internet.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        database = SQLiteDatabase.openOrCreateDatabase(DBManager.DB_PATH + "/" + DBManager.DB_NAME, null);

        search = (Button) findViewById(R.id.search_button);
        text = findViewById(R.id.search_word);
        text_meaning = findViewById(R.id.meaning);
        image_Bonjour = findViewById(R.id.image_bonjour);
        text_InternetMeaning = findViewById(R.id.InternetMeaning);
        band_Internet = findViewById(R.id.Internet);
        band_local = findViewById(R.id.Local);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        text_meaning.setVisibility(View.GONE);
        text_InternetMeaning.setVisibility(View.GONE);
        band_local.setVisibility(View.GONE);
        band_Internet.setVisibility(View.GONE);

        search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("IndexActivity", text.getText().toString());
                display(searchForMeaning());
            }
        });

    }

}
