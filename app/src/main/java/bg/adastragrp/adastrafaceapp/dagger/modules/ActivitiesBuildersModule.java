package bg.adastragrp.adastrafaceapp.dagger.modules;

import bg.adastragrp.adastrafaceapp.activities.MainActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivitiesBuildersModule {

    @ContributesAndroidInjector
    abstract MainActivity contributesMainActivity();
}