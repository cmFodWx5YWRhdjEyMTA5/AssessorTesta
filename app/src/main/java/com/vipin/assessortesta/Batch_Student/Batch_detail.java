package com.vipin.assessortesta.Batch_Student;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.vipin.assessortesta.R;

public class Batch_detail extends AppCompatActivity {
Button proceed_batchdetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_detail);
        proceed_batchdetail=findViewById(R.id.proceed_batchdetail);
        proceed_batchdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii=new Intent(Batch_detail.this,Students_list.class);
                startActivity(ii);
            }
        });
    }
}
