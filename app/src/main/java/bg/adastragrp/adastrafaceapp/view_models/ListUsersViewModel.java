package bg.adastragrp.adastrafaceapp.view_models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;

import java.util.List;

import javax.inject.Inject;

import bg.adastragrp.adastrafaceapp.MainApplication;
import bg.adastragrp.adastrafaceapp.constants.enums.ErrorEvent;
import bg.adastragrp.adastrafaceapp.constants.enums.InternetConnectionErrors;
import bg.adastragrp.adastrafaceapp.data.events.CommonErrorEvent;
import bg.adastragrp.adastrafaceapp.data.events.Event;
import bg.adastragrp.adastrafaceapp.data.models.User;
import bg.adastragrp.adastrafaceapp.data.repositories.UserRepository;
import bg.adastragrp.adastrafaceapp.exceptions.NoInternetException;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ListUsersViewModel extends BaseViewModel {

    private UserRepository userRepository;
    private LiveData<List<User>> users;
    private ObservableField<Boolean> isLoading = new ObservableField<>(false);
    private boolean shouldFetchData = true;// data should be fetched only first time
    private MutableLiveData<Event<ErrorEvent>> errorMessages = new MutableLiveData<>();

    public @Inject ListUsersViewModel(MainApplication application, UserRepository userRepository) {
        super(application);
        this.userRepository = userRepository;
        users = this.userRepository.getUsers();
    }

    public ObservableField<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<List<User>> getUsers() {
        return users;
    }

    public MutableLiveData<Event<ErrorEvent>> getErrorMessages() {
        return errorMessages;
    }

    public void refreshUsers() {
        shouldFetchData = true;
        fetchUsers();
    }

    public void fetchUsers() {
        if (shouldFetchData) {
            isLoading.set(true);
            // contact remote API and update the DB
            userRepository.fetchUsers()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<List<User>>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            compositeDisposable.add(d);
                        }

                        @Override
                        public void onSuccess(List<User> usersResponse) {
                            isLoading.set(false);
                            shouldFetchData = false;
                        }

                        @Override
                        public void onError(Throwable e) {
                            isLoading.set(false);
                            if (e instanceof NoInternetException) {
                                // assign error for no internet connection
                                errorMessages.setValue(new Event<>(InternetConnectionErrors.NO_INTERNET_CONNECTION));
                            } else {
                                errorMessages.setValue(new Event<>(new CommonErrorEvent(e.getMessage())));
                            }
                        }
                    });
        }
    }
}
