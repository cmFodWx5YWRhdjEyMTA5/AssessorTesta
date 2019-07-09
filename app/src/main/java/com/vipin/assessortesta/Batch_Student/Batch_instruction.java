package com.vipin.assessortesta.Batch_Student;

import android.animation.AnimatorInflater;
import android.animation.StateListAnimator;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.vipin.assessortesta.Group_Photo_Activity.Group_Photo_Instructor_Activity;
import com.vipin.assessortesta.Initials.Annexure;
import com.vipin.assessortesta.Photos.Photo_navigation;
import com.vipin.assessortesta.R;
import com.vipin.assessortesta.pojo.feedback.Practical;
import com.vipin.assessortesta.practical_student_list.PracticalStuListActivity;
import com.vipin.assessortesta.student_group.StudentGroupActivity;

public class Batch_instruction extends AppCompatActivity {
    TextView card1_textview1,card2_textview2,card3_textview3,card4_textview4,card5_textview5,card6_textview6;
    CardView card1,card2,card3,card4,card5,card6;
    private static final int CARD6_REQUESTCODE = 100;
    private static final int CARD1_REQUESTCODE = 52;
    private static final int CARD2_REQUESTCODE = 53;
    private static final int CARD3_REQUESTCODE = 2254;
    private static final int CARD4_REQUESTCODE = 5555;
    private static final int CARD5_REQUESTCODE = 556;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_instruction);


         card1 = findViewById(R.id.card1);
         card2 = findViewById(R.id.card2);

         card3 = findViewById(R.id.card3);

         card4 = findViewById(R.id.card4);

         card5 = findViewById(R.id.card5);

         card6 = findViewById(R.id.card6);


        card1_textview1 = findViewById(R.id.card_textview1);
        card2_textview2 = findViewById(R.id.card_textview2);
        card3_textview3 = findViewById(R.id.card_textview3);
        card4_textview4 = findViewById(R.id.card_textview4);
        card5_textview5 = findViewById(R.id.card_textview5);
        card6_textview6 = findViewById(R.id.card_textview6);




















        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StateListAnimator stateListAnimator = AnimatorInflater
                    .loadStateListAnimator(getApplicationContext(), R.animator.lift_on_touch);
            card1.setStateListAnimator(stateListAnimator);
            card2.setStateListAnimator(stateListAnimator);
            card3.setStateListAnimator(stateListAnimator);
            card4.setStateListAnimator(stateListAnimator);
            card5.setStateListAnimator(stateListAnimator);
            card6.setStateListAnimator(stateListAnimator);







        }
// add a click handler to ensure the CardView handles touch events
// otherwise the animation won't work




 card1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        Intent card1_intent = new Intent(Batch_instruction.this, Photo_navigation.class);
        startActivityForResult(card1_intent, CARD1_REQUESTCODE); }
});



        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent card2_intent = new Intent(Batch_instruction.this,Batch_detail.class);
                startActivity(card2_intent);

            }
        });



        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent card3_intent = new Intent(Batch_instruction.this, StudentGroupActivity.class);
                startActivityForResult(card3_intent, CARD3_REQUESTCODE);}
        });



        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent card4_intent = new Intent(Batch_instruction.this, PracticalStuListActivity.class);
                startActivityForResult(card4_intent, CARD4_REQUESTCODE);
            }
        });

        card5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent card5_intent = new Intent(Batch_instruction.this, Annexure.class);
                startActivityForResult(card5_intent, CARD5_REQUESTCODE);
            }
        });

        card6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent card6_intent = new Intent(Batch_instruction.this,Group_Photo_Instructor_Activity.class);
                startActivityForResult(card6_intent, CARD6_REQUESTCODE);


            }




        });












    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);






        if (requestCode == CARD1_REQUESTCODE) {

            if (resultCode == 1) {
                card1.setClickable(false);
                card1_textview1.setText("100%");
            }

        }

        if (requestCode == CARD2_REQUESTCODE) {

            if (resultCode == 2) {
                card2.setClickable(false);
                card2_textview2.setText("100%");
            }

        }

        if (requestCode == CARD3_REQUESTCODE) {

            if (resultCode == 3) {
                card3.setClickable(false);
                card3_textview3.setText("100%");
            }

        }



        if (requestCode == CARD4_REQUESTCODE) {

            if (resultCode == 4) {
                card1.setClickable(false);
                card1_textview1.setText("100%");
            }

        }


        if (requestCode == CARD5_REQUESTCODE) {

            if (resultCode == 5) {
                card5.setClickable(false);
                card5_textview5.setText("100%");
            }

        }







        if (requestCode == CARD6_REQUESTCODE) {

            if (resultCode == 6) {
                card6.setClickable(false);
                card6_textview6.setText("100%");
            }

        }




    }
}
