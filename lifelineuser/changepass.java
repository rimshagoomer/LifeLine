package com.example.pc.lifelineuser;

import android.app.Activity;
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
import android.widget.TextView;
import android.widget.Toast;

public class changepass extends Activity {
    EditText passold,passnew1,passnew2;
    String passold1,newpass1s,newpass2s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepass);
        passold=(EditText)findViewById(R.id.cpassT);
        passnew1=(EditText)findViewById(R.id.newpassT);
        passnew2=(EditText)findViewById(R.id.cnewpassT);
        ImageView mImageView;
        mImageView = (ImageView)findViewById(R.id.imageView2);
        mImageView.setImageResource(R.drawable.lifelineplus2);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        if (ev.getAction() == MotionEvent.ACTION_DOWN &&
                !getLocationOnScreen(passold).contains(x, y) &&
                !getLocationOnScreen(passnew1).contains(x, y) &&
                !getLocationOnScreen(passnew2).contains(x, y)) {
            InputMethodManager input = (InputMethodManager)
                    this.getBaseContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            input.hideSoftInputFromWindow(passold.getWindowToken(), 0);
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

    public void onBackPressed(View v){
        Intent intent=new Intent(this,Emergency.class);
        startActivity(intent);
    }


    public void onDone(View v) {
        passold1=passold.getText().toString();
        newpass1s=passnew1.getText().toString();
        newpass2s=passnew2.getText().toString();
        Log.i("Aakriti", Emergency.password1);

        if(newpass1s.length()<8)
        {
            Toast.makeText(this,"Password should be minimun 8 characters",Toast.LENGTH_LONG).show();
            passnew1.setText("");
            passnew2.setText("");
        }
        else if (!passold1.equals(Emergency.password1) || !newpass1s.equals(newpass2s)) {
            Toast.makeText(this, "Wrong Information. Re-enter Details", Toast.LENGTH_LONG).show();
            passnew1.setText("");
            passnew2.setText("");
            passold.setText("");
        } else {
            PasswordServerRequest serverRequests = new PasswordServerRequest(this);
            serverRequests.storePasswordInBackground(newpass1s, new GetPassCallBack() {
                @Override
                public void done(String returnedPass) {
                    Intent intent = new Intent(changepass.this, Login.class);
                    startActivity(intent);

                }
            });
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_changepass, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
