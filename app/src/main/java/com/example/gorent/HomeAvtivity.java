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
import android.widget.Toast;

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

        Intent intent = getIntent();
        String userEmail = intent.getStringExtra("userEmail");




        carsbutton=(ImageButton)findViewById(R.id.carsbutton);
        carsbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i6 = new Intent(HomeAvtivity.this, CarsActivity.class);
                i6.putExtra("userEmail",userEmail);
                startActivity(i6);

            }
        });

        boatsbutton=(ImageButton)findViewById(R.id.boatsbutton);
        boatsbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i5 = new Intent(HomeAvtivity.this, BoatsActivity.class);
                i5.putExtra("userEmail",userEmail);
                startActivity(i5);
            }
        });

        motorcyclesbutton=(ImageButton)findViewById(R.id.motorcyclesbutton);
        motorcyclesbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i4 = new Intent(HomeAvtivity.this, MotorcyclesActivity.class);
                i4.putExtra("userEmail",userEmail);
                startActivity(i4);
            }
        });

        offersicon= (ImageView) findViewById(R.id.listicon);
        offersicon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(HomeAvtivity.this, OffersActivity.class);
                i1.putExtra("userEmail",userEmail);
                startActivity(i1);


            }
        });

        addicon= (ImageView) findViewById(R.id.addicon);
        addicon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i2 = new Intent(HomeAvtivity.this, AddActivity.class);
                i2.putExtra("userEmail",userEmail);
                startActivity(i2);
            }
        });

        basketicon= (ImageView) findViewById(R.id.basketicon);
        basketicon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // ما انسى ارجع رينتد مو رينت!!!!!!1
                Intent i3 = new Intent(HomeAvtivity.this, RentedActivity.class);
                i3.putExtra("userEmail",userEmail);
                startActivity(i3);
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