package ru.infonum.smsauth;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.sql.Timestamp;

/**
 * Created by d1i on 09.02.15.
 */

public class SMSNotification extends BroadcastReceiver {
    private static final String AUTH_STR = "infonum.ru/smsauth";
    //private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    public static String msgTel = "";
    public static String msgTxt = "";
    public static long msgTimeL = 0L;
    public static String msgTimeS = "";
    public static final String TAG = "SmsAuth";
    public static Timestamp timeNow;
    public static long timeNowL;
 ;

    @Override
    public void onReceive(Context context, Intent intent) {


        Toast.makeText(context, "SmsAuth перехватило смс", Toast.LENGTH_LONG).show();

        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs = null;
        String str = "";
        if(bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];

            for (int i = 0; i < msgs.length; i++) {
                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                msgTel = msgs[i].getOriginatingAddress();
                msgTxt = msgs[i].getMessageBody();
                msgTimeL = msgs[i].getTimestampMillis();
                msgTimeS = Long.toString(msgTimeL);
                timeNow = new java.sql.Timestamp(System.currentTimeMillis());
                timeNowL = (new java.sql.Timestamp(System.currentTimeMillis())).getTime();

                //if (msgTxt.toLowerCase().startsWith(context.getString(R.string.AUTH_STR)) && (timeNowL > msgTimeL)) {
                if (msgTxt.toLowerCase().startsWith(context.getString(R.string.AUTH_STR))) {
                    Toast.makeText(context, "AUTH OK!", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "003--" + msgTxt.toLowerCase() + " : " + context.getString(R.string.AUTH_STR));

                }else{
                    Toast.makeText(context, "AUTH not OK!!!", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "004--" + msgTxt.toLowerCase() + " : " + context.getString(R.string.AUTH_STR));
                }
            }
            str = "Tel=" + msgTel + ", Text=" + msgTxt + " msgTimeStamp=" + msgTimeS + " : " + timeNowL;
            Log.d(TAG, "002--" + str);
            Toast.makeText(context, str, Toast.LENGTH_LONG).show();




            if (msgTxt.toLowerCase().startsWith(AUTH_STR)) {
                str = msgTxt.substring(AUTH_STR.length());
                Toast.makeText(context, str, Toast.LENGTH_LONG).show();
                //sms.sendTextMessage(to, null, out, null, null);
            }
        }
    }
}
