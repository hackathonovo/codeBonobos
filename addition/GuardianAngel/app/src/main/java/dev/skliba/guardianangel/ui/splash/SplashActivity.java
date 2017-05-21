package dev.skliba.guardianangel.ui.splash;

import android.os.Bundle;
import android.os.Handler;

import dev.skliba.guardianangel.R;
import dev.skliba.guardianangel.di.MvpFactory;
import dev.skliba.guardianangel.ui.dashboard.MainActivity;
import dev.skliba.guardianangel.ui.login.LoginActivity;
import dev.skliba.guardianangel.ui.shared.BaseActivity;

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
