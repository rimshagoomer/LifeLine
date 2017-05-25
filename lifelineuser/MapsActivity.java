package com.example.pc.lifelineuser;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import com.google.android.gms.location.LocationListener;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class
        MapsActivity extends FragmentActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleMap mMap;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    LatLng latLng,dest;
    double latitudefinal,longitudefinal;
    int k;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        EditText address=(EditText)findViewById(R.id.addressT);
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        if (ev.getAction() == MotionEvent.ACTION_DOWN &&
                !getLocationOnScreen(address).contains(x, y)) {
            InputMethodManager input = (InputMethodManager)
                    this.getBaseContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            input.hideSoftInputFromWindow(address.getWindowToken(), 0);
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        if (mMap == null) {
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        mMap.setMyLocationEnabled(true);
        buildGoogleApiClient();
        mGoogleApiClient.connect();

    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }


    @Override
    public void onConnected(Bundle bundle) {
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        k=0;
        if (mLastLocation != null) {
            mMap.clear();
            latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title("Current Position");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            Marker m = mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000); //5 seconds
        mLocationRequest.setFastestInterval(3000); //3 seconds
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }


    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }
    public void onLocationChanged(Location location) {
        k++;
        if (k>1){
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,this);
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        latitudefinal=latitude;
        longitudefinal=longitude;

    }


    public void onSearch(View v){
        EditText address=(EditText)findViewById(R.id.addressT);
        String location=address.getText().toString();
        List<Address> addressList=null;
        if(location!=null || !location.equals("")){
            Geocoder geocoder=new Geocoder(this);
            try {
                addressList=geocoder.getFromLocationName(location,1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address1=addressList.get(0);
            LatLng latLng=new LatLng(address1.getLatitude(),address1.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            latitudefinal=address1.getLatitude();
            longitudefinal=address1.getLongitude();

        }
    }

    public void extractLatLon(double latitude,double longitude)
    {
        String ppp=Emergency.str1;
        String namee=Emergency.nme;
        Coord coord=new Coord(latitude+"",longitude+"",ppp,namee);
        MapServerRequests serverRequests=new MapServerRequests(this);
        serverRequests.storeDataInBackground(coord, new GetMapCallBack() {
            @Override
            public void done(Coord coord) {
                Intent intent=new Intent(MapsActivity.this, Final.class);
                startActivity(intent);
            }
        });
    }
    public void onBookClick(View view)
    {
        Button b1=(Button)findViewById(R.id.bookB);
       extractLatLon(latitudefinal, longitudefinal);
    }

    public void onBackPressed() {
        Intent intent=new Intent(this,Emergency.class);
        startActivity(intent);
    }

}
