package ru.infonum.smsauth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Timestamp;
import java.util.Random;


public class MainActivity extends ActionBarActivity {

    public static final String TAG = "SmsAuth";

    Button btnSendSms;
    Button btnBI;
    EditText editTextTel;
    EditText editTextTxt;

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

        btnSendSms = (Button) findViewById(R.id.btnSendSms);
        btnBI = (Button) findViewById(R.id.btnBI);

        editTextTel = (EditText) findViewById(R.id.editTextPhoneNo);
        //editTextTel.setText("Введите сюда свой номер");
        editTextTel.setText(getString(R.string.TESTTEL));

        editTextTxt = (EditText) findViewById(R.id.editTextSMS);
        final Random random = new Random();
        final String randS = String.valueOf(Math.abs(random.nextInt(9999))); // лидирующие нули отбрасываются !!
        msgTxt = getString(R.string.AUTH_STR_RAND) + randS;
        editTextTxt.setText(msgTxt);

        final TextView textViewLog = (TextView) findViewById(R.id.textView3);

        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
        editor.clear();
        editor.putString(getString(R.string.AUTH_RAND), msgTxt);
        editor.apply();

        Log.d(TAG, "015-- Time=" + new java.sql.Timestamp(System.currentTimeMillis()).toString());

        //startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts(msgTxt, msgTel, null)));
        //smsMessageET.setText(String.valueOf(Math.abs(random.nextInt(9999))));

        btnSendSms.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendSMS();
                textViewLog.setText("001-- Time=" + new java.sql.Timestamp(System.currentTimeMillis()).toString()
                        + ": Попытка отправить смс через это приложение\n " + msgTxt );
                editTextTxt.setText(msgTxt);
            }
        });

        btnBI.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendSmsBI();
                textViewLog.setText("001-- Time=" + new java.sql.Timestamp(System.currentTimeMillis()).toString()
                        + ": Попытка отправлить смс через стандартное смс-приложение\n " + msgTxt);

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

    protected void sendSmsBI() { //Send Sms by Built-in sms manager
        msgTel = editTextTel.getText().toString();
        //msgTxt = editTextTxt.getText().toString() + " Отправьте эту смс на свой номер";
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.putExtra("sms_body", msgTxt);
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
