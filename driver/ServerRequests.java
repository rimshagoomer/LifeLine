package com.example.user.driver;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.MalformedInputException;

/**
 * Created by pc on 10/28/2015.
 */
public class ServerRequests {
    ProgressDialog progressDialog;
    public static final int CONNECTION_TIMEOUT = 15000;
    public static final String SERVER_ADDRESS = "http://lifelineplus.3eeweb.com/";

    public ServerRequests(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing");
        progressDialog.setMessage("Please Wait...");
    }


    public void fetchDataInBackground(Drivers user, GetDriverCallBack callback) {
        progressDialog.show();
        new FetchDataAsyncTask(user , callback).execute();
    }





    public class FetchDataAsyncTask extends AsyncTask<Void, Void, Drivers>{
        Drivers user;
        GetDriverCallBack callback;
        public FetchDataAsyncTask(Drivers user, GetDriverCallBack callback){
            this.user=user;
            this.callback=callback;
        }
        @Override
        protected Drivers doInBackground(Void... voids) {
            Drivers returnedUser=null;

            try {

                URL url = new URL(SERVER_ADDRESS + "FetchDriverData.php");
                Log.i("Aakriti", "rimi");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(CONNECTION_TIMEOUT);
                con.setDoOutput(true);
                con.setRequestMethod("POST");
                StringBuilder data=new StringBuilder();


                Log.i("Aakriti", "Connection done");
                data.append(URLEncoder.encode("PhoneNo", "UTF-8") + "=" + URLEncoder.encode(user.phone, "UTF-8") + "&");
                data.append(URLEncoder.encode("Password", "UTF-8") + "=" + URLEncoder.encode(user.password, "UTF-8") + "&");
                Log.i("Aakriti", data.toString());

                OutputStreamWriter outputStream = new OutputStreamWriter(con.getOutputStream());
                outputStream.write(data.toString());
                outputStream.flush();
                Log.i("Aakriti", "flushed");

                if(con.getResponseCode()==HttpURLConnection.HTTP_OK)
                {
                    Log.i("Aakriti","OK");

                }
                else
                {
                    Log.i("Aakriti","ERROR");

                }

                outputStream.close();
                String line=null;
                StringBuilder result=new StringBuilder();
                InputStream in = new BufferedInputStream(con.getInputStream());

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                Log.i("Aakriti", result.toString());
                // if (result.toString().equals("[]<!-- Hosting24 Analytics Code --><script type=\"text/javascript\" src=\"http://stats.hosting24.com/count.php\"></script><!-- End Of Analytics Code -->"))
                //          returnedUser=null;
                // else {
                String name = null, phone = null, email = null, password = null, ambul = null, driv = null,hosp=null,regid=null;
                Log.i("Aakriti", "ee");
                String x=result.toString();
                if (x.substring(0, 2).equals("[]")) {
                    Log.i("Aakriti", "ram");
                    x="{}";
                }
                JSONObject ja = new JSONObject(x);
                Log.i("Aakriti","2");
                if (ja.length()==0) {
                    returnedUser =null;
                    Log.i("Aakriti","nulllllll");
                }
                else{

                    if (ja.has("name"))
                        name = (ja.getString("name"));

                    if (ja.has("phone"))
                        phone = (ja.getString("phone"));

                    if (ja.has("email"))
                        email = (ja.getString("email"));

                    if (ja.has("password"))
                        password = (ja.getString("password"));

                    if (ja.has("ambulance"))
                        ambul = (ja.getString("ambulance"));

                    if (ja.has("driving"))
                        driv = (ja.getString("driving"));
                    if(ja.has("hospital"))
                        hosp=(ja.getString("hospital"));
                    if(ja.has("regID"))
                        regid=(ja.getString("regID"));


                    returnedUser = new Drivers(name, phone, email, password, ambul,driv,hosp,regid);

                    Log.i("Aakriti", returnedUser.email);
                    Log.i("Aakriti","correct");
                }

                con.disconnect();
                Log.d("Aakriti", "whole try block executed");


                in.close();

            } catch (MalformedInputException e) {
                e.printStackTrace();

            } catch (Exception e) {
                e.printStackTrace();

            }

            return returnedUser;
        }
        protected void onPostExecute(Drivers user){
            progressDialog.dismiss();

            Log.i("Aakriti", "rimi");
            callback.done(user);
            super.onPostExecute(user);

        }
    }



}
