package com.example.shicanjie.xmot.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.example.shicanjie.xmot.R;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;


public class UserActivity extends AppCompatActivity {

    Button sign_in;
    AutoCompleteTextView email;
    EditText password;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_searchword:
                    Intent intent_index = new Intent(UserActivity.this, IndexActivity.class);
                    startActivity(intent_index);
                    finish();
                    return true;
                case R.id.navigation_reviewword:
                    Intent intent_wordmode = new Intent(UserActivity.this, WordModeActivity.class);
                    startActivity(intent_wordmode);
                    finish();
                    return true;
                case R.id.navigation_user:
                    return true;
            }
            return false;
        }
    };

    public void TCPclient(String message_to_server) {
        try {

            Log.d("UserActivity", "TCPclient");
            String modifiedSentence;

            // create input stream
//        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

            // create client socket, connect to server
            String hostname = "192.168.1.102";
            Socket clientSocket = new Socket(hostname, 67897);

            Log.d("UserActivity", "Socket creates.");

            // create output stream attached to socket
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            Log.d("UserActivity", "2");

            // create input stream attached to socket
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            Log.d("UserActivity", "3");

            // send line to server
            outToServer.writeBytes(message_to_server + '\n');
            Log.d("UserActivity", "4");

            // read line from server
            modifiedSentence = inFromServer.readLine();
            Log.d("UserActivity", "5");

            clientSocket.close();
            Log.d("UserActivity", "6");

            AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(UserActivity.this);
            Log.d("UserActivity", "7");
            builder.setTitle("提醒");
            builder.setMessage(modifiedSentence);
            builder.setPositiveButton("是", null);
        }
        catch (Exception e){
            Log.d("UserActivity", "Exception:" + e.getCause().getClass() + "," + e.getCause().getMessage());
        }

    }

    class MyListener implements View.OnClickListener {
        public void onClick(View v){
            Log.d("UserActivity", "onClick: ");
            try {
                Log.d("UserActivity", "onClick2: ");
                TCPclient(email.toString() + "\n" + password.getText());
            } catch (Exception e) {
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        sign_in = findViewById(R.id.sign_in_button);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        navigation.getMenu().getItem(2).setChecked(true);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        sign_in.setOnClickListener(new MyListener());


    }

}

