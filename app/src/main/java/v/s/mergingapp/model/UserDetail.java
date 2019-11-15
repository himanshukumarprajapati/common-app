package v.s.mergingapp.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;



@Entity(tableName = "User")
public class UserDetail{

   @PrimaryKey
    public int User_id;

    @ColumnInfo(name = "User_name")
    private String User_name;


    @ColumnInfo(name = "User_Email")
    private String User_Email;

    @ColumnInfo(name = "image_url")
    private String image_url;

//    public UserDetail(int user_id,String user_name,String user_Email,String image_url){
//        this.User_id=user_id;
//        this.User_name=user_name;
//        this.User_Email=user_Email;
//        this.image_url=image_url;
//
//    }


    public int getUser_id() {
        return User_id;
    }

    public void setUser_id(int user_id) {
        User_id = user_id;
    }

    public String getUser_name() {
        return User_name;
    }

    public void setUser_name(String user_name) {
        User_name = user_name;
    }



    public String getUser_Email() {
        return User_Email;
    }

    public void setUser_Email(String User_Email) {
        User_Email = User_Email;
    }




    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
