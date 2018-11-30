package bg.adastragrp.adastrafaceapp.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import bg.adastragrp.adastrafaceapp.R;
import bg.adastragrp.adastrafaceapp.activities.MainActivity;
import bg.adastragrp.adastrafaceapp.adapters.BaseAdapter;
import bg.adastragrp.adastrafaceapp.adapters.UsersListingAdapter;
import bg.adastragrp.adastrafaceapp.data.models.User;
import bg.adastragrp.adastrafaceapp.databinding.FragmentListUsersBinding;
import bg.adastragrp.adastrafaceapp.view_models.ListUsersViewModel;
import butterknife.BindView;

public class ListUsersFragment extends BaseFragment<FragmentListUsersBinding, ListUsersViewModel> {

    @BindView(R.id.pull_to_refresh_container)
    SwipeRefreshLayout swipeRefreshLayout;

    private UsersListingAdapter adapter;

    public static ListUsersFragment newInstance() {
        return new ListUsersFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new UsersListingAdapter(getUserSelectedClickListener());
        viewModel.getUsers().observe(this, users -> {
            swipeRefreshLayout.setRefreshing(false);
            adapter.updateData(users);
            binding.executePendingBindings();
        });
        viewModel.getErrorMessages().observe(this, errorEvent -> {
            if (!errorEvent.isHandled()) {
                swipeRefreshLayout.setRefreshing(false);
                showShortToast(errorEvent.getContentIfNotHandled().getErrorMessage(getResources()));
            }
        });

        binding.setViewModel(viewModel);
        binding.recyclerUsers.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerUsers.setAdapter(adapter);
        binding.executePendingBindings();

        viewModel.fetchUsers();

        swipeRefreshLayout.setOnRefreshListener(() -> {
            viewModel.refreshUsers();
        });

        ((MainActivity) getActivity()).showActionBar();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Create menu
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_go_to_my_profile:
                goToMyProfile();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list_users;
    }

    @Override
    protected Class<ListUsersViewModel> getViewModelClass() {
        return ListUsersViewModel.class;
    }

    private BaseAdapter.ItemClickListener<User> getUserSelectedClickListener() {
        return (user, v) -> ((MainActivity) getActivity()).replaceFragmentWithBackStack(DetailedUserFragment.newInstance(user));
    }

    private void goToMyProfile() {
        ((MainActivity) getActivity()).replaceFragmentWithBackStack(EditProfileFragment.newInstance());
    }
}
