package com.vipin.assessortesta.Initials;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.vipin.assessortesta.Batch_Student.Batch_instruction;
import com.vipin.assessortesta.Group_Photo_Activity.Group_Photo_Instructor_Activity;
import com.vipin.assessortesta.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Annexure extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {



    RadioGroup rgroup1,rgroup2,rgroup3,rgroup4,rgroup5,rgroup6,rgroup7,rgroup8,rgroup9,rgroup10,
    rgroup11,rgroup12,rgroup13,rgroup14,rgroup15,rgroup16,rgroup17,rgroup18,rgroup19,rgroup20,
    rgroup21,rgroup22;

    Button  submitAnnexure;

    JSONObject annexureData ;
    SharedPreferences sharedpreferences;
    final String mypreference = "mypref";
    String assessor_id,batchid;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annexure);

        annexureData = new JSONObject();
        rgroup1 = findViewById(R.id.rgroup1);
        rgroup2 = findViewById(R.id.rgroup2);
        rgroup3 = findViewById(R.id.rgroup3);
        rgroup4 = findViewById(R.id.rgroup4);
        rgroup5 = findViewById(R.id.rgroup5);
        rgroup6 = findViewById(R.id.rgroup6);
        rgroup7 = findViewById(R.id.rgroup7);
        rgroup8 = findViewById(R.id.rgroup8);
        rgroup9 = findViewById(R.id.rgroup9);
        rgroup10 = findViewById(R.id.rgroup10);
        rgroup11 = findViewById(R.id.rgroup11);
        rgroup12 = findViewById(R.id.rgroup12);
        rgroup13 = findViewById(R.id.rgroup13);
        rgroup14 = findViewById(R.id.rgroup14);
        rgroup15 = findViewById(R.id.rgroup15);
        rgroup16 = findViewById(R.id.rgroup16);
        rgroup17 = findViewById(R.id.rgroup17);
        rgroup18 = findViewById(R.id.rgroup18);
        rgroup19 = findViewById(R.id.rgroup19);
        rgroup20 = findViewById(R.id.rgroup20);
        rgroup21 = findViewById(R.id.rgroup21);
        rgroup22 = findViewById(R.id.rgroup22);
        submitAnnexure = findViewById(R.id.submitOfAnnexure);


        rgroup1.setOnCheckedChangeListener(this::onCheckedChanged);
        rgroup2.setOnCheckedChangeListener(this::onCheckedChanged);
        rgroup3.setOnCheckedChangeListener(this::onCheckedChanged);
        rgroup4.setOnCheckedChangeListener(this::onCheckedChanged);
        rgroup5.setOnCheckedChangeListener(this::onCheckedChanged);
        rgroup6.setOnCheckedChangeListener(this::onCheckedChanged);
        rgroup7.setOnCheckedChangeListener(this::onCheckedChanged);
        rgroup8.setOnCheckedChangeListener(this::onCheckedChanged);
        rgroup9.setOnCheckedChangeListener(this::onCheckedChanged);
        rgroup10.setOnCheckedChangeListener(this::onCheckedChanged);
        rgroup11.setOnCheckedChangeListener(this::onCheckedChanged);
        rgroup12.setOnCheckedChangeListener(this::onCheckedChanged);
        rgroup13.setOnCheckedChangeListener(this::onCheckedChanged);
        rgroup14.setOnCheckedChangeListener(this::onCheckedChanged);
        rgroup15.setOnCheckedChangeListener(this::onCheckedChanged);
        rgroup16.setOnCheckedChangeListener(this::onCheckedChanged);
        rgroup17.setOnCheckedChangeListener(this::onCheckedChanged);
        rgroup18.setOnCheckedChangeListener(this::onCheckedChanged);
        rgroup19.setOnCheckedChangeListener(this::onCheckedChanged);
        rgroup20.setOnCheckedChangeListener(this::onCheckedChanged);
        rgroup21.setOnCheckedChangeListener(this::onCheckedChanged);
        rgroup22.setOnCheckedChangeListener(this::onCheckedChanged);


        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);

        if (sharedpreferences.contains("user_name")) {
            assessor_id = sharedpreferences.getString("user_name", "");
            System.out.println("asessoriddd" + assessor_id);

        }


        if (sharedpreferences.contains("batch_id")) {
            batchid = sharedpreferences.getString("batch_id", "");
            System.out.println("asessoriddd" + batchid);


            submitAnnexure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (annexureData.length() == 22) {

                        SendAnnexure();

                        Intent intent = new Intent();
                        setResult(5, intent);
                        Annexure.this.finish();


                    } else {

                        SelectAll();

                    }


                }
            });


        }

    }



    private void SendAnnexure() {


        String serverURL = "https://www.skillassessment.org/sdms/android_connect1/assessor/save_annexure_m_status.php";

        System.out.println("geturll" + " " + serverURL);


        StringRequest request = new StringRequest(Request.Method.POST, serverURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jobj = new JSONObject(response);


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error: Please try again Later1"+response, Toast.LENGTH_LONG).show();
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
                HashMap<String, String> map = new HashMap<>();
                map.put("key_salt", "UmFkaWFudEluZm9uZXRTYWx0S2V5");
                map.put("assessor_id", assessor_id);
                map.put("batch_id", batchid);
                map.put("annexure_id", annexureData.toString());
                System.out.println("annexure m data is"+annexureData.toString());

                return map;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(20000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyNetwork.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    public void SelectAll(){



        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setMessage("Please Select All The Option ")
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
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (group.getId())
        {
            case R.id.rgroup1:
                RadioButton radioButton1 = (RadioButton)findViewById(checkedId);
                String s1 = radioButton1.getText().toString();
                try {
                    annexureData.put("1",s1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;


            case R.id.rgroup2:
                RadioButton radioButton2 = (RadioButton)findViewById(checkedId);
                String s2 = radioButton2.getText().toString();
                try {
                    annexureData.put("3",s2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.rgroup3:
                RadioButton radioButton3 = (RadioButton)findViewById(checkedId);
                String s3 = radioButton3.getText().toString();
                try {
                    annexureData.put("5",s3);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.rgroup4:
                RadioButton radioButton4 = (RadioButton)findViewById(checkedId);
                String s4 = radioButton4.getText().toString();
                try {
                    annexureData.put("5.1",s4);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.rgroup5:
                RadioButton radioButton5 = (RadioButton)findViewById(checkedId);
                String s5 = radioButton5.getText().toString();
                try {
                    annexureData.put("5.2",s5);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.rgroup6:
                RadioButton radioButton6 = (RadioButton)findViewById(checkedId);
                String s6 = radioButton6.getText().toString();
                try {
                    annexureData.put("5.3",s6);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.rgroup7:
                RadioButton radioButton7 = (RadioButton)findViewById(checkedId);
                String s7 = radioButton7.getText().toString();
                try {
                    annexureData.put("5.4",s7);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.rgroup8:
                RadioButton radioButton8 = (RadioButton)findViewById(checkedId);
                String s8 = radioButton8.getText().toString();
                try {
                    annexureData.put("5.5",s8);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.rgroup9:
                RadioButton radioButton9 = (RadioButton)findViewById(checkedId);
                String s9 = radioButton9.getText().toString();
                try {
                    annexureData.put("5.6",s9);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

                case R.id.rgroup10:
            RadioButton radioButton10 = (RadioButton)findViewById(checkedId);
            String s10 = radioButton10.getText().toString();
                    try {
                        annexureData.put("5.7",s10);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            case R.id.rgroup11:
                RadioButton radioButton11 = (RadioButton)findViewById(checkedId);
                String s11 = radioButton11.getText().toString();
                try {
                    annexureData.put("5.8",s11);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.rgroup12:
                RadioButton radioButton12 = (RadioButton)findViewById(checkedId);
                String s12 = radioButton12.getText().toString();
                try {
                    annexureData.put("5.9",s12);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.rgroup13:
                RadioButton radioButton13 = (RadioButton)findViewById(checkedId);
                String s13 = radioButton13.getText().toString();
                try {
                    annexureData.put("6",s13);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.rgroup14:
                RadioButton radioButton14 = (RadioButton)findViewById(checkedId);
                String s14 = radioButton14.getText().toString();
                try {
                    annexureData.put("6.1",s14);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.rgroup15:
                RadioButton radioButton15 = (RadioButton)findViewById(checkedId);
                String s15 = radioButton15.getText().toString();
                try {
                    annexureData.put("2.4",s15);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.rgroup16:
                RadioButton radioButton16 = (RadioButton)findViewById(checkedId);
                String s16 = radioButton16.getText().toString();
                try {
                    annexureData.put("2.3",s16);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;


                case R.id.rgroup17:
            RadioButton radioButton17 = (RadioButton)findViewById(checkedId);
            String s17 = radioButton17.getText().toString();
                    try {
                        annexureData.put("6.2",s17);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;


            case R.id.rgroup18:
                RadioButton radioButton18 = (RadioButton)findViewById(checkedId);
                String s18 = radioButton18.getText().toString();
                try {
                    annexureData.put("2.2",s18);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.rgroup19:
                RadioButton radioButton19 = (RadioButton)findViewById(checkedId);
                String s19 = radioButton19.getText().toString();
                try {
                    annexureData.put("2.1",s19);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.rgroup20:
                RadioButton radioButton20 = (RadioButton)findViewById(checkedId);
                String s20 = radioButton20.getText().toString();
                try {
                    annexureData.put("2",s20);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;


            case R.id.rgroup21:
                RadioButton radioButton21 = (RadioButton)findViewById(checkedId);
                String s21 = radioButton21.getText().toString();
                try {
                    annexureData.put("3.1",s21);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.rgroup22:
                RadioButton radioButton22 = (RadioButton)findViewById(checkedId);
                String s22= radioButton22.getText().toString();
                try {
                    annexureData.put("22",s22);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;




                default:
                    Toast.makeText(this, "Wrong Selection", Toast.LENGTH_SHORT).show();
                    break;
        }
    }
}
