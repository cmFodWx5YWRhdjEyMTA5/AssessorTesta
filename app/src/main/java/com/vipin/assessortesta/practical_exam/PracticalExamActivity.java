package com.vipin.assessortesta.practical_exam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.vipin.assessortesta.R;
import com.vipin.assessortesta.practical_exam.adapter.NonSelectedRcAdapter;
import com.vipin.assessortesta.student_group.StudentGroupActivity;
import com.vipin.assessortesta.utils.GridSpacingItemDecoration;

public class PracticalExamActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView rcNonSelected;
    private TextView tvQuesNO, tvQues;
    private Button btnProceed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_exam);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initView();
        manageView();

    }

    private void initView() {
        tvQuesNO = (TextView)findViewById(R.id.tvQuesNO) ;
        tvQues = (TextView)findViewById(R.id.tvQues) ;
        rcNonSelected = (RecyclerView)findViewById(R.id.rcNonSelected);
        btnProceed = (Button) findViewById(R.id.btnProceed);


    }
    private void manageView() {
        tvQues.setText("Read each sentence to find out whether there is any grammatical error in it. The error, if any will be in one part of the sentence.");
        btnProceed.setOnClickListener(this::onClick);
        rcNonSelected.setLayoutManager(new LinearLayoutManager(this));
        funcNonSelectedStudent();
    }

    private void funcNonSelectedStudent() {
        NonSelectedRcAdapter adapter = new NonSelectedRcAdapter(this);
        rcNonSelected.setAdapter(adapter);
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
                Toast.makeText(this, "Back button clicked", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnProceed:
                startActivity(new Intent(PracticalExamActivity.this, StudentGroupActivity.class));
                finish();
                break;
        }
    }
}
