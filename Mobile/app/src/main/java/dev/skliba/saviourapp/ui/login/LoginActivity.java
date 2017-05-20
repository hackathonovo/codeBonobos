package dev.skliba.saviourapp.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dev.skliba.saviourapp.R;
import dev.skliba.saviourapp.data.managers.FacebookManager;
import dev.skliba.saviourapp.di.ManagerFactory;
import dev.skliba.saviourapp.di.MvpFactory;
import dev.skliba.saviourapp.ui.dashboard.MainActivity;
import dev.skliba.saviourapp.ui.shared.BaseActivity;
import dev.skliba.saviourapp.util.SharedPrefsUtil;

public class LoginActivity extends BaseActivity implements LoginMvp.View {

    @BindView(R.id.username)
    EditText username;

    @BindView(R.id.password)
    EditText password;

    private LoginMvp.Presenter presenter;

    private FacebookManager facebookManager;

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
        initUi();
    }

    private void initUi() {

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
}
