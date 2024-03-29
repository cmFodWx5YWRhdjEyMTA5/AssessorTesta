package com.vipin.assessortesta.Photos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.vipin.assessortesta.Batch_Student.BatchInstructionActivity;
import com.vipin.assessortesta.R;

public class AnnexureMphotosActivity extends AppCompatActivity {

    private static final int pmsign = 100;
    private static final int counsiii = 52;
    private static final int booklet = 53;
    private static final int enrolmentt = 2254;
    private static final int attendencee = 5555;
    private static final int feedback = 556;
    private static final int infra = 257;
    private static final int classs = 5658;
    private static final int labpicc = 559;
    private static final int pmkvlab = 410;


    ImageView tvsignane, counsilling, bookletpic, enrolment, attendence, feedbackform, infrapic, classroom, labpic, pmkvylab;
    Button button_proceed;
    boolean count1, count2, count3, count4, count5, count6, count7, count8, count9, count10;

    Button submitb;
    int perc1;

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

        button_proceed = findViewById(R.id.button_proceed1);









        tvsignane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AnnexureMphotosActivity.this, Pmkvysignane.class);
                startActivityForResult(intent, pmsign);
            }
        });


        counsilling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AnnexureMphotosActivity.this, Pmkvycounselling.class);
                startActivityForResult(intent, counsiii);
            }
        });


        bookletpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AnnexureMphotosActivity.this,Booklet_pic.class);
                startActivityForResult(intent,booklet);
            }
        });


        enrolment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AnnexureMphotosActivity.this, Enrolmentform_Pic.class);
                startActivityForResult(intent, enrolmentt);
            }
        });


        attendence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AnnexureMphotosActivity.this, Attendancepic.class);
                startActivityForResult(intent, attendencee);
            }
        });


        feedbackform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AnnexureMphotosActivity.this, Feedbackformpic.class);
                startActivityForResult(intent, feedback);
            }
        });


        infrapic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AnnexureMphotosActivity.this, Infrapic.class);
                startActivityForResult(intent, infra);
            }
        });


        classroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AnnexureMphotosActivity.this, Pmkvy_classroom.class);
                startActivityForResult(intent, classs);
            }
        });


        labpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AnnexureMphotosActivity.this, Labpics.class);
                startActivityForResult(intent, labpicc);
            }
        });


        pmkvylab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AnnexureMphotosActivity.this, Pmkvy_lab.class);
                startActivityForResult(intent, pmkvlab);
            }
        });




        perc1 =getIntent().getExtras().getInt("percentage_of_photo");
        System.out.println("percentage_of_photopppppppp"+perc1);



            precentageCount();




        button_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (count1 == true && count2 == true && count3 == true && count4 == true && count5 == true &&
                        count6 == true && count7 == true && count8 == true && count9 == true && count10 == true  || perc1==100) {
                    Intent intent = new Intent();
                    setResult(1, intent);
                    AnnexureMphotosActivity.this.finish();

                } else {

                    FinishTask_Alert();

                    Toast.makeText(getApplicationContext(), "please attempt all the task", Toast.LENGTH_SHORT).show();

                }
            }
        });
 }



    @Override
    protected void onActivityResult(int requestCode, final int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == pmsign) {


//
            if (resultCode == 1) {
                //tvsignane.setText(data.getStringExtra("encoded"));
                //  tvsignane.setText("done");

                //tvsignane.setImageResource(R.drawable.ic_photo_camera);

                tvsignane.setImageResource(R.drawable.checked);
                count1 = true;


//
//                Bundle dd = data.getExtras();
//
//
//                String MMessage = dd.getString("encode");
//
//                System.out.print("rrrr" + resultCode);

            }

        }


        if (requestCode == counsiii) {
            if (resultCode == 2) {
                counsilling.setImageResource(R.drawable.checked);
                count2 = true;


            }


        }


        if (requestCode == booklet) {

            if (resultCode == 3) {
                bookletpic.setImageResource(R.drawable.checked);
                count3 = true;


            }

        }


        if (requestCode == enrolmentt) {

            if (resultCode == 4) {

                enrolment.setImageResource(R.drawable.checked);
                count4 = true;

            }

        }


        if (requestCode == attendencee) {


            if (resultCode == 5) {
                attendence.setImageResource(R.drawable.checked);
                count5 = true;

            }

        }


        if (requestCode == feedback) {

            if (resultCode == 6) {
                feedbackform.setImageResource(R.drawable.checked);
                count6 = true;


            }

        }


        if (requestCode == infra) {

            if (resultCode == 7) {

                infrapic.setImageResource(R.drawable.checked);
                count7 = true;

            }

        }

        if (requestCode == classs) {

            if (resultCode == 8) {

                classroom.setImageResource(R.drawable.checked);
                count8 = true;


            }

        }

        if (requestCode == labpicc) {


            if (resultCode == 9) {


                labpic.setImageResource(R.drawable.checked);
                count9 = true;


            }

        }

        if (requestCode == pmkvlab) {


            if (resultCode == 10) {


                pmkvylab.setImageResource(R.drawable.checked);
                count10 = true;


//                Bundle dd = data.getExtras();
//
//
//                String MMessage = dd.getString("encode");
//
//                System.out.print("rrrr" + resultCode);

            }

        }





    }



    public void FinishTask_Alert(){

        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setMessage("Make Sure Finish All The Task ")
                .setTitle("Message")
                .setCancelable(true)
                .setNegativeButton("OK",new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                            }}

                ).create();

        alertDialog.show();
    }








    public void precentageCount()
    {

        if(perc1==100)
        {
            tvsignane.setImageResource(R.drawable.checked);
            counsilling.setImageResource(R.drawable.checked);
            bookletpic.setImageResource(R.drawable.checked);
            enrolment.setImageResource(R.drawable.checked);
            attendence.setImageResource(R.drawable.checked);
            feedbackform.setImageResource(R.drawable.checked);
            infrapic.setImageResource(R.drawable.checked);
            classroom.setImageResource(R.drawable.checked);
            labpic.setImageResource(R.drawable.checked);
            pmkvylab.setImageResource(R.drawable.checked);
 }
}











    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, BatchInstructionActivity.class));
        finish();
    }




}




