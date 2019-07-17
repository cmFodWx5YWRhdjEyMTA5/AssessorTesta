package com.vipin.assessortesta.Ass_Registration;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.basgeekball.awesomevalidation.ValidationHolder;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.custom.CustomErrorReset;
import com.basgeekball.awesomevalidation.utility.custom.CustomValidation;
import com.basgeekball.awesomevalidation.utility.custom.CustomValidationCallback;
import com.google.android.gms.vision.Frame;
import com.obsez.android.lib.filechooser.ChooserDialog;
import com.rx2androidnetworking.Rx2AndroidNetworking;
import com.vipin.assessortesta.Ass_Registration.db.DBAdapterClass;
import com.vipin.assessortesta.Ass_Registration.pojo.category.SscCateResponse;
import com.vipin.assessortesta.Ass_Registration.pojo.category.SscItem;
import com.vipin.assessortesta.Ass_Registration.pojo.certificate.CertificateResponse;
import com.vipin.assessortesta.Ass_Registration.pojo.certificate.JobrolesItem;
import com.vipin.assessortesta.Barcode_d.SimpleScannerActivity;
import com.vipin.assessortesta.Initials.MyNetwork;
import com.vipin.assessortesta.Initials.NetworkStateReceiver;
import com.vipin.assessortesta.Initials.SplashScreen;
import com.vipin.assessortesta.R;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.vipin.assessortesta.utils.AlertDialogPayment;
import com.vipin.assessortesta.utils.AwesomeValidation;
import com.vipin.assessortesta.utils.CommonUtils;
import com.vipin.assessortesta.utils.VerhoeffAlgorithm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AssRegActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = AssRegActivity.class.getSimpleName();
    Paint myRectPaint;
    Spinner yearofbirth,monthofbirth,dateofbirth, experience, spnSscCat, spnPayment,employment,state,district,
            spnrEmpnlmentStatus,OtherIdproof,category;
    EditText input_name,input_last_name,input_mobile_no,input_address1,input_Id_no,input_address2,input_pincode,
            input_aadhar,input_pancard,Email,alt_no,your_city,etTransactionNo;
    ListView lvSSC, lvQualification;
    LinearLayout actionUploadDoc;
    Button btnAddQualfcn, btnRemoveQualfcn, btn_Add, btn_Remove;
    int sscId = 0;

    private DBAdapterClass dbAdapterClass;
    CoordinatorLayout parentv;
    private android.app.AlertDialog progressDialog;
    CircleImageView input_photograph,input_aadharpic;
    Button input_submit,input_photograph1,input_aadharpic1;
    SwipeRefreshLayout mySwipeRefreshLayout;
    Spinner myspinner;
    private ImageView actionQrCode, ivUploadPan, ivUploadOthrId, ivUploadExp;
    private ConstraintLayout layoutOtherId;
    LinearLayout actionUploadExpDoc;
    TextView tvDocTextExp;

    List<AllCollegeNameModel.Result> result;
    String emp_statuss;
    int countAcademic=0, countQualification=0;
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
    String Stateid,Statevalue,bankid,bankvalue,districtid,districtvalue,selectedstatetext,sectorid,sectorvalue,
    employerid,employervalue,jobroleid,jobrolevalue,preflangid,preflangvalue,newString2;
    private static final int CAMERA_REQUEST = 1888;
    private static final int PAN_CAMERA_REQUEST = 1890;

    private static final int CAMERA_AADHAR_REQUEST = 1889;
    private static final int CAMERA_OTHER_ID_REQUEST = 1889;

    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int ZBAR_CAMERA_PERMISSION = 1;
    private static final int READ_REQUEST_CODE = 42;

    String yearobirth,monthobirth,dateobirth;
    AwesomeValidation awesomeValidation;
    String gender,eduction1,employer1,sector1,bankname1,state1,district1,encodedphoto,encodedphotoaadhar, encodedphotoPan,
            encodedphotoExp, encodedphotoOtherId, jobrole1,    preflang1,categoryy,disablity_type1,    type_of_disablity1,Employment_status1,OtherIdproof1;
    String bankiddd,stateiddd,districtiddd,employeridname,sectoridd,jobroleeiddd,preflangiddd;
    NetworkStateReceiver networkStateReceiver;
    ArrayAdapter<String> jobroleadapter;
    SparseArray<Face> faces;
    String cmp_id;
    CertificateResponse certResponse = null;
    private String namefromaadhaar_main;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ass_reg);

        initView();
        manageView();
        awesomeValidations();

        academicListView(countAcademic);
        addItemSSSCList();

    }

    @Override
    protected void onStart() {
        super.onStart();
        Employerlist();
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

        HashMap<String, String> map = new HashMap<>();
        Bundle extras = getIntent().getExtras();
        if (extras!=null) {
            String ss[] = extras.getStringArray("ss");
            for (String s : ss) {
                String[] sd = s.split("=");
                Log.d("dataaa", sd[0]);
                Log.d("dataaa", sd[1]);
                map.put("key", sd[0]);
                map.put("value", sd[1]);
                Toast.makeText(this, s, Toast.LENGTH_LONG).show();
                System.out.println("dataa is" + map);
            }
        }
    }

    private void initView() {
        progressDialog = new SpotsDialog(AssRegActivity.this, R.style.Custom);
        myspinner = findViewById(R.id.input_layout_gender);
        actionQrCode = findViewById(R.id.actionQrCode);
        parentv = findViewById(R.id.register_yourself);
        yearofbirth=findViewById(R.id.input_layout_year);
        category=findViewById(R.id.input_layout_category);
        monthofbirth=findViewById(R.id.input_layout_month);
        dateofbirth=findViewById(R.id.input_layout_date);
        experience=findViewById(R.id.input_layout_exp);

        spnrEmpnlmentStatus= findViewById(R.id.spnrEmpnlmentStatus);
        OtherIdproof = findViewById(R.id.otherIdproof);
        input_Id_no = findViewById(R.id.input_Id_no);
        alt_no = findViewById(R.id.input_alt_mobile_no);
        your_city = findViewById(R.id.input_city);
        spnSscCat=findViewById(R.id.spnSscCat);
        spnPayment=findViewById(R.id.spnPayment);
        etTransactionNo=findViewById(R.id.etTransactionNo);

        lvSSC=findViewById(R.id.lv_ssc);
        lvQualification=findViewById(R.id.lvQualoification);
        btnAddQualfcn=findViewById(R.id.btnAddQualfcn);
        btnRemoveQualfcn=findViewById(R.id.btnRemoveQualfcn);
        btn_Add=findViewById(R.id.btn_Add);
        btn_Remove=findViewById(R.id.btn_Remove);
        actionUploadDoc=findViewById(R.id.actionUploadDoc);


        state=findViewById(R.id.input_layout_State);
        district=findViewById(R.id.input_layout_District);
        input_photograph=findViewById(R.id.input_photograph);
        input_photograph1 = findViewById(R.id.input_photograph1);
        input_aadharpic1=findViewById(R.id.input_photograph_aadhar1);
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
        Email = findViewById(R.id.input_email);
        ivUploadPan = findViewById(R.id.ivUploadPan);

        layoutOtherId = findViewById(R.id.layoutOtherId);
        ivUploadOthrId = findViewById(R.id.ivUploadOthrId);

        actionUploadExpDoc = findViewById(R.id.actionUploadExpDoc);
        ivUploadExp = findViewById(R.id.ivUploadExp);
        tvDocTextExp = findViewById(R.id.tvDocTextExp);




        awesomeValidation=new AwesomeValidation(ValidationStyle.BASIC);
        mySwipeRefreshLayout=new SwipeRefreshLayout(getApplicationContext());
        myRectPaint = new Paint();
        myRectPaint.setStrokeWidth(1);
        myRectPaint.setColor(Color.WHITE);
        myRectPaint.setStyle(Paint.Style.STROKE);

        // DB Setup
        dbAdapterClass = new DBAdapterClass(AssRegActivity.this);
        dbAdapterClass.createDatabase();
        dbAdapterClass.open();  // --- open database connection
        dbAdapterClass.deleteAcademicTable();
        dbAdapterClass.deleteJobTable();
    }
    private void manageView(){

        actionQrCode.setOnClickListener(this::onClick);
        input_submit.setOnClickListener(this::onClick);
        btnAddQualfcn.setOnClickListener(this::onClick);
        btnRemoveQualfcn.setOnClickListener(this::onClick);
        btn_Add.setOnClickListener(this::onClick);
        btn_Remove.setOnClickListener(this::onClick);
        input_photograph.setOnClickListener(this::onClick);
        input_photograph1.setOnClickListener(this::onClick);
        input_aadharpic1.setOnClickListener(this::onClick);
        input_aadharpic.setOnClickListener(this::onClick);
        ivUploadPan.setOnClickListener(this::onClick);
        ivUploadOthrId.setOnClickListener(this::onClick);
        actionUploadExpDoc.setOnClickListener(this::onClick);




        Bankdetails();
        Statedetails();
        callApiForSscCategory();
        Employerlist();
        dropdownsSetup();
        callSscCategory();

        qualificationListView(1);

    }
    private void awesomeValidations() {
        awesomeValidation.addValidation(AssRegActivity.this, R.id.input_name,"[a-zA-Z\\s]+", R.string.err_msg_for_first_name);
        awesomeValidation.addValidation(AssRegActivity.this, R.id.input_mobile_no,"^[0-9]{10}$", R.string.err_msg_formobile);
        awesomeValidation.addValidation(AssRegActivity.this, R.id.input_email, Patterns.EMAIL_ADDRESS, R.string.err_msg_email);
        awesomeValidation.addValidation(AssRegActivity.this, R.id.etTransactionNo,"(.|\\s)*\\S(.|\\s)*", R.string.err_msg_certificate);
        awesomeValidation.addValidation(AssRegActivity.this, R.id.input_pincode,"^[0-9]{6}$", R.string.err_msg_pincode);
        awesomeValidation.addValidation(AssRegActivity.this, R.id.input_address1,"(.|\\s)*\\S(.|\\s)*", R.string.err_msg_for_address1);
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





       // select ssc details category


        awesomeValidation.addValidation(AssRegActivity.this, R.id.spnSscCat, new CustomValidation() {
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








        // select ssc details payment


        awesomeValidation.addValidation(AssRegActivity.this, R.id.spnPayment, new CustomValidation() {
            @Override
            public boolean compare(ValidationHolder validationHolder) {
                if (((Spinner) validationHolder.getView()).getSelectedItem().toString().equals("Select Payment")) {
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










        // select Enpanelment company


        awesomeValidation.addValidation(AssRegActivity.this, R.id.spnrEmpnlmentStatus, new CustomValidation() {
            @Override
            public boolean compare(ValidationHolder validationHolder) {
                if (((Spinner) validationHolder.getView()).getSelectedItem().toString().equals("Select")) {
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

    private void callSscCategory(){
        Rx2AndroidNetworking.get("https://www.skillassessment.org/sdms/android_connect1/get_ssc.php")
                .build()
                .getObjectObservable(SscCateResponse.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SscCateResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SscCateResponse sscCateResponse) {
                        System.out.println(sscCateResponse);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void dropdownsSetup(){

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
//        ArrayAdapter<String> myAdapterEmployment_status = new ArrayAdapter<String>(AssRegActivity.this,
//
//                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.Employment_status_string));
//        myAdapterEmployment_status.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        spnrEmpnlmentStatus.setEnabled(false);
//        spnrEmpnlmentStatus.setClickable(false);
//        spnrEmpnlmentStatus.setAdapter(myAdapterEmployment_status);
        spnrEmpnlmentStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id)
            {
                Employment_status1=spnrEmpnlmentStatus.getSelectedItem().toString();
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
                    layoutOtherId.setVisibility(View.GONE);
                }
                else {
                    layoutOtherId.setVisibility(View.VISIBLE);
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


        //Payment
        spnPayment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id)
            {
//                String sSpnrtext = state.getSelectedItem().toString();
//                String sStateId = Statedetail.get(sSpnrtext);
//                DistrictDetails(sStateId);

                String sSpnrtext = spnPayment.getSelectedItem().toString();
                if (!sSpnrtext.equalsIgnoreCase("Select Payment")){
                    if (sSpnrtext.equalsIgnoreCase("Yes")){
                        etTransactionNo.setVisibility(View.VISIBLE);
                    }else {
                        etTransactionNo.setVisibility(View.GONE);
                        new AlertDialogPayment().showDialog(AssRegActivity.this);
                    }
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


    }

    //API For Bank
    private void Bankdetails() {


        String serverURL = CommonUtils.url +"get_bank.php";

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
                map.put("key_salt", "UmFkaWFudEluZm9uZXRTYWx0S2V5");
                return map;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(20000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyNetwork.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    //State List
    private void Statedetails() {

        String serverURL = CommonUtils.url +"get_state.php";

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
                map.put("key_salt", "UmFkaWFudEluZm9uZXRTYWx0S2V5");
                return map;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(20000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyNetwork.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    //District_List
    private void DistrictDetails(final String districtidd) {


        String serverURL = CommonUtils.url +"get_district.php";
        show_progressbar();

        StringRequest request = new StringRequest(Request.Method.POST, serverURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),"Success"+districtlist,Toast.LENGTH_LONG).show();
                try {
                    JSONObject jobj = new JSONObject(response);
                    String status= jobj.getString("status");

                    if (status.equals("1")){

                        district.setVisibility(View.VISIBLE);
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
                map.put("key_salt", "UmFkaWFudEluZm9uZXRTYWx0S2V5");
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
        String serverURL = CommonUtils.url +"get_employer.php";

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
                map.put("key_salt", "UmFkaWFudEluZm9uZXRTYWx0S2V5");
                return map;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(20000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyNetwork.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    //Sector_list
    private void Sectorlist(final String Sectorvalue) {
        String serverURL = CommonUtils.url +"get_sector.php";


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
                map.put("key_salt", "UmFkaWFudEluZm9uZXRTYWx0S2V5");
                return map;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(20000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyNetwork.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    //Jobrole Api Call
    private void getJobroleList(final String sscid) {
        String serverURL = CommonUtils.url +"get_jobrole.php";


        StringRequest request = new StringRequest(Request.Method.POST, serverURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jobj = new JSONObject(response);
                    String status= jobj.getString("status");
                    emp_statuss=jobj.getString("emp_status");

                    ArrayAdapter myAdap = (ArrayAdapter) spnrEmpnlmentStatus.getAdapter(); //cast to an ArrayAdapter
                    int spinnerPosition = myAdap.getPosition(emp_statuss);

//set the default according to value
                    spnrEmpnlmentStatus.setSelection(spinnerPosition);

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
                map.put("key_salt", "UmFkaWFudEluZm9uZXRTYWx0S2V5");
                System.out.println("aaaaaa"+map);
                return map;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(20000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyNetwork.getInstance(getApplicationContext()).addToRequestQueue(request);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
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
            if (resultCode == 2 && requestCode == 1) {
                //do something
                HashMap<String, String> map = new HashMap<>();
                Bundle extras = data.getExtras();
                String ss[] = extras.getStringArray("ss");
                String uid_data=ss[1].replace("encoding=\"UTF-8\"?>\n<PrintLetterBarcodeData ","");
                ss[1] = uid_data;
                for (String s : ss) {
                    String[] sd = s.split("=");
                    Log.d("dataaa", sd[0]);
                    Log.d("dataaa", sd[1]);
                    map.put(sd[0], sd[1]);
                    //map.put("value", sd[1]);
                    //Toast.makeText(this,"result"+ss[0]+" "+ss[1],Toast.LENGTH_LONG).show();
                }
                System.out.println("dataa is" + map);
                System.out.println("name is" + map.get("name"));
                System.out.println("co is" + map.get("co"));
                System.out.println("gender is" + map.get("gender"));
                System.out.println("street is" + map.get("street"));
                System.out.println("dist is" + map.get("dist"));
                System.out.println("lm is" + map.get("lm"));
                System.out.println("subdist is" + map.get("subdist"));
                System.out.println("yob is" + map.get("yob"));

                if (map.get("name")!=null){
                    namefromaadhaar_main=map.get("name").replace("\"","");
                    String namee[]=namefromaadhaar_main.split(" ");
                    input_name.setEnabled(false);
                    input_last_name.setEnabled(false);
                    input_name.setText(namee[0]);
                    input_last_name.setText(namee[1]);
                }

                if (map.get("uid")!=null){
                    input_aadhar.setEnabled(false);
                    input_aadhar.setText(map.get("uid").replace("\"",""));

                }

                if(map.get("pc")!=null){
                    input_pincode.setText(map.get("pc").replace("/>","").replace("\"",""));
                    input_pincode.setEnabled(false);
                }
                if(map.get("house")!=null){
                    input_address1.setText(map.get("house").replace("\"",""));
                    input_address1.setEnabled(false);
                }
                if(map.get("lm")!=null){
                    input_address2.setText(map.get("lm").replace("\"",""));
                    input_address2.setEnabled(false);
                }
                if(map.get("subdist")!=null){
                    your_city.setText(map.get("subdist").replace("\"",""));
                    your_city.setEnabled(false);
                }
            }else{
                // Toast.makeText(this,"aaaaa",Toast.LENGTH_LONG).show();
                //do something else
            }}catch (Exception e){
            System.out.println("fffff"+e);
        }


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

                FaceDetector faceDetector = new
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
                    float y2 = y1 + thisFace.getHeight();

                    //tempCanvas.drawCircle(x1,y1,1,myRectPaint);

                    tempCanvas.drawRoundRect(new RectF(x1, y1, x2, y2), 1, 1, myRectPaint);
                }


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

            if (requestCode == PAN_CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
                ivUploadPan.setImageResource(R.drawable.checked);
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                int currentBitmapWidth = photo.getWidth();
                int currentBitmapHeight = photo.getHeight();
                mySwipeRefreshLayout.setRefreshing(false);
                int newHeight = (int) Math.floor((double) currentBitmapHeight * ((double) currentBitmapWidth / (double) currentBitmapWidth));
                Bitmap newbitMap = Bitmap.createScaledBitmap(photo, currentBitmapWidth, newHeight, true);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                newbitMap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                encodedphotoPan = Base64.encodeToString(byteArray, Base64.DEFAULT);

            }
            if (requestCode == CAMERA_OTHER_ID_REQUEST && resultCode == Activity.RESULT_OK) {
                ivUploadOthrId.setImageResource(R.drawable.checked);
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                int currentBitmapWidth = photo.getWidth();
                int currentBitmapHeight = photo.getHeight();
                mySwipeRefreshLayout.setRefreshing(false);
                int newHeight = (int) Math.floor((double) currentBitmapHeight * ((double) currentBitmapWidth / (double) currentBitmapWidth));
                Bitmap newbitMap = Bitmap.createScaledBitmap(photo, currentBitmapWidth, newHeight, true);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                newbitMap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                encodedphotoOtherId= Base64.encodeToString(byteArray, Base64.DEFAULT);

            }



        }catch (Exception e){
            e.printStackTrace();
        }

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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.actionQrCode:
                funcScanQRCode();
                break;
            case R.id.btn_Add:
                if (countAcademic <= 4) { addItemSSSCList(); }else {Snackbar.make(v, "Maximum 5 allowed", Snackbar.LENGTH_SHORT).show(); }
                break;
            case R.id.btn_Remove:
                if (countAcademic > 1) { removeItemSSSCList(); }else { Snackbar.make(v, "Minimum 1 required", Snackbar.LENGTH_SHORT).show(); }
                break;
            case R.id.btnAddQualfcn:
                if (countQualification <= 4) { addItemQualificationList(); }else {Snackbar.make(v, "Maximum 5 allowed", Snackbar.LENGTH_SHORT).show(); }
                break;
            case R.id.btnRemoveQualfcn:
                if (countQualification > 1) { removeItemQualificationList(); }else { Snackbar.make(v, "Minimum 1 required", Snackbar.LENGTH_SHORT).show(); }
                break;
            case R.id.actionUploadDoc:
                Toast.makeText(this, "Upload!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ivUploadPan:
                Toast.makeText(this, "PAN Upload!", Toast.LENGTH_SHORT).show();
                funcStartCamera(MY_CAMERA_PERMISSION_CODE, PAN_CAMERA_REQUEST);
                break;
            case R.id.btn_signup:
                funcSubmitData();
//                getSscCertList();
//                getQualificationList();
                break;
            case R.id.input_photograph:
                    funcStartCamera(MY_CAMERA_PERMISSION_CODE, CAMERA_REQUEST);
                break;
                case R.id.input_photograph1:
                    funcStartCamera(MY_CAMERA_PERMISSION_CODE, CAMERA_REQUEST);
                break;
                case R.id.input_photograph_aadhar:
                    funcStartCamera(MY_CAMERA_PERMISSION_CODE, CAMERA_AADHAR_REQUEST);
                break;
                case R.id.input_photograph_aadhar1:
                    funcStartCamera(MY_CAMERA_PERMISSION_CODE, CAMERA_AADHAR_REQUEST);
                break;
                case R.id.ivUploadOthrId:
                    funcStartCamera(MY_CAMERA_PERMISSION_CODE, CAMERA_OTHER_ID_REQUEST);
                break;
                case R.id.actionUploadExpDoc:
                    funcUploadExpDoc();
                break;



                default:
                    Toast.makeText(this, "Not a valid selection!", Toast.LENGTH_SHORT).show();
        }
    }

    private void funcUploadExpDoc() {


        String path = Environment.getExternalStorageDirectory().toString()+ File.separator + Environment.DIRECTORY_DOWNLOADS;

        new ChooserDialog().with(AssRegActivity.this)
                .withFilter(false, false, "pdf", "jpeg", "png", "jpg")
                .withStartFile(path)
                .withResources(R.string.title_choose_file, R.string.title_choose, R.string.dialog_cancel)
                .withChosenListener(new ChooserDialog.Result() {
                    @Override
                    public void onChoosePath(String path, File pathFile) {
//                                Toast.makeText(mContext, "FILE: " + path, Toast.LENGTH_SHORT).show();
//                                System.out.println(pathFile);

                        if (pathFile.length() > 0) {
                            int fileSize = Integer.parseInt(String.valueOf(pathFile.length() / 1024));

                            if (fileSize <= 500) {
                                String sPath = path.substring(path.lastIndexOf("/") + 1);
                                tvDocTextExp.setText(path);
                                tvDocTextExp.setText(sPath);
                                tvDocTextExp.setTextColor(getResources().getColor(R.color.button2));
                                ivUploadExp.setImageResource(R.drawable.ic_file_done);
                                ivUploadExp.setColorFilter(ContextCompat.getColor(AssRegActivity.this, R.color.button2), android.graphics.PorterDuff.Mode.SRC_IN);

                                encodedphotoExp = encodeFileToBase64Binary(path);

                            }else {
                                new android.app.AlertDialog.Builder(AssRegActivity.this)
                                        .setIcon(R.drawable.ic_complain)
                                        .setTitle("Warning")
                                        .setMessage("File size can't be greater than 500kb")
                                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                tvDocTextExp.setTextColor(getResources().getColor(R.color.black));
                                                tvDocTextExp.setText("Upload Document");
                                                ivUploadExp.setImageResource(R.drawable.ic_uploading_file);

                                            }
                                        })
                                        .show();
                            }
                        }
                    }
                })
                .build()
                .show();

    }

    private void funcScanQRCode() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, ZBAR_CAMERA_PERMISSION);
        } else {
            Intent intent = new Intent(this, SimpleScannerActivity.class);
            startActivityForResult(intent, ZBAR_CAMERA_PERMISSION);
        }
    }

    private boolean isFormValid(){
        return true;
    }

    private void funcStartCamera(int PERMISSION_CODE, int REQUEST_CODE){
        try {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA},
                            PERMISSION_CODE);
                } else {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, REQUEST_CODE);
                }
            } else {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, REQUEST_CODE);
            }
        }catch (Exception e){
            Log.e(TAG, "#Error :  Camera error occured", e);
        }
    }

    /*API CALL*/
    private void callApiForSscCategory(){

        Rx2AndroidNetworking.get("https://www.skillassessment.org/sdms/android_connect1/get_ssc.php")
                .build()
                .getObjectObservable(SscCateResponse.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SscCateResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SscCateResponse sscCateResponse) {

                        if(sscCateResponse.getStatus() == 1) {

                            List<String> list = new ArrayList<>();
                            for (SscItem sscItem: sscCateResponse.getSsc()){
                                list.add(sscItem.getName());
                            }
                            list.add(0, "Select Category");
                            ArrayAdapter<String> myAdapterCat = new ArrayAdapter<String>(AssRegActivity.this,
                                    android.R.layout.simple_list_item_1, list);
                            myAdapterCat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            spnSscCat.setAdapter(myAdapterCat);

                    }

                        spnSscCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view,
                                                           int position, long id)
                                {

                                    String txt = spnSscCat.getSelectedItem().toString();
                                    if (!txt.equals("Select Category")) {
                                        List<SscItem> list = sscCateResponse.getSsc();
                                        sscId = list.get(position + 1).getId();

                                        callApiForSscCertName(String.valueOf(sscId));
                                    }

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            });
                        }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void callApiForSscCertName(String id){

        Rx2AndroidNetworking.post("https://www.skillassessment.org/sdms/android_connect1/get_jobrole_sscwise.php")
                .addUrlEncodeFormBodyParameter("ssc_id", id)
                .build()
                .getObjectObservable(CertificateResponse.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CertificateResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CertificateResponse certificateResponse) {

                        if (certificateResponse.getStatus() == 1){

                            certResponse = certificateResponse;

                            countAcademic = 1;
                            academicListView(1);

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println(e);
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("Complete");
                    }
                });
    }




    /*ADD REMOVE LAYOUT-SSC Detail,s*/
    private void addItemSSSCList() {

        View parentView = null;
        String sField1 = "", sField2 = "", sField3 = "", final_year = "";

        dbAdapterClass.deleteAcademicTable();
        for (int i = 0; i < countAcademic; i++) {

            parentView = getViewByPosition(i, lvSSC);
            Spinner spnJobRole = (Spinner) parentView.findViewById(R.id.spnJobRole);
            EditText input_cert_no = (EditText) parentView.findViewById(R.id.input_cert_no);
            TextView tvDoc = (TextView) parentView.findViewById(R.id.tvDoc);

            sField1 =  spnJobRole.getSelectedItem().toString().trim();
            sField2 =  input_cert_no.getText().toString().trim();
            sField3 =  tvDoc.getText().toString().trim();
            dbAdapterClass.insertAcademicData(sField1, sField2, sField3, final_year);
        }
        countAcademic++;
        academicListView(countAcademic);

    }
    private void removeItemSSSCList() {

        View parentViewAcademic = null;
        String sField1 = "", sField2 = "", sField3 = "", final_year = "";

        countAcademic--;
        dbAdapterClass.deleteAcademicTable();
        for (int i = 0; i < countAcademic; i++) {

            parentViewAcademic = getViewByPosition(i, lvSSC);
            //   parentViewAcademic = (View) mAcademicList.getParent();
            Spinner spnJobRole = (Spinner) parentViewAcademic.findViewById(R.id.spnJobRole);
            EditText input_cert_no = (EditText) parentViewAcademic.findViewById(R.id.input_cert_no);
            TextView tvDoc = (TextView) parentViewAcademic.findViewById(R.id.tvDoc);

            sField1 =  spnJobRole.getSelectedItem().toString().trim();
            sField2 =  input_cert_no.getText().toString().trim();
            sField3 =  tvDoc.getText().toString().trim();
            dbAdapterClass.insertAcademicData(sField1, sField2, sField3, final_year);
        }
        academicListView(countAcademic);

    }
    public void academicListView(int countAcademic){
        SscListAdapter sscListAdapter = new SscListAdapter(AssRegActivity.this, countAcademic,result, certResponse);
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
    private JSONArray getSscCertList() {

        View parentViewAcademic = null;
        String sCertName = "", sCertNo = "", sPath = "";
        JSONArray jsonArray = new JSONArray();

        List<JobrolesItem> jobrolesItemList = certResponse.getJobroles();
        dbAdapterClass.deleteAcademicTable();
        for (int i = 0; i < countAcademic; i++) {

            parentViewAcademic = getViewByPosition(i, lvSSC);
            Spinner spnJobRole = (Spinner) parentViewAcademic.findViewById(R.id.spnJobRole);
            EditText input_cert_no = (EditText) parentViewAcademic.findViewById(R.id.input_cert_no);
            TextView tvDoc = (TextView) parentViewAcademic.findViewById(R.id.tvDoc);

            sCertName =  spnJobRole.getSelectedItem().toString().trim();
            sCertNo =  input_cert_no.getText().toString().trim();
            sPath =  tvDoc.getText().toString().trim();

            JSONObject jsonObject = new JSONObject();
            try {
            jsonObject.put("jobrole_id", jobrolesItemList.get(i).getId());
            jsonObject.put("certificate_number", sCertNo);
            jsonObject.put("jobrole_docs", encodeFileToBase64Binary(sPath.trim()));

            String sExt = sPath.substring(sPath.length()-4);
            jsonObject.put("doc_ext", sExt);
                jsonArray.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return jsonArray;
    }


    /*ADD REMOVE LAYOUT-Qualification*/
    private void addItemQualificationList() {

        View parentView = null;
        String sField1 = "", sField2 = "", sField3 = "", final_year = "";

        dbAdapterClass.deleteJobTable();
        for (int i = 0; i < countQualification; i++) {

            parentView = getViewByPosition(i, lvQualification);
            Spinner spnQualification = (Spinner) parentView.findViewById(R.id.spnQualification);
            TextView tvDoc = (TextView) parentView.findViewById(R.id.tvDoc);

            sField1 =  spnQualification.getSelectedItem().toString().trim();
            sField2 =  tvDoc.getText().toString().trim();
            dbAdapterClass.insertJobData(sField1, sField2, sField3, "");













        }
        countQualification++;
        qualificationListView(countQualification);

    }
    private void removeItemQualificationList() {

        View parentView = null;
        String sField1 = "", sField2 = "", sField3 = "", final_year = "";

        countQualification--;
        dbAdapterClass.deleteJobTable();
        for (int i = 0; i < countQualification; i++) {

            parentView = getViewByPosition(i, lvQualification);
            Spinner spnQualification = (Spinner) parentView.findViewById(R.id.spnQualification);
            TextView tvDoc = (TextView) parentView.findViewById(R.id.tvDoc);

            sField1 =  spnQualification.getSelectedItem().toString().trim();
            sField2 =  tvDoc.getText().toString().trim();
            dbAdapterClass.insertJobData(sField1, sField2, sField3, "");
        }
        qualificationListView(countQualification);

    }
    public void qualificationListView(int count){
        QualificationListAdapter qualificationListAdapter = new QualificationListAdapter(AssRegActivity.this, count,result);
        lvQualification.setAdapter(qualificationListAdapter);
        qualificationListAdapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(lvQualification);
    }
    private JSONArray getQualificationList() {

        View parentView = null;
        String sField1 = "", sField2 = "", sField3 = "", final_year = "";
        JSONArray jsonArray = new JSONArray();

        dbAdapterClass.deleteAcademicTable();
        for (int i = 0; i < countAcademic; i++) {

            parentView = getViewByPosition(i, lvQualification);
            Spinner spnQualification = (Spinner) parentView.findViewById(R.id.spnQualification);
            TextView tvDoc = (TextView) parentView.findViewById(R.id.tvDoc);

            sField1 =  spnQualification.getSelectedItem().toString().trim();
            sField2 =  tvDoc.getText().toString().trim();

            JSONObject jsonObject = new JSONObject();
            try {
                String sExt = sField2.substring(sField2.length()-4);
                jsonObject.put("qualification", sField1);
                jsonObject.put("image_path", encodeFileToBase64Binary(sField2.trim()));
                jsonObject.put("doc_ext", sExt);

                jsonArray.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonArray;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkStateReceiver);
    }


    private String encodeFileToBase64Binary(String path) {
        File yourFile = new File(path);
        int size = (int) yourFile.length();
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(yourFile));
            buf.read(bytes, 0, bytes.length);
            buf.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String encoded = Base64.encodeToString(bytes,Base64.NO_WRAP);
        return encoded;
    }

    private void funcSubmitData(){

        if (faces!=null && faces.size()==0){
            Snackbar.make(parentv,"Your photo is not in correct format.Click another photo.",Snackbar.LENGTH_SHORT).show();
        }

        else if (!new VerhoeffAlgorithm().validateVerhoeff(input_aadhar.getText().toString())){
            Snackbar.make(parentv,"This Aadhaar number is invalid.Please input correct aadhaar number.",Snackbar.LENGTH_SHORT).show();
        }

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
                && !yearobirth.equals("Year") && !district1.equals("Select the District")
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


            JSONObject map = new JSONObject();
            try{
                map.put("key_salt", "UmFkaWFudEluZm9uZXRTYWx0S2V5");
                map.put("mobile", input_mobile_no.getText().toString());
                map.put("image_path",encodedphoto);
                map.put("aadhar_img",encodedphotoaadhar);
                map.put("firstname", input_name.getText().toString());
                map.put("lastname", input_last_name.getText().toString());
                map.put("gender", gender);
                map.put("category", categoryy);
                map.put("email",Email.getText().toString());
                map.put("landline",alt_no.getText().toString());
                map.put("year_of_birth", yearobirth);
                map.put("dob",yearobirth+"-"+monthobirth+"-"+dateobirth);
                map.put("state_id", stateiddd);
                map.put("district_id", districtiddd);
                map.put("City",your_city.getText().toString());
                map.put("address1", input_address1.getText().toString());
                map.put("address2", input_address2.getText().toString());
                map.put("pincode", input_pincode.getText().toString());

                String sDocText = tvDocTextExp.getText().toString();
                String sExp = experience.getSelectedItem().toString().split("\\s")[0].trim();
                map.put("experience", sExp);
                map.put("experience_certificate", encodedphotoExp);
                map.put("doc_ext", sDocText.substring(sDocText.length()-4));

                map.put("ssc_id", sscId);
                map.put("payment", spnPayment.getSelectedItem().toString());
                map.put("transaction_id", etTransactionNo.getText());

                map.put("partner_id", 1);
                String otherIdText = OtherIdproof1.replace(" ", "");
                map.put("other_id_type", otherIdText);
                map.put("other_id_number",input_Id_no.getText().toString());
                map.put("other_id_img", encodedphotoOtherId);

                map.put("aadhar", input_aadhar.getText().toString());
                map.put("pan", input_pancard.getText().toString());
                map.put("pan_img", encodedphotoPan);
                System.out.println("abc");

                JSONArray jsonArray = new JSONArray();
                jsonArray.put(map);

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("registration", jsonArray);
                callApiForRegistration(jsonObject);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }else
        {
            Snackbar.make(parentv,"The form is not filled correctly.Please verify it and submit.",Snackbar.LENGTH_SHORT).show();
        }

    }


    private void callApiForRegistration(JSONObject jsonObject){
       show_progressbar();

        AndroidNetworking.post(CommonUtils.url+"save_assessor_data.php")
                .addJSONObjectBody(jsonObject)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hide_progressbar();

                        try {
                            if (response.getInt( "status") == 1){
                                submitData2();
                            }else {
                                String msg = response.getString("msg");
                                Drawable d = (Drawable)getResources().getDrawable(android.R.drawable.ic_delete);
                                new AlertDialog.Builder(AssRegActivity.this)
                                        .setIcon(d)
                                        .setTitle("Alert")
                                        .setMessage(msg)
                                        .setNegativeButton("Ok", null)
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        Toast.makeText(AssRegActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(ANError anError) {
                        hide_progressbar();
                        Toast.makeText(AssRegActivity.this, "Failed-1", Toast.LENGTH_SHORT).show();
                    }
                })
                ;
    }

    private void submitData2(){
        JSONObject map = new JSONObject();
        try{
            map.put("key_salt", "UmFkaWFudEluZm9uZXRTYWx0S2V5");
            map.put("email",Email.getText().toString());
            map.put("config_qualification", getQualificationList());

            JSONArray jsonArray = new JSONArray();
            jsonArray.put(map);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("registration", jsonArray);
            callApiForRegImage(jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void callApiForRegImage(JSONObject jsonObject){
        show_progressbar();

        AndroidNetworking.post(CommonUtils.url+"save_assessor_data_image.php")
                .addJSONObjectBody(jsonObject)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hide_progressbar();

                        try {
                            if (response.getInt("status") == 1){

                                Toast.makeText(AssRegActivity.this, "Success-2", Toast.LENGTH_SHORT).show();
                                submitData3();

                            }else {
                                String msg = response.getString("msg");
                                Drawable d = (Drawable)getResources().getDrawable(android.R.drawable.ic_delete);
                                new AlertDialog.Builder(AssRegActivity.this)
                                        .setIcon(d)
                                        .setTitle("Alert")
                                        .setMessage(msg)
                                        .setNegativeButton("Ok", null)
                                        .show();
                                submitData3();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        hide_progressbar();
                        Toast.makeText(AssRegActivity.this, "Failed-2", Toast.LENGTH_SHORT).show();
                        submitData3();
                    }
                })
        ;
    }
    private void submitData3(){
        JSONObject map = new JSONObject();
        try{
            map.put("key_salt", "UmFkaWFudEluZm9uZXRTYWx0S2V5");
            map.put("email",Email.getText().toString());
            map.put("ssc_job", getSscCertList());

            JSONArray jsonArray = new JSONArray();
            jsonArray.put(map);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("registration", jsonArray);
            callApiForRegImage3(jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void callApiForRegImage3(JSONObject jsonObject){
        show_progressbar();

        AndroidNetworking.post(CommonUtils.url+"save_assessor_data_image.php")
                .addJSONObjectBody(jsonObject)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hide_progressbar();

                        try {
                            if (response.getInt("status") == 1){

                                Drawable d = (Drawable)getResources().getDrawable(android.R.drawable.ic_menu_info_details);
                                new AlertDialog.Builder(AssRegActivity.this)
                                        .setIcon(d)
                                        .setTitle("Alert")
                                        .setMessage("Registration Succesfull.")
                                        .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                startActivity(new Intent(AssRegActivity.this, SplashScreen.class));
                                                finish();
                                                dialog.dismiss();
                                            }
                                        })
                                        .show();

                            }else {
                                String msg = response.getString("msg");
                                Drawable d = (Drawable)getResources().getDrawable(android.R.drawable.ic_delete);
                                new AlertDialog.Builder(AssRegActivity.this)
                                        .setIcon(d)
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
                        Toast.makeText(AssRegActivity.this, "Failed-3", Toast.LENGTH_SHORT).show();
                    }
                })
        ;
    }




}
