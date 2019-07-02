package com.learnandroid.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SecondActivity extends AppCompatActivity {
    Button submit;
    TextView  otp_mode;
    int field_checked;


    public static final String DEFAULT = "N/A";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seoncd_activity);
        submit=(Button)findViewById(R.id.submit);
        otp_mode=(TextView)findViewById(R.id.otp_mode);
    }

    public void radio_btn_clicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.ck_email:
                if (checked){
                    field_checked=1;
                    Toast.makeText(getApplicationContext(), "Email is checked" , Toast.LENGTH_SHORT).show();
                    }
                    break;
            case R.id.ck_sms:
                if (checked){
                    field_checked=2;
                    Toast.makeText(getApplicationContext(), "SMS is checked" , Toast.LENGTH_SHORT).show();
                    }
                    break;
            case R.id.ck_token:
                if (checked){
                    field_checked=3;
                    Toast.makeText(getApplicationContext(), "Token is checked" , Toast.LENGTH_SHORT).show();}

                    break;

        }
    }

    public void fetch_otp(View view) {

        if (field_checked==1 || field_checked==2 || field_checked==3 )
        {
            startActivity(new Intent(SecondActivity.this,ThirdActivity.class));}
        else {
            Toast.makeText(getApplicationContext(), "Something has to be checked" , Toast.LENGTH_SHORT).show();}
        }

    }




