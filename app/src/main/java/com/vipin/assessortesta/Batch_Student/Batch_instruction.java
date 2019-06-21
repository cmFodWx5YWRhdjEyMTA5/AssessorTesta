package com.vipin.assessortesta.Batch_Student;

import android.animation.AnimatorInflater;
import android.animation.StateListAnimator;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;

import com.vipin.assessortesta.Group_Photo_Activity.Group_Photo_Instructor_Activity;
import com.vipin.assessortesta.Initials.Annexure;
import com.vipin.assessortesta.Photos.Photo_navigation;
import com.vipin.assessortesta.R;

public class Batch_instruction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_instruction);
        CardView card1 = findViewById(R.id.card1);
        CardView card2 = findViewById(R.id.card2);

        CardView card3 = findViewById(R.id.card3);

        CardView card4 = findViewById(R.id.card4);

        CardView card5 = findViewById(R.id.card5);

        CardView card6 = findViewById(R.id.card6);











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
        startActivity(card1_intent); }
});



        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent card2_intent = new Intent(Batch_instruction.this,Students_list.class);
                startActivity(card2_intent); }
        });



        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent card3_intent = new Intent(Batch_instruction.this,Students_list .class);
                startActivity(card3_intent); }
        });



        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent card4_intent = new Intent(Batch_instruction.this, Photo_navigation.class);
                startActivity(card4_intent); }
        });

        card5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent card5_intent = new Intent(Batch_instruction.this, Annexure.class);
                startActivity(card5_intent); }
        });

        card6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent card6_intent = new Intent(Batch_instruction.this,Group_Photo_Instructor_Activity.class);
                startActivity(card6_intent); }
        });







    }
}
