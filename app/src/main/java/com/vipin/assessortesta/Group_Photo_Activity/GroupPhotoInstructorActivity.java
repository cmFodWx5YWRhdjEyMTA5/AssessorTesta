package com.vipin.assessortesta.Group_Photo_Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.vipin.assessortesta.Batch_Student.BatchInstructionActivity;
import com.vipin.assessortesta.R;

public class GroupPhotoInstructorActivity extends AppCompatActivity {


    private static final int groupphoto = 111;
    private static final int groupphotoselfie = 112;


    ImageView groupphotos, groupphotoselfies;
    Button button_proceeds;
    boolean count1, count2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group__photo__instructor_);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        groupphotos = findViewById(R.id.imageview11);
        groupphotoselfies = findViewById(R.id.imageview22);
        button_proceeds = findViewById(R.id.button_proceeds);


        groupphotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GroupPhotoInstructorActivity.this, GroupPhoto_Activity.class);
                startActivityForResult(intent, groupphoto);
            }
        });


        groupphotoselfies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GroupPhotoInstructorActivity.this, GroupPhoto_selfie_Activity.class);
                startActivityForResult(intent, groupphotoselfie);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                startActivity(new Intent(this, BatchInstructionActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, final int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == groupphoto) {


            if (resultCode == 1) {

                groupphotos.setImageResource(R.drawable.checked);
                count1 = true;

            }

        }


        if (requestCode == groupphotoselfie) {
            if (resultCode == 2) {
                groupphotoselfies.setImageResource(R.drawable.checked);
                count2 = true;


            }


        }





        button_proceeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {






                if (count1 == true && count2 == true ) {

                    Intent intent = new Intent();
                    setResult(6, intent);
                    GroupPhotoInstructorActivity.this.finish();
                } else {

                    completeAllTask();

                    //Toast.makeText(getApplicationContext(), "please attempt all the task", Toast.LENGTH_SHORT).show();

                }
            }
        });





    }




    public void completeAllTask(){



        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setMessage("Please Complete All The Task ")
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


    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        startActivity(new Intent(this, BatchInstructionActivity.class));
        finish();
    }



}
