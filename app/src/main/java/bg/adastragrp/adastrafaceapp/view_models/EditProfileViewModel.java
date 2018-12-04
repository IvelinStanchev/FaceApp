package bg.adastragrp.adastrafaceapp.view_models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import bg.adastragrp.adastrafaceapp.MainApplication;
import bg.adastragrp.adastrafaceapp.constants.Constants;
import bg.adastragrp.adastrafaceapp.constants.enums.FieldsValidationErrors;
import bg.adastragrp.adastrafaceapp.constants.enums.InternetConnectionErrors;
import bg.adastragrp.adastrafaceapp.data.events.CommonErrorEvent;
import bg.adastragrp.adastrafaceapp.data.events.Event;
import bg.adastragrp.adastrafaceapp.data.models.User;
import bg.adastragrp.adastrafaceapp.data.repositories.UserRepository;
import bg.adastragrp.adastrafaceapp.exceptions.NoInternetException;
import bg.adastragrp.adastrafaceapp.utils.FirebaseUtils;
import bg.adastragrp.adastrafaceapp.validators.EmailValidator;
import bg.adastragrp.adastrafaceapp.validators.GeneralFieldValidator;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class EditProfileViewModel extends RegisterViewModel {

    private LiveData<User> user;
    private MutableLiveData<Boolean> successfullyUpdated = new MutableLiveData<>(); // notifies the observer (view) when profile data is updated
    private boolean isRefreshed; // flag for refresh only once indication

    public @Inject EditProfileViewModel(@NonNull MainApplication application, UserRepository userRepository) {
        super(application, userRepository);
        user = userRepository.getMyProfileData(FirebaseUtils.getMyProfileId());
    }

    public LiveData<User> getUser() {
        return user;
    }

    public LiveData<Boolean> getSuccessfullyUpdated() {
        return successfullyUpdated;
    }

    public void validateFieldsAndUpdate() {
        User user = getUser().getValue();

        if (user != null) {
            String email = this.email.get();
            String name = this.name.get();
            String room = this.room.get();
            Long selectedDate = this.whenJoinedDateTime.get();

            if (!validateFields(email, name, room, selectedDate)) {
                return;
            }

            User updateUser = new User(
                    user.getId(),
                    name,
                    email,
                    user.getAvatarUrl(),
                    user.getAvatarShortPathName(),
                    position.get(),
                    department.get(),
                    seniority.get(),
                    office.get(),
                    room,
                    selectedDate,
                    user.getDateUserCreated()
            );

            updateUser(updateUser);
        }
    }

    public void refreshFields(User user) {
        if (user != null && !isRefreshed) {
            isRefreshed = true;
            email.set(user.getEmail());
            name.set(user.getName());
            department.set(user.getDepartment());
            position.set(user.getPosition());
            seniority.set(user.getSeniority());
            office.set(user.getOffice());
            room.set(user.getRoom());
            whenJoinedDateTime.set(user.getWhenJoined());
            imageUrl.set(user.getAvatarUrl());
        }
    }

    private boolean validateFields(String email, String name, String room, Long selectedDate) {
        // resetting error messages
        emailErrorMessage.set(null);
        nameErrorMessage.set(null);
        roomErrorMessage.set(null);

        if (!EmailValidator.validateLength(email)) {
            emailErrorMessage.set(FieldsValidationErrors.EMAIL_LENGTH);
            return false;
        }
        if (!EmailValidator.validateEmailContent(email)) {
            emailErrorMessage.set(FieldsValidationErrors.EMAIL_NOT_VALID);
            return false;
        }
        if (!GeneralFieldValidator.validateLength(name, Constants.NAME_MIN_LENGTH, Constants.NAME_MAX_LENGTH)) {
            nameErrorMessage.set(FieldsValidationErrors.NAME_LENGTH);
            return false;
        }
        if (!GeneralFieldValidator.containsDigitsOnly(room)) {
            roomErrorMessage.set(FieldsValidationErrors.INVALID_ROOM_FORMAT);
            return false;
        }
        if (selectedDate == null || selectedDate == 0L) {
            commonErrorMessage.setValue(new Event<>(FieldsValidationErrors.INVALID_DATE));
            return false;
        }

        return true;
    }

    private void updateUser(User updateUser) {
        isLoading.set(true);
        userRepository.updateUser(updateUser, imageDrawable.get())
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
                        successfullyUpdated.setValue(true);
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
