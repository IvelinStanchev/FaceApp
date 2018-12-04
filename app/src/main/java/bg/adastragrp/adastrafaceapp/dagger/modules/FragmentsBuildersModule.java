package bg.adastragrp.adastrafaceapp.dagger.modules;

import bg.adastragrp.adastrafaceapp.fragments.DetailedUserFragment;
import bg.adastragrp.adastrafaceapp.fragments.EditProfileFragment;
import bg.adastragrp.adastrafaceapp.fragments.ListUsersFragment;
import bg.adastragrp.adastrafaceapp.fragments.LoginFragment;
import bg.adastragrp.adastrafaceapp.fragments.RegisterFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Module for contribution of fragments
 * (Tells to the dependency builder that corresponding fragment should be injected/has to inject variables)
 */
@Module
public abstract class FragmentsBuildersModule {

    @ContributesAndroidInjector
    abstract DetailedUserFragment contributesDetailedUserFragment();

    @ContributesAndroidInjector
    abstract EditProfileFragment contributesEditProfileFragment();

    @ContributesAndroidInjector
    abstract ListUsersFragment contributesListUsersFragment();

    @ContributesAndroidInjector
    abstract LoginFragment contributesLoginFragment();

    @ContributesAndroidInjector
    abstract RegisterFragment contributesRegisterFragment();
}
