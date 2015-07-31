package com.sebastiangomez.konjugapp;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by USUARIO on 25/07/2015.
 */
public class UserLocalStore {

    public static final String SP_NAME="userDetails";
    SharedPreferences UserLocalDataBase;

    public UserLocalStore(Context context) {

        UserLocalDataBase=context.getSharedPreferences(SP_NAME,0);

    }

    public void storeUserData(User user){

        SharedPreferences.Editor spEditor =  UserLocalDataBase.edit();
        spEditor.putString("name",user.name);
        spEditor.putInt("age",user.age);
        spEditor.putString("username",user.username);
        spEditor.putString("password",user.password);
        spEditor.commit();

    }

    public User getLoggedInUser(){

        String name=UserLocalDataBase.getString("name","");
        String username=UserLocalDataBase.getString("username","");
        String password=UserLocalDataBase.getString("password","");
        int age=UserLocalDataBase.getInt("age",-1);

        User StoredUser = new User(name,age,username,password);

        return StoredUser;
    }

    public void SetUserLoggedIn(boolean LoggedIn){

        SharedPreferences.Editor spEditor = UserLocalDataBase.edit();
        spEditor.putBoolean("LoggedIn",LoggedIn);
        spEditor.commit();
    }

    public void ClearUserData(){

        SharedPreferences.Editor spEditor = UserLocalDataBase.edit();
        spEditor.clear();
        spEditor.commit();
    }

    public boolean getUserLoggedIn(){
        if(UserLocalDataBase.getBoolean("LoggedIn",false)==true){
            return true;
        }else{
            return false;
        }
    }
}
