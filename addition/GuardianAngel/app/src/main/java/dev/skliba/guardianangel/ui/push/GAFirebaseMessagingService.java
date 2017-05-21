package dev.skliba.guardianangel.ui.push;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat;

import dev.skliba.guardianangel.R;

public class GAFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            showNotification(remoteMessage.getData().get("actionId"));
        }
    }

    private void showNotification(String actionId) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_logo_user)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("There's a new action that requires your attention")
                .setVibrate(new long[]{500L, 200L, 200L, 500L});

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}
