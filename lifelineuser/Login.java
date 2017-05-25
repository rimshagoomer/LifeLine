package com.example.pc.lifelineuser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.Typeface;
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
import android.widget.TextView;

public class Login extends Activity {

    public static Localdatabase localDatabase;
    EditText username,password;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        localDatabase=new Localdatabase(this);
        ImageView mImageView;
        mImageView = (ImageView)findViewById(R.id.imageView2);
        mImageView.setImageResource(R.drawable.lifelineplus2);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        EditText username = (EditText) findViewById(R.id.phoneT);
        EditText password = (EditText) findViewById(R.id.passwordT);
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        if (ev.getAction() == MotionEvent.ACTION_DOWN &&
                !getLocationOnScreen(username).contains(x, y) &&
                !getLocationOnScreen(password).contains(x, y)) {
            InputMethodManager input = (InputMethodManager)
                    this.getBaseContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            input.hideSoftInputFromWindow(username.getWindowToken(), 0);
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


    public void onLoginClick(View v) {
        username = (EditText) findViewById(R.id.phoneT);
        password = (EditText) findViewById(R.id.passwordT);
        String phone=username.getText().toString();
        String pass=password.getText().toString();
        Users user=new Users(phone,pass);
        authenticate(user);

    }
    public void authenticate(Users user)
    {
        ServerRequests serverRequests=new ServerRequests(this);
        serverRequests.fetchDataInBackground(user, new GetUserCallBack() {
            @Override
            public void done(Users returnedUser) {
                if(returnedUser==null)
                {
                    AlertDialog.Builder builder=new AlertDialog.Builder(Login.this);
                    builder.setMessage("Username and Password do not match");
                    username.setText("");
                    password.setText("");
                    builder.setPositiveButton("OK", null);
                    builder.show();
                }
                else
                {
                    Log.i("Aakriti",returnedUser.name);
                    localDatabase.storeData(returnedUser);
                    localDatabase.setUserLoggedIn(true);
                    Intent intent;
                    intent = new Intent(Login.this, Emergency.class);
                    startActivity(intent);
                }
            }
        });
    }
    public void onSignupClick(View view)
    {
        Intent intent=new Intent(this,SignUp.class);
        startActivity(intent);
    }
}
