package com.example.gorent;

import static com.example.gorent.DBHelperr.COLUMN_ID;
import static com.example.gorent.DBHelperr.COLUMN_VEHICLE_MODEL;
import static com.example.gorent.DBHelperr.COLUMN_VEHICLE_PHOTO;
import static com.example.gorent.DBHelperr.COLUMN_VEHICLE_RENT;
import static com.example.gorent.DBHelperr.COLUMN_VEHICLE_TYPE;

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
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class BoatsActivity extends AppCompatActivity implements RecyclerViewInterface{

    ImageView homeicon;
    ImageView offersicon;
    ImageView addicon;
    ImageView basketicon;
    ImageView logouticon;

    RecyclerView recyclerView;
    ArrayList<String> model,type,rent;

    ArrayList id;
    ArrayList<byte[]>photo;
    DBHelperr DB;
    String userEmail;
    int Vid;
    String Vtype;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boats);

        Intent intent = getIntent();
         userEmail = intent.getStringExtra("userEmail");

        homeicon= (ImageView) findViewById(R.id.homeicon);
        homeicon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(BoatsActivity.this, HomeAvtivity.class);
                i1.putExtra("userEmail",userEmail);
                startActivity(i1);
            }
        });

        offersicon= (ImageView) findViewById(R.id.listicon);
        offersicon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i2 = new Intent(BoatsActivity.this, OffersActivity.class);
                i2.putExtra("userEmail",userEmail);
                startActivity(i2);
            }
        });

        addicon= (ImageView) findViewById(R.id.addicon);
        addicon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i3 = new Intent(BoatsActivity.this, AddActivity.class);
                i3.putExtra("userEmail",userEmail);
                startActivity(i3);;
            }
        });

        basketicon= (ImageView) findViewById(R.id.basketicon);
        basketicon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i4 = new Intent(BoatsActivity.this, RentedActivity.class);
                i4.putExtra("userEmail",userEmail);
                startActivity(i4);
            }
        });



        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Log out");
        builder.setMessage("Are you sure you want to log out?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent =  new Intent(getApplicationContext(), LogInActivity.class);
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


        DB = new DBHelperr(this);
        model=new ArrayList<>();
        type=new ArrayList<>();
        rent=new ArrayList<>();
        id=new ArrayList();
        recyclerView=findViewById(R.id.recyclerview);
        photo= new ArrayList<byte[]>();
        adapter = new MyAdapter(this, model, rent,id ,type,photo, this);
        recyclerView.setAdapter(adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        displaydata();




    }

    private void displaydata() {
        Cursor cursor = DB.getdata();
        if(cursor.getCount()==0){
            Toast.makeText(this, "No Boats for rent", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            while(cursor.moveToNext()){
                if (cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VEHICLE_TYPE)).equals("Boat")) {
                    type.add(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VEHICLE_TYPE)));
                    id.add(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                    model.add(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VEHICLE_MODEL)).concat(" "));
                    int price =cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_VEHICLE_RENT));
                    String priceAdd=Integer.toString(price).concat(" SR");
                    rent.add(priceAdd);
                    photo.add(cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_VEHICLE_PHOTO)));


                }
            }
        }


    }

    @Override
    public void onVehicleClicked(int position) {

        Vid= (int) id.get(position);
        Vtype = type.get(position);

        Intent intentView = new Intent(BoatsActivity.this, rentActivity.class);
        intentView.putExtra("userEmail", userEmail);
        intentView.putExtra("VehicleID",Vid);
        intentView.putExtra("type",Vtype);

        startActivity(intentView);

    }
}