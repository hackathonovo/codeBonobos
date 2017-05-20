package dev.skliba.saviourapp.ui.dashboard.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import dev.skliba.saviourapp.R;
import dev.skliba.saviourapp.di.MvpFactory;
import dev.skliba.saviourapp.ui.login.LoginActivity;
import dev.skliba.saviourapp.ui.shared.BaseFragment;
import dev.skliba.saviourapp.ui.shared.BaseMvp;


public class ProfileFragment extends BaseFragment implements ProfileMvp.View {

    @BindView(R.id.userImage)
    CircleImageView userImage;

    @BindView(R.id.userName)
    TextView userName;

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
    }

    @OnClick(R.id.signOut)
    protected void signOutClicked() {
        presenter.signOut();
    }

    @OnClick(R.id.guardianAngel)
    protected void onGuardianAngelClicked() {

    }

    @Override
    public void navigateToLogin() {
        startActivity(LoginActivity.newIntent(getActivity()));
        getActivity().supportFinishAfterTransition();
    }
}