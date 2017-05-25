package com.example.pc.lifelineuser;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class Final extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
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

    public void onBackPressed(View v){
        Intent intent=new Intent(this,Emergency.class);
        startActivity(intent);
    }

   public void onFinalBack(View view)
   {
       Button b1=(Button)findViewById(R.id.backbutt);
       Intent intent=new Intent(this,Emergency.class);
       startActivity(intent);
   }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }
}
