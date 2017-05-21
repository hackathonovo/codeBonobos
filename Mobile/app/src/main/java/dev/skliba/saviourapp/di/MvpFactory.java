package dev.skliba.saviourapp.di;


import dev.skliba.saviourapp.SaviourApplication;
import dev.skliba.saviourapp.data.interactors.ActionDetailsInteractor;
import dev.skliba.saviourapp.data.interactors.ContactInteractor;
import dev.skliba.saviourapp.data.interactors.LoginInteractor;
import dev.skliba.saviourapp.data.interactors.NewsInteractor;
import dev.skliba.saviourapp.data.interactors.ProfileInteractor;
import dev.skliba.saviourapp.data.interactors.SplashInteractor;
import dev.skliba.saviourapp.ui.action_details.ActionDetailsMvp;
import dev.skliba.saviourapp.ui.action_details.ActionDetailsPresenter;
import dev.skliba.saviourapp.ui.dashboard.contact.ContactMvp;
import dev.skliba.saviourapp.ui.dashboard.contact.ContactPresenter;
import dev.skliba.saviourapp.ui.dashboard.news.NewsMvp;
import dev.skliba.saviourapp.ui.dashboard.news.NewsPresenter;
import dev.skliba.saviourapp.ui.dashboard.profile.ProfileMvp;
import dev.skliba.saviourapp.ui.dashboard.profile.ProfilePresenter;
import dev.skliba.saviourapp.ui.login.LoginMvp;
import dev.skliba.saviourapp.ui.login.LoginPresenter;
import dev.skliba.saviourapp.ui.splash.SplashMvp;
import dev.skliba.saviourapp.ui.splash.SplashPresenter;

public class MvpFactory {

    public static SplashMvp.Presenter providePresenter(SplashMvp.View view) {
        return new SplashPresenter(view, new SplashInteractor(SaviourApplication.getApiService()));
    }

    public static NewsMvp.Presenter providePresenter(NewsMvp.View view) {
        return new NewsPresenter(view, new NewsInteractor(SaviourApplication.getApiService()));
    }

    public static ProfileMvp.Presenter providePresenter(ProfileMvp.View view) {
        return new ProfilePresenter(view, new ProfileInteractor(SaviourApplication.getApiService()));
    }

    public static LoginMvp.Presenter providePresenter(LoginMvp.View view) {
        return new LoginPresenter(view, new LoginInteractor(SaviourApplication.getApiService()));
    }

    public static ContactMvp.Presenter providePresenter(ContactMvp.View view) {
        return new ContactPresenter(view, new ContactInteractor(SaviourApplication.getApiService()));
    }

    public static ActionDetailsMvp.Presenter providePresenter(ActionDetailsMvp.View view) {
        return new ActionDetailsPresenter(view, new ActionDetailsInteractor(SaviourApplication.getApiService()));
    }
}
