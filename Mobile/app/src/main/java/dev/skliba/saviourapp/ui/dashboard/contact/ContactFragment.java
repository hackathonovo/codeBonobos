package dev.skliba.saviourapp.ui.dashboard.contact;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import butterknife.BindView;
import dev.skliba.saviourapp.R;
import dev.skliba.saviourapp.di.MvpFactory;
import dev.skliba.saviourapp.ui.shared.BaseFragment;
import dev.skliba.saviourapp.ui.shared.BaseMvp;

public class ContactFragment extends BaseFragment implements ContactMvp.View {

    @BindView(R.id.webview)
    WebView webview;

    @BindView(R.id.contactLeader)
    Button contactLeader;

    private ContactMvp.Presenter presenter;

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
        contactLeader.setVisibility(View.GONE);
        webview.setVisibility(View.GONE);


    }

    @Override
    public void loadWebViewUrl(String url) {
        webview.setVisibility(View.VISIBLE);
        webview.loadUrl(url);
    }
}