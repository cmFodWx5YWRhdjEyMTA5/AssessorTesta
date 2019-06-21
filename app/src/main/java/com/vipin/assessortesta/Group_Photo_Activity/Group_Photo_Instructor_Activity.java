package com.vipin.assessortesta.Group_Photo_Activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.vipin.assessortesta.Photos.Photo_navigation;
import com.vipin.assessortesta.Photos.Pmkvycounselling;
import com.vipin.assessortesta.Photos.Pmkvysignane;
import com.vipin.assessortesta.R;

public class Group_Photo_Instructor_Activity extends AppCompatActivity {


    private static final int groupphoto = 111;
    private static final int groupphotoselfie = 112;


    ImageView groupphotos, groupphotoselfies;
    Button button_proceeds;
    boolean count1, count2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group__photo__instructor_);

        groupphotos = findViewById(R.id.imageview11);
        groupphotoselfies = findViewById(R.id.imageview22);
        button_proceeds = findViewById(R.id.button_proceeds);


        groupphotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Group_Photo_Instructor_Activity.this, GroupPhoto_Activity.class);
                startActivityForResult(intent, groupphoto);
            }
        });


        groupphotoselfies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Group_Photo_Instructor_Activity.this, GroupPhoto_selfie_Activity.class);
                startActivityForResult(intent, groupphotoselfie);
            }
        });


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


    }
}
