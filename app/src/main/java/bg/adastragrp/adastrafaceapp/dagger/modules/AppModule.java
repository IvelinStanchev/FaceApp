package bg.adastragrp.adastrafaceapp.dagger.modules;

import android.arch.persistence.room.Room;
import android.content.Context;

import javax.inject.Singleton;

import bg.adastragrp.adastrafaceapp.MainApplication;
import bg.adastragrp.adastrafaceapp.database.AppDatabase;
import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * General module for providing of dependencies
 */
@Module
public class AppModule {

    @Provides
    Context providesApplicationContext(MainApplication application) {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    AppDatabase providesAppDatabase(Context applicationContext) {
        return Room.databaseBuilder(applicationContext,
                AppDatabase.class, "face_app_database")
                .build();
    }

    @Provides
    CompositeDisposable providesCompositeDisposable() {
        return new CompositeDisposable();
    }
}
