package dev.skliba.saviourapp.ui.login;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dev.skliba.saviourapp.R;
import dev.skliba.saviourapp.SaviourApplication;
import dev.skliba.saviourapp.data.managers.FacebookManager;
import dev.skliba.saviourapp.data.models.response.BaseResponse;
import dev.skliba.saviourapp.data.network.BaseCallback;
import dev.skliba.saviourapp.di.ManagerFactory;
import dev.skliba.saviourapp.di.MvpFactory;
import dev.skliba.saviourapp.ui.dashboard.MainActivity;
import dev.skliba.saviourapp.ui.shared.BaseActivity;
import dev.skliba.saviourapp.util.SharedPrefsUtil;
import retrofit2.Call;
import retrofit2.Response;
import timber.log.Timber;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.SEND_SMS;

public class LoginActivity extends BaseActivity implements LoginMvp.View {

    @BindView(R.id.username)
    EditText username;

    @BindView(R.id.password)
    EditText password;

    private static final long LOCATION_REFRESH_TIME = 2000;

    private static final float LOCATION_REFRESH_DISTANCE = 7.5f;

    private static final int RC_PERMISSIONS = 0x02;

    private static final int RC_SMS_PERM = 0x03;

    private String[] permissions = {ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION};

    private LoginMvp.Presenter presenter;

    private FacebookManager facebookManager;

    private Location currentLocation;

    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        presenter = MvpFactory.providePresenter(this);
        facebookManager = ManagerFactory.provideManager(this);
    }

    @OnClick(R.id.facebook_login)
    protected void onFacebookLoginClicked() {
        if (!facebookManager.isLoggedIn()) {
            presenter.onFacebookLoginClicked(facebookManager);
        } else {
            navigateToMain();
        }
    }

    @OnClick(R.id.native_login)
    protected void onNativeLoginClicked() {
        if (!SharedPrefsUtil.isLoggedIn()) {
            presenter.onNativeLoginClicked(username.getText().toString(), password.getText().toString());
        } else {
            navigateToMain();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebookManager.handleActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void navigateToMain() {
        startActivity(MainActivity.newIntent(this));
        supportFinishAfterTransition();
    }

    @Override
    public void showEmptyUsername(String error) {
        username.setError(error);
    }

    @Override
    public void showEmptyPassword(String error) {
        password.setError(error);
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

    @OnClick(R.id.panicMode)
    protected void onPanicModeClicked() {
        startPanicMode();
    }

    private void startPanicMode() {
        Toast.makeText(this, "Engaging panic mode", Toast.LENGTH_SHORT).show();
        fetchUserLocation();
        sendSmsToClosestPeople();
        sendLocationToApi();
    }

    private void sendLocationToApi() {
        if (currentLocation != null) {
            Call<BaseResponse<Void>> call = SaviourApplication.getApiService()
                    .sendUserLocation(SharedPrefsUtil.getUserId(), currentLocation.getLatitude(), currentLocation.getLongitude(),
                            System.currentTimeMillis());

            BaseCallback<BaseResponse<Void>> callback = new BaseCallback<BaseResponse<Void>>() {
                @Override
                public void onSuccess(BaseResponse<Void> body, Response<BaseResponse<Void>> response) {
                    Toast.makeText(LoginActivity.this,
                            "I've alerted your friends and sent your coordinates to the designated services. Don't move from your location!",
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onUnknownError(@Nullable String error) {
                    Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            };

            call.enqueue(callback);
        }
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
