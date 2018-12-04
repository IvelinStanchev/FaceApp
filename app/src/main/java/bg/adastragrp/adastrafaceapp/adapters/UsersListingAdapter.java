package bg.adastragrp.adastrafaceapp.adapters;

import bg.adastragrp.adastrafaceapp.R;
import bg.adastragrp.adastrafaceapp.data.models.User;

/**
 * Adapter for users main listing
 */
public class UsersListingAdapter extends BaseAdapter<User> {

    public UsersListingAdapter(ItemClickListener<User> clickListener) {
        setClickListener(clickListener);
    }

    @Override
    public int getLayoutIdForType(int viewType) {
        return R.layout.item_person;
    }
}
