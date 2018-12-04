package bg.adastragrp.adastrafaceapp;

import bg.adastragrp.adastrafaceapp.dagger.components.DaggerAppComponent;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class MainApplication extends DaggerApplication {

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        // builds the dependency graph
        return DaggerAppComponent.builder().create(this);
    }
}