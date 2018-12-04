package bg.adastragrp.adastrafaceapp.dagger.scope;

import android.arch.lifecycle.ViewModel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import dagger.MapKey;

/**
 * Creates a Key (ViewModel class) to be associated with its built ViewModel instance (using IntoMap)
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@MapKey
public @interface ViewModelKey {
    Class<? extends ViewModel> value();
}
