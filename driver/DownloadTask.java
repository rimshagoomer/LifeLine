package com.example.user.driver;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadTask extends AsyncTask<String, Void, String> {

    MapsActivity obj;
    private Context con;
    ProgressDialog pd;
    public DownloadTask(Context con,GoogleMap mp,Location lng)
       {
        this.con=con;
           obj=new MapsActivity();
           obj.mMap=mp;
           obj.oLocation = lng;
    }

    public String downloadUrl(String strUrl) throws IOException {

        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while( ( line = br.readLine()) != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d("Exception while downloading url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = ProgressDialog.show(con,"Please Wait","Showing path...",true);
        pd.setCancelable(false);
    }

    // Downloading data in non-ui thread
    @Override
    protected String doInBackground(String... url) {
        // For storing data from web service
        String data = "";
        try{
            // Fetching the data from web service

            data = downloadUrl(url[0]);
        }catch(Exception e){
            Log.d(" DownloadTask: Background Task", e.toString());
        }
        return data;
    }

    // Executes in UI thread, after the execution of
    // doInBackground()
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        pd.dismiss();
        ParserTask parserTask = new ParserTask(con,obj.mMap,obj.oLocation);
        // Invokes the thread for parsing the JSON data
        parserTask.execute(result);
    }
}