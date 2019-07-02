package com.learnandroid.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {

    EditText userName;
    EditText password;
    EditText email;
    Button login;
    Button reset;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reff;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        userName = (EditText) findViewById(R.id.UserName);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.Password);
        login = (Button) findViewById(R.id.login);
        reset = (Button) findViewById(R.id.reset);




    }

    public void login(View view) {
        String u_name = userName.getText().toString();
        final String u_email = email.getText().toString();
        final String u_pwd = password.getText().toString();
        firebaseDatabase = FirebaseDatabase.getInstance();
        reff = firebaseDatabase.getReference("Users");
        sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("UserName",u_name);
        editor.putString("Email",u_email);
        editor.putString("Password",u_pwd);
        editor.commit();

        try {
            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String s_email = dataSnapshot.child("email").getValue().toString();
                    String s_pwd = dataSnapshot.child("password").getValue().toString();

                    if (u_email.equals(s_email) && u_pwd.equals(s_pwd)) {

                        startActivity(new Intent(MainActivity.this, SecondActivity.class));


                    } else {
                        Toast.makeText(getApplicationContext(), "Wrong Username or Password", Toast.LENGTH_SHORT).show();
                    }



                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            reff.child(u_name).addValueEventListener(valueEventListener);


            /*reff.child(u_name).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    s_email = dataSnapshot.child("email").getValue().toString();
                    s_pwd = dataSnapshot.child("password").getValue().toString();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            //Toast.makeText(getApplicationContext(), "from server" + s_email + s_pwd, Toast.LENGTH_SHORT).show();
            //Toast.makeText(getApplicationContext(), "from user" + u_email + u_pwd, Toast.LENGTH_SHORT).show();

            if (u_email.equals(s_email) && u_pwd.equals(u_pwd)) {

                startActivity(new Intent(MainActivity.this, SecondActivity.class));


            } else {
                Toast.makeText(getApplicationContext(), "Wrong Username or Password", Toast.LENGTH_SHORT).show();
            }*/
        }
        catch (NullPointerException e){
            Toast.makeText(getApplicationContext(), "User doesnt exist", Toast.LENGTH_SHORT).show();
            e.printStackTrace();

        }





}
    public void reset(View view) {
        userName.setText("");
        email.setText("");
        password.setText("");

    }
}

