package dev.skliba.saviourapp.ui.dashboard.contact;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.OnClick;
import dev.skliba.saviourapp.R;
import dev.skliba.saviourapp.data.network.ApiManagerImpl;
import dev.skliba.saviourapp.di.MvpFactory;
import dev.skliba.saviourapp.ui.shared.BaseFragment;
import dev.skliba.saviourapp.ui.shared.BaseMvp;

public class ContactFragment extends BaseFragment implements ContactMvp.View {

    private static final int RC_PERMISSIONS = 0x06;

    @BindView(R.id.webview)
    WebView webview;

    @BindView(R.id.contactLeader)
    Button contactLeader;

    @BindView(R.id.emptyView)
    LinearLayout emptyView;

    @BindView(R.id.content)
    LinearLayout content;

    private ContactMvp.Presenter presenter;

    private String leaderPhone;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_contact;
    }

    @Override
    protected BaseMvp.Presenter providePresenter() {
        presenter = MvpFactory.providePresenter(this);
        return presenter;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = MvpFactory.providePresenter(this);
        presenter.init();
    }

    @Override
    public void showEmptyUi() {
        content.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadWebViewUrl(String url, String leaderPhoneNo) {
        this.leaderPhone = leaderPhoneNo;
        emptyView.setVisibility(View.GONE);
        content.setVisibility(View.VISIBLE);
        webview.loadUrl(url);
    }

    @OnClick(R.id.contactLeader)
    protected void onContactLeaderClicked() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_CALL);

            intent.setData(Uri.parse("tel:" + leaderPhone));
            startActivity(intent);
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, RC_PERMISSIONS);
        }
    }

    @OnClick(R.id.addAction)
    protected void onAddActionClicked() {
        Intent intent= new Intent(Intent.ACTION_VIEW,Uri.parse(ApiManagerImpl.WEB_ENDPOINT + "/#/addAction"));
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RC_PERMISSIONS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onContactLeaderClicked();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }
}