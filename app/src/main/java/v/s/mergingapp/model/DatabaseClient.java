package v.s.mergingapp.model;

import android.arch.persistence.room.Room;
import android.content.Context;

import v.s.mergingapp.utils.MyAppDataBase;

public class DatabaseClient {
    private Context context;
    private static DatabaseClient mInstance;

    private MyAppDataBase appDataBase;

    private DatabaseClient(Context context){

        this.context=context;
        appDataBase= Room.databaseBuilder(context,MyAppDataBase.class,"allDatabse").build();


    }

    public static synchronized DatabaseClient getInstance(Context context){
      if (mInstance==null){
          mInstance=new DatabaseClient(context);
      }
      return mInstance;
       }
    public MyAppDataBase getAppDataBase(){
        return appDataBase;
    }
 }
