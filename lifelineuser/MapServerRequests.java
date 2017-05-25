package com.example.pc.lifelineuser;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.MalformedInputException;

public class MapServerRequests {
    ProgressDialog progressDialog;

    public static final int CONNECTION_TIMEOUT = 15000;

    public static final String SERVER_ADDRESS = "http://lifelineplus.3eeweb.com/";

    public MapServerRequests(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing");
        progressDialog.setMessage("Please Wait...");

    }
    public void storeDataInBackground(Coord coord, GetMapCallBack callback) {
        progressDialog.show();
        new StoreDataAsyncTask(coord , callback).execute();
    }
    public class StoreDataAsyncTask extends AsyncTask<Void, Void, Void> {
        Coord coord;
        GetMapCallBack callback;
        public StoreDataAsyncTask(Coord coord, GetMapCallBack callback) {
            this.coord = coord;
            this.callback = callback;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                URL url = new URL(SERVER_ADDRESS + "UserCoords.php");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(CONNECTION_TIMEOUT);
                con.setReadTimeout(10000);
                con.setDoOutput(true);
                con.setRequestMethod("POST");
                StringBuilder data=new StringBuilder();
                String lati=coord.lat+"";
                String long1=coord.lon+"";
                String ph1=coord.phone+"";
                String name=coord.name1+"";
                data.append(URLEncoder.encode("Name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&");
                data.append(URLEncoder.encode("PhoneNo", "UTF-8")+"="+URLEncoder.encode(ph1,"UTF-8")+"&");
                data.append(URLEncoder.encode("Latitude","UTF-8")+"="+URLEncoder.encode(lati,"UTF-8")+"&");
                data.append(URLEncoder.encode("Longitude","UTF-8")+"="+URLEncoder.encode(long1,"UTF-8")+"&");
                OutputStreamWriter outputStream = new OutputStreamWriter(con.getOutputStream());
                outputStream.write(data.toString());
                outputStream.flush();
                if(con.getResponseCode()==HttpURLConnection.HTTP_OK)
                {
                    Log.i("Aakriti","OK");
                }
                else
                {
                }
                outputStream.close();
                con.disconnect();
            } catch (MalformedInputException e) {
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            callback.done(null);
            super.onPostExecute(aVoid);
        }
    }
}
