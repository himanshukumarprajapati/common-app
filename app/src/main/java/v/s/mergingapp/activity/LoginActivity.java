package v.s.mergingapp.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.ProfileTracker;
import com.facebook.login.Login;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;
import v.s.mergingapp.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener{
    EditText user_name,user_email;
    private ImageView login_button_gmail;
    private ImageView  login_button_facebook;
    private CallbackManager callbackManager;
    public ProgressBar mProgressDialog;
    private ProfileTracker profileTracker;
    //a constant for detecting the login intent result
    private static final int RC_SIGN_IN = 0;
    //creating a GoogleSignInClient object
    GoogleSignInClient mGoogleSignInClient;
    //And also a Firebase Auth object
    FirebaseAuth mAuth;
    private boolean mIsResolving = false;
    /* Should we automatically resolve ConnectionResults when possible? */
    private boolean mShouldResolve = false;
    private GoogleApiClient mGoogleApiClient;

    EditText ed_otp;
    TextView sign_up;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login_button_gmail=findViewById(R.id.login_button);
        login_button_facebook=findViewById(R.id.gmail_btn);
        login_button_gmail.setOnClickListener(this);
        login_button_facebook.setOnClickListener(this);
        sign_up=findViewById(R.id.sign_up);
        sign_up.setOnClickListener(this);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage( this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {


                AccessToken accessToken=loginResult.getAccessToken();

                GraphRequest request=GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        JSONObject jObject = response.getJSONObject();
                        String data = null;
                        try {
                            data = "name = " + jObject.getString("name");
                            data += "\nid = " + jObject.getString("id");
                            data += "\nemail = " + jObject.getString("email");

                            data += "\nprofile pic = " + "http://graph.facebook.com/"
                                    + jObject.getString("id") + "/picture?type=large";

                            Toast.makeText(getApplicationContext(),
                                    "Welcome " + jObject.getString("name"),
                                    Toast.LENGTH_LONG).show();

                            String[] parts = jObject.getString("name").split("");
                            String fName = parts[0]; // 004
                            String lName = parts[1].length()>1?parts[1]:"";
                            Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
                            intent.putExtra("from","login");
                            startActivity(intent);
                            finish();
                                           /* pref.saveData(Pref.userImage, "http://graph.facebook.com/"
                                                    + jObject.getString("id") + "/picture?type=large");*/

                            Log.e("facebookdata", data);

                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(),"Please enter email id and password",Toast.LENGTH_SHORT).show();

                        }


                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link,email");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(),"Please enter email id and password",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(),"Please enter email id and password",Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("Tag", "onActivityResult:" + requestCode + ":" + resultCode + ":" + data);

        if(requestCode==RC_SIGN_IN) {

            if (resultCode == Activity.RESULT_OK) {
                GoogleSignInResult result=Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                GoogleSignInAccount acct = result.getSignInAccount();
                String personName = acct.getDisplayName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();
                Uri personPhoto = acct.getPhotoUrl();
                Log.e("Gplus User Detail", "email=" + personEmail + "\nname= " + personName
                        + "\nPhoto = " + personPhoto+"\nID" + personId);
                Toast.makeText(this, "Welcome " + personName, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, ProfileActivity.class);
                intent.putExtra("from","login");
                startActivity(intent);
                this.finish();
            }

        }else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onClick(View v) {

        int id=v.getId();
        switch (id){
            case R.id.login_button:
                LoginManager.getInstance().logInWithReadPermissions(this,
                        Arrays.asList("email", "public_profile", "user_friends"));

                break;

            case R.id.gmail_btn:
                signIn();
                break;


            case R.id.sign_up:
                register();

        }



    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void register() {
        Intent signInIntent = new Intent(LoginActivity.this,RegistrationActivity.class);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    @Override
    public void onPause() {
        super.onPause();

        mGoogleApiClient.stopAutoManage(this);
        mGoogleApiClient.disconnect();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.stopAutoManage(this);
        mGoogleApiClient.disconnect();
    }


}
