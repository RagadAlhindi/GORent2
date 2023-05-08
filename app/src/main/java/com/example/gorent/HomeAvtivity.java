package com.example.gorent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class HomeAvtivity extends AppCompatActivity {

    ImageButton carsbutton;
    ImageButton boatsbutton;
    ImageButton motorcyclesbutton;
    ImageView offersicon;
    ImageView addicon;
    ImageView basketicon;
    ImageView logouticon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        carsbutton=(ImageButton)findViewById(R.id.carsbutton);
        carsbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeAvtivity.this, CarsActivity.class));
            }
        });

        boatsbutton=(ImageButton)findViewById(R.id.boatsbutton);
        boatsbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeAvtivity.this, BoatsActivity.class));
            }
        });

        motorcyclesbutton=(ImageButton)findViewById(R.id.motorcyclesbutton);
        motorcyclesbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeAvtivity.this, MotorcyclesActivity.class));
            }
        });

        offersicon= (ImageView) findViewById(R.id.listicon);
        offersicon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeAvtivity.this, OffersActivity.class));
            }
        });

        addicon= (ImageView) findViewById(R.id.addicon);
        addicon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeAvtivity.this, AddActivity.class));
            }
        });

        basketicon= (ImageView) findViewById(R.id.basketicon);
        basketicon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeAvtivity.this, RentedActivity.class));
            }
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