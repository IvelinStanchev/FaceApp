package bg.adastragrp.adastrafaceapp.view_models;

import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import bg.adastragrp.adastrafaceapp.data.models.User;

public class DetailedUserViewModel extends ViewModel {

    private User user;

    public @Inject DetailedUserViewModel() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
