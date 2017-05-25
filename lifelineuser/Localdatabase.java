package com.example.pc.lifelineuser;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by pc on 10/13/2015.
 */
public class Localdatabase {
    public static final String SP_NAME="Userdetails";
    SharedPreferences localDatabase;
    public Localdatabase(Context context)
    {
        localDatabase=context.getSharedPreferences(SP_NAME,0);
    }
    public void storeData(Users users)
    {
        SharedPreferences.Editor spEditor=localDatabase.edit();
        spEditor.putString("Name",users.name);
        spEditor.putString("PhoneNo",users.phone);
        spEditor.putString("Email",users.email);
        spEditor.putString("Password",users.password);
        spEditor.putString("Emergency1",users.emer1);
        spEditor.putString("Emergency2",users.emer2);
        spEditor.commit();
    }
    public Users getLoggedInUser()
    {
        String name=localDatabase.getString("Name","");
        String phone=localDatabase.getString("PhoneNo","");
        String email=localDatabase.getString("Email","");
        String password=localDatabase.getString("Password","");
        String emer1=localDatabase.getString("Emergency1","");
        String emer2=localDatabase.getString("Emergency2","");
        Users storedUser=new Users(name,phone,email,password,emer1,emer2);
        return storedUser;
    }
    public void setUserLoggedIn(boolean  loggedIn)
    {
        SharedPreferences.Editor spEditor=localDatabase.edit();
        spEditor.putBoolean("loggedIn",loggedIn);
        spEditor.commit();
    }
    public boolean getUserLoggedIn()
    {
        if(localDatabase.getBoolean("loggedIn",false))
            return true;
        else return false;
    }
    public void clearData()
    {
        SharedPreferences.Editor spEditor=localDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }
}
