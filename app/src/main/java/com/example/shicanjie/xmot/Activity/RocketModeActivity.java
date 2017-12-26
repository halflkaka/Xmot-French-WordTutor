package com.example.shicanjie.xmot.Activity;

import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
//import android.support.v7.widget.CardView;

import com.andtinder.model.CardModel;
import com.andtinder.model.Orientations;
import com.andtinder.view.CardContainer;
import com.andtinder.view.SimpleCardStackAdapter;
import com.example.shicanjie.xmot.Class.DBManager;
import com.example.shicanjie.xmot.Class.WordClass;
import com.example.shicanjie.xmot.R;

import java.util.ArrayList;

public class RocketModeActivity extends AppCompatActivity {

    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rocket_mode);
        database = SQLiteDatabase.openOrCreateDatabase(DBManager.DB_PATH + "/" + DBManager.DB_NAME, null);

        CardContainer mCardContainer = (CardContainer) findViewById(R.id.layoutview);
        mCardContainer.setOrientation(Orientations.Orientation.Ordered);
        SimpleCardStackAdapter adapter = new SimpleCardStackAdapter(this);


        ArrayList<WordClass> Words = CreateWordCards();

        for (WordClass word:Words) {
            final CardModel card = new CardModel(word.word, word.meaning, this.getResources().getDrawable(R.drawable.picture3));

            card.setOnClickListener(new CardModel.OnClickListener() {
                @Override
                public void OnClickListener() {
                    Log.i("Swipeable Cards","I am pressing the card");
                }
            });

            adapter.add(card);
        }

        mCardContainer.setAdapter(adapter);



        database.close();

    }
    private ArrayList<WordClass> CreateWordCards(){
        Cursor cur = database.rawQuery("SELECT word,meaning FROM words ORDER BY RANDOM() LIMIT 20",null);

        if(cur != null){
            int Num = cur.getCount();
            ArrayList<WordClass> Words = new ArrayList<WordClass>(Num);
            if(cur.moveToFirst()){
                do{
                    String word = cur.getString(cur.getColumnIndex("word"));
                    String meaning = cur.getString(cur.getColumnIndex("meaning"));
                    WordClass Word = new WordClass(word,meaning);
                    Words.add(Word);
                }while (cur.moveToNext());
            }
            return Words;
        }else {
            return null;
        }
    }
}
