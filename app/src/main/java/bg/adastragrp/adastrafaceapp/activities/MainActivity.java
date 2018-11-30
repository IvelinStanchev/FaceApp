package bg.adastragrp.adastrafaceapp.activities;

import android.os.Bundle;

import bg.adastragrp.adastrafaceapp.R;
import bg.adastragrp.adastrafaceapp.fragments.ListUsersFragment;
import bg.adastragrp.adastrafaceapp.fragments.LoginFragment;
import bg.adastragrp.adastrafaceapp.view_models.MainActivityViewModel;

public class MainActivity extends BaseActivity<MainActivityViewModel> {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            init();
        }

        setTitle(null);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected int getFrameLayoutContainerId() {
        return R.id.main_container;
    }

    @Override
    protected Class<MainActivityViewModel> getViewModelClass() {
        return MainActivityViewModel.class;
    }

    private void init() {
        if (viewModel.getIsLoggedIn()) {
            replaceFragmentWithoutBackStack(ListUsersFragment.newInstance());
        } else {
            replaceFragmentWithoutBackStack(LoginFragment.newInstance());
        }
    }
}
