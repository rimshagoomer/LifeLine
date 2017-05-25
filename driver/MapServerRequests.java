package com.example.user.driver;

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

/**
 * Created by pc on 10/31/2015.
 */
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
    public void deleteDataInBackground(Coord coord, GetMapCallBack callback) {
        progressDialog.show();
        new DeleteDataAsyncTask(coord , callback).execute();
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
                URL url = new URL(SERVER_ADDRESS + "DriverCoords.php");
                Log.i("Aakriti", url.toString());
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(CONNECTION_TIMEOUT);
                con.setDoOutput(true);
                Log.i("Aakriti", "Conn done");
                con.setRequestMethod("POST");
                StringBuilder data=new StringBuilder();
                Log.i("Aakriti","POST done");
                String lati=coord.lat+"";
                String long1=coord.lon+"";
                Log.i("Aakriti",lati);
                String ph1=coord.phone+"";
                Log.i("Aakriti",ph1);

                Log.i("Aakriti", "Connection done");

                data.append(URLEncoder.encode("PhoneNo", "UTF-8")+"="+URLEncoder.encode(ph1,"UTF-8")+"&");
                data.append(URLEncoder.encode("Latitude","UTF-8")+"="+URLEncoder.encode(lati,"UTF-8")+"&");
                data.append(URLEncoder.encode("Longitude","UTF-8")+"="+URLEncoder.encode(long1,"UTF-8")+"&");
                Log.i("Aakriti",data.toString());
                OutputStreamWriter outputStream = new OutputStreamWriter(con.getOutputStream());
                outputStream.write(data.toString());
                outputStream.flush();
                if(con.getResponseCode()==HttpURLConnection.HTTP_OK)
                {
                    Log.i("Aakriti","OK");

                }
                else
                {
                    Log.i("Aakriti","ERROR");

                }
//                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
//
//                bufferedWriter.write(data.toString());
//                bufferedWriter.flush();
//                bufferedWriter.close();
                outputStream.close();


                con.disconnect();
                Log.d("Aakriti","whole try block executed");

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
    public class DeleteDataAsyncTask extends AsyncTask<Void, Void, Void> {
        Coord coord;
        GetMapCallBack callback;
        public DeleteDataAsyncTask(Coord coord, GetMapCallBack callback) {
            this.coord = coord;
            this.callback = callback;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                URL url = new URL(SERVER_ADDRESS + "DeleteDriver.php");
                Log.i("Aakriti", url.toString());
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(CONNECTION_TIMEOUT);
                con.setDoOutput(true);
                Log.i("Aakriti", "Conn done");
                con.setRequestMethod("POST");
                StringBuilder data=new StringBuilder();
                Log.i("Aakriti","POST done");
                String ph1=coord.phone+"";
                Log.i("Aakriti",ph1);

                Log.i("Aakriti", "Connection done");
                data.append(URLEncoder.encode("PhoneNo","UTF-8")+"="+URLEncoder.encode(ph1,"UTF-8")+"&");
                Log.i("Aakriti",data.toString());
                OutputStreamWriter outputStream = new OutputStreamWriter(con.getOutputStream());
                outputStream.write(data.toString());
                outputStream.flush();
                if(con.getResponseCode()==HttpURLConnection.HTTP_OK)
                {
                    Log.i("Aakriti","OK");

                }
                else
                {
                    Log.i("Aakriti","ERROR");

                }
//                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
//
//                bufferedWriter.write(data.toString());
//                bufferedWriter.flush();
//                bufferedWriter.close();
                outputStream.close();


                con.disconnect();
                Log.d("Aakriti","whole try block executed");

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
