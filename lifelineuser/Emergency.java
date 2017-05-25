package com.example.pc.lifelineuser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc.lifelineuser.Login;
import com.example.pc.lifelineuser.Localdatabase;
public class Emergency extends AppCompatActivity {

    public static Localdatabase db;
    public static String str1,name,email,password1,ph1,ph2,nme;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LinearLayout emergency=(LinearLayout) findViewById(R.id.emergency_act);
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        if (ev.getAction() == MotionEvent.ACTION_DOWN ) {
            InputMethodManager input = (InputMethodManager)
                    this.getBaseContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            input.hideSoftInputFromWindow(emergency.getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
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
    public void toMaps(View v){
        RadioButton heart=(RadioButton)findViewById(R.id.traumaCB);
        RadioButton labour=(RadioButton)findViewById(R.id.labourpainCB);
        RadioButton respiratory=(RadioButton)findViewById(R.id.respiratoryCB);
        RadioButton others=(RadioButton)findViewById(R.id.othersCB);
        RadioButton abdominal=(RadioButton)findViewById(R.id.abdominalCB);
        RadioButton mental=(RadioButton)findViewById(R.id.mentalCB);
        String str=null;
        if(heart.isChecked() ==true)
            str="Heart Attack";
        else if(labour.isChecked()==true)
            str="Labour Pain";
        else if(respiratory.isChecked()==true)
            str="Respiratoy problems";
        else if(others.isChecked()==true)
            str="Some disease";
        else if(abdominal.isChecked()==true)
            str="Abdominal Pain";
        else if(mental.isChecked()==true)
            str="Mental illness";
        else
            str="emergency";
        RadioButton self=(RadioButton )findViewById(R.id.selfCB);
        RadioButton other=(RadioButton)findViewById(R.id.otherbookingCB);

        if(self.isChecked() ==true)
        {
            String sms="Hello, Your relative "+name+" called an ambulance for "+str+".You were registered as one of the emergency contacts.";
            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(ph1, null, sms, null, null);
                Toast.makeText(getApplicationContext(), "SMS Sent!",
                        Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),
                        "SMS faild, please try again later!",
                        Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(ph2, null, sms, null, null);
                Toast.makeText(getApplicationContext(), "SMS Sent!",
                        Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),
                        "SMS faild, please try again later!",
                        Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
        if(other.isChecked() ==true){
        }
        Intent intent = new Intent(Emergency.this, MapsActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);
        db=new Localdatabase(this);
        Users user=db.getLoggedInUser();
        ph1=user.emer1;
        ph2=user.emer2;
        name=user.name;
        email=user.email;
        str1=user.phone;
        nme=user.name;
        password1=user.password;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_emergency, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_account: {
                if (item.isChecked())
                    item.setChecked(false);
                else
                    item.setChecked(true);
                Intent intent = new Intent(this, account.class);
                startActivity(intent);

                return true;
            }
            case R.id.menu_changepass: {
                if (item.isChecked())
                    item.setChecked(false);
                else
                    item.setChecked(true);
                Intent intent1 = new Intent(this, changepass.class);
                startActivity(intent1);
                return true;
            }
            case R.id.menu_logout: {
                if (item.isChecked()) {
                    item.setChecked(false);
                }else
                {
                    item.setChecked(true);
                }
                db.clearData();
                db.setUserLoggedIn(false);
                Intent intent2 = new Intent(this, Login.class);
                startActivity(intent2);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
