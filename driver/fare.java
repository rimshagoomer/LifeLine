
package com.example.user.driver;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class fare extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_fare);
            Log.i("Ayush", "Inside intent");
            Bundle inf = getIntent().getExtras();
            Log.i("Ayush", "Inside intent 2");
            TextView tv = (TextView) findViewById(R.id.textView1);
            Log.i("Ayush", "Inside intent 3");
            String applemsg = inf.getString("Distance");
            Log.i("Ayush", "Inside intent 4");
            tv.setText("Total distance :" + ""+applemsg + "m\nTotal time :" + inf.getString("Duration"));
        }
        catch (Exception e)
        {
            Log.i("Ayush",e.getMessage());
        }
        Log.i("Ayush","Completed");
        ImageView mImageView;
        mImageView = (ImageView)findViewById(R.id.imageView2);
        mImageView.setImageResource(R.drawable.lifelineplus2);  RelativeLayout myLayout =
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
    public void onDoneMap(View view)
    {
        MapsActivity.ulatitude="";
        MapsActivity.ulongitude="";
        Intent intent=new Intent(this,MapsActivity.class);
        startActivity(intent);
    }

    public void onBackPressed(View v){
        MapsActivity.ulatitude="";
        MapsActivity.ulongitude="";
        Intent intent=new Intent(this,MapsActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }



}
