package com.example.pc.lifelineuser;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RelativeLayout;

public class First extends AppCompatActivity{

    Localdatabase localdatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        RelativeLayout myLayout =
                (RelativeLayout)findViewById(R.id.RelativeLayout1);

        myLayout.setOnTouchListener(
                new RelativeLayout.OnTouchListener() {
                    public boolean onTouch(View v,
                                           MotionEvent m) {
                        return true;
                    }
                }
        );
    }

    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent=new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }).create().show();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
            return super.dispatchTouchEvent(ev);
    }

    public void onProceed(View view)
    {
        Intent intent;
        Button b1=(Button)findViewById(R.id.proceed);
        localdatabase=new Localdatabase(this);
        if(localdatabase.getUserLoggedIn()==true) {
            intent = new Intent(this, Emergency.class);
            startActivity(intent);
        }
        else
        {
            intent=new Intent(this,Login.class);
            startActivity(intent);
        }
    }

}
