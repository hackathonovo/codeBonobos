package dev.skliba.saviourapp.ui.splash;

import android.os.Bundle;
import android.os.Handler;

import dev.skliba.saviourapp.R;
import dev.skliba.saviourapp.di.MvpFactory;
import dev.skliba.saviourapp.ui.dashboard.MainActivity;
import dev.skliba.saviourapp.ui.login.LoginActivity;
import dev.skliba.saviourapp.ui.shared.BaseActivity;


public class SplashActivity extends BaseActivity implements SplashMvp.View {

    public static final int DELAY_MILLIS = 2000;

    private SplashMvp.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        presenter = MvpFactory.providePresenter(this);
        new Handler().postDelayed(() -> presenter.checkUserLoggedIn(), DELAY_MILLIS);
    }

    @Override
    public void navigateToMain() {
        startActivity(MainActivity.newIntent(this));
        supportFinishAfterTransition();
    }

    @Override
    public void navigateToLogin() {
        startActivity(LoginActivity.newIntent(this));
        supportFinishAfterTransition();
    }
}
