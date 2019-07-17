package com.vipin.assessortesta.practical_student_list;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.androidnetworking.common.Priority;
import com.rx2androidnetworking.Rx2AndroidNetworking;
import com.vipin.assessortesta.Batch_Student.BatchInstructionActivity;
import com.vipin.assessortesta.R;
import com.vipin.assessortesta.feedback.AssessorFeedbackActivity;
import com.vipin.assessortesta.pojo.feedback_stu_list.PracticalStuListResponse;
import com.vipin.assessortesta.pojo.feedback_stu_list.StudentDetailsItem;
import com.vipin.assessortesta.practical_student_list.adapter.PracticalStuListRcAdapter;
import com.vipin.assessortesta.utils.CommonUtils;
import com.vipin.assessortesta.utils.RecyclerItemClickListener;

import java.util.List;

import dmax.dialog.SpotsDialog;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PracticalStuListActivity extends AppCompatActivity {

    private RecyclerView rcNonSelected;
    private android.app.AlertDialog progressDialog;

    SharedPreferences sharedpreferences;
    final String mypreference = "mypref";
    String assessor_id,batchid;
    Button btnProceed;
    static int totalCount = 0;
    static int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_student_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressDialog = new SpotsDialog(this, R.style.Custom);

        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);

        if (sharedpreferences.contains("user_name")) {
            assessor_id = sharedpreferences.getString("user_name", "");
            System.out.println("asessoriddd" + assessor_id);

        }

        if (sharedpreferences.contains("batch_id")) {
            batchid = sharedpreferences.getString("batch_id", "");
            System.out.println("asessoriddd" + batchid);
        }


        initView();
        manageView();

    }

    private void initView() {
        rcNonSelected = (RecyclerView)findViewById(R.id.rcNonSelected);
        btnProceed = (Button) findViewById(R.id.btnProceed);
    }

    private void manageView() {

        rcNonSelected.setLayoutManager(new LinearLayoutManager(this));

        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count == totalCount) {
                    Intent i = new Intent(PracticalStuListActivity.this, BatchInstructionActivity.class);
                    startActivity(i);
                    finish();
                }else {
                    showAlertMessage(R.drawable.ic_complain, "Alert", "\nYou must submit feedback of all the students.");
                }
            }
        });
        callApiForStuList();
    }

    private void callApiForStuList() {
        show_progressbar();
        Rx2AndroidNetworking.post(CommonUtils.url+"get_student_basic_details.php")
                .addBodyParameter("key_salt", "UmFkaWFudEluZm9uZXRTYWx0S2V5")
                .addBodyParameter("batch_id",batchid)
                .addBodyParameter("assessor_id", assessor_id)
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectObservable(PracticalStuListResponse.class)
                .  subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PracticalStuListResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PracticalStuListResponse response) {

                        hide_progressbar();
                        if (response.getStatus() == 1) {
                            funcNonSelectedStudent(response);
                        }else {
                            showAlertMessageWithBack(R.drawable.ic_complain, "Alert", getResources().getString(R.string.api_error));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        hide_progressbar();
                        showAlertMessageWithBack(R.drawable.ic_complain, "Alert", getResources().getString(R.string.api_error));
//                        Toast.makeText(PracticalStuListActivity.this, "Failed to get", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void funcNonSelectedStudent(PracticalStuListResponse response) {

        if (response.getStatus() == 1) {

            List<StudentDetailsItem> list = response.getStudentDetails();
            totalCount = list.size();
            count = 0;
            for (int i = 0; i < list.size(); i++){
                if (list.get(i).getExamFeedbackStatus() == 1 && list.get(i).getExamVideoStatus() == 1){
                    count += 1;
                }
            }
            PracticalStuListRcAdapter adapter = new PracticalStuListRcAdapter(this, response);
            rcNonSelected.setAdapter(adapter);
        }

        rcNonSelected.addOnItemTouchListener(
                new RecyclerItemClickListener(this, rcNonSelected,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // TODO Handle item click

                        List<StudentDetailsItem> itemList = response.getStudentDetails();
                        Intent intent = new Intent(PracticalStuListActivity.this, AssessorFeedbackActivity.class);
                        intent.putExtra("stu_id", itemList.get(position).getStudentId());
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                }));
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
                startActivity(new Intent(PracticalStuListActivity.this, BatchInstructionActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void show_progressbar(){
        progressDialog.show();
    }

    public void hide_progressbar(){
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private void showAlertMessage(int icon, String title, String msg){
        new AlertDialog.Builder(PracticalStuListActivity.this)
                .setIcon(icon)
                .setTitle(title)
                .setMessage(msg)
                .setCancelable(false)
                .setNegativeButton("Ok", null)
                .show();
    }
    private void showAlertMessageWithBack(int icon, String title, String msg){
        new AlertDialog.Builder(PracticalStuListActivity.this)
                .setIcon(icon)
                .setTitle(title)
                .setMessage(msg)
                .setCancelable(false)
                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(PracticalStuListActivity.this, BatchInstructionActivity.class));
                        finish();
                    }
                })
                .show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(PracticalStuListActivity.this, BatchInstructionActivity.class));
        finish();
    }
}
