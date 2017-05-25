package com.example.user.driver;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.TimeUnit;
public class CalculateDistance extends AsyncTask<Location  , Void , Void > implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private Long time;
    private double dis;
    Intent mIntentService;
    PendingIntent mPendingIntent;
    private  Context context;
    MapsActivity obj;
    Location loc1;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    public CalculateDistance(Context con,Location l,GoogleApiClient mG)
    {
        this.context=con;
        obj=new MapsActivity();
        obj.oLocation=l;
        //mGoogleApiClient=mG;

        Log.i("cd","Constructor called from button 1");

    }
    @Override
    protected Void doInBackground(Location... arr) {

        dis=0;

        Location loc1 = obj.oLocation;
        Log.i("cd",""+loc1.getLatitude()+" & "+loc1.getLongitude());
        time=System.currentTimeMillis();
        Log.i("cd"," Before "+time);
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
        while(isCancelled()!=true){

        }
        if(isCancelled()==true)
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(
                    mGoogleApiClient, this);


        }






            //Log.i("Ayush", "Calculating distance");
            /*try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Location location=findLocation();
            *//*LocationManager locationManager=null;
            try {
                 locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
            }
            catch(Exception e)
            {
                Log.i("Ayush2",e.getMessage());
            }
            // Creating a criteria object to retrieve provider
            Criteria criteria = new Criteria();
            // Getting the name of the best provider
            String provider = locationManager.getBestProvider(criteria, true);
            Location location = locationManager.getLastKnownLocation(provider);
            Log.i("Ayush2",""+location.getLatitude()+" & "+location.getLongitude());
            */
            /*if (location != null) {

                dis+=loc1.distanceTo(location);
                Log.i("Ayush2",""+dis);
            }
            */
            //loc1=location;
           // Log.i("Ayush", "Loop Calculating distance"+dis);


        Log.i("df","doInBackground finished");
        return null;

    }
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    
    @Override
    protected void onCancelled() {
        super.onCancelled();

        Log.i("cd","onCancelled Called");
        time=System.currentTimeMillis()-time;
        Log.i("cd","After "+time);

        Long millis=time;

        String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        //Log.i("cd"," on Cancelled 2");
        try {
            Intent i = new Intent(context, fare.class);
            //i= new Intent(context,fare.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //Log.i("cd", " on Cancelled 3");
            dis=round(dis,3);
            i.putExtra("Distance", "" + dis);
            i.putExtra("Duration",  hms);
            //mPendingIntent = PendingIntent.getService(context, 1, mIntentService, 0);
            //mPendingIntent.send(context,0,mIntentService);
            context.startActivity(i);
            //Log.i("cd", "got Intent start");
            //tv.setText("Distance:" + dis + ", Duration:" + hms);
        }
        catch(Exception e)
        {
            Log.i("Ayush","Calculate distance "+e.getMessage());
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i("cd","onConnected");
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        loc1=mLastLocation;
        Log.i("cd",""+loc1.getLatitude()+" & "+loc1.getLongitude());

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000); //5 seconds
        mLocationRequest.setFastestInterval(3000); //3 seconds
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        //mLocationRequest.setSmallestDisplacement(0.1F); //1/10 meter
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

        Log.i("cd","Inside onLocationChanged");
//        if(isCancelled()==true)
//        {
//            LocationServices.FusedLocationApi.removeLocationUpdates(
//                    mGoogleApiClient, this);
//            return ;
//
//        }
        if (location != null) {
            Log.i("cd","Inside onLocationChanged: location not null");
            if(loc1.distanceTo(location)>=10)
            {
                dis+=loc1.distanceTo(location);
                loc1=location;
            }

            Log.i("cd",""+location.getLatitude()+" & "+location.getLongitude());
            Log.i("cd","Distance "+dis);

        }


    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
