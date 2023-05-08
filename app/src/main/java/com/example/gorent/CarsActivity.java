package com.example.gorent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class CarsActivity extends AppCompatActivity {

    ImageView homeicon;
    ImageView offersicon;
    ImageView addicon;
    ImageView basketicon;
    ImageView logouticon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cars);


        homeicon= (ImageView) findViewById(R.id.homeicon);
        homeicon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CarsActivity.this, HomeAvtivity.class));
            }
        });

        offersicon= (ImageView) findViewById(R.id.listicon);
        offersicon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CarsActivity.this, OffersActivity.class));
            }
        });

        addicon= (ImageView) findViewById(R.id.addicon);
        addicon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CarsActivity.this, AddActivity.class));
            }
        });

        basketicon= (ImageView) findViewById(R.id.basketicon);
        basketicon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CarsActivity.this, RentedActivity.class));
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