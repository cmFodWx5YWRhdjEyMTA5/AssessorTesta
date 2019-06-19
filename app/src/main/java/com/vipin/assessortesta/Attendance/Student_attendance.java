package com.vipin.assessortesta.Attendance;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.vipin.assessortesta.Global.BaseActivity;
import com.vipin.assessortesta.R;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;

public class Student_attendance extends BaseActivity {
    LinearLayout uploadphotoo_student, uploadid_student, currentlocation_student, tclocation_student;
    CircleImageView input_photograph_student, input_photograph2_student;
    TextView input_photograph22_student, input_photograph1_student;
    Button atten_student;
    TextView centrelocation_student, currentlocationn_student;
    private android.app.AlertDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        uploadphotoo_student = findViewById(R.id.uploadphoto1_student);
        input_photograph2_student = findViewById(R.id.input_photograph2_student);
        input_photograph1_student = findViewById(R.id.input_photograph1_student);
        input_photograph22_student = findViewById(R.id.input_photograph22_student);
        uploadid_student = findViewById(R.id.inputidproof_student);
        currentlocation_student = findViewById(R.id.currentlocation_student);
        tclocation_student = findViewById(R.id.tclocation_student);
        atten_student = findViewById(R.id.markattendance_student);
        centrelocation_student = findViewById(R.id.centrelocationn_student);
        currentlocationn_student = findViewById(R.id.locationn_student);
        input_photograph_student = findViewById(R.id.input_photograph_student);
        progressDialog = new SpotsDialog(Student_attendance.this, R.style.Custom);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rgroup_student);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                RadioButton rb = (RadioButton) findViewById(checkedId);
                if (rb.getText().equals("Yes")) {
                    System.out.println("yes clicked");
                    uploadphotoo_student.setVisibility(View.VISIBLE);
                    uploadid_student.setVisibility(View.VISIBLE);
                    currentlocation_student.setVisibility(View.VISIBLE);
                    tclocation_student.setVisibility(View.VISIBLE);
                    atten_student.setVisibility(View.VISIBLE);
                } else {
                    System.out.println("No clicked");
                    attendancealert();
                    uploadphotoo_student.setVisibility(View.GONE);
                    uploadid_student.setVisibility(View.GONE);
                    currentlocation_student.setVisibility(View.GONE);
                    tclocation_student.setVisibility(View.GONE);
                    atten_student.setVisibility(View.GONE);
                }

            }
        });
    }

    @Override
    protected int getMenuId() {
        return R.menu.main;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_student_attendance;
    }
}
