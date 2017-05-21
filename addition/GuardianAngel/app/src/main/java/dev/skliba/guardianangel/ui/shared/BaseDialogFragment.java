package dev.skliba.guardianangel.ui.shared;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dev.skliba.guardianangel.R;
import timber.log.Timber;

public abstract class BaseDialogFragment extends AppCompatDialogFragment implements BaseMvp.View {

    private BaseMvp.ProgressView progressView;

    private BaseMvp.ErrorView errorView;

    private BaseMvp.Presenter presenter;

    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.AppTheme_SlideUpDown);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        Window window = dialog.getWindow();
        if (window != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getThemeStatusBarColor());
        }
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root;

        //This piece of code inflates a layout with a specific theme set especially for dialog fragments in case you wish to have a specific
        //theme set. This is used to color item's text in a specific color thats declared in themes.xml
        if (getThemeId() != 0) {
            Context contextThemeWrapper = new ContextThemeWrapper(getContext(), getThemeId());
            LayoutInflater infl = inflater.cloneInContext(contextThemeWrapper);
            root = infl.inflate(getLayoutId(), container, false);
        } else {
            root = inflater.inflate(getLayoutId(), container, false);
        }

        unbinder = ButterKnife.bind(this, root);
        progressView = createProgressView();
        errorView = createErrorView();
        presenter = providePresenter();
        return root;
    }

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract BaseMvp.Presenter providePresenter();

    /**
     * The theme you wish your dialog to be set to, provides a way to make a custom theme in order to color items you can't reach through
     * toolbar.
     */
    protected abstract int getThemeId();

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

    @Override
    public void showError(@StringRes int resourceInt) {
        showError(resourceInt, null);
    }

    @Override
    public void showError(@StringRes int resourceInt, @Nullable BaseMvp.DismissListener dismissListener) {
        showError(getString(resourceInt), dismissListener);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @ColorInt
    private int getThemeStatusBarColor() {
        final TypedValue value = new TypedValue();
        boolean valueFound = getContext().getTheme().resolveAttribute(android.R.attr.statusBarColor, value, true);
        if (!valueFound) {
            valueFound = getContext().getTheme().resolveAttribute(R.attr.colorPrimaryDark, value, true);
        }

        if (!valueFound) {
            Timber.d("colorPrimaryDark or android:statusBarColor was not found in theme!");
            return Color.BLACK;
        } else {
            return value.data;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        progressView = null;
        errorView = null;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (presenter != null) {
            presenter.cancel();
        }
    }
}
