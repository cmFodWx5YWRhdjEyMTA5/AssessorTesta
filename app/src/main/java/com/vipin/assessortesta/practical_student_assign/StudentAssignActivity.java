package com.vipin.assessortesta.practical_student_assign;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.rx2androidnetworking.Rx2AndroidNetworking;
import com.vipin.assessortesta.R;
import com.vipin.assessortesta.pojo.practical_que.PracticalItem;
import com.vipin.assessortesta.pojo.practical_que.PracticalQuesResponse;
import com.vipin.assessortesta.pojo.stu_list.StudentDetailsItem;
import com.vipin.assessortesta.pojo.stu_list.StudentListResponse;
import com.vipin.assessortesta.practical_student_assign.adapter.NonSelectedRcAdapter;
import com.vipin.assessortesta.student_group.StudentGroupActivity;
import com.vipin.assessortesta.utils.RecyclerItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dmax.dialog.SpotsDialog;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class StudentAssignActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView rcNonSelected;
    private TextView tvQuesNO, tvQues;
    private Button btnProceed;
    private android.app.AlertDialog progressDialog;

    PracticalQuesResponse response;
    int position;
    Map<String, Boolean> statusMap;
    int quesId;
    Double stuCount, quesCount;

    SharedPreferences sharedpreferences;
    final String mypreference = "mypref";
    String assessor_id,batchid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_exam);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressDialog = new SpotsDialog(this, R.style.Custom);

        try {
            if (getIntent().getExtras().getString("que_data") != null) {
                String sJson = getIntent().getExtras().getString("que_data");
                position = getIntent().getExtras().getInt("position");
                Gson gson = new Gson();
                response = gson.fromJson(sJson, PracticalQuesResponse.class);

            }
        }catch (Exception e){
            Log.e("Student Assign", " Err "+e, e);
        }
        initView();
        manageView();


        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);

        if (sharedpreferences.contains("user_name")) {
            assessor_id = sharedpreferences.getString("user_name", "");
            System.out.println("asessoriddd" + assessor_id);

        }



        if (sharedpreferences.contains("batch_id")) {
            batchid = sharedpreferences.getString("batch_id", "");
            System.out.println("asessoriddd" + batchid);
        }

    }

    private void initView() {
        tvQuesNO = (TextView)findViewById(R.id.tvQuesNO) ;
        tvQues = (TextView)findViewById(R.id.tvQues) ;
        rcNonSelected = (RecyclerView)findViewById(R.id.rcNonSelected);
        btnProceed = (Button) findViewById(R.id.btnProceed);
    }

    private void manageView() {

        statusMap = new HashMap<>();

        try{
        stuCount = Double.valueOf(response.getTotalStudents());
        quesCount = Double.valueOf(response.getTotalQuestions());
        List<PracticalItem> practicalItemList = response.getPractical();
        PracticalItem practicalItem = practicalItemList.get(position);

            tvQuesNO.setText("Q"+(position+1+"."));
            tvQues.setText(practicalItem.getQuestion());
            quesId = practicalItem.getQuestionId();

        }catch (Exception e){
            Log.e("Student Assign", " Err "+e, e);
        }

        btnProceed.setOnClickListener(this::onClick);
        rcNonSelected.setLayoutManager(new LinearLayoutManager(this));

        callApiForStuList();
    }

    private void callApiForStuList() {
        show_progressbar();
        Rx2AndroidNetworking.post("https://www.skillassessment.org/sdms/android_connect1/assessor/get_not_assigned_student.php")
                .addBodyParameter("key_salt", "UmFkaWFudEluZm9uZXRTYWx0S2V5")
                .addBodyParameter("batch_id", batchid)
                .addBodyParameter("question_id", ""+quesId)
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectObservable(StudentListResponse.class)
                .  subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StudentListResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(StudentListResponse response) {

                        hide_progressbar();
                        funcNonSelectedStudent(response);

                    }

                    @Override
                    public void onError(Throwable e) {
                        hide_progressbar();
                        Toast.makeText(StudentAssignActivity.this, "Failed to get", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void funcNonSelectedStudent(StudentListResponse response) {

        if (response.getStatus() == 1) {

            List<StudentDetailsItem> itemList = response.getStudentDetails();
            for (int i = 0; i < itemList.size(); i++ ) {

                String stuId = itemList.get(i).getStudentId();
                if (itemList.get(i).isAssigned() == 1){
                    statusMap.put(stuId, true);
                }

            }

            NonSelectedRcAdapter adapter = new NonSelectedRcAdapter(this, response, statusMap);
            rcNonSelected.setAdapter(adapter);
        }

        rcNonSelected.addOnItemTouchListener(
                new RecyclerItemClickListener(this, rcNonSelected,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // TODO Handle item click

                        FrameLayout frameSelectBg = (FrameLayout)view.findViewById(R.id.frameSelectBg);
                        ImageView ivTick = (ImageView) view.findViewById(R.id.ivTick);

                        List<StudentDetailsItem> itemList = response.getStudentDetails();
                        String stuId = itemList.get(position).getStudentId();
                        if (ivTick.getTag().equals("None")) {
                            frameSelectBg.setBackgroundColor(getResources().getColor(R.color.colorSelectBg));
                            Drawable d = (Drawable)getResources().getDrawable(R.drawable.ic_check_circle_24dp);
                            ivTick.setImageDrawable(d);
                            ivTick.setTag("Selected");
                            statusMap.put(stuId, true);
                        }else {
                            frameSelectBg.setBackgroundColor(getResources().getColor(R.color.colorNonSelectBg));
                            Drawable d = (Drawable)getResources().getDrawable(R.drawable.ic_add_circle_outline_black_24dp);
                            ivTick.setImageDrawable(d);
                            ivTick.setTag("None");
                            statusMap.remove(stuId);
                        }
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
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnProceed:
                double limit = stuCount / quesCount;
                int maxLimit = (int) Math.ceil(limit);
                if(statusMap.size() <= (maxLimit+1)) {
                    callApiToSaveData();
                }else {
                    Drawable d = (Drawable)getResources().getDrawable(android.R.drawable.ic_delete);
                    new AlertDialog.Builder(StudentAssignActivity.this)
                            .setIcon(d)
                            .setTitle("Alert")
                            .setMessage("You can't assign more than "+(maxLimit+1)+" for a Practical")
                            .setNegativeButton("Ok", null)
                            .show();
                }
                break;
        }
    }

    private void callApiToSaveData(){
        List<String> list = new ArrayList<String>(statusMap.keySet());

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (String s: list){
            jsonArray.put(s);
        }
        try {
            jsonObject.put("ids", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        String[] arr = new String[list.size()];
//        for (int i = 0; i < list.size(); i++){
//            arr[i] = list.get(i);
//        }

        show_progressbar();
        AndroidNetworking.post("https://www.skillassessment.org/sdms/android_connect1/assessor/save_student_group.php")
                .addBodyParameter("key_salt", "UmFkaWFudEluZm9uZXRTYWx0S2V5")
                .addBodyParameter("batch_id", batchid)
                .addBodyParameter("question_id", ""+quesId)
                .addBodyParameter("assessor_id", assessor_id)
                .addBodyParameter("student_id", jsonObject.toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        hide_progressbar();
                        try {
                            int status = response.getInt("status");
                            if (status == 1){

                                new AlertDialog.Builder(StudentAssignActivity.this)
                                        .setTitle("Info")
                                        .setMessage("Success")
                                        .setCancelable(false)
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                startActivity(new Intent(StudentAssignActivity.this, StudentGroupActivity.class));
                                                finish();
                                            }
                                        })
                                        .show();
                            }else {
                                String msg = response.getString("msg");
                                new AlertDialog.Builder(StudentAssignActivity.this)
                                        .setTitle("Alert")
                                        .setMessage(msg)
                                        .setNegativeButton("Ok", null)
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        hide_progressbar();
                        new AlertDialog.Builder(StudentAssignActivity.this)
                                .setTitle("Alert")
                                .setMessage("Something went wrong, Please try again!")
                                .setNegativeButton("Close", null)
                                .show();
                    }
                });
    }
    public void show_progressbar(){
        progressDialog.show();
    }

    public void hide_progressbar(){
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
