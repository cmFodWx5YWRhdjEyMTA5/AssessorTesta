package com.vipin.assessortesta.Initials;

import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vipin.assessortesta.R;

public class SignIn extends AppCompatActivity {

    TextView forgotpassword;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        forgotpassword = findViewById(R.id.forgotpassword);
        login = findViewById(R.id.loginbutton);


        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(SignIn.this,ForgotPassword.class);
                startActivity(j);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent k = new Intent(SignIn.this,AssessorTask.class);
                startActivity(k);
            }
        });




    }
}
