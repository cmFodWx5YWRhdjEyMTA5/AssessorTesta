package com.vipin.assessortesta.Photos;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.vipin.assessortesta.R;

public class Photo_navigation extends AppCompatActivity {

    private static final int pmsign = 1;
    private static final int counsiii = 2;
    private static final int booklet = 3;
    private static final int enrolmentt = 4;
    private static final int attendencee = 5;
    private static final int feedback = 6;
    private static final int infra = 7;
    private static final int classs = 8;
    private static final int labpicc = 9;
    private static final int pmkvlab = 10;




    ImageView tvsignane,counsilling,bookletpic,enrolment,attendence,feedbackform,infrapic,classroom,labpic,pmkvylab;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_navigation);



        tvsignane = findViewById(R.id.imageview1);
        counsilling = findViewById(R.id.imageview2);
        bookletpic = findViewById(R.id.imageview3);
        enrolment = findViewById(R.id.imageview4);
        attendence = findViewById(R.id.imageview5);
        feedbackform = findViewById(R.id.imageview6);
        infrapic = findViewById(R.id.imageview7);
        classroom = findViewById(R.id.imageview8);
        labpic = findViewById(R.id.imageview9);
        pmkvylab = findViewById(R.id.imageview10);



        tvsignane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Photo_navigation.this,Pmkvysignane.class);
                startActivityForResult(intent,pmsign);
            }
        });


        counsilling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Photo_navigation.this,Pmkvycounselling.class);
                startActivityForResult(intent,counsiii);
            }
        });



        bookletpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Photo_navigation.this,Booklet_pic.class);
                startActivityForResult(intent,booklet);
            }
        });


        enrolment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Photo_navigation.this,Enrolmentform_Pic.class);
                startActivityForResult(intent,enrolmentt);
            }
        });



        attendence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Photo_navigation.this,Attendancepic.class);
                startActivityForResult(intent,attendencee);
            }
        });


        feedbackform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Photo_navigation.this,Feedbackformpic.class);
                startActivityForResult(intent,feedback);
            }
        });



        infrapic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Photo_navigation.this,Infrapic.class);
                startActivityForResult(intent,infra);
            }
        });


        classroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Photo_navigation.this,Pmkvy_classroom.class);
                startActivityForResult(intent,classs);
            }
        });


        labpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Photo_navigation.this,Labpics.class);
                startActivityForResult(intent,labpicc);
            }
        });


        pmkvylab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Photo_navigation.this,Pmkvy_lab.class);
                startActivityForResult(intent,pmkvlab);
            }
        });







    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        switch (requestCode)

        {
            case pmsign:


                if (requestCode == RESULT_OK)
                {
                    //tvsignane.setText(data.getStringExtra("encoded"));
                    tvsignane.setImageResource(R.drawable.checked);

                }
                if (requestCode == RESULT_CANCELED);
            {
                //tvsignane.setImageResource(R.drawable.checked);

                //tvsignane.setText(data.getStringExtra("encoded"));

            }
            break;


            case counsiii:

                if (requestCode == RESULT_OK)
                {
                    //tvsignane.setText(data.getStringExtra("encoded"));
                    counsilling.setImageResource(R.drawable.checked);

                }
                if (requestCode == RESULT_CANCELED);
                {
                    counsilling.setImageResource(R.drawable.checked);

                //tvsignane.setText(data.getStringExtra("encoded"));

                }


            case booklet:


                if (requestCode == RESULT_OK)
                {
                    //tvsignane.setText(data.getStringExtra("encoded"));
                    bookletpic.setImageResource(R.drawable.checked);

                }
                if (requestCode == RESULT_CANCELED);
            {
                bookletpic.setImageResource(R.drawable.checked);

                //tvsignane.setText(data.getStringExtra("encoded"));

            }
            break;




            case enrolmentt:


                if (requestCode == RESULT_OK)
                {
                    //tvsignane.setText(data.getStringExtra("encoded"));
                    enrolment.setImageResource(R.drawable.checked);

                }
                if (requestCode == RESULT_CANCELED);
            {
                enrolment.setImageResource(R.drawable.checked);

                //tvsignane.setText(data.getStringExtra("encoded"));

            }
            break;





            case attendencee:


                if (requestCode == RESULT_OK)
                {
                    //tvsignane.setText(data.getStringExtra("encoded"));
                    attendence.setImageResource(R.drawable.checked);

                }
                if (requestCode == RESULT_CANCELED);
            {
                attendence.setImageResource(R.drawable.checked);

                //tvsignane.setText(data.getStringExtra("encoded"));

            }
            break;



            case feedback:


                if (requestCode == RESULT_OK)
                {
                    //tvsignane.setText(data.getStringExtra("encoded"));
                    feedbackform.setImageResource(R.drawable.checked);

                }
                if (requestCode == RESULT_CANCELED);
            {
                feedbackform.setImageResource(R.drawable.checked);

                //tvsignane.setText(data.getStringExtra("encoded"));

            }
            break;



            case infra:


                if (requestCode == RESULT_OK)
                {
                    //tvsignane.setText(data.getStringExtra("encoded"));
                    infrapic.setImageResource(R.drawable.checked);

                }
                if (requestCode == RESULT_CANCELED);
            {
                infrapic.setImageResource(R.drawable.checked);

                //tvsignane.setText(data.getStringExtra("encoded"));

            }
            break;


            case classs :


                if (requestCode == RESULT_OK)
                {
                    //tvsignane.setText(data.getStringExtra("encoded"));
                    classroom.setImageResource(R.drawable.checked);

                }
                if (requestCode == RESULT_CANCELED);
            {
                classroom.setImageResource(R.drawable.checked);

                //tvsignane.setText(data.getStringExtra("encoded"));

            }
            break;



            case labpicc:


                if (requestCode == RESULT_OK)
                {
                    //tvsignane.setText(data.getStringExtra("encoded"));
                    labpic.setImageResource(R.drawable.checked);

                }
                if (requestCode == RESULT_CANCELED);
            {
                labpic.setImageResource(R.drawable.checked);

                //tvsignane.setText(data.getStringExtra("encoded"));

            }
            break;


            case pmkvlab:


//
                    if (requestCode == RESULT_OK)
                    {
                        //tvsignane.setText(data.getStringExtra("encoded"));
                        pmkvylab.setImageResource(R.drawable.checked);

                    }
                    if (requestCode == RESULT_CANCELED);
                    {
                        pmkvylab.setImageResource(R.drawable.checked);

                        //tvsignane.setText(data.getStringExtra("encoded"));

                    }


            default:














        }
    }



}
