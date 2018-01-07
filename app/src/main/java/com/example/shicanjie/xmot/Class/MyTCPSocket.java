package com.example.shicanjie.xmot.Class;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by luodihao on 2018/1/6.
 */

public class MyTCPSocket extends Socket {
    private static final String host = "192.168.1.100";
    private static final int port = 6789;
    private static BufferedReader br = null;
    private static PrintWriter pw = null;
    /* 持有私有静态实例，防止被引用，此处赋值为null，目的是实现延迟加载 */
    private static MyTCPSocket socket = null;
    private static DataOutputStream outToServer = null;
    private static BufferedReader inFromServer = null;

    private MyTCPSocket(String host, int port) throws UnknownHostException, IOException {
        super(host, port);
    }

    public static MyTCPSocket getsocket() throws IOException {
        if(socket==null){
            socket= new MyTCPSocket(host,port);
        }
        return socket;
    }

    public static String sendMessage(String command) throws IOException{
        // create output stream attached to socket
        if (outToServer == null) outToServer = new DataOutputStream(socket.getOutputStream());

        // create input stream attached to socket
        if (inFromServer == null) inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));

        // send line to server
        Log.d("MyTCPSocket", "sendMessage: " + command);
        outToServer.write((command + '\n').getBytes("utf-8"));

        // read line from server
        String response = inFromServer.readLine();
        Log.d("MyTCPSocket", "sendMessage: " + response);
        return response;
    }

    public static String returnMessage() throws IOException{
        if(inFromServer == null) inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));

        String response = inFromServer.readLine();
        Log.d("MyTCPSocket", "response: " + response);
        return response;
    }
    public static BufferedReader getbr() throws IOException {
        if(br==null){
            br=new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
        }
        return br;
    }

    public static PrintWriter getpw() throws IOException {
        if(pw==null){
            pw=new PrintWriter(
                    new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
        }
        return pw;
    }

}
