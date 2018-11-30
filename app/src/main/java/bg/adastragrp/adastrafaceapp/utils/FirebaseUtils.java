package bg.adastragrp.adastrafaceapp.utils;

import com.google.firebase.auth.FirebaseAuth;

public class FirebaseUtils {

    public static String getMyProfileId() {
        return FirebaseAuth.getInstance().getCurrentUser() != null
                ? FirebaseAuth.getInstance().getCurrentUser().getUid()
                : null;
    }
}
