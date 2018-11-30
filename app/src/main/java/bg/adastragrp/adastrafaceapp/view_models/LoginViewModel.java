package bg.adastragrp.adastrafaceapp.view_models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import bg.adastragrp.adastrafaceapp.MainApplication;
import bg.adastragrp.adastrafaceapp.constants.Constants;
import bg.adastragrp.adastrafaceapp.constants.enums.ErrorEvent;
import bg.adastragrp.adastrafaceapp.constants.enums.FieldsValidationErrors;
import bg.adastragrp.adastrafaceapp.constants.enums.InternetConnectionErrors;
import bg.adastragrp.adastrafaceapp.data.events.CommonErrorEvent;
import bg.adastragrp.adastrafaceapp.data.events.Event;
import bg.adastragrp.adastrafaceapp.data.repositories.UserRepository;
import bg.adastragrp.adastrafaceapp.exceptions.NoInternetException;
import bg.adastragrp.adastrafaceapp.validators.EmailValidator;
import bg.adastragrp.adastrafaceapp.validators.GeneralFieldValidator;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginViewModel extends BaseViewModel {

    private UserRepository userRepository;
    private ObservableField<String> email = new ObservableField<>();
    private ObservableField<String> password = new ObservableField<>();
    private ObservableField<ErrorEvent> emailErrorMessage = new ObservableField<>();
    private ObservableField<ErrorEvent> passwordErrorMessage = new ObservableField<>();
    private MutableLiveData<Event<ErrorEvent>> commonErrorMessage = new MutableLiveData<>();
    private MutableLiveData<Boolean> shouldLogin = new MutableLiveData<>();
    private ObservableField<Boolean> isLoading = new ObservableField<>(false);

    public @Inject LoginViewModel(@NonNull MainApplication application, UserRepository userRepository) {
        super(application);
        this.userRepository = userRepository;
        shouldLogin.setValue(false);
    }

    // region getters and setters
    public LiveData<Boolean> getShouldLogin() {
        return shouldLogin;
    }

    public ObservableField<String> getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public ObservableField<String> getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public ObservableField<Boolean> getIsLoading() {
        return isLoading;
    }

    public ObservableField<ErrorEvent> getEmailErrorMessage() {
        return emailErrorMessage;
    }

    public ObservableField<ErrorEvent> getPasswordErrorMessage() {
        return passwordErrorMessage;
    }

    public LiveData<Event<ErrorEvent>> getCommonErrorMessage() {
        return commonErrorMessage;
    }

    // endregion

    public void validateFieldsAndLogin() {
        String email = this.email.get();
        String password = this.password.get();

        // resetting error messages
        emailErrorMessage.set(null);
        passwordErrorMessage.set(null);

        if (!EmailValidator.validateLength(email)) {
            emailErrorMessage.set(FieldsValidationErrors.EMAIL_LENGTH);
            return;
        }
        if (!EmailValidator.validateEmailContent(email)) {
            emailErrorMessage.set(FieldsValidationErrors.EMAIL_NOT_VALID);
            return;
        }
        if (!GeneralFieldValidator.validateLength(password, Constants.PASSWORD_MIN_LENGTH, Constants.PASSWORD_MAX_LENGTH)) {
            passwordErrorMessage.set(FieldsValidationErrors.PASSWORD_LENGTH);
            return;
        }

        loginUser(email, password);
    }

    private void loginUser(String email, String password) {
        isLoading.set(true);
        this.userRepository.loginUser(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onComplete() {
                        isLoading.set(false);
                        shouldLogin.setValue(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        isLoading.set(false);
                        if (e instanceof NoInternetException) {
                            commonErrorMessage.setValue(new Event<>(InternetConnectionErrors.NO_INTERNET_CONNECTION));
                        } else {
                            commonErrorMessage.setValue(new Event<>(new CommonErrorEvent(e.getMessage())));
                        }
                    }
                });
    }
}
