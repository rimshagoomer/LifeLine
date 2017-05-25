package com.example.user.driver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public final class CommonUtilities {

    // give your server registration url here
    public static final String SERVER_URL = "lifelineplus.3eeweb.com/register.php";

    // Google project id
    public static final String SENDER_ID = "20284206474";

    /**
     * Tag used on log messages.
     */
    static final String TAG = "Aakriti";

    static final String DISPLAY_MESSAGE_ACTION =
            "com.example.pc.driverfinal.DISPLAY_MESSAGE";

    static final String EXTRA_MESSAGE = "message";

    /**
     * Notifies UI to display a message.
     * <p>
     * This method is defined in the common helper because it's used both by
     * the UI and the background service.
     *
     * @param context application's context.
     * @param message message to be displayed.
     */
    static void displayMessage(Context context, String message) {
        Intent intent = new Intent("com.example.pc.driverfinal.DISPLAY_MESSAGE");
        Log.i("Aakriti", "rimi");
        intent.putExtra(EXTRA_MESSAGE, message);
        context.sendBroadcast(intent);
    }
}
