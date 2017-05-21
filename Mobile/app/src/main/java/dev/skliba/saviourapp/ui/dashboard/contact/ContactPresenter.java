package dev.skliba.saviourapp.ui.dashboard.contact;

import dev.skliba.saviourapp.data.interactors.ContactInteractor;
import dev.skliba.saviourapp.data.listeners.Listener;
import dev.skliba.saviourapp.data.models.response.ContactResponse;
import dev.skliba.saviourapp.data.network.ApiManagerImpl;

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
                view.loadWebViewUrl(ApiManagerImpl.WEB_ENDPOINT + "/#/current/" + contactResponse.getActionId(),
                        contactResponse.getLeaderPhoneNo());
            } else {
                view.showEmptyUi();
            }
        }

        @Override
        public void onFailure(String message) {
            view.hideProgress();
            if (message.equals("No such action")) {
                view.showEmptyUi();
            } else {
                view.showError(message);
            }
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