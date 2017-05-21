package dev.skliba.guardianangel.ui.dashboard.nfc.new_scan;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import be.appfoundry.nfclibrary.activities.NfcActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import dev.skliba.guardianangel.GuardianAngelApp;
import dev.skliba.guardianangel.R;
import dev.skliba.guardianangel.data.models.response.BaseResponse;
import dev.skliba.guardianangel.data.network.BaseCallback;
import dev.skliba.guardianangel.utils.SharedPrefsUtil;
import retrofit2.Call;
import retrofit2.Response;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class NfcScanActivity extends NfcActivity {

    @BindView(R.id.webview)
    WebView webview;

    private static final long LOCATION_REFRESH_TIME = 2000;

    private static final float LOCATION_REFRESH_DISTANCE = 7.5f;

    private static final int RC_PERMISSIONS = 0x02;

    @BindView(R.id.progressView)
    ProgressBar progressView;

    @BindView(R.id.desription)
    TextView desription;

    private String[] permissions = {ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION};

    private Location currentLocation;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        for (String message : getNfcMessages()) {
            if (!TextUtils.isEmpty(message)) {
                progressView.setVisibility(View.VISIBLE);
                webview.setVisibility(View.VISIBLE);
                desription.setVisibility(View.GONE);
                triggerSendingLocation();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_scan);
        ButterKnife.bind(this);
        enableBeam();
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                if (progressView != null) {
                    progressView.setVisibility(View.GONE);
                }
            }
        });
    }

    private void triggerSendingLocation() {
        fetchUserLocation();
        sendLocationToApi();
    }

    private void sendLocationToApi() {
        if (currentLocation != null) {
            Call<BaseResponse<Void>> call = GuardianAngelApp.getApiService()
                    .sendUserLocation(SharedPrefsUtil.getUserId(), currentLocation.getLatitude(), currentLocation.getLongitude(),
                            System.currentTimeMillis());

            BaseCallback<BaseResponse<Void>> callback = new BaseCallback<BaseResponse<Void>>() {
                @Override
                public void onSuccess(BaseResponse<Void> body, Response<BaseResponse<Void>> response) {
                    webview.loadUrl("http://www.prirodni-svijet.com/dinara/");
                }

                @Override
                public void onUnknownError(@Nullable String error) {
                    Toast.makeText(NfcScanActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            };

            call.enqueue(callback);
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
