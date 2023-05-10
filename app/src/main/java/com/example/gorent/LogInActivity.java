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
import com.example.gorent.MainActivity;
import com.example.gorent.SignUpActivity;

public class LogInActivity extends AppCompatActivity {
    EditText Email, Password;


    DBHelperr DB;
    Button loginButton;
    Button backbutton;
    TextView logintext6;
    Button twittericon;
    Button facebookicon;
    Button youtubeicon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        Email = findViewById(R.id.loginEnteredEmail);
        Password = findViewById(R.id.loginEnteredPassword);

        loginButton = findViewById(R.id.loginbutton);
        DB = new DBHelperr(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = Email.getText().toString();
                String pass = Password.getText().toString();


                if(email.equals("")||pass.equals(""))
                    Toast.makeText(LogInActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else{
                    Boolean check = DB.checkEmailPassword(email, pass);
                    if (check){
                        Intent i = new Intent(LogInActivity.this, AddActivity.class);
                        i.putExtra("userEmail", email);
                        Toast.makeText(LogInActivity.this, "Sign in successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), HomeAvtivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LogInActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });

        backbutton=(Button)findViewById(R.id.loginback);
        backbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(getIntent().getStringExtra("PreviousPage")!=null || getIntent().getStringExtra("PreviousPage")=="Sign-up")
                    startActivity(new Intent(LogInActivity.this, SignUpActivity.class));
                else
                    startActivity(new Intent(LogInActivity.this, MainActivity.class));
            }
        });



        logintext6=(TextView)findViewById(R.id.logintext6);
        logintext6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LogInActivity.this, SignUpActivity.class);
                intent.putExtra("PreviousPage", "Sign-in");
                startActivity(intent);
            }
        });

        twittericon=(Button)findViewById(R.id.loginTwitterButton);
        twittericon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LogInActivity.this, BrowsingActivity.class);
                intent.putExtra("URL", "https://twitter.com/GORENT23");
                startActivity(intent);
            }
        });

        facebookicon=(Button)findViewById(R.id.loginFacebookButton);
        facebookicon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LogInActivity.this, BrowsingActivity.class);
                intent.putExtra("URL", "https://www.facebook.com/profile.php?id=100092632443228");
                startActivity(intent);
            }
        });

        youtubeicon=(Button)findViewById(R.id.loginYoutubeButton);
        youtubeicon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LogInActivity.this, BrowsingActivity.class);
                intent.putExtra("URL", "https://www.youtube.com/channel/UCL8e8DR11S3ylQUgzLeSF1g");
                startActivity(intent);
            }
        });
    }
}