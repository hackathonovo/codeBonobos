package dev.skliba.guardianangel.ui.dashboard.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import dev.skliba.guardianangel.R;
import dev.skliba.guardianangel.di.MvpFactory;
import dev.skliba.guardianangel.ui.guardian.GuardianAngelDialog;
import dev.skliba.guardianangel.ui.login.LoginActivity;
import dev.skliba.guardianangel.ui.shared.BaseFragment;
import dev.skliba.guardianangel.ui.shared.BaseMvp;
import dev.skliba.guardianangel.utils.SimpleTextWatcher;

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
}