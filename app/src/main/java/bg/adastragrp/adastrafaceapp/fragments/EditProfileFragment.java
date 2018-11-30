package bg.adastragrp.adastrafaceapp.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

import bg.adastragrp.adastrafaceapp.R;
import bg.adastragrp.adastrafaceapp.activities.MainActivity;
import bg.adastragrp.adastrafaceapp.databinding.FragmentEditProfileBinding;
import bg.adastragrp.adastrafaceapp.view_models.EditProfileViewModel;
import butterknife.BindView;
import butterknife.OnClick;

public class EditProfileFragment extends BaseFragmentWithImageSelection<FragmentEditProfileBinding, EditProfileViewModel> {

    @BindView(R.id.img_avatar)
    ImageView imgAvatarView;

    public static EditProfileFragment newInstance() {
        return new EditProfileFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.getUser().observe(this, (user) -> viewModel.refreshFields(user));
        viewModel.getSuccessfullyUpdated().observe(this, isSuccess -> {
            if (isSuccess) {
                ((MainActivity) getActivity()).goBack();
            }
        });
        viewModel.getCommonErrorMessage().observe(this, errorEvent -> {
            if (!errorEvent.isHandled()) {
                showShortToast(errorEvent.getContentIfNotHandled().getErrorMessage(getResources()));
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

    @OnClick(R.id.btn_logout)
    public void clickedOnLogout() {
        FirebaseAuth.getInstance().signOut();
        ((MainActivity) getActivity()).clearBackstack();
        ((MainActivity) getActivity()).replaceFragmentWithoutBackStack(LoginFragment.newInstance());
    }

    @Override
    protected void onImageChosen(Bitmap image) {
        viewModel.setImageDrawable(image);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_edit_profile;
    }

    @Override
    protected Class<EditProfileViewModel> getViewModelClass() {
        return EditProfileViewModel.class;
    }
}