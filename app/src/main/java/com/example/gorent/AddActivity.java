package com.example.gorent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity {

    ImageView homeicon;
    ImageView offersicon;
    ImageView basketicon;
    ImageView logouticon;

    Spinner sp ;
    String Colector = "";
    EditText plate, model,location ,UserComment, Amount;
    Button SubmitSave;
    Button BSelectImage;

    // One Preview Image
    ImageView IVPreviewImage;

    // constant to compare
    // the activity result code
    int SELECT_PICTURE = 10;
    String selectedType ="";
    String selectedCity ="";

    String URL ="";


    DBHelperr dataBaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Intent intent = getIntent();
        String userEmail = intent.getStringExtra("userEmail");





        homeicon= (ImageView) findViewById(R.id.homeicon);
        homeicon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddActivity.this, HomeAvtivity.class));
            }
        });

        offersicon= (ImageView) findViewById(R.id.listicon);
        offersicon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddActivity.this, OffersActivity.class));
            }
        });

        basketicon= (ImageView) findViewById(R.id.basketicon);
        basketicon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddActivity.this, RentedActivity.class));
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


        sp = findViewById(R.id.pickuploc);
        plate = findViewById(R.id.plate);
        model = findViewById(R.id.model);
        Amount = findViewById(R.id.Amount);
        location=findViewById(R.id.pickupinput);
        UserComment = findViewById(R.id.userDescription);
        SubmitSave = findViewById(R.id.btnSubmit);

        dataBaseHelper = new DBHelperr(AddActivity.this);

        SubmitSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Plate = plate.getText().toString();
                String Model = model.getText().toString();
                String comment = UserComment.getText().toString();
                String amount = Amount.getText().toString();
                String Location = location.getText().toString();


                if (Plate.isEmpty()) {
                    Toast.makeText(AddActivity.this, "Please fill the plate number field", Toast.LENGTH_SHORT).show();
                } else if (Model.isEmpty()) {
                    Toast.makeText(AddActivity.this, "Please fill the model field", Toast.LENGTH_SHORT).show();
                } else if (comment.isEmpty()) {
                    Toast.makeText(AddActivity.this, "Please fill the description field", Toast.LENGTH_SHORT).show();
                }
                else if (amount.isEmpty()) {
                    Toast.makeText(AddActivity.this, "Please fill the rent amount field", Toast.LENGTH_SHORT).show();
                }
                else if (Location.isEmpty()) {
                    Toast.makeText(AddActivity.this, "Please fill the pick-up location field", Toast.LENGTH_SHORT).show();
                }
                else if (selectedType.equals("")) {
                    Toast.makeText(AddActivity.this, "Please choose the vehicle type", Toast.LENGTH_SHORT).show();
                }

                else {

                    Colector += "Plate: " + Plate + "\n";
                    Colector += "Model: " + Model + "\n";
                    Colector += "Description: " +comment + "\n";

                    // create model
                    VehicleModel vehicleMod;
                    try {
                        vehicleMod = new VehicleModel(-1, Plate, Model, selectedType, Location, comment, Integer.parseInt(amount) );
                        DBHelperr dataBaseHelper = new DBHelperr(AddActivity.this);
                        boolean b = dataBaseHelper.addOne(vehicleMod, userEmail);
                        if(b==true){

                            AlertDialog.Builder b1=  new AlertDialog.Builder(AddActivity.this);
                                     b1.setTitle("Added successfully");
                                     b1.setMessage("Your " + selectedType +" is ready for rent!");

                                     b1.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
                                         public void onClick(DialogInterface dialogInterface, int i) {

                                             Intent intent = new Intent(getApplicationContext(), HomeAvtivity.class);
                                             startActivity(intent);
                                         }
                                     });

                            b1.show();







                        }
                    } catch (Exception e) {
                        Toast.makeText(AddActivity.this, "Enter Valid input", Toast.LENGTH_SHORT).show();


                    }

                }
            }


        });

        List<String> categoryType = new ArrayList<>();
        categoryType.add("Select Type");
        categoryType.add("Car");
        categoryType.add("Boat");
        categoryType.add("Motorcycle");


        ArrayAdapter<String> arrayAdapter;
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryType);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(arrayAdapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                if (parent.getItemAtPosition(position).equals("Select City")) {
                    //Do Nothing

                } else {
                    String item = parent.getItemAtPosition(position).toString();
                    Colector +="selectedType: "+ item + "\n" ;
                    selectedType = item;
                    Toast.makeText(AddActivity.this, "Selected city: " + item, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        // register the UI widgets with their appropriate IDs
        BSelectImage = findViewById(R.id.BSelectImage);
        IVPreviewImage = findViewById(R.id.IVPreviewImage);

        // handle the Choose Image button to trigger
        // the image chooser function
        BSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

    }

    void imageChooser() {

        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }
    // this function is triggered when user
    // selects the image from the imageChooser
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                URL = String.valueOf(selectedImageUri);
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    IVPreviewImage.setImageURI(selectedImageUri);
                }
            }
        }
    }
}