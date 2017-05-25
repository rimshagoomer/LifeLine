package com.example.user.driver;

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
    public void storeData(Drivers users)
    {
        SharedPreferences.Editor spEditor=localDatabase.edit();
        spEditor.putString("Name",users.name);
        spEditor.putString("PhoneNo",users.phone);
        spEditor.putString("Email",users.email);
        spEditor.putString("Password",users.password);
        spEditor.putString("Ambulance",users.ambulance);
        spEditor.putString("Driving",users.driving);
        spEditor.putString("Hospital",users.hospital);
        spEditor.putString("RegistrationID",users.regid);
        spEditor.commit();
    }
    public Drivers getLoggedInUser()
    {
        String name=localDatabase.getString("Name","");
        String phone=localDatabase.getString("PhoneNo","");
        String email=localDatabase.getString("Email","");
        String password=localDatabase.getString("Password","");
        String ambul=localDatabase.getString("Ambulance","");
        String driv=localDatabase.getString("Driving","");
        String hosp=localDatabase.getString("Hospital","");
        String regid=localDatabase.getString("RegistrationID","");
        Drivers storedUser=new Drivers(name,phone,email,password,ambul,driv,hosp,regid);
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
