package dev.skliba.saviourapp.ui.dashboard.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import dev.skliba.saviourapp.di.MvpFactory;
import dev.skliba.saviourapp.ui.guardian.GuardianAngelDialog;
import dev.skliba.saviourapp.ui.login.LoginActivity;
import dev.skliba.saviourapp.ui.shared.BaseFragment;
import dev.skliba.saviourapp.ui.shared.BaseMvp;
import dev.skliba.saviourapp.util.SharedPrefsUtil;
import dev.skliba.saviourapp.util.SimpleTextWatcher;


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
}