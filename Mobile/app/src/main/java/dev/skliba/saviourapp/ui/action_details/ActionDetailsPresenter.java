package dev.skliba.saviourapp.ui.action_details;

import dev.skliba.saviourapp.data.interactors.ActionDetailsInteractor;
import dev.skliba.saviourapp.data.listeners.Listener;
import dev.skliba.saviourapp.data.models.response.ActionDetailsResponse;

public class ActionDetailsPresenter implements ActionDetailsMvp.Presenter {

    private ActionDetailsMvp.View view;

    private ActionDetailsInteractor interactor;

    public ActionDetailsPresenter(ActionDetailsMvp.View view, ActionDetailsInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void cancel() {
        interactor.cancel();
    }

    @Override
    public void init(String actionId) {
        view.showProgress();
        interactor.execute(actionId, listener);
    }

    private Listener<ActionDetailsResponse> listener = new Listener<ActionDetailsResponse>() {
        @Override
        public void onSuccess(ActionDetailsResponse actionDetailsResponse) {
            view.hideProgress();
            view.displayData(actionDetailsResponse);
        }

        @Override
        public void onFailure(String message) {
            view.hideProgress();
            view.showError(message);
        }

        @Override
        public void onConnectionFailure(String message) {

        }
    };
}