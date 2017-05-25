package com.example.user.driver;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import com.google.android.gms.location.LocationListener;
import android.location.Location;
//import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MapsActivity extends FragmentActivity implements GoogleMap.OnMarkerClickListener,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {


    public  GoogleMap mMap; // Might be null if Google Play services APK is not available.
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    ArrayList<LatLng> markerPoints;
    public Location oLocation;
    LatLng origin=null,dest=null,ulatlng=null;
    TextView tv;
    Button a,b,c,b1;
    public static Localdatabase db1;
    CalculateDistance cd;
    public static String latitudefinal,longitudefinal;
    public static String ulatitude="",ulongitude="",userphone="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
       // tv=(TextView)findViewById(R.id.textView);
        a=(Button)findViewById(R.id.button1);
        b=(Button)findViewById(R.id.button2);
        c=(Button)findViewById(R.id.button3);
        b1=(Button)findViewById(R.id.freeB);


        //Log.i(getCurrentFocus().)


        a.setClickable(true);
        a.setEnabled(true);
        b.setClickable(false);
        c.setClickable(false);
        b.setEnabled(false);
        c.setEnabled(false);
        a.setTextColor(Color.parseColor("#ffffff"));
        b.setTextColor(Color.parseColor("#989898"));
        c.setTextColor(Color.parseColor("#989898"));

        markerPoints = new ArrayList<LatLng>();

        Log.i("Ayush","Button creation successful");
        setUpMapIfNeeded();
       Log.i("Ayush","setUpMapIfNeeded() successful" );
        implementMapListener();
        Log.i("Ayush","on Create successful");
    }
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.

        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                Log.d("Ayush", "Setup map create");
                setUpMap();
                //implementMapListener();

            }
        }
    }




    private void setUpMap() {
        mMap.setMyLocationEnabled(true);
        db1=new Localdatabase(this);
        Log.i("Aakriti", "rimsha");
        //mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        buildGoogleApiClient();
        Log.i("Aakriti", "rimshaaaa");


        Log.i("Aakriti", "rimshaaaa123");
        mGoogleApiClient.connect();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMap();
    }

    protected synchronized void buildGoogleApiClient() {
        // Toast.makeText(this, "buildGoogleApiClient", Toast.LENGTH_SHORT).show();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }


    @Override
    public void onConnected(Bundle bundle) {
        //Toast.makeText(this,"onConnected",Toast.LENGTH_SHORT).show();
        Log.i("cd","outer onConnected");
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        oLocation=mLastLocation;
        if (mLastLocation != null) {
            //place marker at current position
            mMap.clear();
            origin = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            latitudefinal=mLastLocation.getLatitude()+"";
            longitudefinal=mLastLocation.getLongitude()+"";

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(origin);
            markerOptions.title("Current Position");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            Marker m = mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(origin));
            // Zoom in the Google Map
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
            if (!ulatitude.equals(""))
            {
                double d1=Double.parseDouble(ulatitude);
                double d2=Double.parseDouble(ulongitude);
                ulatlng=new LatLng(d1,d2);
                userMarkers(ulatlng);
            }
        }

        /*mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000); //5 seconds
        mLocationRequest.setFastestInterval(3000); //3 seconds
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        //mLocationRequest.setSmallestDisplacement(0.1F); //1/10 meter
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);*/
    }


    @Override
    public void onConnectionSuspended(int i) {
        //Toast.makeText(this,"onConnectionSuspended",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        //    Toast.makeText(this,"onConnectionFailed",Toast.LENGTH_SHORT).show();
    }

//    private void setUpMap() {
//
//        mMap.setMyLocationEnabled(true);
//
//        final LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//        // Creating a criteria object to retrieve provider
//        /*LocationListener locationListener = new LocationListener() {
//
//            int counter = 0;
//
//            public void onLocationChanged(Location location) {
//                // Called when a new location is found by the network location provider.
//                if(location != null){
//                    counter = 0;
//                    double lat = location.getLatitude();
//                    double lon = location.getLongitude();
//                }
//                else
//                {
//                    counter++;
//                    if(counter > 3)
//                    {
//                        // Remove the listener you previously added
//                       // locationManager.removeUpdates(locationListener);
//                        //locationManager = null;
//
//                        //Let user know there was a problem and that GPS was shut off....
//                    }
//
//                }
//            }
//
//            public void onStatusChanged(String provider, int status, Bundle extras) {}
//
//            public void onProviderEnabled(String provider) {}
//
//            public void onProviderDisabled(String provider) {}
//        };
//
//// Register the listener with the Location Manager to receive location updates
//        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
//
//        Criteria criteria = new Criteria();
//        // Getting the name of the best provider
//        String provider = locationManager.getBestProvider(criteria, true);
//        // Getting Current Location
//        Location location = locationManager.getLastKnownLocation(provider);
//        oLocation=location;
//        if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
//        {
//            Log.i("Ayush","provider enabled ");
//        }
//        else
//        {
//            Log.i("Ayush","provider not enabled ");
//        }
//        if(location==null)
//            Log.i("Ayush","Location coming NULL");
//        if (location != null) {
//            Log.d("Ayush", "Inside Setup map create");
//            onLocationChanged(location);
//        }
//        if (!isNetworkAvailable())
//        {
//            Toast.makeText(getApplicationContext(), "No network provider the gps wont work", Toast.LENGTH_LONG).show();
//            // no network provider is enabled
//        }
//        else{
//
//            Toast.makeText(getApplicationContext(), "Network is available", Toast.LENGTH_LONG).show();
//        }
//    }
//    */



    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void onLocationChanged(Location location) {

        Log.d("cd", " Outer: Inside onLocationChanged");
        // Getting latitude of the current location

//        double latitude = location.getLatitude();
//        // Getting longitude of the current location
//        double longitude = location.getLongitude();
//
//        origin=new LatLng(latitude, longitude);
//        Log.i("Ayush","Before Marker Set");
//        mMap.addMarker(new MarkerOptions().position(origin).title("Marker"));
//        Log.i("Ayush","After Marker Set");
        // Showing the current location in Google Map
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(origin));
        // Zoom in the Google Map
        //mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

    }


    private void implementMapListener()
       {

           try{
               Log.i("Ayush","Inside implementinMap listener");
               mMap.setOnMarkerClickListener((GoogleMap.OnMarkerClickListener)this);
//               mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//
//               @Override
//               public void onMapClick(LatLng point) {
//
//                   // Already only one locations
//                   if(markerPoints.size()>=1){
//                       markerPoints.clear();
//                       mMap.clear();
//                       mMap.addMarker(new MarkerOptions().position(origin).title("Marker"));
//                   }
//                   Log.i("Ayush","inside on Map click");
//                   // Adding new item to the ArrayLis
//                       markerPoints.add(point);
//                   MarkerOptions options = new MarkerOptions();
//                   // Setting the position of the marker
//                   options.position(point);
//
//                   /**
//                    * For the start location, the color of marker is GREEN and
//                    * //for the end location, the color of marker is RED.
//                    */
//                   if(markerPoints.size()==1){
//                       options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
//                   }
//                /*else if(markerPoints.size()==2){
//                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
//                }*/
//                   mMap.addMarker(options);
//                   // Checks, whether start and end locations are captured
//                   if(markerPoints.size() >= 1){
//                       dest = markerPoints.get(0);
//                   }
//                   Log.i("Ayush","Tap work finished");
//               }
//           });
//
}
           catch(Exception e) {
                Log.i("Ayush",e.getMessage());
           }

       }


    public void onClick(View v)
     {
           if(v.getId()==R.id.button1)
           {
               if(!ulatitude.equals("")) {
                   Drivers driv = db1.getLoggedInUser();
                   String name11 = driv.name;
                   String ambu11 = driv.ambulance;
                   String phon11 = driv.phone;
                   String sms = "Your ambulance is on its way.Details: Name-" + name11 + ",Ambulance No-" + ambu11 + ", Phone No -" + phon11 + ".Thank you for using lifelineplus services";
                   try {
                       SmsManager smsManager = SmsManager.getDefault();
                       smsManager.sendTextMessage(userphone, null, sms, null, null);
                       Toast.makeText(getApplicationContext(), "SMS Sent!",
                               Toast.LENGTH_LONG).show();
                   } catch (Exception e) {
                       Toast.makeText(getApplicationContext(),
                               "SMS faild, please try again later!",
                               Toast.LENGTH_LONG).show();
                       e.printStackTrace();
                   }

                   // Getting URL to the Google Directions API
                   //cd.execute(oLocation);
                   cd = new CalculateDistance(this.getBaseContext(), oLocation, mGoogleApiClient);
                   Log.i("Ayush", "Reached on click");
                   String url = getDirectionsUrl(origin, ulatlng);

                   Log.i("Ayush", " After getDirections Url");
                   DownloadTask downloadTask = new DownloadTask(this, mMap, oLocation);
                   Log.i("Ayush", "Declaration complete");
                   // Start downloading json data from Google Directions API
                   downloadTask.execute(url);
                   Log.i("Ayush", "Execution complete");

                   Log.i("Ayush", "Download task finished");
                   a.setEnabled(false);
                   a.setClickable(false);
                   b.setEnabled(true);
                   b.setClickable(true);
                   a.setTextColor(Color.parseColor("#989898"));
                   b.setTextColor(Color.parseColor("#ffffff"));
                   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                       cd.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                   else
                       cd.execute();
                   extractLatLon(ulatitude,ulongitude,2);


                   //cd.execute(oLocation);
               }
               else{
                   Toast toast=Toast.makeText(this.getBaseContext(), "You haven't received any request!!", Toast.LENGTH_LONG);
                   toast.show();
               }

           }
            else if(v.getId()==R.id.button2)
           {
               b.setEnabled(false);
               c.setEnabled(true);
               c.setClickable(true);
               b.setClickable(false);
               b.setTextColor(Color.parseColor("#989898"));
               c.setTextColor(Color.parseColor("#ffffff"));
               Double latitude[]=new Double[6],longitude[]=new Double[6];
               latitude[0]=29.9633612;
               latitude[1]=29.969625;
               latitude[2]=29.9744481;
               latitude[3]=29.969625;
               latitude[4]=29.9707031;
               latitude[5]=29.969625;
               longitude[0]=76.8208307;
               longitude[1]=76.8327476;
               longitude[2]=76.861152;
               longitude[3]=76.8327476;
               longitude[4]=76.825731;
               longitude[5]=76.8327476;
               LatLng[] points=new LatLng[6];
               for (int i=0; i <6; i++) {
                   points[i]=new LatLng(latitude[i],longitude[i]);
               }
               hospitalMarkers(points, 6);

           }
         else if(v.getId()==R.id.button3)
           {
               Log.i("cd","on button 3");
               cd.cancel(true);
               Log.i("Ayush", "Cancel success");
               c.setClickable(false);
               c.setEnabled(false);

               b1.setEnabled(true);
               b1.setClickable(true);
               c.setTextColor(Color.parseColor("#989898"));
               b1.setTextColor(Color.parseColor("#ffffff"));
           }


     }
    private String getDirectionsUrl(LatLng origin,LatLng dest){
        String str_origin = "origin="+origin.latitude+","+origin.longitude;
        String str_dest = "destination="+dest.latitude+","+dest.longitude;
        String sensor = "sensor=false";
        String parameters = str_origin+"&"+str_dest+"&"+sensor;
        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;
        return url;
    }

    public void hospitalMarkers(LatLng arr[],int s){
        int i;
        String name[]=new String[6];
        name[0]="LNJP Hospital";
        name[1]="Bhola Hospital";
        name[2]="BS Heart Care and Multispeciality Hospital";
        name[3]="Cygnus Super Speciality Hospital";
        name[4]="Saraswati Mission Hospital";
        name[5]="Bhola Orthopaedic And Dental Hospital";
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        for(i=0;i<s;i++){
            markerOptions.position(arr[i]);
            markerOptions.title(name[i]);
            Marker m = mMap.addMarker(markerOptions);
        }
    }


    public  void userMarkers(LatLng obj){

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            markerOptions.position(obj);
            Marker m = mMap.addMarker(markerOptions);

    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.i("Riya","marker clicked");
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(ulatitude),Double.parseDouble(ulongitude))).title("Driver"));
        mMap.addMarker(new MarkerOptions().position(marker.getPosition()).title("Hospital"));
        String url = getDirectionsUrl(origin, marker.getPosition());
        DownloadTask downloadTask = new DownloadTask(this,mMap,oLocation);
        downloadTask.execute(url);
        return true;
    }

    public void extractLatLon(String latitude,String longitude,int i)
    {String ppp;
        Log.i("Aakriti", "Extactlatlan");
        if(i==1)
        {

        Log.i("Aakriti","hello");
        Drivers driv=db1.getLoggedInUser();
        ppp=driv.phone;
            Log.i("Aakriti",ppp);

        Coord coord=new Coord(latitude+"",longitude+"",ppp);
        MapServerRequests serverRequests=new MapServerRequests(this);
        serverRequests.storeDataInBackground(coord, new GetMapCallBack() {
            @Override
            public void done(Drivers coord) {

            }
        });}
        else if(i==2)
        {
            Drivers driv=db1.getLoggedInUser();
            ppp=driv.phone;
            Coord coord=new Coord(latitude+"",longitude+"",ppp);
            MapServerRequests serverRequests=new MapServerRequests(this);
            serverRequests.deleteDataInBackground(coord,new GetMapCallBack()
            {
                @Override
                public void done(Drivers coord) {

                }
            });


        }
    }

    public void onFree(View view)
    {

        String s=b1.getText().toString();
        if(s.equals("Free"))
        {
            b1.setEnabled(false);
            b1.setClickable(false);
            a.setEnabled(true);
            a.setClickable(true);
            a.setTextColor(Color.parseColor("#ffffff"));
            b1.setTextColor(Color.parseColor("#989898"));
            extractLatLon(latitudefinal, longitudefinal, 1);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        if (ev.getAction() == MotionEvent.ACTION_DOWN ) {
            InputMethodManager input = (InputMethodManager)
                    this.getBaseContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            input.hideSoftInputFromWindow(a.getWindowToken(), 0);
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

}
