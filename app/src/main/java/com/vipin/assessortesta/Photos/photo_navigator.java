package com.vipin.assessortesta.Photos;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.vipin.assessortesta.R;

import me.biubiubiu.justifytext.library.JustifyTextView;

public class photo_navigator extends AppCompatActivity {

    private static final int pmsign = 1;
    private static final int couns = 2;
           // ,booklet = 3, eroll = 4,atten = 5,feedback=6,infra = 7,classs = 8,lab = 9,pmlab= 10;



    TextView tvsignane,counsilling,bookletpic,enrolment,attendence,feedbackform,infrapic,classroom,labpic,pmkvylab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_navigator);


        tvsignane = findViewById(R.id.txt1);
        counsilling = findViewById(R.id.txt1);
        bookletpic = findViewById(R.id.txt1);
        enrolment = findViewById(R.id.txt1);
        attendence = findViewById(R.id.tv5);
        feedbackform = findViewById(R.id.tv6);
        infrapic = findViewById(R.id.tv7);
        classroom = findViewById(R.id.tv8);
        labpic = findViewById(R.id.tv9);
        pmkvylab = findViewById(R.id.tv10);


        tvsignane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(photo_navigator.this,Pmkvysignane.class);
                startActivityForResult(intent,pmsign);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == pmsign)
        {
//
            if (requestCode == RESULT_OK)
            {
                //tvsignane.setText(data.getStringExtra("encoded"));
                tvsignane.setText("done");

        }
            if (requestCode == RESULT_CANCELED);
            {
                tvsignane.setText("nndone");

                //tvsignane.setText(data.getStringExtra("encoded"));

            }



        }
    }
}
