package dev.skliba.guardianangel.ui.dashboard.profile;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import dev.skliba.guardianangel.GuardianAngelApp;
import dev.skliba.guardianangel.R;
import dev.skliba.guardianangel.data.models.response.BaseResponse;
import dev.skliba.guardianangel.data.network.BaseCallback;
import dev.skliba.guardianangel.di.MvpFactory;
import dev.skliba.guardianangel.ui.guardian.GuardianAngelDialog;
import dev.skliba.guardianangel.ui.login.LoginActivity;
import dev.skliba.guardianangel.ui.shared.BaseFragment;
import dev.skliba.guardianangel.ui.shared.BaseMvp;
import dev.skliba.guardianangel.utils.SharedPrefsUtil;
import dev.skliba.guardianangel.utils.SimpleTextWatcher;
import retrofit2.Call;
import retrofit2.Response;
import timber.log.Timber;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.SEND_SMS;
import static android.content.Context.LOCATION_SERVICE;

public class ProfileFragment extends BaseFragment implements ProfileMvp.View {

    @BindView(R.id.userImage)
    CircleImageView userImage;

    @BindView(R.id.userName)
    TextView userName;

    @BindView(R.id.firstPersonNumber)
    EditText firstPersonNumber;

    @BindView(R.id.secondPersonNumber)
    EditText secondPersonNumber;

    @BindView(R.id.saveNumbers)
    Button saveNumbers;

    private static final long LOCATION_REFRESH_TIME = 2000;

    private static final float LOCATION_REFRESH_DISTANCE = 7.5f;

    private static final int RC_PERMISSIONS = 0x02;

    private static final int RC_SMS_PERM = 0x03;

    private String[] permissions = {ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION};

    private Location currentLocation;


    private ProfileMvp.Presenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_profile;
    }

    @Override
    protected BaseMvp.Presenter providePresenter() {
        presenter = MvpFactory.providePresenter(this);
        return presenter;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUi();
    }

    private void initUi() {
        userImage.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_user));
        userName.setText("George Clooney");
        if (!TextUtils.isEmpty(SharedPrefsUtil.getFirstPanicNumber())) {
            firstPersonNumber.setText("");
            firstPersonNumber.setText(SharedPrefsUtil.getFirstPanicNumber());
        }
        if (!TextUtils.isEmpty(SharedPrefsUtil.getSecondPanicNumber())) {
            secondPersonNumber.setText("");
            secondPersonNumber.setText(SharedPrefsUtil.getFirstPanicNumber());
        }
        firstPersonNumber.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                saveNumbers.setEnabled(true);
            }
        });
        secondPersonNumber.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                saveNumbers.setEnabled(true);
            }
        });
    }

    @OnClick(R.id.saveNumbers)
    protected void onSaveNumbersClicked() {
        if (!TextUtils.isEmpty(firstPersonNumber.getText().toString()) && firstPersonNumber.getText().toString().length() > 5) {
            SharedPrefsUtil.setFirstPanicNumber(firstPersonNumber.getText().toString());
        }
        if (!TextUtils.isEmpty(secondPersonNumber.getText().toString()) && secondPersonNumber.getText().toString().length() > 5) {
            SharedPrefsUtil.setFirstPanicNumber(secondPersonNumber.getText().toString());
        }

        Toast.makeText(getActivity(), "Successfully saved phones", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.signOut)
    protected void signOutClicked() {
        presenter.signOut();
    }

    @OnClick(R.id.guardianAngel)
    protected void onGuardianAngelClicked() {
        new GuardianAngelDialog().show(getChildFragmentManager(), "myTag");
    }

    @Override
    public void navigateToLogin() {
        startActivity(LoginActivity.newIntent(getActivity()));
        getActivity().supportFinishAfterTransition();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RC_PERMISSIONS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchUserLocation();
                } else {
                    Toast.makeText(getActivity(), "You have declined the permissions for location therefore I'm not activating panic mode",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case RC_SMS_PERM:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sendSmsToClosestPeople();
                } else {
                    Toast.makeText(getActivity(), "You have declined sms permissions, I wont send any messages to your closest people",
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
        Toast.makeText(getActivity(), "Engaging panic mode", Toast.LENGTH_SHORT).show();
        fetchUserLocation();
        sendSmsToClosestPeople();
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
                    Toast.makeText(getActivity(),
                            "I've alerted your friends and sent your coordinates to the designated services. Don't move from your location!",
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onUnknownError(@Nullable String error) {
                    Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                }
            };

            call.enqueue(callback);
        }
    }

    private void sendSmsToClosestPeople() {
        if (ContextCompat.checkSelfPermission(getActivity(), SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            try {
                SmsManager smsManager = SmsManager.getDefault();

                smsManager.sendTextMessage(SharedPrefsUtil.getFirstPanicNumber(), null,
                        "Help me I'm trapped at: " + currentLocation.getLatitude() + " " + currentLocation.getLongitude()
                                + "\nThis is NOT a joke! Call for help now!", null, null);

                smsManager.sendTextMessage(SharedPrefsUtil.getSecondPanicNumber(), null,
                        "Help me I'm trapped at: " + currentLocation.getLatitude() + " " + currentLocation.getLongitude()
                                + "\nThis is NOT a joke! Call for help now!", null, null);

                Toast.makeText(getActivity(), "Messages Sent", Toast.LENGTH_LONG).show();
            } catch (Exception ex) {
                Timber.e(ex);
            }
        } else {
            requestPermissions(new String[]{Manifest.permission.SEND_SMS}, RC_SMS_PERM);
        }
    }

    private void fetchUserLocation() {
        LocationManager mLocationManager = (LocationManager) GuardianAngelApp.getInstance().getSystemService(LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(getActivity(), ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(getActivity(), ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
                    LOCATION_REFRESH_DISTANCE, locationListener);
            currentLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        } else {
            requestPermissions(permissions, RC_PERMISSIONS);
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