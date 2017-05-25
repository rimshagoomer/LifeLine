package com.example.user.driver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gms.maps.model.LatLng;

import java.lang.reflect.Method;

import static com.example.user.driver.CommonUtilities.SENDER_ID;
import static com.example.user.driver.CommonUtilities.displayMessage;

public class GCMIntentService extends GCMBaseIntentService {

    private static final String TAG = "GCMIntentService";


    public GCMIntentService() {
        super(SENDER_ID);
    }



    /**
     * Method called on device registered
     **/
    @Override
    protected void onRegistered(Context context, String registrationId) {
        Log.i(TAG, "Device registered: regId = " + registrationId);
        displayMessage(context, "Your device registred with GCM");
        Log.d("NAME", SignUp.names);
        ServerUtilities.register(context,SignUp.names,SignUp.phone1,SignUp.emer1,SignUp.emer2,SignUp.emails,SignUp.password1,SignUp.hosp11,SignUp.regId);
    }

    /**
     * Method called on device un registred
     * */
    @Override
    protected void onUnregistered(Context context, String registrationId) {
        Log.i(TAG, "Device unregistered");
        displayMessage(context, "Unregistered");
        ServerUtilities.unregister(context, registrationId);
    }

    /**
     * Method called on Receiving a new message
     * */
    @Override
    protected void onMessage(Context context, Intent intent) {
        Log.i(TAG, "Received message");
        String message = intent.getExtras().getString("product");
        String msg1=intent.getExtras().getString("lat");
        String msg2=intent.getExtras().getString("long");
        String ph11=intent.getExtras().getString("phone");
        Log.i("Aakriti",msg1+" "+msg2);
        MapsActivity.ulatitude=msg1;
        MapsActivity.ulongitude=msg2;
        MapsActivity.userphone=ph11;
//        if (!MapsActivity.ulatitude.equals(""))
//        {
//            double d1=Double.parseDouble(MapsActivity.ulatitude);
//            double d2=Double.parseDouble(MapsActivity.ulongitude);
//            LatLng latLng=new LatLng(d1,d2);
//            MapsActivity.userMarkers(latLng);
//        }


        //displayMessage(context, message);
        // notifies user

        generateNotification(context, message);
    }

    /**
     * Method called on receiving a deleted message
     * */
    @Override
    protected void onDeletedMessages(Context context, int total) {
        Log.i(TAG, "Received deleted messages notification");
        String message ="Server deleted pending messages";
        //displayMessage(context, message);
        // notifies user
        generateNotification(context, message);
    }

    /**
     * Method called on Error
     * */
    @Override
    public void onError(Context context, String errorId) {
        Log.i(TAG, "Received error: " + errorId);
        displayMessage(context, "On Error");
    }

    @Override
    protected boolean onRecoverableError(Context context, String errorId) {
        // log message
        Log.i(TAG, "Received recoverable error: " + errorId);
        displayMessage(context, "On recoverable er");
        return super.onRecoverableError(context, errorId);
    }

    /**
     * Issues a notification to inform the user that server has sent a message.
     */
    private static void generateNotification(Context context, String message) {
        int icon = R.drawable.ic_launcher;
        long when = System.currentTimeMillis();
        String title = "Patient Call";
       Intent notificationIntent = new Intent(context, MapsActivity.class);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                context);

        PendingIntent intent =
                PendingIntent.getActivity(context, 0, notificationIntent, 0);
        Notification notification = mBuilder.setSmallIcon(icon).setTicker(title).setWhen(0)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setContentIntent(intent)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                .setContentText(message).build();



        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
//        Notification notification = new Notification(icon, message, when);



        // set intent so it does not start a new activity
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);

        Toast.makeText(context,"message="+message,Toast.LENGTH_LONG).show();

        try {
            Method deprecatedMethod = notification.getClass().getMethod("setLatestEventInfo", Context.class, CharSequence.class, CharSequence.class, PendingIntent.class);
            deprecatedMethod.invoke(notification, context, title, message, intent);
        } catch (Exception e) {
            Log.w(TAG, "Method not found", e);
        }
        //notification.setLatestEventInfo(context, title, message, intent);




        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        // Play default notification sound
        notification.defaults |= Notification.DEFAULT_SOUND;

        //notification.sound = Uri.parse("android.resource://" + context.getPackageName() + "your_sound_file_name.mp3");

        // Vibrate if vibrate is enabled
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notificationManager.notify(0, notification);

    }

}

