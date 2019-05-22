package com.vipin.assessortesta.Initials;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
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
import com.vipin.assessortesta.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForgotPassword extends AppCompatActivity {
TextView emailEditText;
Button loginbutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        emailEditText=findViewById(R.id.emailEditText);
        loginbutton=findViewById(R.id.loginbutton);

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send_credentials();
            }
        });
    }

    public void send_credentials(){

        String serverURL ="https://www.skillassessment.org/sdms/android_connect1/assessor/reset_password.php";

        StringRequest stringRequest= new StringRequest(Request.Method.POST, serverURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jobj = new JSONObject(response);
                    String status= jobj.getString("status");
                    String msg= jobj.getString("msg");
                    if (status.equals("0") && msg.equals("Your are not registered user")){

                        Toast.makeText(getApplicationContext(),"This Email ID is not registered with Us",Toast.LENGTH_LONG).show();
                    }
                    else if (status.equals("1") && msg.equals("Password has been sent to your email id, please check your email")){
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(ForgotPassword.this, R.style.myDialog));

                        // set title
                        alertDialogBuilder.setTitle("Password Reset");

                        // set dialog message
                        alertDialogBuilder
                                .setMessage("Password will be sent to your registered email ID.Please go back to login page and try again.")
                                .setCancelable(false)
                                .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // if this button is clicked, close
                                        // current activity
                                        ForgotPassword.this.finish();
                                    }
                                });

                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println("response is"+response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
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
                map.put("key_salt","UmFkaWFudEluZm9uZXRTYWx0S2V5");
                map.put("email", emailEditText.getText().toString());
                return map;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyNetwork.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    }


