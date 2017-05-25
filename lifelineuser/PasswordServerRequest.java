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

public class PasswordServerRequest {
    ProgressDialog progressDialog;
    public static final int CONNECTION_TIMEOUT = 15000;
    public static final String SERVER_ADDRESS = "http://lifelineplus.3eeweb.com/";

    public PasswordServerRequest(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing");
        progressDialog.setMessage("Please Wait...");
    }
    public void storePasswordInBackground(String newpass1s, GetPassCallBack callback) {
        progressDialog.show();
        new StoreDataAsyncTask(newpass1s , callback).execute();
    }
    public class StoreDataAsyncTask extends AsyncTask<Void, Void, String> {
        String npass;
        GetPassCallBack callback;
        public StoreDataAsyncTask(String newpass, GetPassCallBack callback) {
            npass=newpass;
            this.callback = callback;
        }

        @Override
        protected String doInBackground(Void... voids) {

            try {
                URL url = new URL(SERVER_ADDRESS + "ChangePass.php");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(CONNECTION_TIMEOUT);
                con.setDoOutput(true);
                con.setRequestMethod("POST");
                StringBuilder data=new StringBuilder();
                String uid=Emergency.str1;
                data.append(URLEncoder.encode("PhoneNo", "UTF-8")+"="+URLEncoder.encode(uid,"UTF-8")+"&");
                data.append(URLEncoder.encode("NewPass","UTF-8")+"="+URLEncoder.encode(npass,"UTF-8")+"&");
                OutputStreamWriter outputStream = new OutputStreamWriter(con.getOutputStream());
                outputStream.write(data.toString());
                outputStream.flush();
                if(con.getResponseCode()==HttpURLConnection.HTTP_OK)
                {
                    Log.i("Aakriti","OK");
                }
                else
                {
                    Log.i("Aakriti","Error");
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
        protected void onPostExecute(String str) {

            progressDialog.dismiss();
            callback.done(null);
            super.onPostExecute(str);
        }
    }
}
