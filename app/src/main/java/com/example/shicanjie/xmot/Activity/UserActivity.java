package com.example.shicanjie.xmot.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.example.shicanjie.xmot.Class.MyTCPSocket;
import com.example.shicanjie.xmot.Class.UserInformation;
import com.example.shicanjie.xmot.R;


public class UserActivity extends AppCompatActivity {

    Button sign_in;
    Button register;
    AutoCompleteTextView email;
    EditText password;
    public MyTCPSocket socket_helper;
    public UserInformation user_infor;
    boolean login_state = false;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_searchword:
                    Intent intent_index = new Intent(UserActivity.this, IndexActivity.class);
                    startActivity(intent_index);
//                    finish();
                    return true;
                case R.id.navigation_reviewword:
                    Intent intent_wordmode = new Intent(UserActivity.this, WordModeActivity.class);
                    startActivity(intent_wordmode);
//                    finish();
                    return true;
                case R.id.navigation_user:
                    return true;
            }
            return false;
        }
    };


    class MyListener_register implements View.OnClickListener {
        public void onClick(View v){
            try {
                Connect_Thread_register connect_Thread = new Connect_Thread_register();
                connect_Thread.start();
//                TCPclient(email.toString() + "\n" + password.getText());
            } catch (Exception e) {
            }
        }
        class Connect_Thread_register extends Thread implements Runnable//继承Thread
        {
            MyTCPSocket clientSocket;


            public void run()
            {
                try {
                    String modifiedSentence;
                    socket_helper.getsocket();
                    modifiedSentence = socket_helper.sendMessage("/register " + email.getText() + " " + password.getText());
                    Log.d("UserActivity", modifiedSentence);

                    android.support.v7.app.AlertDialog.Builder alertDialogBuilder=new android.support.v7.app.AlertDialog.Builder(UserActivity.this);
                    alertDialogBuilder.setTitle("提醒");
                    alertDialogBuilder.setMessage(modifiedSentence);
                    alertDialogBuilder.setPositiveButton("是", null);
                    Looper.prepare();
                    android.support.v7.app.AlertDialog alert_signin = alertDialogBuilder.create();
                    alert_signin.show();
                    Looper.loop();


//                        AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(UserActivity.this);
//                        Log.d("UserActivity", "7");
//                        builder.setTitle("提醒");
//                        builder.setMessage(modifiedSentence);
//                        builder.setPositiveButton("是", null);
//                        builder.show();
//                        Toast.makeText(UserActivity.this, "提醒", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    class MyListener_signin implements View.OnClickListener {
        public void onClick(View v){
            try {
                Connect_Thread_signin connect_Thread = new Connect_Thread_signin();
                connect_Thread.start();

                Log.d("UserActivity", "onClick: " + login_state);
                if (login_state){
                    Log.d("UserActivity", "onClick: " + email.getText() + " " + password.getText().toString());
//                        user_infor.set_Username(email.getText().toString());
//                        user_infor.set_Password(password.getText().toString());
                    Log.d("UserActivity", "run: loading to information activity...");
                    Intent intent_index = new Intent(UserActivity.this, UserInforActivity.class);
                    startActivity(intent_index);
                    finish();
                }

//                TCPclient(email.toString() + "\n" + password.getText());
            } catch (Exception e) {
            }
        }
        class Connect_Thread_signin extends Thread implements Runnable//继承Thread
        {

            public void run()
            {
                try {
                    String modifiedSentence;
                    socket_helper.getsocket();
                    modifiedSentence = socket_helper.sendMessage("/signin " + email.getText() + " " + password.getText());
                    Log.d("UserActivity", modifiedSentence);

                    if (modifiedSentence.equals("Sign in succeed.")) login_state = true;

                    android.support.v7.app.AlertDialog.Builder alertDialogBuilder=new android.support.v7.app.AlertDialog.Builder(UserActivity.this);
                    alertDialogBuilder.setTitle("提醒");
                    alertDialogBuilder.setMessage(modifiedSentence);
                    alertDialogBuilder.setPositiveButton("是", null);
                    Looper.prepare();
                    android.support.v7.app.AlertDialog alert_signin = alertDialogBuilder.create();
                    alert_signin.show();
                    Looper.loop();






//                        AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(UserActivity.this);
//                        Log.d("UserActivity", "7");
//                        builder.setTitle("提醒");
//                        builder.setMessage(modifiedSentence);
//                        builder.setPositiveButton("是", null);
//                        builder.show();
//                        Toast.makeText(UserActivity.this, "提醒", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        sign_in = findViewById(R.id.sign_in_button);
        register = findViewById(R.id.register_button);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        navigation.getMenu().getItem(2).setChecked(true);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        sign_in.setOnClickListener(new MyListener_signin());
        register.setOnClickListener(new MyListener_register());


    }

}

