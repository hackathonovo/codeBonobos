package dev.skliba.saviourapp.data.managers;


import android.telephony.SmsManager;
import android.widget.Toast;

import dev.skliba.saviourapp.util.SharedPrefsUtil;
import timber.log.Timber;

import static com.facebook.FacebookSdk.getApplicationContext;

public class PanicModeManager {

    public static void startPanicking() {

        sendSmsToClosestPeople();
    }

    private static void sendSmsToClosestPeople() {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(SharedPrefsUtil.getFirstPanicNumber(), null, "Help me I'm trapped at: ", null, null);
            smsManager.sendTextMessage(SharedPrefsUtil.getSecondPanicNumber(), null, "Help me I'm trapped at: ", null, null);
            Toast.makeText(getApplicationContext(), "Messages Sent", Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Timber.e(ex);
        }
    }

}
