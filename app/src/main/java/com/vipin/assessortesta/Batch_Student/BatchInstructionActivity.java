package com.vipin.assessortesta.Batch_Student;

import android.animation.AnimatorInflater;
import android.animation.StateListAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.vipin.assessortesta.Group_Photo_Activity.GroupPhotoInstructorActivity;
import com.vipin.assessortesta.Initials.AnnexureMFormActivity;
import com.vipin.assessortesta.Initials.AssessorTask;
import com.vipin.assessortesta.Initials.MyNetwork;
import com.vipin.assessortesta.Photos.AnnexureMphotosActivity;
import com.vipin.assessortesta.R;
import com.vipin.assessortesta.practical_student_list.PracticalStuListActivity;
import com.vipin.assessortesta.student_group.StudentGroupActivity;
import com.vipin.assessortesta.utils.CommonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class BatchInstructionActivity extends AppCompatActivity {

    TextView card1_textview1,card2_textview2,card3_textview3,card4_textview4,card5_textview5,card6_textview6;
    CardView card1,card2,card3,card4,card5,card6;
    private static final int CARD6_REQUESTCODE = 100;
    private static final int CARD1_REQUESTCODE = 52;
    private static final int CARD2_REQUESTCODE = 53;
    private static final int CARD3_REQUESTCODE = 2254;
    private static final int CARD4_REQUESTCODE = 5555;
    private static final int CARD5_REQUESTCODE = 556;
    private android.app.AlertDialog progressDialog;
    boolean count1, count2, count3, count4, count5, count6;
    String datacard2;
    Button Submit_Final_Button;
    String status,batchid,username, assessor_id;
    SharedPreferences sharedpreferences;
    final String mypreference = "mypref";

    int annexureM_PhotoPerc, annexureMPerc,attendancePerc,alignStudentPerc,feedbackPerc,groupPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_instruction);


        progressDialog = new SpotsDialog(BatchInstructionActivity.this, R.style.Custom);
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
        Submit_Final_Button = findViewById(R.id.submitofBatchInst);



        datacard2 = getIntent().getStringExtra("resultcode");


        if(datacard2 != null){

            if(datacard2.equals("abc"))
            {
                card2_textview2.setText("100%");


                count2 = true;
            }
        }


        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);


        if (sharedpreferences.contains("batch_id")) {
            batchid = sharedpreferences.getString("batch_id", "");
            System.out.println("asessoriddd" + batchid);


        }
        if (sharedpreferences.contains("user_name")) {
            assessor_id = sharedpreferences.getString("user_name", "");
            System.out.println("asessoriddd" + batchid);


        }

        if (sharedpreferences.contains("user_name")) {
            username = sharedpreferences.getString("user_name", "");
            System.out.println("asessoriddd" + username);

        }







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

                Intent card1_intent = new Intent(BatchInstructionActivity.this, AnnexureMphotosActivity.class);
                card1_intent.putExtra("percentage_of_photo",annexureM_PhotoPerc);
                startActivityForResult(card1_intent, CARD1_REQUESTCODE); }
        });



        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent card2_intent = new Intent(BatchInstructionActivity.this, BatchDetailActivity.class);
                startActivity(card2_intent);

            }
        });



        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent card3_intent = new Intent(BatchInstructionActivity.this, StudentGroupActivity.class);
                startActivityForResult(card3_intent, CARD3_REQUESTCODE);}
        });



        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent card4_intent = new Intent(BatchInstructionActivity.this, PracticalStuListActivity.class);
                startActivityForResult(card4_intent, CARD4_REQUESTCODE);
            }
        });

        card5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent card5_intent = new Intent(BatchInstructionActivity.this, AnnexureMFormActivity.class);
                startActivityForResult(card5_intent, CARD5_REQUESTCODE);
            }
        });

        card6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent card6_intent = new Intent(BatchInstructionActivity.this, GroupPhotoInstructorActivity.class);
                card6_intent.putExtra("percentage_of_photo_group",groupPhoto);
                startActivityForResult(card6_intent, CARD6_REQUESTCODE);


            }




        });



        Submit_Final_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(annexureM_PhotoPerc==100 && annexureMPerc == 100 &&  attendancePerc ==100 && alignStudentPerc == 100 && feedbackPerc ==100 && groupPhoto ==100) {
                    Toast.makeText(BatchInstructionActivity.this, "Clicked", Toast.LENGTH_SHORT).show();

                    SendFinalData();
                     //exitByBackKey();
                }else
                {
                    FinishTask_Alert();
                }
            }
        });











    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);






        if (requestCode == CARD1_REQUESTCODE) {

            if (resultCode == 1) {

                card1_textview1.setText("100%");
                count1 = true;
            }

        }

//        if (requestCode == CARD2_REQUESTCODE) {
//
//            if (resultCode == 2) {
//
//                card2_textview2.setText("100%");
//            }
//
//        }

        if (requestCode == CARD3_REQUESTCODE) {

            if (resultCode == 3) {

                card3_textview3.setText("100%");
                count3 = true;
            }

        }



        if (requestCode == CARD4_REQUESTCODE) {

            if (resultCode == 4) {

                card1_textview1.setText("100%");
                count4 = true;
            }

        }


        if (requestCode == CARD5_REQUESTCODE) {

            if (resultCode == 5) {

                card5_textview5.setText("100%");
                count5 = true;
            }

        }







        if (requestCode == CARD6_REQUESTCODE) {

            if (resultCode == 6) {

                card6_textview6.setText("100%");
                count6 = true;
            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        callApiForPercent();
    }

    public void FinishTask_Alert(){

        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setMessage("Make Sure Finish All The Task ")
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


    protected void exitByBackKey() {

        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to Submit all the details of this batch.")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                        // moveTaskToBack(true);
                        //finish();

                        //close();

                        Intent ii=new Intent(BatchInstructionActivity.this,AssessorTask.class);
                        startActivity(ii);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .show();

    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, AssessorTask.class));
        finish();
    }

    protected void BackKeyDialog() {

        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        Intent startMain = new Intent(Intent.ACTION_MAIN);
                        startMain.addCategory(Intent.CATEGORY_HOME);
                        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(startMain);
                        finishAffinity();
                        System.exit(0);

                    }
                })
                .setNegativeButton("No", null)
                .show();

    }

    private void SendFinalData() {


        String serverURL = CommonUtils.url+"update_batch_status.php";

        System.out.println("geturll" + " " + serverURL);


        StringRequest request;
        request = new StringRequest(Request.Method.POST, serverURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jobj = new JSONObject(response);
                    status = jobj.getString("status");
                    //exam_status=jobj.getString("exam_status");
                    System.out.print("responsee" + status);

                    exitByBackKey();


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error: Please try again Later1", Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Error: Please try again Later2" + error, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                super.getHeaders();
                Map<String, String> map = new HashMap<>();
                map.put("Content-Type", "application/x-www-form-urlencoded");
                return map;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                Map<String, String> map = new HashMap<>();
                map.put("key_salt", "UmFkaWFudEluZm9uZXRTYWx0S2V5");
                map.put("assessor_id", username);
                map.put("batch_id", batchid);
                map.put("exam_status","1");
                return map;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(20000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyNetwork.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void callApiForPercent(){

        show_progressbar();
        AndroidNetworking.post("https://www.skillassessment.org/sdms/android_connect1/assessor/final_activity_percentage.php")
                .addBodyParameter("key_salt", "UmFkaWFudEluZm9uZXRTYWx0S2V5")
                .addBodyParameter("batch_id", batchid)
                .addBodyParameter("assessor_id", assessor_id)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hide_progressbar();
                        Toast.makeText(BatchInstructionActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        try {
                            if (response.getInt("status") == 1){
                                JSONObject jObject = response.getJSONObject("Activity_details");

                                 annexureM_PhotoPerc = jObject.optInt("annexure_m_photo_percentage");
                                 annexureMPerc = jObject.optInt("annexure_m_status_percentage");
                                 attendancePerc = jObject.optInt("attendance_percentage");
                                 alignStudentPerc = jObject.optInt("align_student_percentage");
                                 feedbackPerc = jObject.optInt("feedback_percentage");
                                 groupPhoto = jObject.optInt("group_photo");

                                card1_textview1.setText(""+annexureM_PhotoPerc+"%");
                                card2_textview2.setText(""+attendancePerc+"%");
                                card3_textview3.setText(""+alignStudentPerc+"%");
                                card4_textview4.setText(""+feedbackPerc+"%");
                                card5_textview5.setText(""+annexureMPerc+"%");
                                card6_textview6.setText(""+groupPhoto+"%");

                            }else {
                                Toast.makeText(BatchInstructionActivity.this, "No Data", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(BatchInstructionActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        hide_progressbar();
                        Toast.makeText(BatchInstructionActivity.this, "Failed", Toast.LENGTH_SHORT).show();
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









}
