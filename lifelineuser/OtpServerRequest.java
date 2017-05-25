package com.example.pc.lifelineuser;
import java.util.Random;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

//import com.google.android.gms.location.places.AutocompletePrediction;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
public class OtpServerRequest {
    ProgressDialog progressDialog;

    public OtpServerRequest(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing");
        progressDialog.setMessage("Please Wait...");
    }
    public void fetchOtpInBackground(String ph, GetOtpCallBack callback) {
        progressDialog.show();
        new FetchOtpAsyncTask(ph , callback).execute();
    }
    public class FetchOtpAsyncTask extends AsyncTask<Void, Void, String> {
        String ph;
        GetOtpCallBack callback;
        public FetchOtpAsyncTask(String ph, GetOtpCallBack callback){
            this.ph=ph;
            this.callback=callback;
        }

        @Override
        protected String doInBackground(Void... voids) {
            String pwd=null;
            String data = "";
            InputStream iStream = null;
            HttpURLConnection urlConnection = null;

            try {
                String s="abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                int a[]={new Random().nextInt(63),new Random().nextInt(63),new Random().nextInt(63),new Random().nextInt(63),new Random().nextInt(63)};
                pwd=s.charAt(a[0])+s.charAt(a[1])+s.charAt(a[2])+s.charAt(a[3])+s.charAt(a[4])+"";
                String urll = "http://api.textlocal.in/send/?username=excal.lifeline@gmail.com&hash=1be84b6624785a9aaa8e0b437d030819966dc153&numbers=" + ph + "&sender=TXTLCL&message="+pwd;
                URL url = new URL(urll);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                iStream = urlConnection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
                StringBuffer sb = new StringBuffer();
                String line = "";
                while( ( line = br.readLine()) != null){
                    sb.append(line);
                }
                data = sb.toString();
                br.close();
                }
                catch (Exception e) {
                }
            return pwd;
        }
        protected void onPostExecute(String otp){
            progressDialog.dismiss();
            callback.done(otp);
            super.onPostExecute(otp);
        }
    }
}
