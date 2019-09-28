package v.s.mergingapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import v.s.mergingapp.R;

public class NewUserOtp extends AppCompatActivity {


    String user_number;

    SharedPreferences pref;

    TextView user_number_input;
    Button continue_otps;

    String name,Exists,OTP;

    EditText et1,et2,et3,et4;

    private TextView tv_coundown;
    String message,newotp;

    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_otp);

        tv_coundown = (TextView) findViewById(R.id.tv_coundown);
        countDownTimer();
        et1=findViewById(R.id.et11);
        et2=findViewById(R.id.et22);
        et3=findViewById(R.id.et33);
        et4=findViewById(R.id.et44);

        et1.addTextChangedListener(new GenericTextWatcher(et1));
        et2.addTextChangedListener(new GenericTextWatcher(et2));
        et3.addTextChangedListener(new GenericTextWatcher(et3));
        et4.addTextChangedListener(new GenericTextWatcher(et4));



        user_number_input=findViewById(R.id.user_number_input_cur);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String name = preferences.getString("phone_number", "");
        Log.e("setvalue",name);
        user_number_input.setText(name);
        //    Log.e("user_number_selet",user_number);






        continue_otps=findViewById(R.id.continue_otps);
        continue_otps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateotp();
            }
        });
    }


    private void countDownTimer() {
        countDownTimer = new CountDownTimer(1000 * 60 * 2, 1000) {
            @Override
            public void onTick(long l) {
                String text = String.format(Locale.getDefault(), "%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(l) % 60,
                        TimeUnit.MILLISECONDS.toSeconds(l) % 60);
                tv_coundown.setText(text);
            }

            @Override
            public void onFinish() {
                tv_coundown.setText("00:00");
            }
        };
        countDownTimer.start();
    }


    public class GenericTextWatcher implements TextWatcher {

        private View view;
        private GenericTextWatcher(View view)
        {
            this.view=view;

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String text=s.toString();
            message = et1.getText().toString()+ et2.getText().toString()+et3.getText().toString()+ et4.getText().toString();
            Log.e("sss",message);
            switch (view.getId())
            {

                case R.id.et11:
                    if(text.length()==1)
                        et2.requestFocus();
                    break;
                case R.id.et22:
                    if(text.length()==1)
                        et3.requestFocus();
                    else if(text.length()==0)
                        et1.requestFocus();
                    break;
                case R.id.et33:
                    if(text.length()==1)
                        et4.requestFocus();
                    else if(text.length()==0)
                        et2.requestFocus();
                    break;
                case R.id.et44:
                    if(text.length()==0)
                        et3.requestFocus();
                    break;
            }
        }


    }



    public void validateotp(){

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor1 = pref.edit();

        Exists = pref.getString("Exists", "");

        OTP = pref.getString("OTP", "");

        Log.e("Existsnew",Exists);

        Log.e("OTPnew",OTP);



        String s = OTP;
        Log.e("newotp",(s.substring(0, s.length() - 2)));
        newotp=(s.substring(0, s.length() - 2));


        if (OTP.equals(message)){
            Intent intent=new Intent(NewUserOtp.this,ProfileActivity.class);
            startActivity(intent);

        }else {
            Toast toast = Toast.makeText(NewUserOtp.this,"OTP Not Valid", Toast.LENGTH_LONG);
            //   Toast.makeText(this,"OTP Not Valid",Toast.LENGTH_SHORT).show();
            toast.setGravity(Gravity.CENTER, 0, 0);
            View view = toast.getView();

//Gets the actual oval background of the Toast then sets the colour filter
            view.getBackground().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);

//Gets the TextView from the Toast so it can be editted
            TextView text = view.findViewById(android.R.id.message);
            text.setTextColor(Color.WHITE);
            toast.show();
        }

    }

}

