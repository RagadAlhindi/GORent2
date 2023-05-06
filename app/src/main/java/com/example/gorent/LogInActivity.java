package com.example.gorent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LogInActivity extends AppCompatActivity {
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

        loginButton=(Button)findViewById(R.id.loginbutton);
        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LogInActivity.this, HomeAvtivity.class));
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