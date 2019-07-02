package com.learnandroid.login;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import android.support.v7.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<Data> list;
    MyAdapter adapter;


    public static final String DEFAULT = "N/A";

    public void onBackPressed() {
        Toast.makeText(this,"You cant go back you have to logout",Toast.LENGTH_SHORT).show();



        //super.onBackPressed();


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_my, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

            switch (item.getItemId()) {

                case R.id.logout:

                    startActivity(new Intent(this, MainActivity.class));

                    return true;

                case R.id.about:
                    Toast.makeText(this,"not yet made",Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.setting:
                    Toast.makeText(this,"not yet made",Toast.LENGTH_SHORT).show();
                    return true;

                default:

                    return super.onOptionsItemSelected(item);

//respond to menu item selection

            }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_activity);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        recyclerView=(RecyclerView)findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list=new ArrayList<Data>();

        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        String u_name = sharedPreferences.getString("UserName", DEFAULT);


        if (u_name.equals(DEFAULT)) {

            Toast.makeText(this, "otp details not found", Toast.LENGTH_SHORT).show();

        }
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference= firebaseDatabase.getReference("Users").child(u_name);

        reference.child("Data").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                    Data d = dataSnapshot1.getValue(Data.class);
                    list.add(d);
                    //Toast.makeText(NotificationActivity.this, d.getDate() + "\n" + d.getSender() + "\n" + d.getSubject(), Toast.LENGTH_SHORT).show();
                }
                adapter = new MyAdapter(NotificationActivity.this,list);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(NotificationActivity.this,"Opps.....Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });



    }





}





