package com.example.pc.lifelineuser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.pc.lifelineuser.ServerRequests;

public class SignUp extends Activity {
    public String finalOtp=null;
EditText name,phone,email,password,cpassword,econtact,econtact2,otp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        name=(EditText)findViewById(R.id.nameT);
        phone=(EditText)findViewById(R.id.numberT);
       email=(EditText)findViewById(R.id.emailT);
        password=(EditText)findViewById(R.id.passT);
       cpassword=(EditText)findViewById(R.id.cpassT);
        econtact=(EditText)findViewById(R.id.econtact1T);
         econtact2=(EditText)findViewById(R.id.econtact2T);
        otp=(EditText)findViewById(R.id.otp);
        ImageView mImageView;
        mImageView = (ImageView)findViewById(R.id.imageView2);
        mImageView.setImageResource(R.drawable.lifelineplus2);
    }


    public void onBackPressed(View v){
        Intent intent=new Intent(this,Login.class);
        startActivity(intent);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        name=(EditText)findViewById(R.id.nameT);
        phone=(EditText)findViewById(R.id.numberT);
        email=(EditText)findViewById(R.id.emailT);
        password=(EditText)findViewById(R.id.passT);
        cpassword=(EditText)findViewById(R.id.cpassT);
        econtact=(EditText)findViewById(R.id.econtact1T);
        econtact2=(EditText)findViewById(R.id.econtact2T);
        otp=(EditText)findViewById(R.id.otp);
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
                !getLocationOnScreen(otp).contains(x, y)) {
            InputMethodManager input = (InputMethodManager)
                    this.getBaseContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            input.hideSoftInputFromWindow(otp.getWindowToken(), 0);
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

    public void onSignupClick(View view)
    {
        String name1=name.getText().toString();
        String phone1=phone.getText().toString();
        String email1=email.getText().toString();
        String password1=password.getText().toString();
        String cpassword1=cpassword.getText().toString();
        String emer1=econtact.getText().toString();
        String emer2=econtact2.getText().toString();
        String otp1= otp.getText().toString();
        double d,e1,e2,n;
        try{
            d=Double.parseDouble(phone1);}
        catch(NumberFormatException e)
            {d=0;}
        try{
            e1=Double.parseDouble(emer1);}
        catch(NumberFormatException e)
        {e1=0;}
        try{
            e2=Double.parseDouble(emer2);}
        catch(NumberFormatException e)
        {e2=0;}
        try{
            n=Double.parseDouble(name1);}
        catch(NumberFormatException e)
        {n=0;}

        if (name1.equals("")||n!=0){
            name.setText("");
            Toast.makeText(this, "Name incorrect", Toast.LENGTH_LONG).show();
        }
        else if(phone1.length()!=10 )
        {
            phone.setText("");

            Toast.makeText(this, "Length of phone number incorrect", Toast.LENGTH_LONG).show();

        }
        else if( emer1.length()!=10)
        {

            econtact.setText("");

            Toast.makeText(this, "Length of emergency contact 1 is incorrect", Toast.LENGTH_LONG).show();

        }
        else if( emer2.length()!=10)
        {

            econtact2.setText("");
            Toast.makeText(this, "Length of emergency contact 2 incorrect", Toast.LENGTH_LONG).show();

        }
        else if(email1.length()==0)
        {
            email.setText("");
            Toast.makeText(this,"Email id cannot be empty.",Toast.LENGTH_LONG).show();
        }
        else if(password1.length()<8)
        {
            password.setText("");
            cpassword.setText("");
            Toast.makeText(this, "Password should be of minimum 8 characters", Toast.LENGTH_LONG).show();
        }
        else if(d==0)
        {
            phone.setText("");
            Toast.makeText(this, "Invalid number", Toast.LENGTH_LONG).show();
        }
        else if(e1==0)
        {
            econtact.setText("");
            Toast.makeText(this, "Invalid emergency number", Toast.LENGTH_LONG).show();
        }
        else if(e2==0)
        {
            econtact2.setText("");
            Toast.makeText(this, "Invalid emergency number", Toast.LENGTH_LONG).show();
        }
        else {
            Users user = null;
            if (password1.equals(cpassword1) && (otp1.equals(finalOtp))) {
                user = new Users(name1, phone1, email1, password1, emer1, emer2);
                ServerRequests serverRequests = new ServerRequests(this);
                serverRequests.storeDataInBackground(user, new GetUserCallBack() {
                    @Override
                    public void done(Users returnedUser) {
                        Intent intent = new Intent(SignUp.this, Login.class);
                        startActivity(intent);

                    }
                });
            } else if (!password1.equals(cpassword1)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, SignUp.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "OTP do not match", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, SignUp.class);
                startActivity(intent);
            }
        }

    }
    public void onOtpClick(View view){
        String phOtp=phone.getText().toString();
        OtpServerRequest serverRequests=new OtpServerRequest(this);
        serverRequests.fetchOtpInBackground(phOtp, new GetOtpCallBack() {
            @Override
            public void done(String returnedOtp) {
                if (returnedOtp == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
                    builder.setMessage("OtpNull");
                    builder.setPositiveButton("OK", null);
                    builder.show();
                } else {
                    finalOtp=returnedOtp;
                }
            }
        });
    }
    public void onLoginClick(View view)
    {
        Intent intent=new Intent(this,Login.class);
        startActivity(intent);
    }

}
