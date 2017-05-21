package dev.skliba.guardianangel.ui.dashboard.news;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import java.util.List;

import butterknife.BindView;
import dev.skliba.guardianangel.R;
import dev.skliba.guardianangel.data.models.response.NewsResponse;
import dev.skliba.guardianangel.di.MvpFactory;
import dev.skliba.guardianangel.ui.shared.BaseFragment;
import dev.skliba.guardianangel.ui.shared.BaseMvp;

public class NewsFragment extends BaseFragment implements NewsMvp.View {

    @BindView(R.id.webview)
    WebView webview;

    @BindView(R.id.progressView)
    ProgressBar progressView;

    private NewsMvp.Presenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    protected BaseMvp.Presenter providePresenter() {
        presenter = MvpFactory.providePresenter(this);
        return presenter;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                if (progressView != null) {
                    progressView.setVisibility(View.GONE);
                }
            }
        });
        init();
    }

    private void init() {
        webview.setVisibility(View.VISIBLE);
        webview.loadUrl("http://www.gss.hr/");
    }

    @Override
    public void initUi(List<NewsResponse> news) {

    }
}
