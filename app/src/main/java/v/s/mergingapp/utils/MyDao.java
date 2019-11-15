package v.s.mergingapp.utils;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import v.s.mergingapp.model.UserDetail;

@Dao
public interface MyDao {


@Insert
public void addUser(UserDetail userDetail);


}
