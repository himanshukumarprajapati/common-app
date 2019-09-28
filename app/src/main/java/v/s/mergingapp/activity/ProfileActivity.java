package v.s.mergingapp.activity;

import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;

import de.hdodenhof.circleimageview.CircleImageView;
import v.s.mergingapp.R;

public class ProfileActivity extends AppCompatActivity {
    private CallbackManager callbackManager;
    CircleImageView circleImageView;
    TextView user_name, user_email;
    LoginButton login_button;
    boolean doubleBackToExitPressedOnce = false;
    DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        circleImageView=findViewById(R.id.circular_imageview);
        user_name=findViewById(R.id.user_name);
        user_email=findViewById(R.id.user_email);


        String User_name,User_email,User_image;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                User_name= null;
                User_email=null;
                User_image=null;
            } else {
                User_name= extras.getString("User_name");
                User_email=extras.getString("User_email");
                User_image=extras.getString("User_image");
            }
        } else {
            User_name= (String) savedInstanceState.getSerializable("User_name");
            User_email= (String) savedInstanceState.getSerializable("User_email");
            User_image= (String) savedInstanceState.getSerializable("User_image");

        }

        user_name.setText(User_name);
        user_email.setText(User_email);
        Glide.with(ProfileActivity.this).load(User_image).into(circleImageView);

    }

    @Override
    public void onBackPressed() {
//        drawer = findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
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

