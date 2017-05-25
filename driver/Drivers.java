package com.example.user.driver;

/**
 * Created by pc on 10/28/2015.
 */
public class Drivers
{
    String name,phone,email,password,ambulance,driving,hospital,regid;
    public Drivers(String name,String phone,String email,String password,String ambulance,String driving,String hospital,String regid)
    {
        this.name=name;
        this.phone=phone;
        this.email=email;
        this.password=password;
        this.ambulance=ambulance;
        this.driving=driving;
        this.hospital=hospital;
        this.regid=regid;

    }
    public Drivers(String phone,String password)
    {
        this.phone=phone;
        this.password=password;
    }
}
