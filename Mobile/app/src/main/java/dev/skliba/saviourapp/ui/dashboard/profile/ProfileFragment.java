package dev.skliba.saviourapp.ui.dashboard.profile;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import dev.skliba.saviourapp.R;
import dev.skliba.saviourapp.SaviourApplication;
import dev.skliba.saviourapp.data.models.response.BaseResponse;
import dev.skliba.saviourapp.data.network.BaseCallback;
import dev.skliba.saviourapp.di.MvpFactory;
import dev.skliba.saviourapp.ui.guardian.GuardianAngelDialog;
import dev.skliba.saviourapp.ui.login.LoginActivity;
import dev.skliba.saviourapp.ui.shared.BaseFragment;
import dev.skliba.saviourapp.ui.shared.BaseMvp;
import dev.skliba.saviourapp.util.SharedPrefsUtil;
import dev.skliba.saviourapp.util.SimpleTextWatcher;
import retrofit2.Call;
import retrofit2.Response;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.Context.LOCATION_SERVICE;


public class ProfileFragment extends BaseFragment implements ProfileMvp.View {

    @BindView(R.id.userImage)
    CircleImageView userImage;

    @BindView(R.id.userName)
    TextView userName;

    @BindView(R.id.isUserAvailable)
    CheckBox isUserAvailable;

    @BindView(R.id.firstPersonNumber)
    EditText firstPersonNumber;

    @BindView(R.id.secondPersonNumber)
    EditText secondPersonNumber;

    @BindView(R.id.saveNumbers)
    Button saveNumbers;

    private static final long LOCATION_REFRESH_TIME = 2000;

    private static final float LOCATION_REFRESH_DISTANCE = 7.5f;

    private static final int RC_PERMISSIONS = 0x02;

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
        userImage.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_user_placeholder));
        userName.setText("George Clooney");
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
    public void success() {
        Toast.makeText(getActivity(), "Successfully updated availability", Toast.LENGTH_SHORT).show();
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

    @OnCheckedChanged(R.id.isUserAvailable)
    protected void onUserAvailableToggled() {
        presenter.onUserAvailable(isUserAvailable.isChecked());
    }

    @OnClick(R.id.sendLocation)
    protected void onSendLocationClicked() {
        fetchUserLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RC_PERMISSIONS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchUserLocation();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void sendUserLocation() {
        Call<BaseResponse<Void>> call = SaviourApplication.getApiService()
                .sendUserLocation(SharedPrefsUtil.getUserId(), currentLocation.getLatitude(), currentLocation.getLongitude(),
                        System.currentTimeMillis());

        BaseCallback<BaseResponse<Void>> callback = new BaseCallback<BaseResponse<Void>>() {
            @Override
            public void onSuccess(BaseResponse<Void> body, Response<BaseResponse<Void>> response) {
                Toast.makeText(getActivity(), "Successfully sent location", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnknownError(@Nullable String error) {

            }
        };

        call.enqueue(callback);
    }

    private void fetchUserLocation() {
        LocationManager mLocationManager = (LocationManager) SaviourApplication.getInstance().getSystemService(LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(getActivity(), ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(getActivity(), ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
                    LOCATION_REFRESH_DISTANCE, locationListener);
            currentLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            sendUserLocation();
        } else {
            ActivityCompat.requestPermissions(getActivity(), permissions, RC_PERMISSIONS);
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