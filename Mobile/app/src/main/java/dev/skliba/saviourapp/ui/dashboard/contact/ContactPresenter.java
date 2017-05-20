package dev.skliba.saviourapp.ui.dashboard.contact;

import dev.skliba.saviourapp.data.interactors.ContactInteractor;
import dev.skliba.saviourapp.data.listeners.Listener;
import dev.skliba.saviourapp.data.models.response.ContactResponse;

public class ContactPresenter implements ContactMvp.Presenter {

    private ContactMvp.View view;

    private ContactInteractor interactor;

    public ContactPresenter(ContactMvp.View view, ContactInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void init() {
        view.showProgress();
        interactor.fetchActions(listener);
    }

    private Listener<ContactResponse> listener = new Listener<ContactResponse>() {
        @Override
        public void onSuccess(ContactResponse contactResponse) {
            view.hideProgress();
            if (contactResponse != null) {
                view.loadWebViewUrl("http://192.168.201.44:8080/api/action" + contactResponse.getActionId());
            } else {
                view.showEmptyUi();
            }
        }

        @Override
        public void onFailure(String message) {
            view.hideProgress();
            view.showError(message);
        }

        @Override
        public void onConnectionFailure(String message) {
            view.hideProgress();
            //nothing here
        }
    };

    @Override
    public void cancel() {
        interactor.cancel();
    }
}