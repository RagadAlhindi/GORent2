package com.example.gorent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
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
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity {

    ImageView homeicon;
    ImageView offersicon;
    ImageView basketicon;
    ImageView logouticon;

    Spinner sp ;
    String Colector = "";

    EditText plate, model, Year, UserComment, Amount;
    Button SubmitSave;
    RadioButton car, boat, motro;
    Button BSelectImage;

    // One Preview Image
    ImageView IVPreviewImage;

    // constant to compare
    // the activity result code
    int SELECT_PICTURE = 10;
    String selectedType ="";
    String selectedCity ="";

    String URL ="";

    byte[] imgTOStore;


    DBHelperr dataBaseHelper;
    DBHelperr dataBaseHelperr;
    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Intent intent = getIntent();
        String userEmail = intent.getStringExtra("userEmail");


        homeicon = (ImageView) findViewById(R.id.homeicon);
        homeicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddActivity.this, HomeAvtivity.class));
            }
        });


        offersicon = (ImageView) findViewById(R.id.listicon);
        offersicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddActivity.this, OffersActivity.class));
            }
        });


        basketicon = (ImageView) findViewById(R.id.basketicon);
        basketicon.setOnClickListener(new View.OnClickListener() {
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
        builder.setNegativeButton("No", null);

        logouticon = (ImageView) findViewById(R.id.logouticon);
        logouticon.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                builder.show();

            }
        });



        sp = findViewById(R.id.SpCountry);
        plate = findViewById(R.id.plate);
        model = findViewById(R.id.model);
        Year = findViewById(R.id.year);
        Amount = findViewById(R.id.Amount);
        UserComment = findViewById(R.id.userDescription);
        car = findViewById(R.id.car);
        boat = findViewById(R.id.boat);
        motro = findViewById(R.id.motorcycle);
        SubmitSave = findViewById(R.id.btnSubmit);

        dataBaseHelperr = new DBHelperr(AddActivity.this);
        BSelectImage = findViewById(R.id.BSelectImage);
        IVPreviewImage = findViewById(R.id.IVPreviewImage);

        SubmitSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (((RadioButton) car).isChecked()) {
                    selectedType = "car";
                } else if (((RadioButton) boat).isChecked()) {
                    selectedType = "boat";
                } else if (((RadioButton) motro).isChecked()) {
                    selectedType = "motrocycle";
                }
                String Plate = plate.getText().toString();
                String Model = model.getText().toString();
                String year = Year.getText().toString();
                String comment = UserComment.getText().toString();
                String amount = Amount.getText().toString();

                try {

                    BitmapDrawable drawable = (BitmapDrawable) IVPreviewImage.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();
                    imgTOStore = getBytes(bitmap);


                } catch (Exception e) {}


                if (Plate.isEmpty()) {
                    Toast.makeText(AddActivity.this, "Please fill the plate number field", Toast.LENGTH_SHORT).show();
                } else if (Model.isEmpty()) {
                    Toast.makeText(AddActivity.this, "Please fill the model field", Toast.LENGTH_SHORT).show();
                } else if (year.isEmpty()) {
                    Toast.makeText(AddActivity.this, "Please fill the year of manufacture field", Toast.LENGTH_SHORT).show();
                } else if (comment.isEmpty()) {
                    Toast.makeText(AddActivity.this, "Please fill the description field", Toast.LENGTH_SHORT).show();
                } else if (amount.isEmpty()) {
                    Toast.makeText(AddActivity.this, "Please fill the rent amount field", Toast.LENGTH_SHORT).show();
                } else if (selectedCity.equals("")) {
                    Toast.makeText(AddActivity.this, "Please choose the location in the city field", Toast.LENGTH_SHORT).show();
                } else if (selectedType.equals("")) {
                    Toast.makeText(AddActivity.this, "Please choose the vehicle type", Toast.LENGTH_SHORT).show();
                }else if (IVPreviewImage.getDrawable()==null){
                    Toast.makeText(AddActivity.this, "Please select photo", Toast.LENGTH_SHORT).show();
                    }
                 else {

                    Colector += "Plate: " + Plate + "\n";
                    Colector += "Model: " + Model + "\n";
                    Colector += "Year of manufacture:  " + year + "\n";
                    Colector += "Description: " + comment + "\n";


                    // create model
                    VehicleModel vehicleMod;
                    try {
                        vehicleMod = new VehicleModel(-1, Plate, Model, Integer.parseInt(year), selectedType, selectedCity, comment, Integer.parseInt(amount), imgTOStore);
                        DBHelperr dataBaseHelper = new DBHelperr(AddActivity.this);
                        boolean b = dataBaseHelperr.addOne(vehicleMod, userEmail);

                        if (b == true) {

                            AlertDialog.Builder b1 = new AlertDialog.Builder(AddActivity.this);
                            b1.setTitle("Added successfully");
                            b1.setMessage("Your " + selectedType + " is ready for rent!");
                            b1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
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


        List<String> categoryCountry = new ArrayList<>();
        categoryCountry.add("Select City");
        categoryCountry.add("Abha");
        categoryCountry.add("AlBaha");
        categoryCountry.add("Dammam");
        categoryCountry.add("Hail");
        categoryCountry.add("Jazan");
        categoryCountry.add("Jeddah");
        categoryCountry.add("Madina");
        categoryCountry.add("Mekkah");
        categoryCountry.add("Riyadh");
        categoryCountry.add("Taif");


        ArrayAdapter<String> arrayAdapter;
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryCountry);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(arrayAdapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                if (parent.getItemAtPosition(position).equals("Select City")) {
                    //Do Nothing

                } else {
                    String item = parent.getItemAtPosition(position).toString();
                    Colector += "City: " + item + "\n";
                    selectedCity = item;
                    Toast.makeText(AddActivity.this, "Selected city: " + item, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        BSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);

                // pass the constant to compare it
                // with the returned requestCode
                startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && data != null){
            Uri uri=data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap decodeStream=BitmapFactory.decodeStream(inputStream);
                IVPreviewImage.setImageBitmap(decodeStream);
            } catch (Exception e) {
                Log.e("ex",e.getMessage());
            }
        }
    }




    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }
}