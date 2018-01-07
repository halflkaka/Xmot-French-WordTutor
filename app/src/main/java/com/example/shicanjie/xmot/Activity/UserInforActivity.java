package com.example.shicanjie.xmot.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.shicanjie.xmot.Class.MyTCPSocket;
import com.example.shicanjie.xmot.Class.UserInformation;
import com.example.shicanjie.xmot.R;

public class UserInforActivity extends AppCompatActivity {

    Button btn_logout;
    UserInformation user_infor = new UserInformation();
    TextView user_name;
    TextView notebook;
    MyTCPSocket socket_helper;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_searchword:
                    Intent intent_index = new Intent(UserInforActivity.this, IndexActivity.class);
                    startActivity(intent_index);
                    finish();
                    return true;
                case R.id.navigation_reviewword:
                    Intent intent_wordmode = new Intent(UserInforActivity.this, WordModeActivity.class);
                    startActivity(intent_wordmode);
                    finish();
                    return true;
                case R.id.navigation_user:
                    return true;
            }
            return false;
        }
    };

    class Connect_Thread extends Thread implements Runnable//继承Thread
    {
        public void run()
        {
            try {
                Log.d("UserInforActivity", "/require " +"#" + user_infor.get_Username());
                socket_helper.getsocket();
                String words = socket_helper.sendMessage("/require " + "#" + user_infor.get_Username());
//                String count = socket_helper.returnMessage();
                Log.d("UserInforActivity", "count: "+words);

//                Integer Count = Integer.valueOf(count).intValue();
//                for(Integer i = 0;i < Count;i ++){
//                    String words = socket_helper.returnMessage();
//                    Log.d("UserInforActivity", "words: "+words);
//                    notebook.setText(notebook.getText() + "\n" + words);
////                    notebook.setText(words);
//                }
//                String result = socket_helper.returnMessage();
////                notebook.setText(count);
//                Log.d("UserInforActivity", "result: "+result);
                String ss[];
                ss = words.split("#");
                for (int i = 0; i < ss.length; i++){
                    notebook.setText(notebook.getText()+"\n"+ss[i]);
                }
            }
            catch (Exception ex){
                Log.d("UserInforActivity", "run: " + ex.getMessage());
            }
        }
    }

    class Logout_Thread extends Thread implements Runnable//继承Thread
    {
        public void run()
        {
            try {
                Log.d("UserInforActivity", "/require " +"#" + user_infor.get_Username());
                socket_helper.getsocket();
                String words = socket_helper.sendMessage("/quit");
                Log.d("UserInforActivity", words);
            }
            catch (Exception ex){
                Log.d("UserInforActivity", "run: " + ex.getMessage());
            }
        }
    }

    private void requireNotes(){
        UserInforActivity.Connect_Thread connect_thread = new UserInforActivity.Connect_Thread();
        connect_thread.start();
    }

    private void logout(){
        user_infor.reset_all();
        UserInforActivity.Logout_Thread connect_thread = new UserInforActivity.Logout_Thread();
        connect_thread.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_infor);

        btn_logout = findViewById(R.id.logout);
        user_name = findViewById(R.id.username);

        user_name.setText(user_infor.get_Username());

        notebook = (TextView)findViewById(R.id.text_collection);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
                Intent intent_index = new Intent(UserInforActivity.this, UserActivity.class);
                startActivity(intent_index);
                finish();
            }
        });

        requireNotes();


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.getMenu().getItem(2).setChecked(true);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);



    }
}
