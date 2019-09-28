package v.s.mergingapp.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import v.s.mergingapp.activity.SmsListener;

public class IncommingSms extends BroadcastReceiver
{
    private static SmsListener mListener;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Bundle data  = intent.getExtras();

        Object[] pdus = (Object[]) data.get("pdus");


        for(int i = 0;i < pdus.length; i++)
        {
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);

            //String sender = smsMessage.getDisplayOriginatingAddress();
            //You must check here if the sender is your provider and not another one with same text.

            String messageBody = smsMessage.getMessageBody();

            //Pass on the text to our listener.
            if(mListener != null)
            {
                mListener.messageReceived(parseCode(messageBody));
            }
        }

    }

    private String parseCode(String message)
    {
        Pattern p = Pattern.compile("\\b\\d{4}\\b");
        Matcher m = p.matcher(message);
        String code = "";
        while (m.find())
        {
            code = m.group(0);
        }
        return code;
    }

    public static void bindListener(SmsListener listener)
    {
        mListener = listener;
    }
}
/*extends BroadcastReceiver {

    // Get the object of SmsManager
    final SmsManager sms = SmsManager.getDefault();

    public void onReceive(Context context, Intent intent) {

        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();

        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody().split(":")[1];

                    message = message.substring(0, message.length() - 1);
                    Log.i("SmsReceivermessagecome", "senderNum: " + senderNum + "; message: " + message);

                    Intent myIntent = new Intent("otp");
                    myIntent.putExtra("messagecomesin", message);
                    Log.e("myIntent", String.valueOf(myIntent));
                    LocalBroadcastManager.getInstance(context).sendBroadcast(myIntent);
                    // Show Alert

                } // end for loop
            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" + e);

        }

    }
    }*/