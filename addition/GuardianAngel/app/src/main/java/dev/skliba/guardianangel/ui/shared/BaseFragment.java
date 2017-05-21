package dev.skliba.guardianangel.ui.shared;


import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment implements BaseMvp.View {

    private BaseMvp.ProgressView progressView;

    private BaseMvp.ErrorView errorView;

    private BaseMvp.Presenter presenter;

    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, root);
        progressView = createProgressView();
        errorView = createErrorView();
        presenter = providePresenter();
        return root;
    }

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract BaseMvp.Presenter providePresenter();

    @Nullable
    protected BaseMvp.ProgressView createProgressView() {
        return null;
    }

    @Nullable
    protected BaseMvp.ErrorView createErrorView() {
        return null;
    }

    @Nullable
    private BaseActivity getBaseActivity() {
        Activity activity = getActivity();
        if (activity == null) {
            return null;
        } else if (activity instanceof BaseActivity) {
            return (BaseActivity) activity;
        } else {
            throw new RuntimeException("Activity should extend BaseActivity!");
        }
    }

    @Override
    public void showProgress() {
        if (progressView != null) {
            progressView.showProgress();
        } else {
            BaseActivity baseActivity = getBaseActivity();
            if (baseActivity != null) {
                baseActivity.showProgress();
            }
        }
    }

    @Override
    public void hideProgress() {
        if (progressView != null) {
            progressView.dismissProgress();
        } else {
            BaseActivity baseActivity = getBaseActivity();
            if (baseActivity != null) {
                baseActivity.hideProgress();
            }
        }
    }

    @Override
    public void showError(String message) {
        showError(message, null);
    }

    @Override
    public void showError(String message, @Nullable BaseMvp.DismissListener dismissListener) {
        if (errorView != null) {
            errorView.showError(message, dismissListener);
        } else {
            BaseActivity baseActivity = getBaseActivity();
            if (baseActivity != null) {
                baseActivity.showError(message, dismissListener);
            }
        }
    }

    public void showConfirmationDialog(String message, DialogInterface.OnClickListener confirmationListener) {
        Activity baseActivity = getBaseActivity();
        if (baseActivity != null) {
            getBaseActivity().showConfirmationDialog(message, confirmationListener);
        }
    }

    @Override
    public void showError(@StringRes int resourceInt) {
        showError(resourceInt, null);
    }

    @Override
    public void showError(@StringRes int resourceInt, @Nullable BaseMvp.DismissListener dismissListener) {
        if (errorView != null) {
            errorView.showError(resourceInt, dismissListener);
        } else {
            BaseActivity baseActivity = getBaseActivity();
            if (baseActivity != null) {
                baseActivity.showError(resourceInt, dismissListener);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) {
            presenter.cancel();
        }
        unbinder.unbind();
        progressView = null;
        errorView = null;
    }
}
