package bg.adastragrp.adastrafaceapp.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;
import android.icu.lang.UScript;

import java.util.List;

import bg.adastragrp.adastrafaceapp.data.models.User;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public abstract class UserDao {

    // @Query is asynchronous
    // @Update/@Insert/@Delete are synchronous

    @Query("SELECT * FROM user ORDER BY date_user_created ASC")
    public abstract LiveData<List<User>> getAll();

    @Query("SELECT * FROM user WHERE id LIKE :id")
    public abstract LiveData<User> findById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(User user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertAll(User... users);

    @Delete
    public abstract void delete(User user);

    @Query("DELETE FROM user")
    public abstract void deleteAll();

    @Transaction
    public void deleteAndInsertAll(User... users) {
        deleteAll();
        insertAll(users);
    }
}