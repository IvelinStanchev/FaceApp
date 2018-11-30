package bg.adastragrp.adastrafaceapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import bg.adastragrp.adastrafaceapp.R;
import bg.adastragrp.adastrafaceapp.activities.MainActivity;
import bg.adastragrp.adastrafaceapp.databinding.FragmentLoginBinding;
import bg.adastragrp.adastrafaceapp.view_models.LoginViewModel;
import butterknife.OnClick;

public class LoginFragment extends BaseFragment<FragmentLoginBinding, LoginViewModel> {

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.getShouldLogin().observe(this, shouldLogin -> {
            if (shouldLogin) {
                goToUsersListing();
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

    @OnClick({R.id.btn_login, R.id.txt_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                viewModel.validateFieldsAndLogin();
                break;
            case R.id.txt_register:
                ((MainActivity) getActivity()).replaceFragmentWithBackStack(RegisterFragment.newInstance());
                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected Class<LoginViewModel> getViewModelClass() {
        return LoginViewModel.class;
    }

    private void goToUsersListing() {
        ((MainActivity) getActivity()).replaceFragmentWithoutBackStack(ListUsersFragment.newInstance());
    }
}