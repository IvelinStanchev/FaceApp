package bg.adastragrp.adastrafaceapp.dagger.components;

import javax.inject.Singleton;

import bg.adastragrp.adastrafaceapp.MainApplication;
import bg.adastragrp.adastrafaceapp.dagger.modules.ActivitiesBuildersModule;
import bg.adastragrp.adastrafaceapp.dagger.modules.AppModule;
import bg.adastragrp.adastrafaceapp.dagger.modules.FragmentsBuildersModule;
import bg.adastragrp.adastrafaceapp.dagger.modules.ViewModelsModule;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * The wiring component for building the dependency graph
 */
@Singleton
@Component(
        modules = {
                AndroidSupportInjectionModule.class,
                AppModule.class,
                ActivitiesBuildersModule.class,
                FragmentsBuildersModule.class,
                ViewModelsModule.class
        })
public interface AppComponent extends AndroidInjector<MainApplication> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<MainApplication> {}
}