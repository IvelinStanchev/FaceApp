package bg.adastragrp.adastrafaceapp.view_models;

import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import bg.adastragrp.adastrafaceapp.MainApplication;
import bg.adastragrp.adastrafaceapp.data.repositories.AppRepository;

public class MainActivityViewModel extends AndroidViewModel {

    private AppRepository appRepository;

    public @Inject MainActivityViewModel(@NonNull MainApplication application, AppRepository appRepository) {
        super(application);
        this.appRepository = appRepository;
    }

    public boolean getIsLoggedIn() {
        return appRepository.getIsLoggedIn();
    }
}
