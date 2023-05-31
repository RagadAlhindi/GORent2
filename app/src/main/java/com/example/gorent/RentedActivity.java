package com.example.gorent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class RentedActivity extends AppCompatActivity {

    ImageView homeicon;
    ImageView offersicon;
    ImageView addicon;
    ImageView logouticon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rented);

        Intent intent = getIntent();
        String userEmail = intent.getStringExtra("userEmail");

        homeicon= (ImageView) findViewById(R.id.homeicon);
        homeicon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(RentedActivity.this, HomeAvtivity.class);
                i1.putExtra("userEmail",userEmail);
                startActivity(i1);
            }
        });

        offersicon= (ImageView) findViewById(R.id.listicon);
        offersicon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i2 = new Intent(RentedActivity.this, OffersActivity.class);
                i2.putExtra("userEmail",userEmail);
                startActivity(i2);            }
        });

        addicon= (ImageView) findViewById(R.id.addicon);
        addicon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i3= new Intent(RentedActivity.this, AddActivity.class);
                i3.putExtra("userEmail",userEmail);
                startActivity(i3);             }
        });



        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Log out");
        builder.setMessage("Are you sure you want to log out?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("No",null);



        logouticon= (ImageView) findViewById(R.id.logouticon);
        logouticon.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View view) {
                builder.show();

            }
        });

    }
}