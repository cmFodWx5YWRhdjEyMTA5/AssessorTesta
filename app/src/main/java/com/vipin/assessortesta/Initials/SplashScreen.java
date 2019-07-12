package com.vipin.assessortesta.Initials;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vipin.assessortesta.Ass_Registration.AssRegActivity;
import com.vipin.assessortesta.Batch_Student.BatchInstructionActivity;
import com.vipin.assessortesta.R;

public class SplashScreen extends AppCompatActivity {
    Button login;
    TextView newuserr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        login = findViewById(R.id.loginbutton);
        newuserr=findViewById(R.id.newuserr);


//        Intent i = new Intent(SplashScreen.this, AssRegActivity.class);
//        Intent i = new Intent(SplashScreen.this, BatchInstructionActivity.class);
//        startActivity(i);

       newuserr.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i = new Intent(SplashScreen.this, AssRegActivity.class);

               startActivity(i);
           }
       });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SplashScreen.this, SignIn.class);
//                Intent i = new Intent(SplashScreen.this, AssRegActivity.class);

                startActivity(i);
            }
        });


    }


}
