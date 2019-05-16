package com.vipin.assessortesta.Initials;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.vipin.assessortesta.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class SignIn extends AppCompatActivity {

    TextView forgotpassword;
    Button login;
    EditText username,password;
    String uname,pass;
    String status;
    SessionManager sessionManager;
    String id,name;
    SharedPreferences sharedpreferences;
    String encode,decode;
    final String mypreference = "mypref";
    private android.app.AlertDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        username = findViewById(R.id.emailEditText);
        password = findViewById(R.id.passwordEditText);

        encode = Base64.encodeToString("RadiantInfonetSaltKey".getBytes(),Base64.DEFAULT);
        System.out.print("encoded" +encode);
        System.out.print("decode" +decode);
        sessionManager = new SessionManager();
        sharedpreferences=getSharedPreferences(mypreference, Context.MODE_PRIVATE);


        forgotpassword = findViewById(R.id.forgotpassword);
        login = findViewById(R.id.loginbutton);
        progressDialog = new SpotsDialog(SignIn.this, R.style.Custom);






        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(SignIn.this,ForgotPassword.class);
                startActivity(j);
            }
        });




        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (username.getText().toString().equals("")|password.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(),"The required fields Username and password can't be empty", Toast.LENGTH_LONG).show();
                }
                else
                {
                    sendDataServer();
                }

//
//                    Intent k = new Intent(SignIn.this,AssessorTask.class);
//                startActivity(k);
            }
        });




    }


    private void sendDataServer() {

        progressDialog.show();


        String serverURL ="https://www.skillassessment.org/sdms/android_connect1/assessor/login.php";
        System.out.println("geturll"+" "+serverURL);
        uname=username.getText().toString();
        pass= password.getText().toString();

        StringRequest request = new StringRequest(Request.Method.POST, serverURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jobj = new JSONObject(response);
                    status= jobj.getString("status");
                    //exam_status=jobj.getString("exam_status");

                    if (status.equals("1")) {


                            JSONObject jsonObject = jobj.getJSONObject("assessor_details");
                            for (int i = 0; i < jsonObject.length(); i++) {

                                name = jsonObject.getString("name");
                                id =jsonObject.getString("id");
                                System.out.print("name-" +name);

                                sessionManager.setPreferences(getApplicationContext(), "status", "1");
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString("Name", name);
                                editor.apply();

                                Intent z = new Intent(SignIn.this,AssessorTask.class);
                                startActivity(z);
                                finish();

                            }

                    }

                    else if (status.equals("0")){
                        Toast.makeText(getApplicationContext(),"Wrong Credentials.",Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Unable to Login",Toast.LENGTH_LONG).show();
                    }
                }

                catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error: Please try again Later1", Toast.LENGTH_LONG).show();
                }
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Toast.makeText(getApplicationContext(), "Error: Please try again Later2"+error, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                super.getHeaders();
                Map<String, String> map = new HashMap<>();
                map.put("Content-Type","application/x-www-form-urlencoded");
                return map;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                Map<String, String> map = new HashMap<>();
                map.put("key_salt",encode);
                map.put("user_name", uname);
                map.put("password", pass);
                return map;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(20000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyNetwork.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }






}
