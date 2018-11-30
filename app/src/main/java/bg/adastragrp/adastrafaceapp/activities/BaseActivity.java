package bg.adastragrp.adastrafaceapp.activities;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity<VM extends ViewModel> extends DaggerAppCompatActivity {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    protected VM viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        Class<VM> viewModelClass = getViewModelClass();
        if (viewModelClass != null) {
            viewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModelClass);
        }
    }

    public void replaceFragmentWithoutBackStack(Fragment destinationFragment) {
        replaceFragment(destinationFragment, false);
    }

    public void replaceFragmentWithBackStack(Fragment destinationFragment) {
        replaceFragment(destinationFragment, true);
    }

    public void goBack() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public void hideActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    public void showActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.show();
        }
    }

    public void clearBackstack() {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    protected abstract int getLayoutId();

    protected abstract int getFrameLayoutContainerId();

    protected abstract Class<VM> getViewModelClass();

    private void replaceFragment(Fragment destinationFragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(getFrameLayoutContainerId(), destinationFragment);
        if (addToBackStack) {
            transaction.addToBackStack(destinationFragment.getClass().getName());
        }
        transaction.commit();
    }
}
