package bg.adastragrp.adastrafaceapp.view_models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import java.util.Calendar;

import javax.inject.Inject;

import bg.adastragrp.adastrafaceapp.MainApplication;
import bg.adastragrp.adastrafaceapp.constants.Constants;
import bg.adastragrp.adastrafaceapp.constants.enums.ErrorEvent;
import bg.adastragrp.adastrafaceapp.constants.enums.FieldsValidationErrors;
import bg.adastragrp.adastrafaceapp.constants.enums.InternetConnectionErrors;
import bg.adastragrp.adastrafaceapp.data.events.CommonErrorEvent;
import bg.adastragrp.adastrafaceapp.data.events.Event;
import bg.adastragrp.adastrafaceapp.data.models.User;
import bg.adastragrp.adastrafaceapp.data.repositories.UserRepository;
import bg.adastragrp.adastrafaceapp.exceptions.NoInternetException;
import bg.adastragrp.adastrafaceapp.validators.EmailValidator;
import bg.adastragrp.adastrafaceapp.validators.GeneralFieldValidator;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RegisterViewModel extends BaseViewModel {

    protected UserRepository userRepository;
    protected ObservableField<String> email = new ObservableField<>();
    private ObservableField<String> password = new ObservableField<>();
    private ObservableField<String> confirmPassword = new ObservableField<>();
    protected ObservableField<String> name = new ObservableField<>();
    protected ObservableField<String> department = new ObservableField<>();
    protected ObservableField<String> position = new ObservableField<>();
    protected ObservableField<String> seniority = new ObservableField<>();
    protected ObservableField<String> office = new ObservableField<>();
    protected ObservableField<String> room = new ObservableField<>();
    protected ObservableField<Long> whenJoinedDateTime = new ObservableField<>();
    protected ObservableField<ErrorEvent> emailErrorMessage = new ObservableField<>();
    private ObservableField<ErrorEvent> passwordErrorMessage = new ObservableField<>();
    private ObservableField<ErrorEvent> passwordRepeatErrorMessage = new ObservableField<>();
    protected ObservableField<ErrorEvent> nameErrorMessage = new ObservableField<>();
    protected ObservableField<ErrorEvent> roomErrorMessage = new ObservableField<>();
    protected MutableLiveData<Event<ErrorEvent>> commonErrorMessage = new MutableLiveData<>();
    private MutableLiveData<Boolean> successfullyRegistered = new MutableLiveData<>();
    protected ObservableField<Boolean> isLoading = new ObservableField<>(false);
    protected ObservableField<Bitmap> imageDrawable = new ObservableField<>();
    protected ObservableField<String> imageUrl = new ObservableField<>();

    public @Inject RegisterViewModel(@NonNull MainApplication application, UserRepository userRepository) {
        super(application);
        this.userRepository = userRepository;
    }

    // region getters and setters
    public ObservableField<String> getEmail() {
        return email;
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

    public ObservableField<String> getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword.set(confirmPassword);
    }

    public ObservableField<String> getName() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public ObservableField<String> getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department.set(department);
    }

    public ObservableField<String> getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position.set(position);
    }

    public ObservableField<String> getSeniority() {
        return seniority;
    }

    public void setSeniority(String seniority) {
        this.seniority.set(seniority);
    }

    public ObservableField<String> getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office.set(office);
    }

    public ObservableField<String> getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room.set(room);
    }

    public ObservableField<Long> getWhenJoinedDateTime() {
        return whenJoinedDateTime;
    }

    public void setWhenJoinedDateTime(long whenJoinedDateTime) {
        this.whenJoinedDateTime.set(whenJoinedDateTime);
    }

    public ObservableField<ErrorEvent> getEmailErrorMessage() {
        return emailErrorMessage;
    }

    public ObservableField<ErrorEvent> getPasswordErrorMessage() {
        return passwordErrorMessage;
    }

    public ObservableField<ErrorEvent> getPasswordRepeatErrorMessage() {
        return passwordRepeatErrorMessage;
    }

    public ObservableField<ErrorEvent> getNameErrorMessage() {
        return nameErrorMessage;
    }

    public ObservableField<ErrorEvent> getRoomErrorMessage() {
        return roomErrorMessage;
    }

    public LiveData<Event<ErrorEvent>> getCommonErrorMessage() {
        return commonErrorMessage;
    }

    public LiveData<Boolean> getSuccessfullyRegistered() {
        return successfullyRegistered;
    }

    public ObservableField<Boolean> getIsLoading() {
        return isLoading;
    }

    public void setImageDrawable(Bitmap imageBitmap) {
        if (imageBitmap != null) {
            this.imageDrawable.set(imageBitmap);
            this.imageUrl = new ObservableField<>();
        }
    }

    public ObservableField<Bitmap> getImageDrawable() {
        return imageDrawable;
    }

    public ObservableField<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl.set(imageUrl);
    }
    // endregion

    public void onSelectedDateChanged(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        whenJoinedDateTime.set(calendar.getTimeInMillis());
    }

    public void validateFieldsAndRegister() {
        String email = this.email.get();
        String password = this.password.get();
        String passwordRepeat = this.confirmPassword.get();
        String name = this.name.get();
        String room = this.room.get();
        Long selectedDate = this.whenJoinedDateTime.get();

        if (!validateFields(email, password, passwordRepeat, name, room, selectedDate)) {
            return;
        }

        User userToRegister =
                new User(name, email, null, position.get(), department.get(), seniority.get(), office.get(), room, selectedDate, password);

        registerUser(userToRegister);
    }

    private boolean validateFields(String email, String password, String passwordRepeat, String name, String room, Long selectedDate) {
        // resetting error messages
        emailErrorMessage.set(null);
        passwordErrorMessage.set(null);
        passwordRepeatErrorMessage.set(null);
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
        if (!GeneralFieldValidator.validateLength(password, Constants.PASSWORD_MIN_LENGTH, Constants.PASSWORD_MAX_LENGTH)) {
            passwordErrorMessage.set(FieldsValidationErrors.PASSWORD_LENGTH);
            return false;
        }
        if (!password.equals(passwordRepeat)) {
            passwordRepeatErrorMessage.set(FieldsValidationErrors.PASSWORDS_DO_NOT_MATCH);
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

    private void registerUser(User userToRegister) {
        isLoading.set(true);
        this.userRepository.registerUser(userToRegister, this.imageDrawable.get())
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
                        successfullyRegistered.setValue(true);
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
