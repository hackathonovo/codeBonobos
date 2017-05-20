package dev.skliba.saviourapp.ui.voice;


import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.widget.Toast;

import java.util.ArrayList;

import dev.skliba.saviourapp.data.managers.PanicModeManager;
import dev.skliba.saviourapp.ui.guardian.GuardianAngelService;
import dev.skliba.saviourapp.ui.shared.BaseActivity;
import dev.skliba.saviourapp.util.SharedPrefsUtil;

public class VoiceActivity extends BaseActivity {

    public static final int RC_VOICE = 0x00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak keyword in order to determine whether you're okay");
        startActivityForResult(i, RC_VOICE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RC_VOICE:
                if (resultCode == RESULT_OK) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    for (String input : result) {
                        if (input.contains(" ")) {
                            String[] inputs = input.split(" ");
                            for (String i : inputs) {
                                if (i.equals(SharedPrefsUtil.panicKeyword())) {
                                    startPanicMode();
                                } else if (i.equals(SharedPrefsUtil.okKeyword())) {
                                    dismissCurrentNotification();
                                } else if (i.equals("kill")) {
                                    killEverything();
                                }
                            }
                        }
                    }
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void killEverything() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
        stopService(new Intent(this, GuardianAngelService.class));
        supportFinishAfterTransition();
    }

    private void dismissCurrentNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);
        notificationManager.cancel(GuardianAngelService.NOTIFICATION_ID);
        Toast.makeText(this, "Dismissing current notification", Toast.LENGTH_SHORT).show();
    }

    private void startPanicMode() {
        Toast.makeText(this, "Engaging panic mode", Toast.LENGTH_SHORT).show();
        stopService(new Intent(this, GuardianAngelService.class));
        PanicModeManager.startPanicking();
    }
}
