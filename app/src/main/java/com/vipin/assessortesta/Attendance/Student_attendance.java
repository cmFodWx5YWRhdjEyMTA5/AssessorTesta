package com.vipin.assessortesta.Attendance;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.vipin.assessortesta.Batch_Student.Batch_instruction;
import com.vipin.assessortesta.Batch_Student.Students_list;
import com.vipin.assessortesta.Global.BaseActivity;
import com.vipin.assessortesta.Group_Photo_Activity.Group_Photo_Instructor_Activity;
import com.vipin.assessortesta.Initials.MyNetwork;
import com.vipin.assessortesta.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;

public class Student_attendance extends BaseActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {


    LinearLayout uploadphotoo, uploadid, currentlocation, tclocation;

    TextView centrelocation, currentlocationn;
    private FusedLocationProviderClient fusedLocationClient;
    List<Address> addresses;
    Geocoder geocoder;
    private static final int CAMERA_REQUEST = 1888;
    private static final int CAMERA_AADHAR_REQUEST = 1889;
    CircleImageView input_photograph, input_photograph2;
    String centeridd;
    GoogleApiClient mGoogleApiClient;
    private Location location;
    private GoogleApiClient googleApiClient;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private LocationRequest locationRequest;
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissions = new ArrayList<>();
    private static final int ALL_PERMISSIONS_RESULT = 1011;
    private static final long UPDATE_INTERVAL = 5000,
            FASTEST_INTERVAL = 5000; // = 5 seconds
    String currloc;
    String traningcentreloc, photoself, photoidproof;

    SharedPreferences sharedpreferences;
    final String mypreferences ="mypref";
    String studentid,batchid,student_name,Batch_name,Assessment_Date,Tc_name;


    TextView studentname,Batchnameee,AssessmentDatee,Tcnamee;

    double lat2 = 28.5953926, lat1;
    double lng2 = 77.1757398, lng1;


    LinearLayout uploadphotoo_student, uploadid_student, currentlocation_student, tclocation_student;
    CircleImageView input_photograph_student, input_photograph2_student;
    TextView input_photograph22_student, input_photograph1_student;
    Button atten_student;
    TextView centrelocation_student, currentlocationn_student;
    private android.app.AlertDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        studentid = getIntent().getStringExtra("student_id");
        student_name = getIntent().getStringExtra("name");
        Tc_name = getIntent().getStringExtra("tc_name");
        Toast.makeText(this, ""+studentid, Toast.LENGTH_LONG).show();




        uploadphotoo_student = findViewById(R.id.uploadphoto1_student);
        input_photograph2_student = findViewById(R.id.input_photograph2_student);
        input_photograph1_student = findViewById(R.id.input_photograph1_student);
        input_photograph22_student = findViewById(R.id.input_photograph22_student);
        uploadid_student = findViewById(R.id.inputidproof_student);
        currentlocation_student = findViewById(R.id.currentlocation_student);
        tclocation_student = findViewById(R.id.tclocation_student);
        atten_student = findViewById(R.id.markattendance_student);
        centrelocation_student = findViewById(R.id.centrelocationn_student);
        currentlocationn_student = findViewById(R.id.locationn_student);
        input_photograph_student = findViewById(R.id.input_photograph_student);
        progressDialog = new SpotsDialog(Student_attendance.this, R.style.Custom);



        studentname = findViewById(R.id.studentname);
        Batchnameee = findViewById(R.id.Batchnameee);
        AssessmentDatee = findViewById(R.id.AssessmentDatee);
        Tcnamee = findViewById(R.id.Tcnamee);


        studentname.setText(student_name);
        //Toast.makeText(this, ""+student_name, Toast.LENGTH_LONG).show();
        Tcnamee.setText(Tc_name);
        //Toast.makeText(this, ""+Tc_name, Toast.LENGTH_LONG).show();




        sharedpreferences = getSharedPreferences(mypreferences, Context.MODE_PRIVATE);

        input_photograph2_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureevent1();
            }
        });

        input_photograph1_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureevent();
            }
        });

        input_photograph22_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureevent1();
            }
        });

        input_photograph_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureevent();
            }
        });






        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rgroup_student);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                RadioButton rb = (RadioButton) findViewById(checkedId);
                if (rb.getText().equals("Yes")) {
                    System.out.println("yes clicked");
                    uploadphotoo_student.setVisibility(View.VISIBLE);
                    uploadid_student.setVisibility(View.VISIBLE);
                    currentlocation_student.setVisibility(View.VISIBLE);
                    tclocation_student.setVisibility(View.VISIBLE);
                    atten_student.setVisibility(View.VISIBLE);
                } else {
                    System.out.println("No clicked");
                    AbsentAlertBox();
                    uploadphotoo_student.setVisibility(View.GONE);
                    uploadid_student.setVisibility(View.GONE);
                    currentlocation_student.setVisibility(View.GONE);
                    tclocation_student.setVisibility(View.GONE);
                    atten_student.setVisibility(View.GONE);
                }

            }


        });




        if (sharedpreferences.contains("batch_id")) {
            batchid = sharedpreferences.getString("batch_id", "");
            System.out.println("asessoriddd" + batchid);

        }

        System.out.println("asessoriddd" + student_name);



        if (sharedpreferences.contains("batch_name")) {
            Batch_name = sharedpreferences.getString("batch_name", "");
            Batchnameee.setText(Batch_name);

            System.out.println("asessoriddd" + Batch_name);

        }
        if (sharedpreferences.contains("exam_date")) {
            Assessment_Date = sharedpreferences.getString("exam_date", "");
            AssessmentDatee.setText(Assessment_Date);

            System.out.println("asessoriddd" + Assessment_Date);

        }























        atten_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (distance(lat1, lng1, lat2, lng2) < 0.1) { // if distance < 0.1 miles we take locations as equal
                    //do what you want to do...
                    System.out.println("distance between these points is" + distance(lat1, lng1, lat2, lng2));
                } else {
                    System.out.println("distance between these points is" + distance(lat1, lng1, lat2, lng2));
                }
                if (photoself == null) {

                    Toast.makeText(getApplicationContext(), "Photo mandotary", Toast.LENGTH_LONG).show();

                } else if (photoidproof == null) {

                    Toast.makeText(getApplicationContext(), "Photo mandotary", Toast.LENGTH_LONG).show();
                } else {


                    save_studentAtten();
                }

            }
        });


    }







    @Override
    protected int getMenuId() {
        return R.menu.main;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                startActivity(new Intent(this, Students_list.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }





    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, Batch_instruction.class));
        finish();
    }






    @Override
    protected int getLayoutId() {
        return R.layout.activity_student_attendance;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {

            if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
                if(data.getExtras()==null || (data.getExtras().get("data")==null ||  !(data.getExtras().get("data") instanceof Bitmap))){
                    //todo - show error
                    Toast.makeText(getApplicationContext(),"The file picked is invalid.Please use default camera to click Photos",Toast.LENGTH_LONG).show();
                    return;
                }
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                input_photograph_student.setImageBitmap(photo);
                //submit_pmkvybooklet.setVisibility(View.VISIBLE);
                int currentBitmapWidth = photo.getWidth();
                int currentBitmapHeight = photo.getHeight();
                int ivWidth = input_photograph_student.getWidth();
                int ivHeight = input_photograph_student.getHeight();
                int newWidth = ivWidth;
                int newHeight = (int) Math.floor((double) currentBitmapHeight *( (double) ivWidth / (double) currentBitmapWidth));
                Bitmap newbitMap = Bitmap.createScaledBitmap(photo, newWidth, newHeight, true);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream .toByteArray();
                photoself = Base64.encodeToString(byteArray, Base64.DEFAULT);
            }

            else   if (requestCode == CAMERA_AADHAR_REQUEST && resultCode == Activity.RESULT_OK) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                int currentBitmapWidth = photo.getWidth();
                int currentBitmapHeight = photo.getHeight();
                //mySwipeRefreshLayout.setRefreshing(false);
                input_photograph2_student.setImageBitmap(photo);
                int newHeight = (int) Math.floor((double) currentBitmapHeight * ((double) currentBitmapWidth / (double) currentBitmapWidth));
                Bitmap newbitMap = Bitmap.createScaledBitmap(photo, currentBitmapWidth, newHeight, true);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                newbitMap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                photoidproof = Base64.encodeToString(byteArray, Base64.DEFAULT);

            }



        }


        catch (Exception e){
            e.printStackTrace();
        }
    }






    @Override
    protected void onStart() {
        super.onStart();

//Get current location
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);

        permissionsToRequest = permissionsToRequest(permissions);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0) {
                requestPermissions(permissionsToRequest.toArray(
                        new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
            }
        }

        // we build google api client
        googleApiClient = new GoogleApiClient.Builder(this).
                addApi(LocationServices.API).
                addConnectionCallbacks(this).
                addOnConnectionFailedListener(this).build();
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    private ArrayList<String> permissionsToRequest(ArrayList<String> wantedPermissions) {
        ArrayList<String> result = new ArrayList<>();

        for (String perm : wantedPermissions) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }



    private boolean hasPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
        }

        return true;
    }
    @Override
    protected void onResume() {
        super.onResume();
        getcenteraddress();
        if (googleApiClient!=null){
            buildGoogleApiClient();
        }
        if (!checkPlayServices()) {
            Toast.makeText(this,"You need to install Google Play Services to use the App properly",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        // stop location updates
        if (googleApiClient != null  &&  googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, (com.google.android.gms.location.LocationListener) this);
            googleApiClient.disconnect();
        }
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST);
            } else {
                finish();
            }

            return false;
        }

        return true;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Geocoder geocoder;
        geocoder = new Geocoder(Student_attendance.this);
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                &&  ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        // Permissions ok, we get last location
        location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

        if (location != null) {
            try {

                addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                lat1=location.getLatitude();
                lng1=location.getLongitude();
                if (addresses != null && addresses.size() > 0) {
                    String address = addresses.get(0).getAddressLine(0);
                    String city = addresses.get(0).getLocality();
                    String streett = addresses.get(0).getSubLocality();
                    String country = addresses.get(0).getCountryName();
                    currloc= address + "," + streett + "," + city + "," + country;
                    System.out.println("current location is"+currloc);
                    currentlocationn_student.setText(currloc);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            buildAlertMessageNoGps();
        }
        startLocationUpdates();
    }

    private void startLocationUpdates() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(50000);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                &&  ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "You need to enable permissions to display location !", Toast.LENGTH_SHORT).show();
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {

        }
    }

    private void buildAlertMessageNoGps() {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.i("TAG", "All location settings are satisfied.");
                        startLocationUpdates();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.i("TAG", "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(Student_attendance.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            Log.i("TAG", "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.i("TAG", "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });
    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

    }



    //Address of the centre
    public void getcenteraddress(){

        String serverURL ="https://www.skillassessment.org/sdms/android_connect1/assessor/get_exam_center_details.php";

        StringRequest stringRequest= new StringRequest(Request.Method.POST, serverURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Toast.makeText(getApplicationContext(),"Center location is"+response,Toast.LENGTH_LONG).show();
                    JSONObject jobj = new JSONObject(response);
                    String status= jobj.getString("status");
                    String msg= jobj.getString("msg");
                    if (status.equals("0")){
                        Toast.makeText(getApplicationContext(),"This Email ID is not registered with Us",Toast.LENGTH_LONG).show();
                    }
                    else if (status.equals("1")){
                        JSONObject jobb=jobj.getJSONObject("exam_center_details");
                        traningcentreloc =jobb.getString("address");
                    }
                    centrelocation_student.setText(traningcentreloc);
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
                map.put("exam_center_id", centeridd);
                return map;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyNetwork.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }









    //Save assessor attendance

    public void save_studentAtten(){

        progressDialog.show();

        String serverURL ="https://www.skillassessment.org/sdms/android_connect1/assessor/save_student_attendance.php";

        StringRequest stringRequest= new StringRequest(Request.Method.POST, serverURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Toast.makeText(getApplicationContext(),"Center location is"+response,Toast.LENGTH_LONG).show();
                    JSONObject jobj = new JSONObject(response);
                    String status= jobj.getString("status");
                    String msg= jobj.getString("msg");
                    if (status.equals("0")){
                        Toast.makeText(getApplicationContext(),"response"+msg,Toast.LENGTH_LONG).show();
                    }
                    else if (status.equals("1")){
                        Intent ii = new Intent(Student_attendance.this, Students_list.class);
                        ii.putExtra("persent","persent");

                        startActivity(ii);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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
                map.put("student_id",studentid);
                map.put("batch_id",batchid);
                map.put("student_image", photoself);
                map.put("id_image", photoidproof);
                map.put("attendance", "Present");

                map.put("location", currentlocationn_student.getText().toString());


                return map;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyNetwork.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }










    //Save assessor attendance

    public void save_studentAttenAbsent(){

        progressDialog.show();

        String serverURL ="https://www.skillassessment.org/sdms/android_connect1/assessor/save_student_attendance.php";

        StringRequest stringRequest= new StringRequest(Request.Method.POST, serverURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Toast.makeText(getApplicationContext(),"Center location is"+response,Toast.LENGTH_LONG).show();
                    JSONObject jobj = new JSONObject(response);
                    String status= jobj.getString("status");
                    String msg= jobj.getString("msg");
                    if (status.equals("0")){
                        Toast.makeText(getApplicationContext(),"response"+msg,Toast.LENGTH_LONG).show();
                    }
                    else if (status.equals("1")){
                        Intent ii = new Intent(Student_attendance.this, Students_list.class);
                        ii.putExtra("Absent","Absent");
                        startActivity(ii);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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
                map.put("student_id",studentid);
                map.put("batch_id",batchid);
                map.put("attendance", "Absent");
                map.put("location", currentlocationn_student.getText().toString());


                return map;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyNetwork.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

































    /** calculates the distance between two locations in MILES */
    private double distance(double lat1, double lng1, double lat2, double lng2) {

        double earthRadius = 3958.75; // in miles, change to 6371 for kilometer output

        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);

        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);

        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        double dist = earthRadius * c;
        Toast.makeText(getApplicationContext(),"distance is"+dist,Toast.LENGTH_LONG).show();
        return dist; // output distance, in MILES
    }





















protected void AbsentAlertBox() {

        AlertDialog alertbox = new AlertDialog.Builder(this)
                .setMessage("Are You Sure You Want To Mark This Student As Absent?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                      //  moveTaskToBack(true);
                        save_studentAttenAbsent();

                        Intent intent = new Intent(Student_attendance.this, Students_list.class);
                        startActivity(intent);

                        //finish();

                        //close();


                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .show();

    }



}

