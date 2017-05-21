package dev.skliba.guardianangel.ui.dashboard;

import com.roughike.bottombar.BottomBar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.skliba.guardianangel.R;
import dev.skliba.guardianangel.ui.dashboard.news.NewsFragment;
import dev.skliba.guardianangel.ui.dashboard.nfc.NfcFragment;
import dev.skliba.guardianangel.ui.dashboard.profile.ProfileFragment;
import dev.skliba.guardianangel.ui.shared.BaseActivity;

public class MainActivity extends BaseActivity {

    @BindView(R.id.container)
    FrameLayout container;

    @BindView(R.id.bottomBar)
    BottomBar bottomBar;

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initUi();
    }

    private void initUi() {

        bottomBar.setOnTabSelectListener(tabId -> {
            Fragment fragment;
            switch (tabId) {
                case R.id.newsTab:
                    fragment = new NewsFragment();
                    break;
                case R.id.favoritesTab:
                    fragment = new NfcFragment();
                    break;
                case R.id.profileTab:
                    fragment = new ProfileFragment();
                    break;
                default:
                    throw new RuntimeException("Unknown bottom bar tab: " + tabId);
            }

            if (fragment != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commitNow();
            }
        });
    }
}
