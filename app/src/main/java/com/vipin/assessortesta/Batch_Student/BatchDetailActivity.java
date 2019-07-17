package com.vipin.assessortesta.Batch_Student;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.vipin.assessortesta.R;
import com.vipin.assessortesta.practical_student_list.PracticalStuListActivity;
import com.vipin.assessortesta.utils.CommonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dmax.dialog.SpotsDialog;

public class BatchDetailActivity extends AppCompatActivity {
    TextView Batchname_Data,Totalstudent_Data,AssessmentDate_Data,Tcname_data,Assessorname_Data,Tclocation_Data,contactPerson_Data;
    String Batchname_Str,Totalstudent_Str,AssessmentDate_Str,Tcname_Str,Assessorname_Str,Tclocation_Str,contactPerson_Str;
    String batchid,username;
    private android.app.AlertDialog progressDialog;

    SharedPreferences sharedpreferences;
    final String mypreference = "mypref";
    Button proceed_batchdetail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        proceed_batchdetail=findViewById(R.id.proceed_batchdetail);
        Batchname_Data= findViewById(R.id.Batchname_Data);
        Totalstudent_Data= findViewById(R.id.Totalstudent_Data);
        AssessmentDate_Data= findViewById(R.id.AssessmentDate_Data);
        Tcname_data= findViewById(R.id.Tcname_data);
        Assessorname_Data= findViewById(R.id.Assessorname_Data);
        Tclocation_Data= findViewById(R.id.Tclocation_Data);
        contactPerson_Data= findViewById(R.id.contactPerson_Data);

        progressDialog = new SpotsDialog(BatchDetailActivity.this, R.style.Custom);





        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);

        if (sharedpreferences.contains("batch_id")) {
            batchid = sharedpreferences.getString("batch_id", "");
            System.out.println("asessoriddd" + batchid);
            callApiforDetail();
        }

        if (sharedpreferences.contains("user_name")) {
            username = sharedpreferences.getString("user_name", "");
            System.out.println("asessoriddd" + username);
            callApiforDetail();
        }

        proceed_batchdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii=new Intent(BatchDetailActivity.this, StudentsListActivity.class);
                startActivity(ii);
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


    private void callApiforDetail(){
        progressDialog.show();
        AndroidNetworking.post(CommonUtils.url+"get_batch_details.php")
                .addBodyParameter("key_salt", "UmFkaWFudEluZm9uZXRTYWx0S2V5")
                .addBodyParameter("user_name",username)
                .addBodyParameter("batch_id",batchid)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            if (response.getInt("status") == 1) {

                                JSONArray jsonArray = response.getJSONArray("batch_details");
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                Batchname_Str= jsonObject.getString("batch_name");
                                System.out.println("Batchname_Str" + Batchname_Str);
                                Totalstudent_Str= jsonObject.getString("number_of_students");
                                AssessmentDate_Str= jsonObject.getString("startdate");
                                Tcname_Str= jsonObject.getString("exam_center_name");
                                Assessorname_Str= jsonObject.getString("assessor_name");
                                Tclocation_Str= jsonObject.getString("exam_center_address");
                                contactPerson_Str= jsonObject.getString("spoc_name");



                                Batchname_Data.setText(Batchname_Str);
                                Totalstudent_Data.setText(Totalstudent_Str);
                                AssessmentDate_Data.setText(AssessmentDate_Str);
                                Tcname_data.setText(Tcname_Str);
                                Assessorname_Data.setText(Assessorname_Str);
                                Totalstudent_Data.setText(Totalstudent_Str);
                                Tclocation_Data.setText(Tclocation_Str);
                                contactPerson_Data.setText(contactPerson_Str);



                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }


                    }

                    @Override
                    public void onError(ANError anError) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }

                    }
                });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, BatchInstructionActivity.class));
        finish();
    }
}
