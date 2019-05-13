package com.vipin.assessortesta.Attendance;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.vipin.assessortesta.Global.BaseActivity;
import com.vipin.assessortesta.R;

public class Assessor_Atten extends BaseActivity {
LinearLayout uploadphotoo,uploadid,currentlocation,tclocation;
        Button loginbutton;
        TextView centrelocation,currentlocationn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        uploadphotoo=findViewById(R.id.uploadphoto1);
        uploadid=findViewById(R.id.inputidproof);
        currentlocation=findViewById(R.id.currentlocation);
        tclocation=findViewById(R.id.tclocation);
        loginbutton=findViewById(R.id.loginbutton);
        centrelocation=findViewById(R.id.centrelocationn);
        currentlocationn=findViewById(R.id.locationn);
        centrelocation.setMovementMethod(new ScrollingMovementMethod());
        currentlocationn.setMovementMethod(new ScrollingMovementMethod());

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
                    loginbutton.setVisibility(View.VISIBLE);
                }else {
                    attendancealert();
                    uploadphotoo.setVisibility(View.GONE);
                    uploadid.setVisibility(View.GONE);
                    currentlocation.setVisibility(View.GONE);
                    tclocation.setVisibility(View.GONE);
                    loginbutton.setVisibility(View.GONE);
                }

            }
        });




    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_assessor__atten;
    }

}
