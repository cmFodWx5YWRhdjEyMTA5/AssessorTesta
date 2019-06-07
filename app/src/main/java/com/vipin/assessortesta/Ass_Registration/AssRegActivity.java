package com.vipin.assessortesta.Ass_Registration;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.camera2.params.Face;
import android.media.FaceDetector;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationHolder;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.custom.CustomErrorReset;
import com.basgeekball.awesomevalidation.utility.custom.CustomValidation;
import com.basgeekball.awesomevalidation.utility.custom.CustomValidationCallback;
import com.vipin.assessortesta.Ass_Registration.db.DBAdapterClass;
import com.vipin.assessortesta.Barcode_d.SimpleScannerActivity;
import com.vipin.assessortesta.Global.BaseActivity;
import com.vipin.assessortesta.Initials.MyNetwork;
import com.vipin.assessortesta.Initials.NetworkStateReceiver;
import com.vipin.assessortesta.Initials.SignIn;
import com.vipin.assessortesta.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;

public class AssRegActivity extends AppCompatActivity {
    private static final int ZBAR_CAMERA_PERMISSION = 1;
    private Class<?> mClss;
    Paint myRectPaint;
    Spinner yearofbirth,monthofbirth,dateofbirth,education, experience, spnSscCat, spnPayment,employment,state,district,
            Employment_status,OtherIdproof,category;
    EditText input_name,input_last_name,input_mobile_no,input_address1,input_Id_no
            ,input_address2,input_pincode,input_aadhar,input_pancard,Email,alt_no,your_city,other_qualification;
    String emp_statuss;
    ListView lvSSC;
    private DBAdapterClass dbAdapterClass;
    int countAcademic=0;
    List<AllCollegeNameModel.Result> result;
    Button btn_Add, btn_Remove;

    CoordinatorLayout parentv;
    private android.app.AlertDialog progressDialog;

    String[] banks,states,districts,employers,jobrole;
    List<String> bankslist,Statelist,districtlist,sectorlist,employerlist,jobrolelist,preflang;
    HashMap<String, String> bankdetail = new HashMap<>();
    HashMap<String, String> Jobrolelist = new HashMap<>();
    HashMap<String,String> Statedetail=new HashMap<>();
    HashMap<String,String> districtdetail=new HashMap<>();
    HashMap<String,String> sectordetail=new HashMap<>();
    HashMap<String,String> employerdetail =new HashMap<>();
    HashMap<String,String> employdetail =new HashMap<>();
    HashMap<String,String> langdetail =new HashMap<>();

    String[] sectors=new String[]{"Select the Sector"};
    String[] preflangg=new String[]{"Select the Preffered Language"};
    CircleImageView input_photograph,input_aadharpic;
    Button input_submit,input_photograph1,input_aadharpic1;
    String Stateid,Statevalue,bankid,bankvalue,districtid,districtvalue,selectedstatetext,sectorid,sectorvalue,
            employerid,employervalue,jobroleid,jobrolevalue,preflangid,preflangvalue,newString2;
    private static final int CAMERA_REQUEST = 1888;
    private static final int CAMERA_AADHAR_REQUEST = 1889;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    String yearobirth,monthobirth,dateobirth;
    AwesomeValidation awesomeValidation;
    String gender,eduction1,employer1,sector1,bankname1,state1,district1,encodedphoto,encodedphotoaadhar,jobrole1,
            preflang1,categoryy,disablity_type1,
            type_of_disablity1,Employment_status1,OtherIdproof1;
    String bankiddd,stateiddd,districtiddd,employeridname,sectoridd,jobroleeiddd,preflangiddd;
    NetworkStateReceiver networkStateReceiver;
    SwipeRefreshLayout mySwipeRefreshLayout;
    ArrayAdapter<String> jobroleadapter;
    SparseArray<Face> faces;
    String cmp_id;
    Spinner myspinner;
    private int requestCode;
    private String[] permissions;
    private int[] grantResults;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ass_reg);

        initView();
        awesomeValidations();

        myRectPaint = new Paint();
        myRectPaint.setStrokeWidth(1);
        myRectPaint.setColor(Color.WHITE);
        myRectPaint.setStyle(Paint.Style.STROKE);

        Bankdetails();
        Statedetails();
        Employerlist();
        banks = new String[]{"Select the Bank"};
        states=new String[]{"Select the State"};
        districts=new String[]{"Select the District"};
        employers=new String[]{"Select the Employer"};
        jobrole=new String[]{"Select the Jobrole"};
        Statelist = new ArrayList<>(Arrays.asList(states));
        bankslist = new ArrayList<>(Arrays.asList(banks));
        districtlist=new ArrayList<>(Arrays.asList(districts));
        sectorlist=new ArrayList<>(Arrays.asList(sectors));
        employerlist=new ArrayList<>(Arrays.asList(employers));
        jobrolelist=new ArrayList<>(Arrays.asList(jobrole));
        preflang=new ArrayList<>(Arrays.asList(preflangg));
        mySwipeRefreshLayout=new SwipeRefreshLayout(getApplicationContext());




        input_submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                /*if(yearobirth.equals("Year")){

                    Toast.makeText(getApplicationContext(),"Year must be selected",Toast.LENGTH_LONG).show();
                }*/
                // else
                if (faces!=null && faces.size()==0){
                    Snackbar.make(parentv,"Your photo is not in correct format.Click another photo.",Snackbar.LENGTH_SHORT).show();
                }
              /*  else if (gender.equals("Select Gender")){
                    Toast.makeText(getApplicationContext(),"Gender must be selected",Toast.LENGTH_LONG).show();
                }
                else if (categoryy.equals("Select categroy")){
                    Toast.makeText(getApplicationContext(),"categroy must be selected",Toast.LENGTH_LONG).show();
                }
                else if (state1.equals("Select the State")){
                    Toast.makeText(getApplicationContext(),"State must be selected",Toast.LENGTH_LONG).show();
                }

                else if (district1.equals("Select the District")){
                    Toast.makeText(getApplicationContext(),"District must be selected",Toast.LENGTH_LONG).show();
                }

                else if (eduction1.equals("Select Education")){
                    Toast.makeText(getApplicationContext(),"Education must be selected",Toast.LENGTH_LONG).show();
                }


                else if (employer1.equals("Select the Employer")){
                    Toast.makeText(getApplicationContext(),"Employer must be selected",Toast.LENGTH_LONG).show();
                }
                else if (bankname1.equals("Select the Bank")){
                    Toast.makeText(getApplicationContext(),"Bank  name must be selected",Toast.LENGTH_LONG).show();

                }*/
                else if (!new VerhoeffAlgorithm().validateVerhoeff(input_aadhar.getText().toString())){
                    Snackbar.make(parentv,"This Aadhaar number is invalid.Please input correct aadhaar number.",Snackbar.LENGTH_SHORT).show();
                }



                else if ((!eduction1.equals("Select Education") && eduction1.equals("Other"))&& (other_qualification.getText().toString().matches(""))){
                    Toast.makeText(getApplicationContext(),"Education must be filled",Toast.LENGTH_LONG).show();
                }
               /* else if (disablity_type1.equals("Any Disability ?")){
                    Toast.makeText(getApplicationContext(),"Disability must be Selected",Toast.LENGTH_LONG).show();
                }*/

                else if ((!disablity_type1.equals("Any Disability ?")&&disablity_type1.equals("Yes"))&& (type_of_disablity1.equals("Select Type of Disability"))){
                    Toast.makeText(getApplicationContext(),"Disablity type must be selected",Toast.LENGTH_LONG).show();
                }

//                else if ((!disablity_type1.equals("Any Disability ?")&&disablity_type1.equals("Yes"))&& (type_of_disablity.equals("Select Type of Disability"))){
//                    Toast.makeText(getApplicationContext(),"Disablity type must be selected",Toast.LENGTH_LONG).show();
//                }

                else if (!(OtherIdproof1.equals("Other Id Proof"))&& (input_Id_no.getText().toString().matches(""))){
                    Toast.makeText(getApplicationContext(),"Id  must be Filled",Toast.LENGTH_LONG).show();
                }





                else if (!state1.equals("Select the State")&&(stateiddd.equals("2") && (input_pancard.getText().toString().matches(""))) ||
                        (!state1.equals("Select the State")&&stateiddd.equals("3") && (input_pancard.getText().toString().matches(""))) ||
                        (!state1.equals("Select the State")&&stateiddd.equals("16") && (input_pancard.getText().toString().matches("")))  ||
                        (!state1.equals("Select the State")&&stateiddd.equals("17") && (input_pancard.getText().toString().matches(""))) ||
                        (!state1.equals("Select the State")&&stateiddd.equals("18") && (input_pancard.getText().toString().matches(""))) ||
                        (!state1.equals("Select the State")&&stateiddd.equals("23") && (input_pancard.getText().toString().matches("")))||
                        (!state1.equals("Select the State")&&stateiddd.equals("19") && (input_pancard.getText().toString().matches(""))) ||
                        (!state1.equals("Select the State")&&stateiddd.equals("26") && (input_pancard.getText().toString().matches("")))||
                        (!state1.equals("Select the State")&&stateiddd.equals("10") && (input_pancard.getText().toString().matches(""))) )

                {

                    Toast.makeText(AssRegActivity.this, "PAN Card cannot be empty according to your State", Toast.LENGTH_SHORT).show();
                }

                else if ((!state1.equals("Select the State")&&stateiddd.equals("1") && (input_aadhar.getText().toString().matches(""))) ||
                        (!state1.equals("Select the State")&&stateiddd.equals("4") && (input_aadhar.getText().toString().matches(""))) ||
                        (!state1.equals("Select the State")&&stateiddd.equals("5") && (input_aadhar.getText().toString().matches("")))  ||
                        (!state1.equals("Select the State")&&stateiddd.equals("6") && (input_aadhar.getText().toString().matches(""))) ||
                        (!state1.equals("Select the State")&&stateiddd.equals("7") && (input_aadhar.getText().toString().matches(""))) ||
                        (!state1.equals("Select the State")&&stateiddd.equals("8") && (input_aadhar.getText().toString().matches("")))||
                        (!state1.equals("Select the State")&&stateiddd.equals("9") && (input_aadhar.getText().toString().matches(""))) ||
                        (!state1.equals("Select the State")&&stateiddd.equals("11") && (input_aadhar.getText().toString().matches("")))||
                        (!state1.equals("Select the State")&&stateiddd.equals("12") && (input_aadhar.getText().toString().matches(""))) ||
                        (!state1.equals("Select the State")&&stateiddd.equals("13") && (input_aadhar.getText().toString().matches("")))  ||
                        (!state1.equals("Select the State")&&stateiddd.equals("14") && (input_aadhar.getText().toString().matches(""))) ||
                        (!state1.equals("Select the State")&&stateiddd.equals("15") && (input_aadhar.getText().toString().matches(""))) ||
                        (!state1.equals("Select the State")&&stateiddd.equals("19") && (input_aadhar.getText().toString().matches("")))||
                        (!state1.equals("Select the State")&&stateiddd.equals("20") && (input_aadhar.getText().toString().matches("")))||
                        (!state1.equals("Select the State")&&stateiddd.equals("21") && (input_aadhar.getText().toString().matches(""))) ||
                        (!state1.equals("Select the State")&&stateiddd.equals("22") && (input_aadhar.getText().toString().matches("")))  ||
                        (!state1.equals("Select the State")&&stateiddd.equals("24") && (input_aadhar.getText().toString().matches(""))) ||
                        (!state1.equals("Select the State")&&stateiddd.equals("25") && (input_aadhar.getText().toString().matches(""))) ||
                        (!state1.equals("Select the State")&&stateiddd.equals("27") && (input_aadhar.getText().toString().matches("")))||
                        (!state1.equals("Select the State")&&stateiddd.equals("28") && (input_aadhar.getText().toString().matches(""))) ||
                        (!state1.equals("Select the State")&&stateiddd.equals("30") && (input_aadhar.getText().toString().matches("")))  ||
                        (!state1.equals("Select the State")&&stateiddd.equals("31") && (input_aadhar.getText().toString().matches(""))) ||
                        (!state1.equals("Select the State")&&stateiddd.equals("32") && (input_aadhar.getText().toString().matches(""))) ||
                        (!state1.equals("Select the State")&&stateiddd.equals("33") && (input_aadhar.getText().toString().matches("")))  ||
                        (!state1.equals("Select the State")&&stateiddd.equals("34") && (input_aadhar.getText().toString().matches(""))))





                {

                    Toast.makeText(AssRegActivity.this, "Aadhar Card Can't be empty according to your state", Toast.LENGTH_SHORT).show();
                }





                else if(awesomeValidation.validate()
                        && !(gender.equals("Select Gender"))&& !state1.equals("Select the State")
                        && !yearobirth.equals("Year") && !district1.equals("Select the District") && !eduction1.equals("Select Education")
                        && !((eduction1.equals("Other"))&& (other_qualification.getText().toString().matches("")))
                        && !(!(OtherIdproof1.equals("Other Id Proof"))&& (input_Id_no.getText().toString().matches("")))


                        && !(stateiddd.equals("2") && (input_pancard.getText().toString().matches("")))
                        && !(stateiddd.equals("3") && (input_pancard.getText().toString().matches("")))
                        && !(stateiddd.equals("16") && (input_pancard.getText().toString().matches("")))
                        && !(stateiddd.equals("17") && (input_pancard.getText().toString().matches("")))
                        && !(stateiddd.equals("18") && (input_pancard.getText().toString().matches("")))
                        && !(stateiddd.equals("23") && (input_pancard.getText().toString().matches("")))
                        && !(stateiddd.equals("19") && (input_pancard.getText().toString().matches("")))
                        && !(stateiddd.equals("26") && (input_pancard.getText().toString().matches("")))
                        && !(stateiddd.equals("10") && (input_pancard.getText().toString().matches("")))


                        && !(stateiddd.equals("4") && (input_aadhar.getText().toString().matches("")))
                        && !(stateiddd.equals("5") && (input_aadhar.getText().toString().matches("")))
                        && !(stateiddd.equals("6") && (input_aadhar.getText().toString().matches("")))
                        && !(stateiddd.equals("7") && (input_aadhar.getText().toString().matches("")))
                        && !(stateiddd.equals("8") && (input_aadhar.getText().toString().matches("")))
                        && !(stateiddd.equals("9") && (input_aadhar.getText().toString().matches("")))
                        && !(stateiddd.equals("11") && (input_aadhar.getText().toString().matches("")))
                        && !(stateiddd.equals("12") && (input_aadhar.getText().toString().matches("")))
                        && !(stateiddd.equals("13") && (input_aadhar.getText().toString().matches("")))
                        && !(stateiddd.equals("14") && (input_aadhar.getText().toString().matches("")))
                        && !(stateiddd.equals("15") && (input_aadhar.getText().toString().matches("")))
                        && !(stateiddd.equals("19") && (input_aadhar.getText().toString().matches("")))
                        && !(stateiddd.equals("20") && (input_aadhar.getText().toString().matches("")))
                        && !(stateiddd.equals("21") && (input_aadhar.getText().toString().matches("")))
                        && !(stateiddd.equals("22") && (input_aadhar.getText().toString().matches("")))
                        && !(stateiddd.equals("24") && (input_aadhar.getText().toString().matches("")))
                        && !(stateiddd.equals("25") && (input_aadhar.getText().toString().matches("")))
                        && !(stateiddd.equals("27") && (input_aadhar.getText().toString().matches("")))
                        && !(stateiddd.equals("28") && (input_aadhar.getText().toString().matches("")))
                        && !(stateiddd.equals("29") && (input_aadhar.getText().toString().matches("")))
                        && !(stateiddd.equals("30") && (input_aadhar.getText().toString().matches("")))
                        && !(stateiddd.equals("31") && (input_aadhar.getText().toString().matches("")))
                        && !(stateiddd.equals("32") && (input_aadhar.getText().toString().matches("")))
                        && !(stateiddd.equals("33") && (input_aadhar.getText().toString().matches("")))
                        && !(stateiddd.equals("34") && (input_aadhar.getText().toString().matches("")))
                        ) {

//                    Intent ii = new Intent(AssRegActivity.this, Reverify.class);
//                    ii.putExtra("first_namee", input_name.getText().toString());
//                    ii.putExtra("last_namee", input_last_name.getText().toString());
//                    ii.putExtra("mobile", input_mobile_no.getText().toString());
//                    ii.putExtra("aadhar", input_aadhar.getText().toString());
//                    ii.putExtra("pancard", input_pancard.getText().toString());
//                    ii.putExtra("bankaccount", input_bank_ac.getText().toString());
//                    ii.putExtra("doy", yearobirth);
//                    ii.putExtra("dom",monthobirth);
//                    ii.putExtra("dod",dateobirth);
//                    ii.putExtra("gender", gender);
//                    ii.putExtra("bank", bankiddd);
//                    ii.putExtra("state", stateiddd);
//                    ii.putExtra("district", districtiddd);
//                    ii.putExtra("education", eduction1);
//                    //ii.putExtra("employed", employment1);
//                    ii.putExtra("employer", employeridname);
//                    ii.putExtra("sector", sectoridd);
//                    ii.putExtra("addline1", input_address1.getText().toString());
//                    ii.putExtra("addline2", input_address2.getText().toString());
//                    ii.putExtra("pincode", input_pincode.getText().toString());
//                    ii.putExtra("nameasinbank", input_bank_username.getText().toString());
//                    ii.putExtra("ifsccode", input_ifsc_code.getText().toString());
//                    ii.putExtra("jobrole",jobroleeiddd);
//                    ii.putExtra("empid",input_empid.getText().toString());
//                    ii.putExtra("location",input_loc.getText().toString());
//                    ii.putExtra("preflang",preflangiddd);
//                    ii.putExtra("pic",encodedphoto);
//                    ii.putExtra("picaadhar",encodedphotoaadhar);
//                    ii.putExtra("Email",Email.getText().toString());
//                    ii.putExtra("categroy", categoryy);
//                    ii.putExtra("alt_no",alt_no.getText().toString());
//                    ii.putExtra("your_city",your_city.getText().toString());
//                    ii.putExtra("other_qualification",other_qualification.getText().toString());
//                    ii.putExtra("input_id_no",input_Id_no.getText().toString());
//                    //  ii.putExtra("Any_disability",disablity_type1);
//                    ii.putExtra("type_of_disblity",type_of_disablity1);
//                    ii.putExtra("Any_disability",disablity_type1);
//                    ii.putExtra("other_Id_proof_type",OtherIdproof1);
//                    ii.putExtra("Employment_status",Employment_status1);
//
//
//
//
//                    startActivity(ii);

                }else
                {

                    //Toast.makeText(getApplicationContext(), "The form is not filled correctly.Please verify it and submit.", Toast.LENGTH_LONG).show();

                    Snackbar.make(parentv,"The form is not filled correctly.Please verify it and submit.",Snackbar.LENGTH_SHORT).show();
                }

            }
        });

        try{


            input_photograph.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                        if (checkSelfPermission(Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            requestPermissions(new String[]{Manifest.permission.CAMERA},
                                    MY_CAMERA_PERMISSION_CODE);
                        } else {



                        }
                    }
                    else {
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    }



                }
            });}

        catch (Exception e){
            e.printStackTrace();
        }


        try{


            input_photograph1.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                        if (checkSelfPermission(Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            requestPermissions(new String[]{Manifest.permission.CAMERA},
                                    MY_CAMERA_PERMISSION_CODE);
                        } else {

                            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(cameraIntent, CAMERA_REQUEST);

                        }
                    }else {
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    }

                }
            });
        }

        catch (Exception e){
            e.printStackTrace();
        }



        input_aadharpic1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                launchActivity(SimpleScannerActivity.class);
               /* if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA},
                                MY_CAMERA_PERMISSION_CODE);
                    } else {
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_AADHAR_REQUEST);
                    }
                }else {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_AADHAR_REQUEST);
                }*/

            }
        });

        input_aadharpic.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA},
                                MY_CAMERA_PERMISSION_CODE);
                    } else {
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_AADHAR_REQUEST);
                    }
                }else {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_AADHAR_REQUEST);
                }
            }
        });




        //Gender
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(AssRegActivity.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.gender));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        myspinner.setAdapter(myAdapter);

        myspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id)
            {
                gender=myspinner.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }


        });




        ArrayAdapter<String> myAdapter_type_of_disablity = new ArrayAdapter<String>(AssRegActivity.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.type_of_Disablity));
        myAdapter_type_of_disablity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Employment _status


        ArrayAdapter<String> myAdapterEmployment_status = new ArrayAdapter<String>(AssRegActivity.this,

                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.Employment_status_string));
        myAdapterEmployment_status.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Employment_status.setEnabled(false);
        Employment_status.setClickable(false);





        Employment_status.setAdapter(myAdapterEmployment_status);

        Employment_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id)
            {
                Employment_status1=Employment_status.getSelectedItem().toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }


        });




        // Other_id_Details


        ArrayAdapter<String> myAdapterOtherIdproof = new ArrayAdapter<String>(AssRegActivity.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.other_id));
        myAdapterOtherIdproof.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        OtherIdproof.setAdapter(myAdapterOtherIdproof);

        OtherIdproof.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id)
            {
                OtherIdproof1=OtherIdproof.getSelectedItem().toString();

                if (OtherIdproof1.equals("Other Id Proof")){
                    input_Id_no.setVisibility(View.GONE);
                }
                else {
                    input_Id_no.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }


        });

        //Year of birth

        ArrayAdapter<String> myAdapter1 = new ArrayAdapter<String>(AssRegActivity.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.Year));
        myAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        yearofbirth.setAdapter(myAdapter1);

        yearofbirth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id)
            {
                yearobirth=yearofbirth.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }


        });

        //Month of birth

        ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(AssRegActivity.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.Month));
        myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        monthofbirth.setAdapter(myAdapter2);

        monthofbirth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id)
            {
                monthobirth=monthofbirth.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }


        });

        //Date of birth

        ArrayAdapter<String> myAdapter3 = new ArrayAdapter<String>(AssRegActivity.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.Date));
        myAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        dateofbirth.setAdapter(myAdapter3);

        dateofbirth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id)
            {
                dateobirth=dateofbirth.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }


        });

        //Education
        ArrayAdapter<String> myAdapter4 = new ArrayAdapter<String>(AssRegActivity.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.Education));
        myAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        education.setAdapter(myAdapter4);

        education.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id)
            {
                eduction1=education.getSelectedItem().toString();

                if (eduction1.equals("Other")){
                    other_qualification.setVisibility(View.VISIBLE);
                }
                else {
                    other_qualification.setVisibility(View.GONE);
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }


        });

        //Experience
        ArrayAdapter<String> myAdapter5 = new ArrayAdapter<String>(AssRegActivity.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.Experience));
        myAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        experience.setAdapter(myAdapter5);

        experience.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id)
            {
//                eduction1=experience.getSelectedItem().toString();
//
//                if (eduction1.equals("Other")){
//                    other_qualification.setVisibility(View.VISIBLE);
//                }
//                else {
//                    other_qualification.setVisibility(View.GONE);
//                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }


        });

        //jobrole

        //Choose category
        ArrayAdapter<String> categoryadapt = new ArrayAdapter<String>(AssRegActivity.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.Category));
        categoryadapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        category.setAdapter(categoryadapt);

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id)
            {
                // if(position > 0) {
                categoryy = category.getSelectedItem().toString();
                //jobroleeiddd=Jobrolelist.get(jobrole1);
                // }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }


        });


        //Preffered Exam Language

        //Bankname

        ArrayAdapter<String> myAdapterBankname = new ArrayAdapter<String>(AssRegActivity.this,
                android.R.layout.simple_list_item_1,bankslist);
        myAdapterBankname.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //state

        ArrayAdapter<String> myAdapterState = new ArrayAdapter<String>(AssRegActivity.this,
                android.R.layout.simple_list_item_1,Statelist);
        myAdapterState.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        state.setAdapter(myAdapterState);

        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id)
            {
                state1=state.getSelectedItem().toString();
                selectedstatetext =(String) parent.getItemAtPosition(position);

                if(position > 0){
                    String value= Statedetail.get(selectedstatetext);
                    stateiddd=value;
                    DistrictDetails(value);
                    district.setVisibility(View.VISIBLE);


                }
                else {
                    district.setVisibility(View.GONE);
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }


        });


        //District

      /*  if (districtlist.size()>1){
            districtlist.clear();
        }
        districtlist.add("Select the District");*/
        district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id)
            {
                district1=district.getSelectedItem().toString();
                districtiddd=districtdetail.get(district1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        // Data clear
        dbAdapterClass = new DBAdapterClass(AssRegActivity.this);
        dbAdapterClass.createDatabase();
        dbAdapterClass.open();  // --- open database connection
        dbAdapterClass.deleteAcademicTable();
        dbAdapterClass.deleteJobTable();

        academicListView(countAcademic);
        addItemSSSCList();

        btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (countAcademic <= 3) {
                    addItemSSSCList();
                }
            }
        });
        btn_Remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (countAcademic > 0) {
                    removeItemSSSCList();
                }
            }
        });


    }

    public void launchActivity(Class<?> clss) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            mClss = clss;
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, ZBAR_CAMERA_PERMISSION);
        } else {
            Intent intent = new Intent(this, clss);
            startActivity(intent);
        }
    }




    private void initView() {
        progressDialog = new SpotsDialog(AssRegActivity.this, R.style.Custom);
        myspinner = findViewById(R.id.input_layout_gender);
        parentv = findViewById(R.id.register_yourself);
        yearofbirth=findViewById(R.id.input_layout_year);
        category=findViewById(R.id.input_layout_category);
        monthofbirth=findViewById(R.id.input_layout_month);
        dateofbirth=findViewById(R.id.input_layout_date);
        education=findViewById(R.id.input_layout_Education);
        experience=findViewById(R.id.input_layout_exp);

        Employment_status= findViewById(R.id.employment_status);
        OtherIdproof = findViewById(R.id.otherIdproof);
        input_Id_no = findViewById(R.id.input_Id_no);
        alt_no = findViewById(R.id.input_alt_mobile_no);
        your_city = findViewById(R.id.input_city);
        other_qualification = findViewById(R.id.input_Eduction_other);
        spnSscCat=findViewById(R.id.spnSscCat);
        spnPayment=findViewById(R.id.spnPayment);
        lvSSC=findViewById(R.id.lv_ssc);
        btn_Add=findViewById(R.id.btn_Add);
        btn_Remove=findViewById(R.id.btn_Remove);


        state=findViewById(R.id.input_layout_State);
        district=findViewById(R.id.input_layout_District);
        input_photograph1 = findViewById(R.id.input_photograph1);
        input_aadharpic1=findViewById(R.id.input_photograph_aadhar1);
        input_photograph=findViewById(R.id.input_photograph);
        input_aadharpic=findViewById(R.id.input_photograph_aadhar);
        input_submit=findViewById(R.id.btn_signup);
        input_name=findViewById(R.id.input_name);
        input_last_name=findViewById(R.id.input_last_name);
        input_mobile_no=findViewById(R.id.input_mobile_no);
        input_address1=findViewById(R.id.input_address1);
        input_address2=findViewById(R.id.input_address2);
        input_pincode=findViewById(R.id.input_pincode);
        input_aadhar=findViewById(R.id.input_aadhar);
        input_pancard = findViewById(R.id.input_pancard);
        awesomeValidation=new AwesomeValidation(ValidationStyle.BASIC);
        Email = findViewById(R.id.input_email);
    }
    private void manageView(){

    }
    private void awesomeValidations() {
        awesomeValidation.addValidation(AssRegActivity.this, R.id.input_name,"[a-zA-Z\\s]+", R.string.err_msg_for_first_name);
        awesomeValidation.addValidation(AssRegActivity.this, R.id.input_last_name,"[a-zA-Z\\s]+", R.string.err_msg_for_last_name);
        awesomeValidation.addValidation(AssRegActivity.this, R.id.input_address1,"(.|\\s)*\\S(.|\\s)*", R.string.err_msg_for_address1);
        awesomeValidation.addValidation(AssRegActivity.this, R.id.input_pincode,"^[0-9]{6}$", R.string.err_msg_pincode);
        awesomeValidation.addValidation(AssRegActivity.this, R.id.input_mobile_no,"^[0-9]{10}$", R.string.err_msg_formobile);

        // awesomeValidation.addValidation(AssRegActivity.this, R.id.input_email, Patterns.EMAIL_ADDRESS, R.string.err_msg_email);

        cmp_id = getIntent().getStringExtra("cmp_id");
        System.out.println("the id of company is"+cmp_id);



        //awesome validation for year
        awesomeValidation.addValidation(AssRegActivity.this, R.id.input_layout_year, new CustomValidation() {
            @Override
            public boolean compare(ValidationHolder validationHolder) {
                if (((Spinner) validationHolder.getView()).getSelectedItem().toString().equals("Year")) {
                    return false;
                } else {
                    return true;
                }
            }
        }, new CustomValidationCallback() {
            @Override
            public void execute(ValidationHolder validationHolder) {
                TextView textViewError = (TextView) ((Spinner) validationHolder.getView()).getSelectedView();
                textViewError.setError(validationHolder.getErrMsg());
                textViewError.setTextColor(Color.RED);
            }
        }, new CustomErrorReset() {
            @Override
            public void reset(ValidationHolder validationHolder) {
                TextView textViewError = (TextView) ((Spinner) validationHolder.getView()).getSelectedView();
                textViewError.setError(null);
                textViewError.setTextColor(Color.BLACK);
            }
        }, R.string.err_tech_stacks);

        //awesome validation for gender
        awesomeValidation.addValidation(AssRegActivity.this, R.id.input_layout_gender, new CustomValidation() {
            @Override
            public boolean compare(ValidationHolder validationHolder) {
                if (((Spinner) validationHolder.getView()).getSelectedItem().toString().equals("Select Gender")) {
                    return false;
                } else {
                    return true;
                }
            }
        }, new CustomValidationCallback() {
            @Override
            public void execute(ValidationHolder validationHolder) {
                TextView textViewError = (TextView) ((Spinner) validationHolder.getView()).getSelectedView();
                textViewError.setError(validationHolder.getErrMsg());
                textViewError.setTextColor(Color.RED);
            }
        }, new CustomErrorReset() {
            @Override
            public void reset(ValidationHolder validationHolder) {
                TextView textViewError = (TextView) ((Spinner) validationHolder.getView()).getSelectedView();
                textViewError.setError(null);
                textViewError.setTextColor(Color.BLACK);
            }
        }, R.string.err_tech_stacks);


        //awesome validation for category
        awesomeValidation.addValidation(AssRegActivity.this, R.id.input_layout_category, new CustomValidation() {
            @Override
            public boolean compare(ValidationHolder validationHolder) {
                if (((Spinner) validationHolder.getView()).getSelectedItem().toString().equals("Select Category")) {
                    return false;
                } else {
                    return true;
                }
            }
        }, new CustomValidationCallback() {
            @Override
            public void execute(ValidationHolder validationHolder) {
                TextView textViewError = (TextView) ((Spinner) validationHolder.getView()).getSelectedView();
                textViewError.setError(validationHolder.getErrMsg());
                textViewError.setTextColor(Color.RED);
            }
        }, new CustomErrorReset() {
            @Override
            public void reset(ValidationHolder validationHolder) {
                TextView textViewError = (TextView) ((Spinner) validationHolder.getView()).getSelectedView();
                textViewError.setError(null);
                textViewError.setTextColor(Color.BLACK);
            }
        }, R.string.err_tech_stacks);

        //awesome validation for state
        awesomeValidation.addValidation(AssRegActivity.this, R.id.input_layout_State, new CustomValidation() {
            @Override
            public boolean compare(ValidationHolder validationHolder) {
                if (((Spinner) validationHolder.getView()).getSelectedItem().toString().equals("Select the State")) {
                    return false;
                } else {
                    return true;
                }
            }
        }, new CustomValidationCallback() {
            @Override
            public void execute(ValidationHolder validationHolder) {
                TextView textViewError = (TextView) ((Spinner) validationHolder.getView()).getSelectedView();
                textViewError.setError(validationHolder.getErrMsg());
                textViewError.setTextColor(Color.RED);
            }
        }, new CustomErrorReset() {
            @Override
            public void reset(ValidationHolder validationHolder) {
                TextView textViewError = (TextView) ((Spinner) validationHolder.getView()).getSelectedView();
                textViewError.setError(null);
                textViewError.setTextColor(Color.BLACK);
            }
        }, R.string.err_tech_stacks);

        //awesome validation for district
        awesomeValidation.addValidation(AssRegActivity.this, R.id.input_layout_District, new CustomValidation() {
            @Override
            public boolean compare(ValidationHolder validationHolder) {
                if (((Spinner) validationHolder.getView()).getSelectedItem().toString().equals("Select the District")) {
                    return false;
                } else {
                    return true;
                }
            }
        }, new CustomValidationCallback() {
            @Override
            public void execute(ValidationHolder validationHolder) {
                TextView textViewError = (TextView) ((Spinner) validationHolder.getView()).getSelectedView();
                textViewError.setError(validationHolder.getErrMsg());
                textViewError.setTextColor(Color.RED);
            }
        }, new CustomErrorReset() {
            @Override
            public void reset(ValidationHolder validationHolder) {
                TextView textViewError = (TextView) ((Spinner) validationHolder.getView()).getSelectedView();
                textViewError.setError(null);
                textViewError.setTextColor(Color.BLACK);
            }
        }, R.string.err_tech_stacks);


        //awesome validation for education
        awesomeValidation.addValidation(AssRegActivity.this, R.id.input_layout_Education, new CustomValidation() {
            @Override
            public boolean compare(ValidationHolder validationHolder) {
                if (((Spinner) validationHolder.getView()).getSelectedItem().toString().equals("Select Education")) {
                    return false;
                } else {
                    return true;
                }
            }
        }, new CustomValidationCallback() {
            @Override
            public void execute(ValidationHolder validationHolder) {
                TextView textViewError = (TextView) ((Spinner) validationHolder.getView()).getSelectedView();
                textViewError.setError(validationHolder.getErrMsg());
                textViewError.setTextColor(Color.RED);
            }
        }, new CustomErrorReset() {
            @Override
            public void reset(ValidationHolder validationHolder) {
                TextView textViewError = (TextView) ((Spinner) validationHolder.getView()).getSelectedView();
                textViewError.setError(null);
                textViewError.setTextColor(Color.BLACK);
            }
        }, R.string.err_tech_stacks);

        //awesome validation for exp
        awesomeValidation.addValidation(AssRegActivity.this, R.id.input_layout_exp, new CustomValidation() {
            @Override
            public boolean compare(ValidationHolder validationHolder) {
                if (((Spinner) validationHolder.getView()).getSelectedItem().toString().equals("Select Experience")) {
                    return false;
                } else {
                    return true;
                }
            }
        }, new CustomValidationCallback() {
            @Override
            public void execute(ValidationHolder validationHolder) {
                TextView textViewError = (TextView) ((Spinner) validationHolder.getView()).getSelectedView();
                textViewError.setError(validationHolder.getErrMsg());
                textViewError.setTextColor(Color.RED);
            }
        }, new CustomErrorReset() {
            @Override
            public void reset(ValidationHolder validationHolder) {
                TextView textViewError = (TextView) ((Spinner) validationHolder.getView()).getSelectedView();
                textViewError.setError(null);
                textViewError.setTextColor(Color.BLACK);
            }
        }, R.string.err_tech_stacks);

    }


    private void addItemSSSCList() {

        View parentViewAcademic = null;
        String qualification = "", college_name = "", joining_year = "", final_year = "";

        dbAdapterClass.deleteAcademicTable();
        for (int i = 0; i < countAcademic; i++) {

            parentViewAcademic = getViewByPosition(i, lvSSC);
            Spinner spnJobRole = (Spinner) parentViewAcademic.findViewById(R.id.spnJobRole);
            EditText input_cert_no = (EditText) parentViewAcademic.findViewById(R.id.input_cert_no);

            joining_year =  spnJobRole.getSelectedItem().toString().trim();
            final_year =  input_cert_no.getText().toString().trim();
            dbAdapterClass.insertAcademicData(qualification, college_name, joining_year, final_year);
        }
        countAcademic++;
        academicListView(countAcademic);

    }
    private void removeItemSSSCList() {

        View parentViewAcademic = null;
        String qualification = "", college_name = "", joining_year = "", final_year = "";

        countAcademic--;
        dbAdapterClass.deleteAcademicTable();
        for (int i = 0; i < countAcademic; i++) {

            parentViewAcademic = getViewByPosition(i, lvSSC);
            //   parentViewAcademic = (View) mAcademicList.getParent();
            Spinner spnJobRole = (Spinner) parentViewAcademic.findViewById(R.id.spnJobRole);
            EditText input_cert_no = (EditText) parentViewAcademic.findViewById(R.id.input_cert_no);

            joining_year =  spnJobRole.getSelectedItem().toString().trim();
            final_year =  input_cert_no.getText().toString().trim();

            dbAdapterClass.insertAcademicData(qualification, college_name, joining_year, final_year);
        }
        academicListView(countAcademic);

    }

    public void academicListView(int countAcademic){
        SscListAdapter sscListAdapter = new SscListAdapter(AssRegActivity.this, countAcademic,result);
        lvSSC.setAdapter(sscListAdapter);
        sscListAdapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(lvSSC);
    }

    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition
                + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }



    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


    @Override
    protected void onResume() {
        super.onResume();
        networkStateReceiver= new NetworkStateReceiver(new NetworkStateReceiver.NetworkListener() {
            @Override
            public void onNetworkAvailable() {
                input_submit.setEnabled(true);
            }

            @Override
            public void onNetworkUnavailable() {
                input_submit.setEnabled(false);
                Snackbar.make(parentv,"Internet Not available",Snackbar.LENGTH_SHORT).show();
            }
        });
        registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    //API For Bank
    private void Bankdetails() {


        String serverURL = "https://www.skillassessment.org/sdms/android_connect/get_bank.php";

        StringRequest request = new StringRequest(Request.Method.POST, serverURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jobj = new JSONObject(response);

                    String status= jobj.getString("status");
                    if (status.equals("1")){
                        JSONArray jsonArray=jobj.getJSONArray("bank");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject c = jsonArray.getJSONObject(i);
                            bankid = c.getString("id");
                            bankvalue = c.getString("name");
                            bankdetail.put(bankvalue, bankid);
                            bankslist.add(bankvalue);

                        }
                        // Toast.makeText(getApplicationContext(),"Success"+bankslist,Toast.LENGTH_LONG).show();

                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Failed to fetch Bank Details",Toast.LENGTH_LONG).show();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Failed to fetch Bank Details", Toast.LENGTH_LONG).show();
            }
        })

        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                super.getHeaders();
                Map<String, String> map = new HashMap<>();
                return map;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                Map<String, String> map = new HashMap<>();
                map.put("Content-Type", "application/x-www-form-urlencoded");

                return map;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(20000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyNetwork.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    //Language Api Call
    private void languageSelect(final String cmp_id) {

        show_progressbar();
        String serverURL = "https://www.skillassessment.org/sdms/android_connect/get_language.php";

        StringRequest request = new StringRequest(Request.Method.POST, serverURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    System.out.println("languages in reg page are"+response);
                    JSONObject jobj = new JSONObject(response);

                    String status= jobj.getString("status");

                    if (status.equals("1")){
                        JSONArray jsonArray=jobj.getJSONArray("language");
                        preflang.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject c = jsonArray.getJSONObject(i);
                            preflangid = c.getString("language_code");
                            preflangvalue = c.getString("name");
                            langdetail.put(preflangvalue,preflangid );
                            preflang.add(preflangvalue);
                        }
                        ArrayAdapter<String> preflanguage = new ArrayAdapter<String>(AssRegActivity.this,
                                android.R.layout.simple_list_item_1,preflang);
                        preflanguage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Failed to fetch Language Details",Toast.LENGTH_LONG).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                hide_progressbar();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hide_progressbar();
                Toast.makeText(getApplicationContext(), "Failed to fetch Language Details", Toast.LENGTH_LONG).show();
            }
        })

        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                super.getHeaders();
                Map<String, String> map = new HashMap<>();
                return map;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                Map<String, String> map = new HashMap<>();
                map.put("Content-Type", "application/x-www-form-urlencoded");
                map.put("company_id",cmp_id);
                return map;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(20000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyNetwork.getInstance(getApplicationContext()).addToRequestQueue(request);
    }


    //State List
    private void Statedetails() {


        String serverURL = "https://www.skillassessment.org/sdms/android_connect/get_state.php";


        StringRequest request = new StringRequest(Request.Method.POST, serverURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jobj = new JSONObject(response);

                    String status= jobj.getString("status");
                    if (status.equals("1")){
                        JSONArray jsonArray=jobj.getJSONArray("state");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject c = jsonArray.getJSONObject(i);
                            Stateid = c.getString("id");
                            Statevalue = c.getString("name");
                            Statedetail.put(Statevalue, Stateid);
                            Statelist.add(Statevalue);
                        }
                        //Toast.makeText(getApplicationContext(),"Success"+bankslist,Toast.LENGTH_LONG).show();

                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Failed to fetch States",Toast.LENGTH_LONG).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
               /* if (pd.isShowing()) {
                    pd.dismiss();
                }*/

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //   pd.dismiss();
                Toast.makeText(getApplicationContext(), "Failed to fetch State list", Toast.LENGTH_LONG).show();
            }
        })

        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                super.getHeaders();
                Map<String, String> map = new HashMap<>();
                return map;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                Map<String, String> map = new HashMap<>();
                map.put("Content-Type", "application/x-www-form-urlencoded");

                return map;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(20000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyNetwork.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    //District_List
    private void DistrictDetails(final String districtidd) {


        String serverURL = "https://www.skillassessment.org/sdms/android_connect/get_district.php";
        show_progressbar();

        StringRequest request = new StringRequest(Request.Method.POST, serverURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),"Success"+districtlist,Toast.LENGTH_LONG).show();
                try {
                    JSONObject jobj = new JSONObject(response);
                    String status= jobj.getString("status");

                    if (status.equals("1")){
                        districtlist.clear();
                        JSONArray jsonArray=jobj.getJSONArray("district");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject c = jsonArray.getJSONObject(i);
                            districtid = c.getString("id");
                            districtvalue = c.getString("name");
                            districtdetail.put(districtvalue, districtid);
                            districtlist.add(districtvalue);


                        }
                        ArrayAdapter<String> myAdapterDistrict = new ArrayAdapter<String>(AssRegActivity.this,
                                android.R.layout.simple_list_item_1,districtlist);
                        myAdapterDistrict.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        district.setAdapter(myAdapterDistrict);
                        //Toast.makeText(getApplicationContext(),"Success"+districtlist,Toast.LENGTH_LONG).show();

                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Failed to fetch Districts",Toast.LENGTH_LONG).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                hide_progressbar();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hide_progressbar();
                Toast.makeText(getApplicationContext(), "Failed to fetch Districts", Toast.LENGTH_LONG).show();
            }
        })

        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                super.getHeaders();
                Map<String, String> map = new HashMap<>();
                return map;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                Map<String, String> map = new HashMap<>();
                map.put("Content-Type", "application/x-www-form-urlencoded");
                map.put("state_id",districtidd);
                return map;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(20000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyNetwork.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    //Employer_List
    private void Employerlist() {
       /* pd = new ProgressDialog(AssRegActivity.this);
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        pd.show();
*/
        String serverURL = "https://www.skillassessment.org/sdms/android_connect/get_employer.php";

        StringRequest request = new StringRequest(Request.Method.POST, serverURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jobj = new JSONObject(response);

                    String status= jobj.getString("status");
                    if (status.equals("1")){
                        JSONArray jsonArray=jobj.getJSONArray("employer");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject c = jsonArray.getJSONObject(i);
                            employerid = c.getString("id");
                            employervalue = c.getString("name");
                            employdetail.put(employervalue, employerid);
                            employerlist.add(employervalue);
                        }

                        for (int i=0;i<=employerlist.size()-1;i++){
                            System.out.println("employere"+employerid);
                            System.out.println("employereeee"+employerlist.get(i));

                            if (employerlist.get(i).equals(newString2)){

                            }
                        }



                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Failed to fetch Employers",Toast.LENGTH_LONG).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
               /* if (pd.isShowing()) {
                }*/

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //pd.dismiss();
            }
        })

        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                super.getHeaders();
                Map<String, String> map = new HashMap<>();
                return map;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                Map<String, String> map = new HashMap<>();
                map.put("Content-Type", "application/x-www-form-urlencoded");

                return map;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(20000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyNetwork.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    //Sector_list
    private void Sectorlist(final String Sectorvalue) {
        String serverURL = "https://www.skillassessment.org/sdms/android_connect/get_sector.php";


        StringRequest request = new StringRequest(Request.Method.POST, serverURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jobj = new JSONObject(response);
                    String status= jobj.getString("status");
                    if (sectorlist.size()>2){
                        sectorlist.clear();
                    }
                    sectorlist.add("Choose the Sector");
                    if (status.equals("1")){
                        JSONArray jsonArray=jobj.getJSONArray("sector");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject c = jsonArray.getJSONObject(i);
                            sectorid = c.getString("id");
                            sectorvalue = c.getString("name");
                            sectordetail.put(sectorvalue, sectorid);
                            sectorlist.add(sectorvalue);
                        }

                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Failed to fetch Sector Details",Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Failed to fetch Sectors List", Toast.LENGTH_LONG).show();
            }
        })

        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                super.getHeaders();
                Map<String, String> map = new HashMap<>();
                return map;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                Map<String, String> map = new HashMap<>();
                map.put("Content-Type", "application/x-www-form-urlencoded");
                map.put("company_id",Sectorvalue);
                return map;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(20000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyNetwork.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    //Jobrole Api Call
    private void getJobroleList(final String sscid) {
        String serverURL = "https://www.skillassessment.org/sdms/android_connect/get_jobrole.php";


        StringRequest request = new StringRequest(Request.Method.POST, serverURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jobj = new JSONObject(response);
                    String status= jobj.getString("status");
                    emp_statuss=jobj.getString("emp_status");

                    ArrayAdapter myAdap = (ArrayAdapter) Employment_status.getAdapter(); //cast to an ArrayAdapter
                    int spinnerPosition = myAdap.getPosition(emp_statuss);

//set the default according to value
                    Employment_status.setSelection(spinnerPosition);

                    /*if (jobrolelist.size()<=1){
                        jobrolelist.clear();
                    }*/
                    if (status.equals("1")){
                        JSONArray jsonArray=jobj.getJSONArray("jobrole");
                        jobrolelist.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject c = jsonArray.getJSONObject(i);
                            jobroleid = c.getString("jobrole_id");
                            jobrolevalue = c.getString("name");
                            Jobrolelist.put(jobrolevalue, jobroleid);
                            jobrolelist.add(jobrolevalue);
                        }

                        jobroleadapter= new ArrayAdapter<String>(AssRegActivity.this,
                                android.R.layout.simple_list_item_1,jobrolelist);
                        jobroleadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Failed to fetch Job Roles",Toast.LENGTH_LONG).show();
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Failed to fetch Job Roles", Toast.LENGTH_LONG).show();
            }
        })

        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                super.getHeaders();
                Map<String, String> map = new HashMap<>();
                return map;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                Map<String, String> map = new HashMap<>();
                map.put("Content-Type", "application/x-www-form-urlencoded");
                map.put("company_id",sscid);
                System.out.println("aaaaaa"+map);
                return map;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(20000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyNetwork.getInstance(getApplicationContext()).addToRequestQueue(request);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        this.requestCode = requestCode;
        this.permissions = permissions;
        this.grantResults = grantResults;
        switch (requestCode) {
            case ZBAR_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(mClss != null) {
                        Intent intent = new Intent(this, mClss);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(this, "Please grant camera permission to use the QR Scanner", Toast.LENGTH_SHORT).show();
                }
                return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();

                Intent cameraIntent = new
                        Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                // cameraIntent.setPackage(defaultCameraPackage);
                if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                    // startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                } else {
                    Toast.makeText(getApplicationContext(),"Click photos using default Camera Of the Device",Toast.LENGTH_LONG).show();
                }

            }
        }
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

                int currentBitmapWidth = photo.getWidth();
                int currentBitmapHeight = photo.getHeight();
                //input_photograph.setImageBitmap(photo);
                int newHeight = (int) Math.floor((double) currentBitmapHeight * ((double) currentBitmapWidth / (double) currentBitmapWidth));
                Bitmap newbitMap = Bitmap.createScaledBitmap(photo, currentBitmapWidth, newHeight, true);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                newbitMap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                encodedphoto = Base64.encodeToString(byteArray, Base64.DEFAULT);

                Bitmap tempBitmap = Bitmap.createBitmap(photo.getWidth(), photo.getHeight(), Bitmap.Config.RGB_565);
                Canvas tempCanvas = new Canvas(tempBitmap);
                tempCanvas.drawBitmap(photo, 0, 0, null);

               /* FaceDetector faceDetector = new
                        FaceDetector.Builder(getApplicationContext()).setTrackingEnabled(true)
                        .build();
                if(!faceDetector.isOperational()){
                    new AlertDialog.Builder(getApplicationContext()).setMessage("Could not set up the face detector!").show();
                    return;
                }

                Frame frame = new Frame.Builder().setBitmap(photo).build();
                faces = faceDetector.detect(frame);
                System.out.println("iiii"+faces.get(1));



                for(int i=0; i<faces.size(); i++) {
                    Face thisFace = faces.valueAt(i);
                    Float x1=new Float(0);
                    x1 = thisFace.getPosition().x;
                    float y1 = thisFace.getPosition().y;
                    float x2 = x1 + thisFace.getWidth();
                    float y2 = y1 + thisFace.getHeight();*/

                    //tempCanvas.drawCircle(x1,y1,1,myRectPaint);

                    //tempCanvas.drawRoundRect(new RectF(x1, y1, x2, y2), 1, 1, myRectPaint);
               // }


                input_photograph.setImageDrawable(new BitmapDrawable(getResources(),tempBitmap));
            }








            if (requestCode == CAMERA_AADHAR_REQUEST && resultCode == Activity.RESULT_OK) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                int currentBitmapWidth = photo.getWidth();
                int currentBitmapHeight = photo.getHeight();
                mySwipeRefreshLayout.setRefreshing(false);
                input_aadharpic.setImageBitmap(photo);
                int newHeight = (int) Math.floor((double) currentBitmapHeight * ((double) currentBitmapWidth / (double) currentBitmapWidth));
                Bitmap newbitMap = Bitmap.createScaledBitmap(photo, currentBitmapWidth, newHeight, true);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                newbitMap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                encodedphotoaadhar = Base64.encodeToString(byteArray, Base64.DEFAULT);

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkStateReceiver);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Employerlist();
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
