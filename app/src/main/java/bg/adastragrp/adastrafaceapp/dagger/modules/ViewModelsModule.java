package bg.adastragrp.adastrafaceapp.dagger.modules;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import bg.adastragrp.adastrafaceapp.dagger.scope.ViewModelKey;
import bg.adastragrp.adastrafaceapp.view_models.DaggerAwareViewModelFactory;
import bg.adastragrp.adastrafaceapp.view_models.DetailedUserViewModel;
import bg.adastragrp.adastrafaceapp.view_models.EditProfileViewModel;
import bg.adastragrp.adastrafaceapp.view_models.ListUsersViewModel;
import bg.adastragrp.adastrafaceapp.view_models.LoginViewModel;
import bg.adastragrp.adastrafaceapp.view_models.MainActivityViewModel;
import bg.adastragrp.adastrafaceapp.view_models.RegisterViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel.class)
    abstract ViewModel bindMainActivityViewModel(MainActivityViewModel mainActivityViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(DetailedUserViewModel.class)
    abstract ViewModel bindDetailedUserViewModel(DetailedUserViewModel detailedUserViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(EditProfileViewModel.class)
    abstract ViewModel bindEditProfileViewModel(EditProfileViewModel editProfileViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ListUsersViewModel.class)
    abstract ViewModel bindListUsersViewModel(ListUsersViewModel listUsersViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    abstract ViewModel bindLoginViewModel(LoginViewModel loginViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel.class)
    abstract ViewModel bindRegisterViewModel(RegisterViewModel registerViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(DaggerAwareViewModelFactory factory);
}
