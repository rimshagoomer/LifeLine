package com.example.pc.lifelineuser;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.provider.Settings;
import android.text.LoginFilter;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.MalformedInputException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pc on 10/13/2015.
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

    public void storeDataInBackground(Users users, GetUserCallBack callback) {
        progressDialog.show();
        new StoreDataAsyncTask(users , callback).execute();
    }

    public void fetchDataInBackground(Users user, GetUserCallBack callback) {
        progressDialog.show();
        new FetchDataAsyncTask(user , callback).execute();
    }




    public class StoreDataAsyncTask extends AsyncTask<Void, Void, Void> {
        Users user;
        GetUserCallBack callback;
        public StoreDataAsyncTask(Users user, GetUserCallBack callback) {
            this.user = user;
            this.callback = callback;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                URL url = new URL(SERVER_ADDRESS + "Register.php");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(CONNECTION_TIMEOUT);
                con.setDoOutput(true);
                con.setRequestMethod("POST");
                StringBuilder data=new StringBuilder();
                data.append(URLEncoder.encode("Name","UTF-8")+"="+URLEncoder.encode(user.name,"UTF-8")+"&");
                data.append(URLEncoder.encode("Email","UTF-8")+"="+URLEncoder.encode(user.email,"UTF-8")+"&");
                data.append(URLEncoder.encode("PhoneNo","UTF-8")+"="+URLEncoder.encode(user.phone,"UTF-8")+"&");
                data.append(URLEncoder.encode("Password","UTF-8")+"="+URLEncoder.encode(user.password,"UTF-8")+"&");
                data.append(URLEncoder.encode("Emergency1", "UTF-8")+"="+URLEncoder.encode(user.emer1, "UTF-8") + "&");
                data.append(URLEncoder.encode("Emergency2", "UTF-8")+"="+URLEncoder.encode(user.emer2, "UTF-8"));
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
    public class FetchDataAsyncTask extends AsyncTask<Void, Void, Users>{
        Users user;
        GetUserCallBack callback;
        public FetchDataAsyncTask(Users user, GetUserCallBack callback){
            this.user=user;
            this.callback=callback;
        }


        @Override
        protected Users doInBackground(Void... voids) {
            Users returnedUser=null;
            try {
                URL url = new URL(SERVER_ADDRESS + "FetchUserData.php");
                Log.i("Aakriti", "rimi");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(CONNECTION_TIMEOUT);
                con.setDoOutput(true);
                con.setRequestMethod("POST");
                StringBuilder data=new StringBuilder();
                data.append(URLEncoder.encode("PhoneNo", "UTF-8") + "=" + URLEncoder.encode(user.phone, "UTF-8") + "&");
                data.append(URLEncoder.encode("Password", "UTF-8") + "=" + URLEncoder.encode(user.password, "UTF-8") + "&");
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
                outputStream.close();
                String line=null;
                StringBuilder result=new StringBuilder();
                InputStream in = new BufferedInputStream(con.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                while ((line = reader.readLine()) != null) {
                        result.append(line);
                }
                String name = null, phone = null, email = null, password = null, emer1 = null, emer2 = null;
                String x=result.toString();
                if (x.substring(0, 2).equals("[]")) {
                    x="{}";
                }
                JSONObject ja = new JSONObject(x);
                if (ja.length()==0) {
                    returnedUser =null;
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

                    if (ja.has("emer1"))
                        emer1 = (ja.getString("emer1"));

                    if (ja.has("emer2"))
                        emer2 = (ja.getString("emer2"));

                    returnedUser = new Users(name, phone, email, password, emer1, emer2);
                }

                    con.disconnect();
                in.close();

            } catch (MalformedInputException e) {
                e.printStackTrace();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return returnedUser;
        }
            protected void onPostExecute(Users user){
                progressDialog.dismiss();
                callback.done(user);
                super.onPostExecute(user);
            }
        }




}

