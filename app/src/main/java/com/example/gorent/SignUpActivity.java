package com.example.gorent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gorent.BrowsingActivity;
import com.example.gorent.HomeAvtivity;
import com.example.gorent.LogInActivity;
import com.example.gorent.MainActivity;

public class SignUpActivity extends AppCompatActivity {

    EditText signupEnteredName, signupEnteredAge, signupEnteredEmail, signupEnteredPassword;

    DBHelperr DB;

    Button signupButton;
    Button backbutton;
    TextView signuptext8;
    Button twittericon;
    Button facebookicon;
    Button youtubeicon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signupEnteredEmail = (EditText) findViewById(R.id.signupEnteredEmail);
        signupEnteredName = (EditText) findViewById(R.id.signupEnteredName);
        signupEnteredPassword = (EditText) findViewById(R.id.signupEnteredPassword);
        signupEnteredAge = (EditText) findViewById(R.id.signupEnteredAge);

        signupButton = (Button) findViewById(R.id.signupbutton);

        DB = new DBHelperr(this);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = signupEnteredEmail.getText().toString();
                String pass = signupEnteredPassword.getText().toString();
                String user = signupEnteredName.getText().toString();
                String age = signupEnteredAge.getText().toString();


                if (user.equals("") || pass.equals("") || email.equals("") || age.equals(""))
                    Toast.makeText(SignUpActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else {
                    Boolean check = DB.checkEmail(email);
                    if (!check) {
                        Boolean insert = DB.insertData(email, pass, user, age);
                        if (insert) {
                            Toast.makeText(SignUpActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), HomeAvtivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(SignUpActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SignUpActivity.this, "Already exists! please sign in", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        signuptext8 = (TextView) findViewById(R.id.signuptext8);

        signuptext8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                intent.putExtra("PreviousPage", "Sign-up");
                startActivity(intent);
            }
        });
        backbutton = (Button) findViewById(R.id.signupBack);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getIntent().getStringExtra("PreviousPage") != null || getIntent().getStringExtra("PreviousPage") == "Sign-in")
                    startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
                else
                    startActivity(new Intent(SignUpActivity.this, MainActivity.class));
            }

        });


        twittericon = (Button) findViewById(R.id.signupTwitterButton);
        twittericon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, BrowsingActivity.class);
                intent.putExtra("URL", "https://twitter.com/GORENT23");
                startActivity(intent);
            }
        });

        facebookicon = (Button) findViewById(R.id.signupFacebookButton);
        facebookicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, BrowsingActivity.class);
                intent.putExtra("URL", "https://www.facebook.com/profile.php?id=100092632443228");
                startActivity(intent);
            }
        });

        youtubeicon = (Button) findViewById(R.id.signupYoutubeButton);
        youtubeicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, BrowsingActivity.class);
                intent.putExtra("URL", "https://www.youtube.com/channel/UCL8e8DR11S3ylQUgzLeSF1g");
                startActivity(intent);
            }
        });
    }
}
