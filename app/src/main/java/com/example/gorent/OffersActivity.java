package com.example.gorent;

import static com.example.gorent.DBHelperr.COLUMN_ID;
import static com.example.gorent.DBHelperr.COLUMN_USER_EMAIL;
import static com.example.gorent.DBHelperr.COLUMN_VEHICLE_MODEL;
import static com.example.gorent.DBHelperr.COLUMN_VEHICLE_PHOTO;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class OffersActivity extends AppCompatActivity {

    ImageView homeicon;
    ImageView addicon;
    ImageView basketicon;
    ImageView logouticon;

    Button btndelete;

    DBHelperr db;

    ArrayList model,type,id;

    RecyclerView recycler;

    OfeersAdapter adapter;

    ArrayList<byte[]>photo;









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);

        Intent intent = getIntent();
        String userEmail = intent.getStringExtra("userEmail");





        homeicon= (ImageView) findViewById(R.id.homeicon);
        homeicon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(OffersActivity.this, HomeAvtivity.class);
                i1.putExtra("userEmail",userEmail);
                startActivity(i1);
            }
        });

        addicon= (ImageView) findViewById(R.id.addicon);
        addicon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i2 = new Intent(OffersActivity.this, AddActivity.class);
                i2.putExtra("userEmail",userEmail);
                startActivity(i2);
            }
        });

        basketicon= (ImageView) findViewById(R.id.basketicon);
        basketicon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i3 = new Intent(OffersActivity.this, RentedActivity.class);
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

        db = new DBHelperr(this);
        model = new ArrayList<>();
        type = new ArrayList<>();
        id=new ArrayList();
        recycler = findViewById(R.id.recycler);
        photo= new ArrayList<byte[]>();

        adapter = new OfeersAdapter(this, model,type, id,photo);
        recycler.setAdapter(adapter);
        GridLayoutManager gridLayoutManager= new GridLayoutManager(this,1,LinearLayoutManager.VERTICAL,false);
        recycler.setLayoutManager(gridLayoutManager);
        displaydata();







        try{

            adapter.SetBtn(new OfeersAdapter.OnBtnClicked() {
                @Override
                public void onBtnClickedListener(int pos) {

                    AlertDialog.Builder builder2 = new AlertDialog.Builder(OffersActivity.this);
                    builder2.setTitle("Delete");
                    builder2.setMessage("Are you sure you want to Delete this vehicle?");
                    builder2.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            int id1= (int) id.remove(pos);
                            model.remove(pos);
                            type.remove(pos);
                            db.DeleteOne(id1);
                            adapter.notifyDataSetChanged();


                        }
                    });
                    builder2.setNegativeButton("No",null);

                   builder2.show();




                }
            });

        }catch(Exception e ){
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }








    }
    private void displaydata() {

        Intent intent = getIntent();
        String userEmail = intent.getStringExtra("userEmail");

        Cursor cursor = db.getMYdata();

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No vehicle for rent", Toast.LENGTH_SHORT).show();
            return;
        } else {
            while (cursor.moveToNext()) {
                try{

                    String em = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_EMAIL));
                    if (em.equals(userEmail)) {
                        id.add(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                        model.add(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VEHICLE_MODEL)).concat(" "));
                        type.add(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VEHICLE_TYPE)));
                        photo.add(cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_VEHICLE_PHOTO)));
                    }



                }catch(Exception e )
                {
                   // Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }                }

        }
    }
}