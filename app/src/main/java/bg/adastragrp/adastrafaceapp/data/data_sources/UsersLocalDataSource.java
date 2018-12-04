package bg.adastragrp.adastrafaceapp.data.data_sources;

import android.arch.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import bg.adastragrp.adastrafaceapp.data.models.User;
import bg.adastragrp.adastrafaceapp.database.AppDatabase;
import bg.adastragrp.adastrafaceapp.database.UserDao;
import io.reactivex.Single;

/**
 * Responsible for getting and setting users data from/to local data source (local DB)
 */
@Singleton
public class UsersLocalDataSource {

    private UserDao userDao;

    public @Inject UsersLocalDataSource(AppDatabase db) {
        this.userDao = db.userDao();
    }

    public LiveData<List<User>> fetchUsers() {
        return userDao.getAll();
    }

    public Single<List<User>> saveUsers(List<User> users) {
        List<User> usersToSave = new ArrayList<>();
        if (users != null) {
            usersToSave.addAll(users);
        }

        return Single.fromCallable(() -> {
            User[] usersArray = usersToSave.toArray(new User[usersToSave.size()]);
            userDao.deleteAndInsertAll(usersArray);

            return usersToSave;
        });
    }

    public LiveData<User> getMyProfileData(String myUserId) {
        return userDao.findById(myUserId);
    }

    public void saveMyProfileData(User user) {
        userDao.insert(user);
    }

    public void updateUser(User user) {
        userDao.insert(user);
    }
}
