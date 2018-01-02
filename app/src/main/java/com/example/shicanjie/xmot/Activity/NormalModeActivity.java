package com.example.shicanjie.xmot.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.shicanjie.xmot.Class.AnswerQuestion;
import com.example.shicanjie.xmot.Class.DBManager;
import com.example.shicanjie.xmot.Class.WordClass;
import com.example.shicanjie.xmot.R;

import java.util.ArrayList;
import java.util.Random;

public class NormalModeActivity extends AppCompatActivity {

    private SQLiteDatabase database;
    private ArrayList<AnswerQuestion> Questions;
    private ArrayList<Integer> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_mode);
        database = SQLiteDatabase.openOrCreateDatabase(DBManager.DB_PATH + "/" + DBManager.DB_NAME, null);

        Questions = Generate_Question();
        display(Questions);

    }

    private ArrayList<AnswerQuestion> Generate_Question(){
        Cursor cur = database.rawQuery("SELECT word,meaning FROM words ORDER BY RANDOM() LIMIT 10",null);
        if(cur != null){
            int Num = cur.getCount();
            ArrayList<AnswerQuestion> Questions = new ArrayList<AnswerQuestion>(Num);
            if(cur.moveToFirst()){
                do{
                    String word = cur.getString(cur.getColumnIndex("word"));
                    String meaning = cur.getString(cur.getColumnIndex("meaning"));
                    AnswerQuestion Question = new AnswerQuestion(word,meaning);
                    Questions.add(Question);
                }while (cur.moveToNext());
            }
            return Questions;
        }else {
            return null;
        }
    }

    private void display(final ArrayList<AnswerQuestion> Questions){
        Button Question = (Button) findViewById(R.id.Question);
        Button Answer1 = (Button) findViewById(R.id.Answer1);
        Button Answer2 = (Button) findViewById(R.id.Answer2);
        Button Answer3 = (Button) findViewById(R.id.Answer3);
        Button Answer4 = (Button) findViewById(R.id.Answer4);

//        Question.setText(Questions.get(0).GetQuestion());
        ArrayList<Integer> randomnums = new ArrayList<Integer>();
        Random rand = new Random();
        int i;
        while(true){
            i = rand.nextInt(10);
            if(!list.contains(i)){
                list.add(i);
                break;
            }
        }
        Question.setText(Questions.get(i).GetQuestion());

        while(true){
            int num = rand.nextInt(10);
            if(!randomnums.contains(num) && num != i){
                randomnums.add(num);
            }
            if(randomnums.size() == 3){break;}
        }

        Random random = new Random();
        int Rand = random.nextInt(3);

        if(Rand == 0){
            Answer1.setText(Questions.get(i).GetAnswer());
            Answer1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("data_return","Correct");
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
            Answer2.setText(Questions.get(randomnums.get(0)).GetAnswer());
            Answer3.setText(Questions.get(randomnums.get(1)).GetAnswer());
            Answer4.setText(Questions.get(randomnums.get(2)).GetAnswer());
        }else if(Rand == 1){
            Answer2.setText(Questions.get(i).GetAnswer());
            Answer2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("data_return","Correct");
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
            Answer1.setText(Questions.get(randomnums.get(0)).GetAnswer());
            Answer3.setText(Questions.get(randomnums.get(1)).GetAnswer());
            Answer4.setText(Questions.get(randomnums.get(2)).GetAnswer());
        }else if(Rand ==2){
            Answer3.setText(Questions.get(i).GetAnswer());
            Answer3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("data_return","Correct");
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
            Answer1.setText(Questions.get(randomnums.get(0)).GetAnswer());
            Answer2.setText(Questions.get(randomnums.get(1)).GetAnswer());
            Answer4.setText(Questions.get(randomnums.get(2)).GetAnswer());
        }else if(Rand == 3){
            Answer4.setText(Questions.get(i).GetAnswer());
            Answer4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("data_return","Correct");
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
            Answer1.setText(Questions.get(randomnums.get(0)).GetAnswer());
            Answer3.setText(Questions.get(randomnums.get(1)).GetAnswer());
            Answer2.setText(Questions.get(randomnums.get(2)).GetAnswer());
        }
    }
}
