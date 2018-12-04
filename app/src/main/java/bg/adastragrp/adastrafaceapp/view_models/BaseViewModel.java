package bg.adastragrp.adastrafaceapp.view_models;

import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import bg.adastragrp.adastrafaceapp.MainApplication;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Base ViewModel for all other ViewModels. Implements most of the repeating code
 */
public class BaseViewModel extends AndroidViewModel {

    @Inject
    protected CompositeDisposable compositeDisposable;

    public BaseViewModel(@NonNull MainApplication application) {
        super(application);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }
}
