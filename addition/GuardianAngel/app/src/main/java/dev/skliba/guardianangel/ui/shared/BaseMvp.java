package dev.skliba.guardianangel.ui.shared;


import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

public interface BaseMvp {

    interface View extends ErrorView {

        void showProgress();

        void hideProgress();
    }

    interface Presenter {

        void cancel();
    }

    interface ProgressView {

        void showProgress(String message);

        void showProgress();

        void dismissProgress();

        boolean isShown();
    }

    interface ErrorView {

        void showError(String message);

        void showError(String message, @Nullable DismissListener dismissListener);

        void showError(@StringRes int resourceInt);

        void showError(@StringRes int resourceInt, @Nullable DismissListener dismissListener);
    }

    interface ErrorListener {

        void onError(String errorMessage);

        void onUnknownProblem();

        void onNetworkProblem();
    }

    interface DismissListener {

        void onDismiss();
    }

}
