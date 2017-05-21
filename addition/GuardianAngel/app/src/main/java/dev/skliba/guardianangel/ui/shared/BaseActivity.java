package dev.skliba.guardianangel.ui.shared;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public abstract class BaseActivity extends AppCompatActivity implements BaseMvp.View {

    private BaseMvp.ErrorView errorView;

    private BaseMvp.ProgressView progressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        errorView = new AlertDialogErrorView(this);
        progressView = createProgressView();
    }

    private BaseMvp.ProgressView createProgressView() {
        return new ProgressDialogDelegate(this);
    }

    @Override
    public void showProgress() {
        hideKeyboard();
        progressView.showProgress();
    }

    @Override
    public void hideProgress() {
        progressView.dismissProgress();
    }

    protected void hideKeyboard() {
        View focusedView = getCurrentFocus();
        if (focusedView != null) {
            focusedView.clearFocus();
            InputMethodManager inputMethodManager = (InputMethodManager) focusedView.getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
        }
    }

    @Override
    public void showError(String message) {
        errorView.showError(message);
    }

    @Override
    public void showError(String message, @Nullable BaseMvp.DismissListener dismissListener) {
        errorView.showError(message, dismissListener);
    }

    @Override
    public void showError(@StringRes int resourceInt) {
        errorView.showError(resourceInt);
    }

    @Override
    public void showError(@StringRes int resourceInt, @Nullable BaseMvp.DismissListener dismissListener) {
        errorView.showError(resourceInt, dismissListener);
    }

    public void openInBrowser(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        if (browserIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(browserIntent);
        } else {
            Log.d("GUADRIANANGEL", "Browser not found!");
        }
    }

    public void showConfirmationDialog(@StringRes int resourceInt, DialogInterface.OnClickListener confirmationListener) {
        showConfirmationDialog(getString(resourceInt), confirmationListener);
    }

    public void showConfirmationDialog(String message, DialogInterface.OnClickListener confirmationListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, confirmationListener)
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

    @Override
    public void onBackPressed() {
        if (isTaskRoot()) {
            supportFinishAfterTransition();
            Intent parentActivityIntent = NavUtils.getParentActivityIntent(this);
            if (parentActivityIntent != null) {
                startActivity(parentActivityIntent);
                supportFinishAfterTransition();
            }
        } else {
            super.onBackPressed();
        }
    }
}
