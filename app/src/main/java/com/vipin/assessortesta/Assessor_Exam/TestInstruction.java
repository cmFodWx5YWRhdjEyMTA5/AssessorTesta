package com.vipin.assessortesta.Assessor_Exam;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.vipin.assessortesta.Initials.SignIn;
import com.vipin.assessortesta.R;

public class TestInstruction extends AppCompatActivity {

    Button startTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_instruction);


        startTest = findViewById(R.id.testinstructproceed);



        startTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences("prefstimer", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear().apply();
                Intent x = new Intent(TestInstruction.this, TestQuestion.class);
                startActivity(x);
                finish();
            }
        });


    }
}
