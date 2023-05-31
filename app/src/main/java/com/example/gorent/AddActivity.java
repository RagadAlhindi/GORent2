package com.example.gorent;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.Manifest;
public class AddActivity extends AppCompatActivity {

    ImageView homeicon;
    ImageView offersicon;
    ImageView basketicon;
    ImageView logouticon;

    Spinner sp ;
    String Colector = "";
    EditText plate, model,location ,UserComment, Amount;
    ImageView SubmitSave;

    Button BSelectImage;



    // constant to compare
    // the activity result code

    String selectedType ="";


    String URL ="";
    Bitmap photoBitmap=null;
    String userEmail;

    private ActivityResultLauncher<String> requestPermissionLauncher;
    private ActivityResultLauncher<Intent> selectImageLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {




            // Request permission to read external storage
            requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(),
                    isGranted -> {
                        if (isGranted) {
                            // Launch the image selection activity
                            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            selectImageLauncher.launch(i);
                        } else {
                            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                        }
                    });

            // Launch the image selection activity and retrieve the selected image
            selectImageLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            Uri selectedImage = result.getData().getData();
                            try {
                                photoBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });


            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add);
            try {
                Intent intent2 = getIntent();
                userEmail = intent2.getStringExtra("userEmail");

            } catch (Exception e) {
                Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }


            homeicon = (ImageView) findViewById(R.id.homeicon);
            homeicon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i1 = new Intent(AddActivity.this, HomeAvtivity.class);
                    i1.putExtra("userEmail", userEmail);
                    startActivity(i1);
                }
            });

            offersicon = (ImageView) findViewById(R.id.listicon);
            offersicon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i2 = new Intent(AddActivity.this, OffersActivity.class);
                    i2.putExtra("userEmail", userEmail);
                    startActivity(i2);
                }
            });


            basketicon = (ImageView) findViewById(R.id.basketicon);
            basketicon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i3 = new Intent(AddActivity.this, RentedActivity.class);
                    i3.putExtra("userEmail", userEmail);
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
            builder.setNegativeButton("No", null);


            logouticon = (ImageView) findViewById(R.id.logouticon);
            logouticon.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View view) {
                    builder.show();

                }
            });


            sp = findViewById(R.id.pickuploc);
            plate = findViewById(R.id.plate);
            model = findViewById(R.id.model);
            Amount = findViewById(R.id.Amount);
            location = findViewById(R.id.pickupinput);
            UserComment = findViewById(R.id.userDescription);
            SubmitSave = findViewById(R.id.btnSubmit);


            // dataBaseHelper = new DBHelperr(AddActivity.this);

            SubmitSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try{

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
                    } else if (amount.isEmpty()) {
                        Toast.makeText(AddActivity.this, "Please fill the rent amount field", Toast.LENGTH_SHORT).show();
                    } else if (Location.isEmpty()) {
                        Toast.makeText(AddActivity.this, "Please fill the pick-up location field", Toast.LENGTH_SHORT).show();
                    } else if (selectedType.equals("")) {
                        Toast.makeText(AddActivity.this, "Please choose the vehicle type", Toast.LENGTH_SHORT).show();
                    }else if (photoBitmap==null){
                          Toast.makeText(AddActivity.this, "Please choose  photo", Toast.LENGTH_SHORT).show();

                        }

                    else {

                        Colector += "Plate: " + Plate + "\n";
                        Colector += "Model: " + Model + "\n";
                        Colector += "Description: " + comment + "\n";

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        photoBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] photoData = stream.toByteArray();
                        // create model
                        VehicleModel vehicleMod;

                            vehicleMod = new VehicleModel(-1, Plate, Model, selectedType, Location, comment, Integer.parseInt(amount), photoData);
                            DBHelperr dataBaseHelper = new DBHelperr(AddActivity.this);
                            boolean b = dataBaseHelper.addOne(vehicleMod, userEmail);
                            if (b == true) {

                                AlertDialog.Builder b1 = new AlertDialog.Builder(AddActivity.this);
                                b1.setTitle("Added successfully");
                                b1.setMessage("Your " + selectedType + " is ready for rent!");

                                b1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogInterface, int i) {


                                        Intent i6 = new Intent(getApplicationContext(), HomeAvtivity.class);
                                        i6.putExtra("userEmail", userEmail);
                                        startActivity(i6);
                                    }
                                });
                                b1.show();


                            }
                        }} catch (Exception e) {
                            Toast.makeText(AddActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();


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
                    if (parent.getItemAtPosition(position).equals("Select Type")) {
                        //Do Nothing

                    } else {
                        String item = parent.getItemAtPosition(position).toString();
                        Colector += "selected Type: " + item + "\n";
                        selectedType = item;
                        //   Toast.makeText(AddActivity.this, "Selected Type: " + item, Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });



            try{
                BSelectImage=findViewById(R.id.BSelectImage);

            BSelectImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Check if the app has permission to read external storage
                    if (ContextCompat.checkSelfPermission(AddActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        // Launch the image selection activity
                        Intent intent3 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        selectImageLauncher.launch(intent3);
                    } else {
                        // Request permission to read external storage
                        requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                    }
                }

            });
        }
        catch(Exception e ){
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && data != null){
            Uri uri=data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap decodeStream=BitmapFactory.decodeStream(inputStream);
                //IVPreviewImage.setImageBitmap(decodeStream);
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
