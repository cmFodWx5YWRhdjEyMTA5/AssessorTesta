package com.vipin.assessortesta.Initials;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vipin.assessortesta.Ass_Registration.AssRegActivity;
import com.vipin.assessortesta.Assessor_Exam.TestInstruction;
import com.vipin.assessortesta.Batch_Student.BatchInstructionActivity;
import com.vipin.assessortesta.R;
import com.vipin.assessortesta.feedback.AssessorFeedbackActivity;
import com.vipin.assessortesta.practical_student_list.PracticalStuListActivity;
import com.vipin.assessortesta.utils.PrefConstants;
import com.vipin.assessortesta.utils.PrefsManager;

public class SplashScreen extends AppCompatActivity implements View.OnClickListener {
    Button login;
    TextView newuserr;
    private PrefsManager prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        prefs = new PrefsManager(this);

        initView();
        manageView();

    }

    private void initView() {
        login = findViewById(R.id.loginbutton);
        newuserr=findViewById(R.id.newuserr);
    }

    private void manageView() {
        newuserr.setOnClickListener(this::onClick);
        login.setOnClickListener(this::onClick);

        if(prefs.getString(PrefConstants.PREF_USERNAME) != null){

            String sExamStaus = prefs.getString(PrefConstants.EXAM_STATUS);

            if(sExamStaus.equals("Approved") ) {

                Intent z = new Intent(this, AssessorTask.class);
                startActivity(z);
                finish();
            }
            else {
                Intent z = new Intent(this, SignIn.class);
                startActivity(z);
//                finish();
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.loginbutton:
                Intent i = new Intent(SplashScreen.this, AssRegActivity.class);
                startActivity(i);
                break;
            case R.id.newuserr:
                Intent i1 = new Intent(SplashScreen.this, SignIn.class);
                startActivity(i1);
                break;
        }
    }
}
