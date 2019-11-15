package v.s.mergingapp.utils;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import v.s.mergingapp.model.UserDetail;



@Database(entities = {UserDetail.class},version = 1)
public abstract class MyAppDataBase extends RoomDatabase {
    public abstract MyDao myDao();
}
