package com.vipin.assessortesta.student_group;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.vipin.assessortesta.Global.BaseActivity;
import com.vipin.assessortesta.R;
import com.vipin.assessortesta.utils.GridSpacingItemDecoration;

public class StudentGroupActivity extends AppCompatActivity {

    private RecyclerView rcView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_group);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Practical Exam Group");
        initView();
        manageView();
    }

    private void initView() {
        rcView = (RecyclerView)findViewById(R.id.rcView);
    }

    private void manageView() {
        setGridList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void setGridList() {

        rcView.setLayoutManager(new GridLayoutManager(StudentGroupActivity.this, 2));
        StudentGroupAdapter adapter = new StudentGroupAdapter(StudentGroupActivity.this);

        int spanCount = 2; // 2 columns
        int spacing = 8; // 50px
        boolean includeEdge = false;
        rcView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));

        rcView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Toast.makeText(this, "Back Button clicked", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
