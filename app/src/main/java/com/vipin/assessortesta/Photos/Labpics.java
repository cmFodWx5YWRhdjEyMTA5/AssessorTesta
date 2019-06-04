package com.vipin.assessortesta.Photos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.vipin.assessortesta.Global.BaseActivity;
import com.vipin.assessortesta.Initials.MyNetwork;
import com.vipin.assessortesta.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class Labpics extends BaseActivity {
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    ImageView classroomlabpic;
    TextView clickmessagelab;
    Button submitbutton_lab;


    String status;
    SharedPreferences sharedpreferences;
    final String mypreference = "mypref";
    String assessor_id, batch_id;
    String encoded;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        classroomlabpic = findViewById(R.id.lab_pic);
        clickmessagelab = findViewById(R.id.clickmessagelab);
        submitbutton_lab = findViewById(R.id.nextbutton_lab);
        //click drawble right of textview
        clickmessagelab.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (event.getX() >= (clickmessagelab.getRight() - clickmessagelab.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        captureevent();
                        Toast.makeText(getApplicationContext(), "drawable clicked", Toast.LENGTH_LONG).show();
                        return true;
                    }
                }
                return false;
            }
        });

        submitbutton_lab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (classroomlabpic == null) {
                    //System.out.print("enn" +encoded);
                    Toast.makeText(getApplicationContext(), "photo   lo", Toast.LENGTH_LONG).show();
//
//                    Intent intent = new Intent();
//                    intent.putExtra("encode",j);
//                    setResult(RESULT_OK,intent);
//                    Pmkvysignane.this.finish();


                } else {

                    Sendphoto();

                    // System.out.print("enn" +encoded);
                    //Toast.makeText(getApplicationContext(),"photo  lo",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    //intent.putExtra("encode",encoded);
                    setResult(9, intent);
                    Labpics.this.finish();


                }
            }
        });


        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);


        assessor_id = (sharedpreferences.getString("user_name", ""));
        System.out.println("asessoriddd" + assessor_id);


        if (sharedpreferences.contains("ccc")) {
            batch_id = sharedpreferences.getString("ccc", "");

            System.out.println("centeridddd" + batch_id);

        }


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_labpics;
    }


   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(getMenuId(),menu);
        return true;
    }*/

    @Override
    protected int getMenuId() {
        return R.menu.main;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
                if (data.getExtras() == null || (data.getExtras().get("data") == null || !(data.getExtras().get("data") instanceof Bitmap))) {
                    //todo - show error
                    Toast.makeText(getApplicationContext(), "The file picked is invalid.Please use default camera to click Photos", Toast.LENGTH_LONG).show();
                    return;
                }
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                classroomlabpic.setImageBitmap(photo);
                submitbutton_lab.setVisibility(View.VISIBLE);
                int currentBitmapWidth = photo.getWidth();
                int currentBitmapHeight = photo.getHeight();
                int ivWidth = classroomlabpic.getWidth();
                int ivHeight = classroomlabpic.getHeight();
                int newWidth = ivWidth;
                int newHeight = (int) Math.floor((double) currentBitmapHeight * ((double) ivWidth / (double) currentBitmapWidth));
                Bitmap newbitMap = Bitmap.createScaledBitmap(photo, newWidth, newHeight, true);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void Sendphoto() {


        String serverURL = "https://www.skillassessment.org/sdms/android_connect1/assessor/save_annexure_m.php";

        System.out.println("geturll" + " " + serverURL);


        StringRequest request = new StringRequest(Request.Method.POST, serverURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jobj = new JSONObject(response);
                    status = jobj.getString("status");
                    //exam_status=jobj.getString("exam_status");
                    System.out.print("responsee" + status);
                    if (status == "1") {
                        Toast.makeText(getApplicationContext(), "sucesss1", Toast.LENGTH_SHORT).show();
                    } else {

                        System.out.print("responsee" + response);

                        Toast.makeText(getApplicationContext(), " not sucesss", Toast.LENGTH_SHORT).show();

                    }


                    System.out.print(status);

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
                map.put("assessor_id", assessor_id);
                map.put("batch_id", batch_id);
                map.put("annexure_id", "4");
                map.put("annexure_image", encoded);
                return map;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(20000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyNetwork.getInstance(getApplicationContext()).addToRequestQueue(request);
    }


}
