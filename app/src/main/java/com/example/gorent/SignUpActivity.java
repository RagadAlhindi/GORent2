package com.example.gorent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity {

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

        backbutton=(Button)findViewById(R.id.signupBack);
        backbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(getIntent().getStringExtra("PreviousPage")!=null || getIntent().getStringExtra("PreviousPage")=="Sign-in")
                    startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
                else
                    startActivity(new Intent(SignUpActivity.this, MainActivity.class));
            }
        });

        signupButton=(Button)findViewById(R.id.signupbutton);
        signupButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
            }
        });

        signuptext8=(TextView)findViewById(R.id.signuptext8);
        signuptext8.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SignUpActivity.this, LogInActivity.class);
                intent.putExtra("PreviousPage", "Sign-up");
                startActivity(intent);
            }
        });

        twittericon=(Button)findViewById(R.id.signupTwitterButton);
        twittericon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SignUpActivity.this, BrowsingActivity.class);
                intent.putExtra("URL", "https://twitter.com/GORENT23");
                startActivity(intent);
            }
        });

        facebookicon=(Button)findViewById(R.id.signupFacebookButton );
        facebookicon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SignUpActivity.this, BrowsingActivity.class);
                intent.putExtra("URL", "https://www.facebook.com/profile.php?id=100092632443228");
                startActivity(intent);
            }
        });

        youtubeicon=(Button)findViewById(R.id.signupYoutubeButton);
        youtubeicon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SignUpActivity.this, BrowsingActivity.class);
                intent.putExtra("URL", "https://www.youtube.com/channel/UCL8e8DR11S3ylQUgzLeSF1g");
                startActivity(intent);
            }
        });
    }
}