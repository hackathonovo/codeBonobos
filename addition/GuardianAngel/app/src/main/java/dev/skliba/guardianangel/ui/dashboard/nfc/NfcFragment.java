package dev.skliba.guardianangel.ui.dashboard.nfc;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import butterknife.OnClick;
import dev.skliba.guardianangel.R;
import dev.skliba.guardianangel.ui.dashboard.nfc.new_scan.NfcScanActivity;
import dev.skliba.guardianangel.ui.shared.BaseFragment;
import dev.skliba.guardianangel.ui.shared.BaseMvp;

public class NfcFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nfc;
    }

    @Override
    protected BaseMvp.Presenter providePresenter() {
        return null;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick(R.id.scan)
    protected void onScanClicked() {
        startActivity(new Intent(getActivity(), NfcScanActivity.class));
    }
}
