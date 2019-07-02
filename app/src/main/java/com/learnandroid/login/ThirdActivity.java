package com.learnandroid.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ThirdActivity extends AppCompatActivity {
    EditText text;
    Button submit;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reff;
     int count=3;
    String s_otp;
    String u_otp;

    public static final String DEFAULT = "N/A";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_activity);
        text=(EditText) findViewById(R.id.otp_text);
        submit=(Button)findViewById(R.id.submit);

    }
    @Override
    public void onBackPressed() {
        Toast.makeText(this,"U cant go back ",Toast.LENGTH_SHORT).show();

        //super.onBackPressed();


    }


    public void submit(View view) {
        u_otp=text.getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        String u_name = sharedPreferences.getString("UserName", DEFAULT);


        if (u_name.equals(DEFAULT)) {

            Toast.makeText(this, "otp details not found", Toast.LENGTH_SHORT).show();

        }
        firebaseDatabase = FirebaseDatabase.getInstance();
        reff = firebaseDatabase.getReference("Users");

        reff.child(u_name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 s_otp = dataSnapshot.child("otp").getValue().toString();
                 validate(s_otp);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }

    private void validate(String s_otp) {
        if (count>=0)
            if (s_otp.equals(u_otp))
            { startActivity(new Intent(ThirdActivity.this,NotificationActivity.class)); }
            else if (count!=0){
                count=count-1;
                Toast.makeText(this, "Wrong Otp \n attempts left:"+count, Toast.LENGTH_SHORT).show();

            }
            else{
                startActivity(new Intent(ThirdActivity.this,MainActivity.class));


            }

    }
}
