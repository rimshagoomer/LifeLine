package com.example.pc.lifelineuser;

import android.widget.EditText;

/**
 * Created by pc on 10/13/2015.
 */
public class Users {
    String name,phone,email,password,emer1,emer2;
    public Users(String name,String phone,String email,String password,String emerg1,String emerg2)
    {
        this.name=name;
        this.phone=phone;
        this.email=email;
        this.password=password;
        this.emer1=emerg1;
        this.emer2=emerg2;

    }
    public Users(String phone,String password)
    {
        this.phone=phone;
        this.password=password;
    }
}


