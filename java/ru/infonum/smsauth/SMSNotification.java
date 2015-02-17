package ru.infonum.smsauth;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.sql.Timestamp;

/**
 * Created by d1i on 09.02.15.
 */

/*
TODO Передавать результат аутентификации еще каким-либо способом, кроме вывода сообщений в лог и во всплывающие окна.
TODO - использовать время для оценки возможности аутентификации - текущее время + время смс.
TODO - разобраться с записью смс в стандартное хранилище. Когда смс отправляет приложение - смс не сохраняется в отправленных.
TODO - реализовать возможность вызова приложения через интент-фильтры, как приложение для аутентификации.
TODO -


 */

public class SMSNotification extends BroadcastReceiver {
    //private static final String AUTH_STR = "infonum.ru/smsauth";
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
        SharedPreferences mPrefs = context.getSharedPreferences(context.getString(R.string.AUTH_RAND), Context.MODE_PRIVATE);
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs = null;
        String str = "";
        if(bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];
            String rS = mPrefs.getString(context.getString(R.string.AUTH_RAND), "");

            for (int i = 0; i < msgs.length; i++) {
                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                msgTel = msgs[i].getOriginatingAddress();
                msgTxt = msgs[i].getMessageBody();
                msgTimeL = msgs[i].getTimestampMillis();
                msgTimeS = Long.toString(msgTimeL);
                timeNow = new java.sql.Timestamp(System.currentTimeMillis());
                timeNowL = (new java.sql.Timestamp(System.currentTimeMillis())).getTime();

                //if (msgTxt.toLowerCase().startsWith(context.getString(R.string.AUTH_STR))
                //        && ((timeNowL - msgTimeL) > 0)) {
                //if (msgTxt.toLowerCase().trim().startsWith(rS.toLowerCase().trim())) {
                String sample = "";
                //for ( int l=0; l<rS.length() && l<msgTxt.length(); l++){
                //    sample += rS.charAt(l) + msgTxt.charAt(l);
                //}
                if (msgTxt.toLowerCase().contains(rS.toLowerCase().trim())) {
                    Toast.makeText(context, "AUTH OK!", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "003--" + msgTxt.toLowerCase() + " : " + context.getString(R.string.AUTH_STR_RAND) + rS);

                    Intent intent2 = new Intent(this, MainActivity.class )
                    break;
                }else{
                    //Toast.makeText(context, "auth NOT ok!!! m1=" + msgTxt.length() + " r2=" + rS.length(), Toast.LENGTH_LONG).show();
                    Toast.makeText(context, "auth NOT ok!!! m1=" + msgTxt.length() + " r2=" + rS.length(), Toast.LENGTH_LONG).show();
                    Log.d(TAG, "004--" + msgTxt.toLowerCase() + " : " + context.getString(R.string.AUTH_STR_RAND) + rS);
                }

            }
            str = "Tel=" + msgTel + ", Text=" + msgTxt + " Time=" + Long.toString(timeNowL - msgTimeL);
            Log.d(TAG, "002--" + str);
            Toast.makeText(context, str, Toast.LENGTH_LONG).show();

        }
    }
}
