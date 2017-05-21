package dev.skliba.guardianangel.di;


import dev.skliba.guardianangel.GuardianAngelApp;
import dev.skliba.guardianangel.data.interactors.LoginInteractor;
import dev.skliba.guardianangel.data.interactors.NewsInteractor;
import dev.skliba.guardianangel.data.interactors.ProfileInteractor;
import dev.skliba.guardianangel.data.interactors.SplashInteractor;
import dev.skliba.guardianangel.ui.dashboard.news.NewsMvp;
import dev.skliba.guardianangel.ui.dashboard.news.NewsPresenter;
import dev.skliba.guardianangel.ui.dashboard.profile.ProfileMvp;
import dev.skliba.guardianangel.ui.dashboard.profile.ProfilePresenter;
import dev.skliba.guardianangel.ui.login.LoginMvp;
import dev.skliba.guardianangel.ui.login.LoginPresenter;
import dev.skliba.guardianangel.ui.splash.SplashMvp;
import dev.skliba.guardianangel.ui.splash.SplashPresenter;

public class MvpFactory {

    public static SplashMvp.Presenter providePresenter(SplashMvp.View view) {
        return new SplashPresenter(view, new SplashInteractor(GuardianAngelApp.getApiService()));
    }

    public static NewsMvp.Presenter providePresenter(NewsMvp.View view) {
        return new NewsPresenter(view, new NewsInteractor(GuardianAngelApp.getApiService()));
    }

    public static ProfileMvp.Presenter providePresenter(ProfileMvp.View view) {
        return new ProfilePresenter(view, new ProfileInteractor(GuardianAngelApp.getApiService()));
    }

    public static LoginMvp.Presenter providePresenter(LoginMvp.View view) {
        return new LoginPresenter(view, new LoginInteractor(GuardianAngelApp.getApiService()));
    }
}
