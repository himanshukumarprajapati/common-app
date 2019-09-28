package v.s.mergingapp.activity;

import android.app.Activity;
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
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.ProfileTracker;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;
import v.s.mergingapp.R;


public class MainActivity extends AppCompatActivity { /*implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener{
    CircleImageView circleImageView;
    EditText user_name,user_email;
  //  LoginButton login_button;

    private ImageView login_button_gmail;
    private ImageView  login_button_facebook;

    private CallbackManager callbackManager;
    public ProgressBar mProgressDialog;

    private ProfileTracker profileTracker;



    //a constant for detecting the login intent result
    private static final int RC_SIGN_IN = 234;

    //Tag for the logs optional
    private static final String TAG = "simplifiedcoding";

    //creating a GoogleSignInClient object
    GoogleSignInClient mGoogleSignInClient;

    //And also a Firebase Auth object
    FirebaseAuth mAuth;

    private boolean mIsResolving = false;

    *//* Should we automatically resolve ConnectionResults when possible? *//*
    private boolean mShouldResolve = false;


    private GoogleApiClient mGoogleApiClient;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

}
//        login_button_gmail=findViewById(R.id.login_button);
//        login_button_facebook=findViewById(R.id.gmail_btn);
//
//        login_button_gmail.setOnClickListener(this);
//        login_button_facebook.setOnClickListener(this);
//
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .build();
//
//        mGoogleApiClient=new GoogleApiClient.Builder(this)
//                .enableAutoManage( this,this)
//                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                .build();
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//                if(requestCode==RC_SIGN_IN) {
//
//                    if (resultCode == Activity.RESULT_OK) {
//                        GoogleSignInResult result=Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//                        GoogleSignInAccount acct = result.getSignInAccount();
//                        String personName = acct.getDisplayName();
//                        String personEmail = acct.getEmail();
//                        String personId = acct.getId();
//                        Uri personPhoto = acct.getPhotoUrl();
//                        Log.e("Gplus User Detail", "email=" + personEmail + "\nname= " + personName
//                                + "\nPhoto = " + personPhoto+"\nID" + personId);
//                        Toast.makeText(this, "Welcome " + personName, Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(this, ProfileActivity.class);
//                        intent.putExtra("from","login");
//                        startActivity(intent);
//                        this.finish();
//                    }
//
//                }
//
//
//
//
//
//
//    }
//
//    @Override
//    public void onClick(View v) {
//
//        int id=v.getId();
//        switch (id){
//            case R.id.login_button:
//                break;
//
//            case R.id.gmail_btn:
//                signIn();
//                break;
//        }
//
//    }
//
//    private void signIn() {
//        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
//        startActivityForResult(signInIntent, RC_SIGN_IN);
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//      }
//    @Override
//    public void onPause() {
//        super.onPause();
//
//        mGoogleApiClient.stopAutoManage(this);
//        mGoogleApiClient.disconnect();
//    }
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mGoogleApiClient.stopAutoManage(this);
//        mGoogleApiClient.disconnect();
//    }
//
//}
//    private void firebaseAuthWithGoogle(GoogleSignInAccount acct){
//
//        Log.e(TAG,"firebaseAuthWithGoogle:" + acct.getId());
//
//        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
//
//        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()) {
//                    Log.d(TAG, "signInWithCredential:success");
//                    FirebaseUser user = mAuth.getCurrentUser();
//
//                    Toast.makeText(MainActivity.this, "User Signed In", Toast.LENGTH_SHORT).show();
//
//                } else {
//                    // If sign in fails, display a message to the user.
//                    Log.w(TAG, "signInWithCredential:failure", task.getException());
//                    Toast.makeText(MainActivity.this, "Authentication failed.",
//                            Toast.LENGTH_SHORT).show();
//
//                }
//            }
//        });


//                callbackManager = CallbackManager.Factory.create();
//
//                login_button.setReadPermissions(Arrays.asList("email","public_profile"));
//
//                LoginManager.getInstance().registerCallback(callbackManager,
//                        new FacebookCallback<LoginResult>() {
//                            @Override
//                            public void onSuccess(LoginResult loginResult) {
//                                // App code
//
//                                mProgressDialog.setVisibility(View.GONE);
//                                login_button.setVisibility(View.INVISIBLE); //<- IMPORTANT
////                                Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
////                                startActivity(intent);
//                               // finish();
//
//
//                            }
//
//                            @Override
//                            public void onCancel() {
//                                // App code
//                            }
//
//                            @Override
//                            public void onError(FacebookException exception) {
//                                // App code
//
//                                Toast.makeText(MainActivity.this, "Don't Have an Account on Facebook", Toast.LENGTH_SHORT).show();
//                            }
//                        });



//    AccessTokenTracker accessTokenTracker=new AccessTokenTracker() {
//        @Override
//        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
//
//
//            if (accessTokenTracker==null) {
//                user_name.setText("");
//                user_email.setText("");
//                circleImageView.setImageResource(0);
//
//            }else {
//
//                loaduserprofile(currentAccessToken);
//
//            }
//
//        }
//    };


//    private void loaduserprofile(final AccessToken newAccessToken){
//
//        GraphRequest request=GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
//            @Override
//            public void onCompleted(JSONObject object, GraphResponse response) {
//
//                String first_name,last_name,email,id;
//                try {
//
//
//
//
//                        first_name=object.getString("first_name");
//
//
//                        last_name=object.getString("last_name");
//                        email=object.getString("email");
//                        id=object.getString("id");
//
//
//
//
//
//
//
//
//                        String img_url="https://graph.facebook.com/"+id+"/picture?type=normal";
//
//
////                    user_name.setText(first_name+last_name);
////                    user_email.setText(email);
//
//
//                    RequestOptions requestOptions=new RequestOptions();
//                    requestOptions.dontAnimate();
//
//
//               //     Glide.with(MainActivity.this).load(img_url).into(circleImageView);
//
//
//
//                    Intent intent=new Intent(MainActivity.this,ProfileActivity.class);
//
//                    intent.putExtra("User_name",first_name+last_name);
//                    intent.putExtra("User_email",email);
//                    intent.putExtra("User_image",img_url);
//
//
//
//                    startActivity(intent);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//
//        Bundle params=new Bundle();
//        params.putString("fields","first_name,last_name,email,id");
//
//        request.setParameters(params);
//        request.executeAsync();
//
//
//    }

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        mProgressDialog.setVisibility(View.VISIBLE);
        super.onActivityResult(requestCode, resultCode, data);

    };

    callbackManager.onActivityResult(requestCode,resultCode,data);
    }*/


