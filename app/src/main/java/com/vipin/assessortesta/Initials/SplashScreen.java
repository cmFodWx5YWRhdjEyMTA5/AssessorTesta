package com.vipin.assessortesta.Initials;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.vipin.assessortesta.R;
import com.vipin.assessortesta.feedback.AssessorFeedbackActivity;
import com.vipin.assessortesta.practical_student_assign.StudentAssignActivity;
import com.vipin.assessortesta.practical_student_list.PracticalStuListActivity;
import com.vipin.assessortesta.student_group.StudentGroupActivity;


public class SplashScreen extends AppCompatActivity {
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        login = findViewById(R.id.loginbutton);

//        Intent i = new Intent(SplashScreen.this, AssRegActivity.class);
//        Intent i = new Intent(SplashScreen.this, StudentGroupActivity.class);
//        Intent i = new Intent(SplashScreen.this, StudentAssignActivity.class);
        Intent i = new Intent(SplashScreen.this, AssessorFeedbackActivity.class);

//        Intent i = new Intent(SplashScreen.this, PracticalStuListActivity.class);
        startActivity(i);

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
