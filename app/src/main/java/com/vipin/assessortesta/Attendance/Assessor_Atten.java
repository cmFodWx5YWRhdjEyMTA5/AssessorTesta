package com.vipin.assessortesta.Attendance;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.vipin.assessortesta.R;

public class Assessor_Atten extends AppCompatActivity {
LinearLayout uploadphotoo,uploadid,currentlocation,tclocation;
        Button loginbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessor__atten);
        uploadphotoo=findViewById(R.id.uploadphoto1);
        uploadid=findViewById(R.id.inputidproof);
        currentlocation=findViewById(R.id.currentlocation);
        tclocation=findViewById(R.id.tclocation);
        loginbutton=findViewById(R.id.loginbutton);

        Animation slide_down = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_down);
       // uploadphotoo.setAnimation(slide_down);


        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rgroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                RadioButton rb=(RadioButton)findViewById(checkedId);
                if (rb.getText().equals("Yes")){
                    uploadphotoo.setVisibility(View.VISIBLE);
                    uploadid.setVisibility(View.VISIBLE);
                    currentlocation.setVisibility(View.VISIBLE);
                    tclocation.setVisibility(View.VISIBLE);
                }else {
                    uploadphotoo.setVisibility(View.GONE);
                    uploadid.setVisibility(View.GONE);
                    currentlocation.setVisibility(View.GONE);
                    tclocation.setVisibility(View.GONE);
                }

            }
        });

    }

}
