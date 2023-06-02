package com.example.gorent;

import static com.example.gorent.DBHelperr.COLUMN_ID;
import static com.example.gorent.DBHelperr.COLUMN_VEHICLE_DESCRIPTION;
import static com.example.gorent.DBHelperr.COLUMN_VEHICLE_LOCATION;
import static com.example.gorent.DBHelperr.COLUMN_VEHICLE_MODEL;
import static com.example.gorent.DBHelperr.COLUMN_VEHICLE_PHOTO;
import static com.example.gorent.DBHelperr.COLUMN_VEHICLE_PLATE;
import static com.example.gorent.DBHelperr.COLUMN_VEHICLE_RENT;
import static com.example.gorent.DBHelperr.COLUMN_VEHICLE_TYPE;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class rentActivity extends AppCompatActivity {
    EditText date, date2;
    Spinner sp ;

    ImageView offersicon;
    ImageView homeicon;
    ImageView basketicon;
    ImageView logouticon;
    ImageView addicon;
    Button submit;
    Button vehicleback;

    ImageView Vehicleimg;

    TextView VehicleModel , VehiclePlate , VehicleRent , VehilceLocation , VehicleDesc;


    int VID;
    String userEmail;
    DatePickerDialog datePickerDialog;

    String Vtype;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);

        Intent intent2 = getIntent();
        userEmail = intent2.getStringExtra("userEmail");
        Vtype = intent2.getStringExtra("type");

        Intent intent3 = getIntent();
        // من صفحة الفيو لازم ينرسل لي الاي دي تبع المركبة عشان اقدر استأجرها
        VID = intent3.getIntExtra("VehicleID",0);

        Vehicleimg = (ImageView) findViewById(R.id.Vehicleimg);
        VehicleDesc = (TextView) findViewById(R.id.VehicleDesc);
        VehicleModel = (TextView) findViewById(R.id.VehicleModel);
        VehiclePlate = (TextView) findViewById(R.id.Vehicleplate);
        VehilceLocation = (TextView) findViewById(R.id.VehicleLocation);
        VehicleRent = (TextView) findViewById(R.id.VehicleRent);
        vehicleback = (Button)findViewById(R.id.vehicleback) ;

        vehicleback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Vtype.equals("Car")){
                    Intent rentType = new Intent(rentActivity.this, CarsActivity.class);
                    rentType.putExtra("userEmail",userEmail);
                    rentType.putExtra("VehicleID",VID);
                    rentType.putExtra("type",Vtype);
                    startActivity(rentType);


                }
                else if(Vtype.equals("Boat")){
                    Intent rentType = new Intent(rentActivity.this, BoatsActivity.class);
                    rentType.putExtra("userEmail",userEmail);
                    rentType.putExtra("VehicleID",VID);
                    rentType.putExtra("type",Vtype);
                    startActivity(rentType);

                } else if (Vtype.equals("Motorcycle")) {
                    Intent rentType = new Intent(rentActivity.this, MotorcyclesActivity.class);
                    rentType.putExtra("userEmail",userEmail);
                    rentType.putExtra("VehicleID",VID);
                    rentType.putExtra("type",Vtype);
                    startActivity(rentType);

                }

            }
        });

        submit = findViewById(R.id.btnSubmit);





        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                DBHelperr dataBaseHelper = new DBHelperr(rentActivity.this);
                boolean rentSuccess = dataBaseHelper.rentV(VID, userEmail);
                if (rentSuccess) {

                    Toast.makeText(rentActivity.this, "Rented successfully", Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(rentActivity.this, "Rented failed", Toast.LENGTH_SHORT).show();

                }



            }
        });

        sp = findViewById(R.id.method);

        List<String> categoryType = new ArrayList<>();
        categoryType.add("Select Type");
        categoryType.add("Credit card");
        categoryType.add("Apple pay");
        categoryType.add("Visa");


        ArrayAdapter<String> arrayAdapter;
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryType);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(arrayAdapter);



        // initiate the date picker and a button
        date = (EditText) findViewById(R.id.period1);
        date2 = (EditText) findViewById(R.id.period2);

        // perform click event on edit text
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(rentActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                date.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        date2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(rentActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                date2.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        /*vehicleback = (Button) findViewById(R.id.vehicleback);
        vehicleback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getIntent().getStringExtra("PreviousPage") != null || getIntent().getStringExtra("PreviousPage") == "Sign-in")
                    startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
                else
                    startActivity(new Intent(SignUpActivity.this, MainActivity.class));
            }

        });*/

        homeicon = (ImageView) findViewById(R.id.homeicon);
        homeicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(rentActivity.this, HomeAvtivity.class);
                i1.putExtra("userEmail", userEmail);
                startActivity(i1);
            }
        });

        offersicon= (ImageView) findViewById(R.id.listicon);
        offersicon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(rentActivity.this, OffersActivity.class);
                i1.putExtra("userEmail",userEmail);
                startActivity(i1);


            }
        });

        addicon= (ImageView) findViewById(R.id.addicon);
        addicon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i3 = new Intent(rentActivity.this, AddActivity.class);
                i3.putExtra("userEmail",userEmail);
                startActivity(i3);;
            }
        });

        basketicon= (ImageView) findViewById(R.id.basketicon);
        basketicon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent i3 = new Intent(rentActivity.this, RentedActivity.class);
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
        DisplayVehicleInfo();


    }

    public void DisplayVehicleInfo(){

        DBHelperr dbHelperr = new DBHelperr(this);
        Cursor cursor = dbHelperr.getInfo(VID);

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
            return;


        } else {
            while (cursor.moveToNext()) {


                VehicleModel.setText(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VEHICLE_MODEL)));
                VehicleDesc.setText(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VEHICLE_DESCRIPTION)));
                VehiclePlate.setText(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VEHICLE_PLATE)));
                VehilceLocation.setText(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VEHICLE_LOCATION)));
                int price = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_VEHICLE_RENT));
                String priceAdd = Integer.toString(price).concat(" SR");
                VehicleRent.setText(priceAdd);

                byte[] photo = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_VEHICLE_PHOTO));
                Bitmap bitmap = BitmapFactory.decodeByteArray(photo, 0, photo.length);
                Vehicleimg.setImageBitmap(bitmap);

            } }



    }



}