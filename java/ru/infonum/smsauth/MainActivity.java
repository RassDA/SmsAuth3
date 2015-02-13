package ru.infonum.smsauth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Timestamp;


public class MainActivity extends ActionBarActivity {

    public static final String TAG = "SmsAuth";

    Button sendSMSBtn;
    EditText editTextTel;
    EditText editTextTxt;
    final String TESTTEL = "+79119148047";
    public static Timestamp sendingTime;
    public static String sendingTimeS;
    public static long sendingTimeL;
    public static String msgTxt ="";
    public static String msgTel = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //msgTel = editTextTel.getText().toString();
        //msgTel = TESTTEL;
        //msgTxt = this.getString(R.string.AUTH_STR) + " Отправьте эту смс на свой номер";
        //startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts(msgTxt, msgTel, null)));

        sendSMSBtn = (Button) findViewById(R.id.btnSendSMS);
        editTextTel = (EditText) findViewById(R.id.editTextPhoneNo);
        editTextTel.setText(TESTTEL);
        editTextTxt = (EditText) findViewById(R.id.editTextSMS);
        //final Random random = new Random();
        editTextTxt.setText(this.getString(R.string.AUTH_STR));
        //startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts(msgTxt, msgTel, null)));

        //smsMessageET.setText(String.valueOf(Math.abs(random.nextInt(9999))));

        sendSMSBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (editTextTel.getText().toString().equals("+")) {
                    sendSmsBI();
                } else {
                    sendSMS();
                }
            }
        });

    }

    protected void sendSMS() {
        msgTel = editTextTel.getText().toString();
        msgTxt = editTextTxt.getText().toString();
        try {

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(msgTel, null, msgTxt, null, null);
            Toast.makeText(getApplicationContext(), "SmsAuth: Смс отправлено.",
                    Toast.LENGTH_LONG).show();
            sendingTime = new java.sql.Timestamp(System.currentTimeMillis());
            sendingTimeL = sendingTime.getTime();
            Log.d(TAG, "001-- Time=" + new java.sql.Timestamp(System.currentTimeMillis()).toString());
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "SmsAuth: Сбой в отправке смс. Tel= " + msgTel + "Txt=" + msgTxt,
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    protected void sendSmsBI() {
        msgTel = editTextTel.getText().toString();
        msgTxt = editTextTxt.getText().toString() + " Отправьте эту смс на свой номер";
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.putExtra("sms_body", "http://infonum.ru/auth");
        sendIntent.setType("vnd.android-dir/mms-sms");
        startActivity(sendIntent);
/*
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts(msgTxt, msgTel, null)));

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(msgTel, null, msgTxt, null, null);
            Toast.makeText(getApplicationContext(), "SmsAuth: Смс отправлено.",
                    Toast.LENGTH_LONG).show();
            sendingTime = new java.sql.Timestamp(System.currentTimeMillis());
            sendingTimeL = sendingTime.getTime();
            Log.d(TAG, "001-- Time=" + new java.sql.Timestamp(System.currentTimeMillis()).toString());

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "SmsAuth: Сбой в отправке смс. Tel= " + msgTel + "Txt=" + msgTxt,
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
