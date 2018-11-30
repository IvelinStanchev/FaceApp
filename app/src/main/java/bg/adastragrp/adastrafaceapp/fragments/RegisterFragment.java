package bg.adastragrp.adastrafaceapp.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import bg.adastragrp.adastrafaceapp.R;
import bg.adastragrp.adastrafaceapp.activities.MainActivity;
import bg.adastragrp.adastrafaceapp.databinding.FragmentRegisterBinding;
import bg.adastragrp.adastrafaceapp.view_models.RegisterViewModel;
import butterknife.OnClick;
import dagger.android.support.AndroidSupportInjection;

public class RegisterFragment extends BaseFragmentWithImageSelection<FragmentRegisterBinding, RegisterViewModel> {

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onViewCreated(view, savedInstanceState);

        viewModel.getCommonErrorMessage().observe(this, errorEvent -> {
            if (!errorEvent.isHandled()) {
                showShortToast(errorEvent.getContentIfNotHandled().getErrorMessage(getResources()));
            }
        });
        viewModel.getSuccessfullyRegistered().observe(this, isSuccess -> {
            if (isSuccess) {
                goToLogin();
            }
        });

        binding.setViewModel(viewModel);
        binding.executePendingBindings();

        ((MainActivity) getActivity()).hideActionBar();
    }

    @OnClick(R.id.img_avatar)
    public void clickedOnAvatar() {
        askForImageTakePermissionsAndOpenCamera();
    }

    @Override
    protected void onImageChosen(Bitmap image) {
        viewModel.setImageDrawable(image);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    protected Class<RegisterViewModel> getViewModelClass() {
        return RegisterViewModel.class;
    }

    private void goToLogin() {
        ((MainActivity) getActivity()).goBack();
    }
}
