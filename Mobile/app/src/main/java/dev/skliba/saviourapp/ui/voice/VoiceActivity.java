package dev.skliba.saviourapp.ui.voice;


import android.Manifest;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.widget.Toast;

import java.util.ArrayList;

import dev.skliba.saviourapp.SaviourApplication;
import dev.skliba.saviourapp.data.models.response.BaseResponse;
import dev.skliba.saviourapp.data.network.BaseCallback;
import dev.skliba.saviourapp.ui.guardian.GuardianAngelService;
import dev.skliba.saviourapp.ui.shared.BaseActivity;
import dev.skliba.saviourapp.util.SharedPrefsUtil;
import retrofit2.Call;
import retrofit2.Response;
import timber.log.Timber;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.SEND_SMS;

public class VoiceActivity extends BaseActivity {

    public static final int RC_VOICE = 0x00;

    private static final long LOCATION_REFRESH_TIME = 2000;

    private static final float LOCATION_REFRESH_DISTANCE = 7.5f;

    private static final int RC_PERMISSIONS = 0x02;

    public static final String KILL_COMMAND = "kill";

    private static final int RC_SMS_PERM = 0x03;

    public static final int DELAY_MILLIS = 2000;

    private String[] permissions = {ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION};

    private Location currentLocation;

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
                                } else if (i.equals(KILL_COMMAND)) {
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RC_PERMISSIONS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchUserLocation();
                } else {
                    Toast.makeText(this, "You have declined the permissions for location therefore I'm not activating panic mode",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case RC_SMS_PERM:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sendSmsToClosestPeople();
                } else {
                    Toast.makeText(this, "You have declined sms permissions, I wont send any messages to your closest people",
                            Toast.LENGTH_SHORT).show();
                }
                break;
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
        supportFinishAfterTransition();
    }

    private void startPanicMode() {
        Toast.makeText(this, "Engaging panic mode", Toast.LENGTH_SHORT).show();
        stopService(new Intent(this, GuardianAngelService.class));
        fetchUserLocation();
        sendSmsToClosestPeople();
        sendLocationToApi();
    }

    private void sendLocationToApi() {
        Call<BaseResponse<Void>> call = SaviourApplication.getApiService()
                .sendUserLocation(SharedPrefsUtil.getUserId(), currentLocation.getLatitude(), currentLocation.getLongitude(),
                        System.currentTimeMillis());

        BaseCallback<BaseResponse<Void>> callback = new BaseCallback<BaseResponse<Void>>() {
            @Override
            public void onSuccess(BaseResponse<Void> body, Response<BaseResponse<Void>> response) {
                Toast.makeText(VoiceActivity.this,
                        "I've alerted your friends and sent your coordinates to the designated services. Don't move from your location!",
                        Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(() -> killEverything(), DELAY_MILLIS);
            }

            @Override
            public void onUnknownError(@Nullable String error) {
                Toast.makeText(VoiceActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        };

        call.enqueue(callback);
    }

    private void sendSmsToClosestPeople() {
        if (ContextCompat.checkSelfPermission(this, SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            try {
                SmsManager smsManager = SmsManager.getDefault();

                smsManager.sendTextMessage(SharedPrefsUtil.getFirstPanicNumber(), null,
                        "Help me I'm trapped at: " + currentLocation.getLatitude() + " " + currentLocation.getLongitude()
                                + "\nThis is NOT a joke! Call for help now!", null, null);

                smsManager.sendTextMessage(SharedPrefsUtil.getSecondPanicNumber(), null,
                        "Help me I'm trapped at: " + currentLocation.getLatitude() + " " + currentLocation.getLongitude()
                                + "\nThis is NOT a joke! Call for help now!", null, null);

                Toast.makeText(getApplicationContext(), "Messages Sent", Toast.LENGTH_LONG).show();
            } catch (Exception ex) {
                Timber.e(ex);
            }
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, RC_SMS_PERM);
        }
    }

    private void fetchUserLocation() {
        LocationManager mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
                    LOCATION_REFRESH_DISTANCE, locationListener);
            currentLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        } else {
            ActivityCompat.requestPermissions(this, permissions, RC_PERMISSIONS);
        }
    }

    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            currentLocation = location;

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };
}
