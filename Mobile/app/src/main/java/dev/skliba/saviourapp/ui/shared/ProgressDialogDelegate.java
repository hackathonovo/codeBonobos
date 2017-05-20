package dev.skliba.saviourapp.ui.shared;


import android.app.Activity;
import android.app.ProgressDialog;
import android.support.annotation.NonNull;

import dev.skliba.saviourapp.R;

class ProgressDialogDelegate implements BaseMvp.ProgressView {

    private final Activity activity;

    private final ProgressDialog progressDialog;

    private final String genericLoadingMessage;

    ProgressDialogDelegate(@NonNull Activity activity) {
        this.activity = activity;
        progressDialog = new ProgressDialog(activity);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        this.genericLoadingMessage = activity.getString(R.string.please_wait);
    }

    @Override
    public void showProgress(String message) {
        if (!progressDialog.isShowing() && !activity.isFinishing()) {
            progressDialog.show();
        }
        message = message == null ? this.genericLoadingMessage : message;
        progressDialog.setMessage(message);
    }

    @Override
    public void showProgress() {
        showProgress(null);
    }

    @Override
    public void dismissProgress() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public boolean isShown() {
        return progressDialog.isShowing();
    }
}
