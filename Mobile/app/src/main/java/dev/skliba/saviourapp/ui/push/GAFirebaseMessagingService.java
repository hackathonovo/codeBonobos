package dev.skliba.saviourapp.ui.push;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import dev.skliba.saviourapp.R;
import dev.skliba.saviourapp.ui.action_details.ActionDetailsActivity;


public class GAFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "SaviourApp";

    public static final String EXTRA_ACTION_ID = "EXTRA_ACTION_ID";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            showNotification(remoteMessage.getData().get("actionId"));

        }
    }

    private void showNotification(String actionId) {

        Intent intent = new Intent(this, ActionDetailsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(EXTRA_ACTION_ID, actionId);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("There's a new action that requires your attention")
                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{500L, 200L, 200L, 500L});

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}
