package bg.adastragrp.adastrafaceapp.data.repositories;

import javax.inject.Inject;
import javax.inject.Singleton;

import bg.adastragrp.adastrafaceapp.MainApplication;
import bg.adastragrp.adastrafaceapp.utils.FirebaseUtils;

@Singleton
public class AppRepository {

    private MainApplication application;

    public @Inject AppRepository(MainApplication application) {
        this.application = application;
    }

    public boolean getIsLoggedIn() {
        return FirebaseUtils.getMyProfileId() != null;
    }
}
