package bg.adastragrp.adastrafaceapp.data.repositories;

import android.arch.lifecycle.LiveData;
import android.graphics.Bitmap;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import bg.adastragrp.adastrafaceapp.data.data_sources.UsersLocalDataSource;
import bg.adastragrp.adastrafaceapp.data.data_sources.UsersRemoteDataSource;
import bg.adastragrp.adastrafaceapp.data.models.User;
import bg.adastragrp.adastrafaceapp.exceptions.NoInternetException;
import bg.adastragrp.adastrafaceapp.managers.NetManager;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Contains redirects for information connected with users
 */
@Singleton
public class UserRepository {

    private final UsersLocalDataSource localDataSource;
    private final UsersRemoteDataSource remoteDataSource;
    private final NetManager netManager;

    public @Inject UserRepository(
            final UsersLocalDataSource localDataSource,
            final UsersRemoteDataSource remoteDataSource,
            final NetManager netManager) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
        this.netManager = netManager;
    }

    public LiveData<List<User>> getUsers() {
        return localDataSource.fetchUsers();
    }

    public Single<List<User>> fetchUsers() {
        if (netManager.isConnectedToInternet()) {
            // fetch remote users and save them in the DB. ViewModel connects to DB via LiveData so will be updated
            // whenever data is being updated in the DB
            return remoteDataSource
                    .fetchUsers()
                    .observeOn(Schedulers.io())
                    .flatMap((Function<List<User>, SingleSource<List<User>>>) localDataSource::saveUsers);
        }
        // else return no internet exception
        return Single.error(new NoInternetException());
    }

    public Completable loginUser(String email, String password) {
        if (netManager.isConnectedToInternet()) {
            return remoteDataSource.loginUser(email, password)
                    .observeOn(Schedulers.io())
                    .flatMap((Function<User, SingleSource<? extends User>>) user ->
                            Single.create(emitter -> {
                                localDataSource.saveMyProfileData(user);

                                emitter.onSuccess(user);
                            }))
                    .ignoreElement();
        }
        // else return no internet exception
        return Completable.error(new NoInternetException());
    }

    public Completable registerUser(User user, Bitmap avatarImage) {
        if (netManager.isConnectedToInternet()) {
            return remoteDataSource.registerUser(user, avatarImage);
        }
        // else return no internet exception
        return Completable.error(new NoInternetException());
    }

    public LiveData<User> getMyProfileData(String myUserId) {
        return localDataSource.getMyProfileData(myUserId);
    }

    public Completable updateUser(User user, Bitmap newAvatarImage) {
        if (netManager.isConnectedToInternet()) {
            return remoteDataSource
                    .updateUser(user, newAvatarImage)
                    .observeOn(Schedulers.io())
                    .flatMap((Function<User, SingleSource<? extends User>>) userToUpdate ->
                            Single.create(emitter -> {
                                localDataSource.updateUser(userToUpdate);

                                emitter.onSuccess(userToUpdate);
                            }))
                    .ignoreElement();
        }
        // else return no internet exception
        return Completable.error(new NoInternetException());
    }
}
