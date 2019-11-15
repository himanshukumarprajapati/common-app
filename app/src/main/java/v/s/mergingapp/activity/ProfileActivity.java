package v.s.mergingapp.activity;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;

import de.hdodenhof.circleimageview.CircleImageView;
import v.s.mergingapp.R;
import v.s.mergingapp.model.UserDetail;
import v.s.mergingapp.utils.MyAppDataBase;

public class ProfileActivity extends AppCompatActivity {
    private CallbackManager callbackManager;
    CircleImageView circleImageView;
    TextView user_name, user_email;
    LoginButton login_button;
    boolean doubleBackToExitPressedOnce = false;
    DrawerLayout drawer;
    Toolbar toolbar;
    TextView txt_title,notice;
    String newString,User_Email,User_Name,User_Image;
    // Database
    public static MyAppDataBase myAppDataBase;

    Button ssuub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        UserDetail userDetail=new UserDetail();

       String valuess= userDetail.getUser_Email();

       Log.e("valuescomesin",valuess);

       ssuub=findViewById(R.id.ssuub);
        ssuub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProfileActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        circleImageView=findViewById(R.id.circular_imageview);
        user_name=findViewById(R.id.user_name);
        user_email=findViewById(R.id.user_email);

        myAppDataBase= Room.databaseBuilder(getApplicationContext(),MyAppDataBase.class,"Userdb").allowMainThreadQueries().build();
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                newString = null;
                User_Email=null;
                User_Name=null;
                User_Image=null;
            } else {
                //  newString= extras.getString("from","login");
                User_Name = extras.getString("User_Name");
                user_name.setText(User_Name);
                User_Email=extras.getString("User_Email");
                user_email.setText(User_Email);

                User_Image=extras.getString("User_Image");
                Glide.with(ProfileActivity.this).load(User_Image).into(circleImageView);


                }
            }
         else {
            newString= (String) savedInstanceState.getSerializable("STRING_I_NEED");
        }


//        UserDetail userDetail=new UserDetail();
//       // userDetail.setUser_id(Integer.parseInt(User_email));
//        userDetail.setUser_name(User_Name);
//        userDetail.setImage_url(User_Image);
//        userDetail.setUser_Email(User_Email);

     //   String x=userDetail.setUser_Email(User_Email);







//        if (userDetail.equals("")){
//            Toast.makeText(this, "Not Registered", Toast.LENGTH_SHORT).show();
//
//        }else {
//       //     ProfileActivity.myAppDataBase.myDao().addUser(userDetail);
//       //     Toast.makeText(this, "Data Save", Toast.LENGTH_SHORT).show();
//
//        }

    }

    @Override
    public void onBackPressed() {
            if (doubleBackToExitPressedOnce) {
                // super.onBackPressed();
                moveTaskToBack(true);
                return;
            }
            this.doubleBackToExitPressedOnce = true;

            LayoutInflater inflater = getLayoutInflater();
            View toastLayout = inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.custom_toast_layout));
            Toast toast = new Toast(getApplicationContext());
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(toastLayout);
            toast.show();
            // Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 4000);
        }
    }

