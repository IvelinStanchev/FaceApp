package bg.adastragrp.adastrafaceapp.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import bg.adastragrp.adastrafaceapp.data.models.User;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();
}
