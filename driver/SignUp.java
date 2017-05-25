package com.example.user.driver;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;

import static com.example.user.driver.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static com.example.user.driver.CommonUtilities.SENDER_ID;
import static com.example.user.driver.CommonUtilities.SERVER_URL;

public class SignUp extends Activity {
    public String finalOtp=null;
    public static String names,emails,regId,name1,email1,phone1,password1,cpassword1,emer1,emer2,hosp11;
    // Asyntask
    AsyncTask<Void, Void, Void> mRegisterTask;

    EditText name,phone,email,password,cpassword,econtact,econtact2,hosp1;
    AlertDialogManager alert = new AlertDialogManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        name=(EditText)findViewById(R.id.nameT);
        phone=(EditText)findViewById(R.id.numberT);
        email=(EditText)findViewById(R.id.emailT);
        password=(EditText)findViewById(R.id.passT);
        cpassword=(EditText)findViewById(R.id.cpassT);
        econtact=(EditText)findViewById(R.id.econtact1T);
        econtact2=(EditText)findViewById(R.id.econtact2T);
        hosp1=(EditText)findViewById(R.id.hosp1T);
        ImageView mImageView;
        mImageView = (ImageView)findViewById(R.id.imageView2);
        mImageView.setImageResource(R.drawable.lifelineplus2);

    }



    public void onOtpClick(View view) {
        if (SERVER_URL == null || SENDER_ID == null || SERVER_URL.length() == 0
                || SENDER_ID.length() == 0) {
            // GCM sernder id / server url is missing
            alert.showAlertDialog(SignUp.this, "Configuration Error!",
                    "Please set your Server URL and GCM Sender ID", false);
            // stop executing code by return
            return;
        }





        Log.i("Aakriti", "rimi");
        name1=name.getText().toString();
        phone1=phone.getText().toString();
        //Log.i("Aakriti",phone1);
         email1=email.getText().toString();
        password1=password.getText().toString();
        cpassword1=cpassword.getText().toString();
        emer1=econtact.getText().toString();
        emer2=econtact2.getText().toString();
        hosp11=hosp1.getText().toString();

//        double d,e1,e2,n;
//        try{
//            d=Double.parseDouble(phone1);}
//        catch(NumberFormatException e)
//        {d=0;}
//        try{
//            e1=Double.parseDouble(emer1);}
//        catch(NumberFormatException e)
//        {e1=0;}
//        try{
//            e2=Double.parseDouble(emer2);}
//        catch(NumberFormatException e)
//        {e2=0;}
//        try{
//            n=Double.parseDouble(name1);}
//        catch(NumberFormatException e)
//        {n=0;}
//
//        if (name1.equals("")||n!=0){
//            name.setText("");
//            Toast.makeText(this, "Name incorrect", Toast.LENGTH_LONG).show();
//        }
//        else if(phone1.length()!=10 || emer1.length()!=10 || emer2.length()!=10)
//        {
//            phone.setText("");
//            econtact.setText("");
//            econtact2.setText("");
//            Toast.makeText(this, "Length incorrect", Toast.LENGTH_LONG).show();
//
//        }
//        else if(password1.length()<8)
//        {
//            password.setText("");
//            cpassword.setText("");
//            Toast.makeText(this, "Password should be of minimum 8 characters", Toast.LENGTH_LONG).show();
//        }
//        else if(d==0)
//        {
//            phone.setText("");
//            Toast.makeText(this, "Invalid number", Toast.LENGTH_LONG).show();
//        }
//        else if(e1==0)
//        {
//            econtact.setText("");
//            Toast.makeText(this, "Invalid emergency number", Toast.LENGTH_LONG).show();
//        }
//        else if(e2==0)
//        {
//            econtact2.setText("");
//            Toast.makeText(this, "Invalid emergency number", Toast.LENGTH_LONG).show();
//        }
//        else {
//            Drivers user = null;
//            Log.i("Aakriti", "rimi2");
//             if (!password1.equals(cpassword1)) {
//                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(this, SignUp.class);
//                startActivity(intent);
//            }
//            else
//             {
//                 // Check if user filled the form
//                 if (name1.length() > 0 && email1.length() > 0) {
//                     // Launch Main Activity
//
//                 } else {
//                     // user doen't filled that data
//                     // ask him to fill the form
//                     alert.showAlertDialog(SignUp.this, "Registration Error!", "Please enter your details", false);
//                 }
                 Log.i("Aakriti","---------------Rimi Reached here--------------");
                 GCMRegistrar.checkDevice(this);

                 Log.i("Aakriti", "---------------Device Checked--------------");
                 // Make sure the manifest was properly set - comment out this line
                 // while developing the app, then uncomment it when it's ready.
                 GCMRegistrar.checkManifest(this);
                 //GCMRegistrar.unregister(this);
                 Log.i("Aakriti", "---------------Manifest Checked--------------");
                 //lblMessage = (TextView) findViewById(R.id.lblMessage);

                 registerReceiver(mHandleMessageReceiver, new IntentFilter(
                         CommonUtilities.DISPLAY_MESSAGE_ACTION));

                 Log.i("Aakriti", "--------------register receiver--------------");
                 // Get GCM registration id
                 regId = GCMRegistrar.getRegistrationId(this);

                 Log.i("Aakriti","--------------regID--------------");
                 // Check if regid already presents
                 if (regId.equals("")) {
                     // Registration is not present, register now with GCM

                     Log.i("Aakriti","---------------RegID is null--------------");
                     GCMRegistrar.register(this, CommonUtilities.SENDER_ID);

                     Log.i("Aakriti", "---------------RegID is null--------------");
                 } else {

                     Log.i("Aakriti",regId);
                     // Device is already registered on GCM
                     if (GCMRegistrar.isRegisteredOnServer(this)) {
                         // Skips registration.
                         Toast.makeText(getApplicationContext(), "Already registered with GCM", Toast.LENGTH_LONG).show();
                         Log.i("Aakriti","ufffff2");
                     } else {
                         // Try to register again, but not in the UI thread.
                         // It's also necessary to cancel the thread onDestroy(),
                         // hence the use of AsyncTask instead of a raw thread.
                         final Context context = this;
                         Log.i("Aakriti","here rimi");
                         mRegisterTask = new AsyncTask<Void, Void, Void>() {

                             @Override
                             protected Void doInBackground(Void... params) {
                                 // Register on our server
                                 // On server creates a new user
                                 ServerUtilities.register(context, name1,phone1,emer1,emer2, email1,password1,hosp11, regId);
                                 return null;
                             }

                             @Override
                             protected void onPostExecute(Void result) {
                                 mRegisterTask = null;
                             }

                         };
                         mRegisterTask.execute(null, null, null);

                     }
                 }

             //}
       // }
    }

    private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String newMessage = intent.getExtras().getString(CommonUtilities.EXTRA_MESSAGE);
            // Waking up mobile if it is sleeping

            Log.i("Aakriti","---------------in Broadcast Receiver--------------");
            Log.i("Aakriti",newMessage);
            WakeLocker.acquire(getApplicationContext());

            /**
             * Take appropriate action on this message
             * depending upon your app requirement
             * For now i am just displaying it on the screen
             * */

            // Showing received message
            //lblMessage.append(newMessage + "\n");
            Toast.makeText(getApplicationContext(), "New Message: " + newMessage, Toast.LENGTH_LONG).show();

            // Releasing wake lock
            WakeLocker.release();
        }
    };

    @Override
    protected void onDestroy() {
        if (mRegisterTask != null) {
            mRegisterTask.cancel(true);
        }
        try {
            unregisterReceiver(mHandleMessageReceiver);
            GCMRegistrar.onDestroy(this);
        } catch (Exception e) {
            // Log.e("UnRegister Receiver Error", "> " + e.getMessage());
        }
        super.onDestroy();
    }

    public void onLoginClick(View view)
    {
        Intent intent=new Intent(this, com.example.user.driver.Login_new.class);
        startActivity(intent);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        if (ev.getAction() == MotionEvent.ACTION_DOWN &&
                !getLocationOnScreen(name).contains(x, y) &&
                !getLocationOnScreen(phone).contains(x, y) &&
                !getLocationOnScreen(email).contains(x, y)&&
                !getLocationOnScreen(password).contains(x, y) &&
                !getLocationOnScreen(cpassword).contains(x, y) &&
                !getLocationOnScreen(econtact).contains(x, y) &&
                !getLocationOnScreen(econtact2).contains(x, y) &&
                !getLocationOnScreen(hosp1).contains(x, y)) {
            InputMethodManager input = (InputMethodManager)
                    this.getBaseContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            input.hideSoftInputFromWindow(name.getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    protected Rect getLocationOnScreen(EditText mEditText) {
        Rect mRect = new Rect();
        int[] location = new int[2];
        mEditText.getLocationOnScreen(location);
        mRect.left = location[0];
        mRect.top = location[1];
        mRect.right = location[0] + mEditText.getWidth();
        mRect.bottom = location[1] + mEditText.getHeight();
        return mRect;
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



}
