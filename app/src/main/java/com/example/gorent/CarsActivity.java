package com.example.gorent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CarsActivity extends AppCompatActivity {

    ImageView homeicon;
    ImageView offersicon;
    ImageView addicon;
    ImageView basketicon;
    ImageView logouticon;

    RecyclerView recyclerView;

    ArrayList<String> model, type, rent;

    DBHelperr DB;

    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cars);


        homeicon = (ImageView) findViewById(R.id.homeicon);
        homeicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CarsActivity.this, HomeAvtivity.class));
            }
        });

        offersicon = (ImageView) findViewById(R.id.listicon);
        offersicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CarsActivity.this, OffersActivity.class));
            }
        });

        addicon = (ImageView) findViewById(R.id.addicon);
        addicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CarsActivity.this, AddActivity.class));
            }
        });

        basketicon = (ImageView) findViewById(R.id.basketicon);
        basketicon.setOnClickListener(new View.OnClickListener() {
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
        builder.setNegativeButton("No", null);


        logouticon = (ImageView) findViewById(R.id.logouticon);
        logouticon.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View view) {
                builder.show();

            }
        });

        DB = new DBHelperr(this);
        model = new ArrayList<>();
        type = new ArrayList<>();
        rent = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview);
        adapter = new MyAdapter(this, model, type, rent);
        recyclerView.setAdapter(adapter);
        GridLayoutManager gridLayoutManager= new GridLayoutManager(this,2,LinearLayoutManager.VERTICAL,false);

        recyclerView.setLayoutManager(gridLayoutManager);
        displaydata();


    }


    private void displaydata() {
        Cursor cursor = DB.getdata();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No cars for rent", Toast.LENGTH_SHORT).show();
            return;
        } else {
            while (cursor.moveToNext()) {
                if (cursor.getString(4).equals("car")) {
                    model.add(cursor.getString(2).concat(" "));
                    type.add(cursor.getString(3));
                    rent.add(cursor.getString(7).concat("SR"));

                }

            }
        }
    }
}