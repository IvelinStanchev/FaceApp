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

/**
 * Base fragment for all other fragments. Implements most of the repeating code
 *
 * @param <VDB> Generated binding for the child fragment layout
 * @param <VM> ViewModel of the child fragment
 */
public abstract class BaseFragment<VDB extends ViewDataBinding, VM extends ViewModel> extends Fragment {

    private Unbinder unbinder; // will unbind all views when its method unbind is called
    @Inject
    ViewModelProvider.Factory viewModelFactory; // factory for creating/fetching ViewModels
    protected VM viewModel;
    protected VDB binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);

        Class<VM> viewModelClass = getViewModelClass();
        if (viewModelClass != null) {
            viewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModelClass); // gets the already created ViewModel
        }

        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false); // newly-created binding for the inflated layout or null if the layoutId wasn't for a binding layout
        View view = binding.getRoot(); // gets the outermost View in the layout file associated with the Binding
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
