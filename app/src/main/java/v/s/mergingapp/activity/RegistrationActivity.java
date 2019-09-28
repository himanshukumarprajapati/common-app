package v.s.mergingapp.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.phone.SmsRetriever;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

import v.s.mergingapp.R;

public class RegistrationActivity extends AppCompatActivity {


    FloatingActionButton buttonConfirm;

    TextInputEditText verify_numbers;

    SharedPreferences pref;
    String phone_number,SchoolId;

    public ProgressDialog mProgressDialog;

    String Exists,OTP;

    TextView username_text_input_layout;

    public static final String USER_PREF = "USER_PREF" ;
    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
       // clickgreen=findViewById(R.id.clickgreen);
        //ed_otp = findViewById(R.id.ed_otp);

        //  username_text_input_layout=findViewById(R.id.username_text_input_layout);
        verify_numbers = findViewById(R.id.verify_numbers);

        phone_number = verify_numbers.getText().toString();
        //setupFloatingLabelError();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        verify_numbers.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hideKeyboard();

                    // searchAction();
                    return true;
                }
                return false;
            }
        });

        verify_numbers.addTextChangedListener(new TextWatcher() {
            // ...
            @SuppressLint("ResourceType")
            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
                if (text.length() < 10 || text.length() > 10) {
                    verify_numbers.setError(getString(R.string.username_required));
                    // username_text_input_layout.setText(R.color.deep_orange_A700);
                    /*if (text.length()){
                        buttonConfirm.setVisibility(View.VISIBLE);
                    }*/
                } else {
                    //   username_text_input_layout.setText("hiiiii");
                    //verify_numbers.setE(getString(R.string.username_required_value));


                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                processButtonByTextLength();
            }
        });

        verify_numbers.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                // Get key action, up or down.
                int action = keyEvent.getAction();
                // Only process key up action, otherwise this listener will be triggered twice because of key down action.
                if (action == KeyEvent.ACTION_UP) {
                    processButtonByTextLength();
                }
                return false;
            }
        });
        buttonConfirm = findViewById(R.id.buttonConfirm);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonConfirm.setEnabled(false);
                if (isValidPhone(phone_number)) {
                    otpVerification();
                }
                verify_numbers = findViewById(R.id.verify_numbers);

                phone_number = verify_numbers.getText().toString();

                if (isValidPhone(verify_numbers.getText().toString())) {

                    // Toast.makeText(getApplicationContext(),"Phone number is valid",Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(getApplicationContext(), "Phone number is not valid", Toast.LENGTH_SHORT).show();

                }


            }


        });

    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("otp")) {
                final String message = intent.getStringExtra("message");
                verify_numbers.setText(message);
                //Do whatever you want with the code here
            }
        }
    };

    @Override
    public void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("otp"));
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    private void processButtonByTextLength() {
        String inputText = verify_numbers.getText().toString();
        if (inputText.length() == 10) {
            //  buttonConfirm.setText("I Am Enabled.");
            buttonConfirm.setVisibility(View.VISIBLE);
            //clickgreen.setVisibility(View.VISIBLE);
        } else {
            //   buttonConfirm.setText("I Am Disabled.");
            buttonConfirm.setVisibility(View.INVISIBLE);
          //  clickgreen.setVisibility(View.INVISIBLE);
        }
    }

    private boolean isValidPhone(String phone) {
        boolean check = true;
        phone_number = verify_numbers.getText().toString();

        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            if (phone_number.length() < 10 || phone_number.length() > 10) {
                check = false;

            }

        }

        return check;
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void otpVerification() {

        SharedPreferences prefs = getSharedPreferences(USER_PREF, MODE_PRIVATE);
        String BaseURL = prefs.getString("BaseURL", null);

        //  String SchoolId=prefs.getString("SchoolId",null);

        verify_numbers = findViewById(R.id.verify_numbers);

        phone_number = verify_numbers.getText().toString();

        Log.e("phone_number", phone_number);


//        Log.e("schoolids",SchoolId);


        String JsonURL = "http://beta-mpp-dpsasr.schooloncloud.com//api/VisitorManagement/VisitorVerify?MobileNo=" + phone_number + "&optMode=Verify&SchoolId=1";
//        http://beta-mpp-dpsasr.schooloncloud.com/api/VisitorManagement/VisitorVerify?MobileNo=8909556541&optMode=Verify&SchoolId=1
        Log.e("himanshuu", JsonURL);
      //  final Boolean isInternet = ConnectionManager.isNetworkOnline(this);

        // This string will hold the results

        // Defining the Volley request queue that handles the URL request concurrently
        RequestQueue requestQueue;

        requestQueue = Volley.newRequestQueue(this);

        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<String>() {
            @Override
            public void onRequestFinished(Request<String> request) {
//                if (mProgressDialog != null && isInternet && mProgressDialog.isShowing())
//                    mProgressDialog.isShowing();


            }


        });
        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET, JsonURL, new Response.Listener<JSONObject>() {

            // Takes the response from the JSON request
            @Override
            public void onResponse(JSONObject response) {
                JSONArray jsonArray = null;
                try {

                    JSONObject jsonObject = new JSONObject(String.valueOf(response));

                    Log.e("himanshuushs", String.valueOf(jsonObject));

                    //   JSONObject on = jsonObject.getJSONObject("cirlist");
                    jsonArray = jsonObject.getJSONArray("Verify");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObj = (JSONObject) jsonArray.get(i);
                        Log.e("Exists", jsonObj.getString("Exists"));

                        Log.e("OTP", jsonObj.getString("OTP"));

                        Exists = jsonObj.getString("Exists");
                        OTP = jsonObj.getString("OTP");

                        IntentFilter filter = new IntentFilter();
                        filter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION);
                     //   registerReceiver(new SmsReceiver(), filter);
                        if (Exists.equals("false")) {

                            Intent intent = new Intent(RegistrationActivity.this, NewUserOtp.class);
                            startActivity(intent);
                            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("OTP", OTP);
                            editor.putString("Exists", Exists);

                            editor.putString("phone_number", phone_number);
                            Log.e("hhhoooas", phone_number);

                            editor.putString("USER_TYPE", "");
                            // editor.clear();
                            editor.commit();

                        } else if (Exists.equals("true")) {

                            Intent intent = new Intent(RegistrationActivity.this, NewUserOtp.class);
                            startActivity(intent);
                            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("OTP", OTP);
                            editor.putString("Exists", Exists);
                            editor.putString("phone_number", phone_number);
                            Log.e("hhhoooas", phone_number);
                            editor.putString("USER_ID", "");
                            editor.putString("USER_TYPE", "");
                            // editor.clear();
                            editor.commit();


                        }


                    }


                    JSONArray jsonArray1 = null;

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        },
                // The final parameter overrides the method onErrorResponse() and passes VolleyError
                //as a parameter
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                        buttonConfirm.setEnabled(true);
                        String message = null;
                        if (error instanceof NetworkError) {
                            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        // Adds the JSON object request "obreq" to the request queue
        requestQueue.add(obreq);


    }

}
//    @Override
//    public void onBackPressed() {
//        Intent intent=new Intent(VerifyUserNumber.this,EntryExitForm.class);
//        startActivity(intent);
//        finish();
//    }
    /*private void setupFloatingLabelError() {
        final TextInputLayout floatingUsernameLabel =findViewById(R.id.username_text_input_layout);
        floatingUsernameLabel.getEditText().addTextChangedListener(new TextWatcher() {
            // ...
            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
                if (text.length() > 0 && text.length() <= 4) {
                    floatingUsernameLabel.setError(getString(R.string.username_required));
                    floatingUsernameLabel.setErrorEnabled(true);
                } else {
                    floatingUsernameLabel.setErrorEnabled(false);
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/
    // }
//}