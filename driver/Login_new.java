package com.example.user.driver;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

public class Login_new extends Activity {

    public static Localdatabase localDatabase;
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
        setContentView(R.layout.activity_login_new);
        localDatabase=new Localdatabase(this);
        ImageView mImageView;
        mImageView = (ImageView)findViewById(R.id.imageView2);
        mImageView.setImageResource(R.drawable.lifelineplus2);
    }


    public void onLoginClick(View v) {
        EditText username = (EditText) findViewById(R.id.phoneT);
        EditText password = (EditText) findViewById(R.id.passwordT);
        String phone=username.getText().toString();
        String pass=password.getText().toString();
        Drivers user=new Drivers(phone,pass);
        authenticate(user);

    }
    public void authenticate(Drivers user)
    {
        ServerRequests serverRequests=new ServerRequests(this);
        serverRequests.fetchDataInBackground(user, new GetDriverCallBack() {
            @Override
            public void done(Drivers returnedUser) {
                if (returnedUser == null) {
                    Log.i("Aakriti", "nothing");
                    AlertDialog.Builder builder = new AlertDialog.Builder(Login_new.this);
                    builder.setMessage("Username and Password do not match");
                    builder.setPositiveButton("OK", null);
                    builder.show();
                } else {
                    Log.i("Aakriti", returnedUser.name);
                    localDatabase.storeData(returnedUser);
                    Log.i("Aakriti", "here");
                    localDatabase.setUserLoggedIn(true);
                    Log.i("Aakriti", "here2");
                    Intent intent = new Intent(Login_new.this, MapsActivity.class);
                    startActivity(intent);
                }
            }
        });
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

    public void onSignupClick(View view)
    {

        Intent intent=new Intent(this,SignUp.class);
        startActivity(intent);
    }


}
