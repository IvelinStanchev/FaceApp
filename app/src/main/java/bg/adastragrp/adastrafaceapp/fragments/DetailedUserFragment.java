package bg.adastragrp.adastrafaceapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import bg.adastragrp.adastrafaceapp.R;
import bg.adastragrp.adastrafaceapp.activities.MainActivity;
import bg.adastragrp.adastrafaceapp.data.models.User;
import bg.adastragrp.adastrafaceapp.databinding.FragmentDetailedUserBinding;
import bg.adastragrp.adastrafaceapp.view_models.DetailedUserViewModel;

public class DetailedUserFragment extends BaseFragment<FragmentDetailedUserBinding, DetailedUserViewModel> {

    private final static String DETAILED_USER = "detailed_user";
    private User user;

    // TODO: check for the shared ViewModel approach
    public static DetailedUserFragment newInstance(User selectedUser) {
        Bundle args = new Bundle();
        args.putSerializable(DETAILED_USER, selectedUser);

        DetailedUserFragment fragment = new DetailedUserFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            this.user = (User) getArguments().getSerializable(DETAILED_USER);
        }

        viewModel.setUser(user);
        binding.setViewModel(viewModel);
        binding.executePendingBindings();

        ((MainActivity) getActivity()).hideActionBar();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_detailed_user;
    }

    @Override
    protected Class<DetailedUserViewModel> getViewModelClass() {
        return DetailedUserViewModel.class;
    }
}
