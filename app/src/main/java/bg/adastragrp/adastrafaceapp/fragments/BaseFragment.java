package bg.adastragrp.adastrafaceapp.fragments;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;

public abstract class BaseFragment<VDB extends ViewDataBinding, VM extends ViewModel> extends Fragment {

    private Unbinder unbinder;
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    protected VM viewModel;
    protected VDB binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);

        Class<VM> viewModelClass = getViewModelClass();
        if (viewModelClass != null) {
            viewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModelClass);
        }

        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        View view = binding.getRoot();
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    protected void showShortToast(int stringResource) {
        showToast(stringResource, Toast.LENGTH_SHORT);
    }

    protected void showShortToast(String stringMessage) {
        showToast(stringMessage, Toast.LENGTH_SHORT);
    }

    protected void showLongToast(int stringResource) {
        showToast(stringResource, Toast.LENGTH_LONG);
    }

    protected void showLongToast(String stringMessage) {
        showToast(stringMessage, Toast.LENGTH_LONG);
    }

    protected abstract int getLayoutId();

    protected abstract Class<VM> getViewModelClass();

    private void showToast(int stringResource, int length) {
        Toast.makeText(getContext(), stringResource, length).show();
    }

    private void showToast(String stringMessage, int length) {
        Toast.makeText(getContext(), stringMessage, length).show();
    }
}
