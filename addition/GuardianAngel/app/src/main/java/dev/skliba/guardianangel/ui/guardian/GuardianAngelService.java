package dev.skliba.guardianangel.ui.guardian;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import dev.skliba.guardianangel.R;
import dev.skliba.guardianangel.ui.voice.VoiceActivity;


public class GuardianAngelService extends Service {

    private static final String INTERVAL_SECONDS = "INTERVAL_SECONDS";

    public static final int NOTIFICATION_ID = 422;

    public static final int HOOVER_NOTIFICATION_ID = 423;

    private ScheduledExecutorService scheduledExecutorService;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getExtras() != null) {
            raiseEnabledNotification();
            int interval = intent.getExtras().getInt(INTERVAL_SECONDS);
            scheduledExecutorService = Executors.newScheduledThreadPool(5);
            scheduledExecutorService
                    .scheduleWithFixedDelay(this::raiseNotification, interval * 1000, interval * 1000, TimeUnit.MILLISECONDS);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void raiseEnabledNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);

        Intent intent = new Intent(getApplicationContext(), GuardianBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 765, intent, 0);

        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_logo_user)
                .setContentTitle("Guardian Angel")
                .setContentText("Guardian Angel deployed. Don't worry, I got your back")
                .setDeleteIntent(pendingIntent)
                .build();

        notificationManager.notify(HOOVER_NOTIFICATION_ID, notification);
    }

    private void raiseNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_logo_user)
                .setContentTitle("Guardian Angel")
                .setContentText("Is everything okay?")

                .build();

        notificationManager.notify(NOTIFICATION_ID, notification);

        AudioManager audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);
        MediaPlayer mp = MediaPlayer.create(this, R.raw.notification_noise);
        mp.start();

        Intent voiceActivityIntent = new Intent(getApplicationContext(), VoiceActivity.class);
        voiceActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        voiceActivityIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(voiceActivityIntent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        scheduledExecutorService.shutdown();
    }
}
