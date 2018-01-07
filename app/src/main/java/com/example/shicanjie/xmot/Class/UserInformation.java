package com.example.shicanjie.xmot.Class;

/**
 * Created by luodihao on 2018/1/6.
 */

public class UserInformation {
    private static String user_name = "";
    private static String password = "";
    public String get_Username(){
        return user_name;
    }
    public String get_Password(){
        return password;
    }
    public boolean getLoginState(){
        if ((!user_name.equals("")) && (!password.equals(""))){
            return true;
        }
        else return false;
    }
    public boolean set_Username(String str){
        if (!user_name.equals("")){
            return false;
        }
        else {
            user_name = str;
            return true;
        }
    }
    public boolean set_Password(String str){
        if (!password.equals("")){
            return false;
        }
        else {
            password = str;
            return true;
        }
    }
    public boolean reset_all(){
        user_name = "";
        password = "";
        return true;
    }
}
