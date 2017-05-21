package dev.skliba.guardianangel.ui.guardian;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.OnClick;
import dev.skliba.guardianangel.R;
import dev.skliba.guardianangel.ui.shared.BaseDialogFragment;
import dev.skliba.guardianangel.ui.shared.BaseMvp;
import dev.skliba.guardianangel.utils.SharedPrefsUtil;

public class GuardianAngelDialog extends BaseDialogFragment {

    public static final String INTERVAL_SECONDS = "INTERVAL_SECONDS";

    @BindView(R.id.intervalTime)
    AppCompatSpinner intervalTime;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.panicKeyword)
    EditText panicKeyword;

    @BindView(R.id.okKeyword)
    EditText okKeyword;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_guardian_dialog;
    }

    @Override
    protected BaseMvp.Presenter providePresenter() {
        return null;
    }

    @Override
    protected int getThemeId() {
        return 0;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUi();
    }

    private void initUi() {
        toolbar.setNavigationOnClickListener(v -> dismiss());
        toolbar.setTitle("Guardian Angel");
        toolbar.setNavigationIcon(R.drawable.ic_close);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(getActivity(), R.array.intervals_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        intervalTime.setAdapter(adapter);
    }

    @OnClick(R.id.startGuardian)
    protected void onStartGuardianClicked() {
        if (intervalTime.getSelectedItemPosition() != 0 && !TextUtils.isEmpty(panicKeyword.getText().toString()) && !TextUtils
                .isEmpty(okKeyword.getText().toString())) {
            SharedPrefsUtil.setPanicKeyword(panicKeyword.getText().toString());
            SharedPrefsUtil.setOkKeyword(okKeyword.getText().toString());
            int interval = getSelectedInterval();
            Intent intent = new Intent(getActivity(), GuardianAngelService.class);
            intent.putExtra(INTERVAL_SECONDS, interval);
            getActivity().startService(intent);
        } else {
            showError("Please input all the data in order to start Guardian Angel service");
        }
    }

    public int getSelectedInterval() {
        switch (intervalTime.getSelectedItemPosition()) {
            case 1:
                return 5;
            case 2:
                return 10;
            case 3:
                return 15;
            case 4:
                return 30;
            case 5:
                return 60;
            case 6:
                return 120;
            case 7:
                return 180;
            case 8:
                return 240;
            case 9:
                return 300;
            case 10:
                return 360;
            case 11:
                return 420;
            case 12:
                return 480;
            case 13:
                return 540;
            case 14:
                return 600;
            default:
                return 120;
        }
    }
}
