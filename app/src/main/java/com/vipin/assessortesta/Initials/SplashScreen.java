package com.vipin.assessortesta.Initials;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vipin.assessortesta.Ass_Registration.AssRegActivity;
import com.vipin.assessortesta.R;


public class SplashScreen extends AppCompatActivity {
    Button login;
    TextView tvv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        login = findViewById(R.id.loginbutton);
        tvv=findViewById(R.id.newuserr);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SplashScreen.this, SignIn.class);
                startActivity(i);
            }
        });

        tvv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii=new Intent(SplashScreen.this, AssRegActivity.class);
                startActivity(ii);
            }
        });

    }
}
