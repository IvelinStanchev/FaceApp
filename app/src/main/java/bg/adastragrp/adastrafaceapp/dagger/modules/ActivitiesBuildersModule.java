package bg.adastragrp.adastrafaceapp.dagger.modules;

import bg.adastragrp.adastrafaceapp.activities.MainActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Module for contribution of activities
 * (Tells to the dependency builder that corresponding activity should be injected/has to inject variables)
 */
@Module
public abstract class ActivitiesBuildersModule {

    @ContributesAndroidInjector
    abstract MainActivity contributesMainActivity();
}