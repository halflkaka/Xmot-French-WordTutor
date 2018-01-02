package com.example.shicanjie.xmot.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.shicanjie.xmot.Class.DBManager;
import com.example.shicanjie.xmot.R;

public class IndexActivity extends AppCompatActivity {

    public DBManager dbHelper;
    private SQLiteDatabase database;
    Button search;
    EditText text;
    TextView text_meaning;

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
            return "No find. Please try again.";
        }
        else {
            Log.d("IndexActivity", "NO meaning");
            return "No find. Please try again.";
        }
    }

    private void display(String meaning){
        text_meaning.setText(meaning);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        database = SQLiteDatabase.openOrCreateDatabase(DBManager.DB_PATH + "/" + DBManager.DB_NAME, null);

        search = (Button) findViewById(R.id.search_button);
        text = findViewById(R.id.search_word);
        text_meaning = findViewById(R.id.meaning);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("IndexActivity", text.getText().toString());
                display(searchForMeaning());
            }
        });

    }

}
