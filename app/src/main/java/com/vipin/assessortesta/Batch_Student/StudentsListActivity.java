package com.vipin.assessortesta.Batch_Student;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.vipin.assessortesta.Attendance.StudentAttendanceActivity;
import com.vipin.assessortesta.R;
import com.vipin.assessortesta.practical_student_list.PracticalStuListActivity;
import com.vipin.assessortesta.utils.CommonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dmax.dialog.SpotsDialog;


public class StudentsListActivity extends AppCompatActivity {
    RecyclerView meet_rc;
    SharedPreferences sharedpreferences;
    final String mypreference = "mypref";
    String batchid,studentid,studentname,ATTENDSTATUS,ATTENDSTATUS1,username;
    private android.app.AlertDialog progressDialog;
    Button submit;
    int attendance_status1;



    final Context myContext = StudentsListActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        ATTENDSTATUS = getIntent().getStringExtra("persent");
//      //  Toast.makeText(getApplicationContext(),""+ATTENDSTATUS,Toast.LENGTH_LONG).show();
//        ATTENDSTATUS1 = getIntent().getStringExtra("Absent");
//        //Toast.makeText(getApplicationContext(),""+ATTENDSTATUS1,Toast.LENGTH_LONG).show();
//
        progressDialog = new SpotsDialog(StudentsListActivity.this, R.style.Custom);
        submit = findViewById(R.id.submit);

        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);


        if (sharedpreferences.contains("batch_id")) {
            batchid = sharedpreferences.getString("batch_id", "");
            System.out.println("asessoriddd" + batchid);


        }

        if (sharedpreferences.contains("user_name")) {
            username = sharedpreferences.getString("user_name", "");
            System.out.println("asessoriddd" + username);

        }




        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (attendance_status1==0)
                {
                    MarkAllAttendence();

                }
                else
                {

                    Intent intent1 = new Intent(StudentsListActivity.this, BatchInstructionActivity.class);
                    setResult(2, intent1);
                    intent1.putExtra("resultcode","abc");
                    startActivity(intent1);
                    //StudentsListActivity.this.finish();





                }
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
                startActivity(new Intent(this, BatchDetailActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        startActivity(new Intent(this, BatchDetailActivity.class));
        finish();
    }

    public void MarkAllAttendence(){



        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setMessage("Please Mark All The Student's Attendece ")
                .setTitle("Message")
                .setCancelable(true)
                .setNegativeButton("OK",new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                            }}

                ).create();

        alertDialog.show();
    }








    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        meet_rc = (RecyclerView) findViewById(R.id.meet_rc);

        if(batchid!=null) {
            callApi();
        }








    }

    private void callApi(){
        progressDialog.show();

        AndroidNetworking.post(CommonUtils.url+"get_students_list_batchwise.php")
                .addBodyParameter("key_salt", "UmFkaWFudEluZm9uZXRTYWx0S2V5")
                .addBodyParameter("assessor_id",username)
                .addBodyParameter("batch_id",batchid)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("gggg"+response);
                        try {
                            attendance_status1 = response.getInt("attendance_status");
                            if (response.getInt("status") == 1) {

                                JSONArray jsonArray = response.getJSONArray("student_details");
                                System.out.println("gggg"+jsonArray);
                                meet_rc.setLayoutManager(new LinearLayoutManager(myContext));
                                meet_rc.setAdapter(new MeetAdapter(jsonArray));

                                submit.setVisibility(View.VISIBLE);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            System.out.println("gggg"+e);
                        }

                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        System.out.println("gggg"+anError);
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }

                    }
                });
    }


    private class MeetAdapter extends RecyclerView.Adapter<MeetAdapter.ViewHolder> {

        JSONArray jsonArray;
        MeetAdapter(JSONArray jsonArray){
            this.jsonArray = jsonArray;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(myContext);
            View view = inflater.inflate(R.layout.student_adapter, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            try {
                JSONObject jsonObject = jsonArray.getJSONObject(position);

                holder.maccountName.setText(jsonObject.getString("name"));


                if (jsonObject.getString("mobile").equals("null") || jsonObject.getString("mobile").equals("")  ) {

                    holder.mcontactperson.setText("NA");

                }
                else
                {
                    holder.mcontactperson.setText(jsonObject.getString("mobile"));
                }

                if (jsonObject.getString("email").equals("null") || jsonObject.getString("email").equals("")) {
                    holder.mlocation.setText("NA");
                }
                else
                {
                    holder.mlocation.setText(jsonObject.getString("email"));
                }


                holder.mstartDate.setText(jsonObject.getString("tc_name"));
                holder.mendDate.setText(jsonObject.getString("batch_id"));
                holder.Attendstatus.setText(jsonObject.getString("student_attendance"));



                String studentid = jsonObject.getString("student_id");
                studentname = jsonObject.getString("name");
                String studentname1 = jsonObject.getString("name");

                String tcname= jsonObject.getString("tc_name");



                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent ii=new Intent(StudentsListActivity.this, StudentAttendanceActivity.class);
                        ii.putExtra("student_id", studentid);
                        ii.putExtra("tc_name",tcname );
                        ii.putExtra("name",studentname1 );
                        startActivity(ii);
                    }
                });



            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return jsonArray.length();
            // return accountName.size();
        }






        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
            TextView maccountName, mcontactperson, mlocation, mstartDate, mendDate, Attendstatus, textMenu;
            AlertDialog dialog;
            double desLat, desLng;
            Geocoder geocoder;

            ViewHolder(@NonNull View itemView) {
                super(itemView);


                itemView.setOnClickListener(this);
                maccountName = itemView.findViewById(R.id.studentlist_batchnameid);
                mcontactperson = (TextView) itemView.findViewById(R.id.studentlist_totalstudentid);
                mlocation = (TextView) itemView.findViewById(R.id.studentlist_assessmentid);
                mstartDate = (TextView) itemView.findViewById(R.id.studentlist_tcnameid);
                mendDate = (TextView) itemView.findViewById(R.id.studentlist_namee);
                Attendstatus = itemView.findViewById(R.id.Attend_data);


            }

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return false;
            }

            @Override
            public void onClick(View v) {

            }

        }
    }






}
