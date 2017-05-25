package com.example.pc.lifelineuser;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class account extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        TextView tname=(TextView)findViewById(R.id.UnameTV);
        TextView tphone=(TextView)findViewById(R.id.phnT);
        TextView temail=(TextView)findViewById(R.id.EmT);
        tname.setText(Emergency.name);
        tphone.setText(Emergency.str1);
        temail.setText(Emergency.email);
        ImageView mImageView;
        mImageView = (ImageView)findViewById(R.id.imageView2);
        mImageView.setImageResource(R.drawable.lifelineplus2);
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

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }


    public void onBackPressed(View v){
        Intent intent=new Intent(this,Emergency.class);
        startActivity(intent);
    }

    public void onBackClick(View v){
        Intent intent=new Intent(this,Emergency.class);
        startActivity(intent);
    }

}
