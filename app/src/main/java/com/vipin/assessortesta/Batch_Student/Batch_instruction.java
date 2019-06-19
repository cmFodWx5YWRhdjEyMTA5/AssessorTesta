package com.vipin.assessortesta.Batch_Student;

import android.animation.AnimatorInflater;
import android.animation.StateListAnimator;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;

import com.vipin.assessortesta.R;

public class Batch_instruction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_instruction);
        CardView card = findViewById(R.id.card1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StateListAnimator stateListAnimator = AnimatorInflater
                    .loadStateListAnimator(getApplicationContext(), R.animator.lift_on_touch);
            card.setStateListAnimator(stateListAnimator);
        }
// add a click handler to ensure the CardView handles touch events
// otherwise the animation won't work
card.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Toast.makeText(getApplicationContext(),"Card 1 clicked",Toast.LENGTH_LONG).show();
    }
});    }
}
