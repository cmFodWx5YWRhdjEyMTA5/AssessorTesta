package com.vipin.assessortesta.Attendance;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.vipin.assessortesta.Global.BaseActivity;
import com.vipin.assessortesta.Photos.Infrapic;
import com.vipin.assessortesta.Photos.Photo_navigation;
import com.vipin.assessortesta.Photos.Pmkvysignane;
import com.vipin.assessortesta.Photos.photo_navigator;
import com.vipin.assessortesta.R;

import java.io.IOException;
import java.util.List;

public class Assessor_Atten extends BaseActivity {
    LinearLayout uploadphotoo, uploadid, currentlocation, tclocation;
    Button loginbutton;
    TextView centrelocation, currentlocationn;
    private FusedLocationProviderClient fusedLocationClient;
    List<Address> addresses;
    Geocoder geocoder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        uploadphotoo = findViewById(R.id.uploadphoto1);
        uploadid = findViewById(R.id.inputidproof);
        currentlocation = findViewById(R.id.currentlocation);
        tclocation = findViewById(R.id.tclocation);
        loginbutton = findViewById(R.id.loginbutton);
        centrelocation = findViewById(R.id.centrelocationn);
        currentlocationn = findViewById(R.id.locationn);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(Assessor_Atten.this);
        centrelocation.setMovementMethod(new ScrollingMovementMethod());
        currentlocationn.setMovementMethod(new ScrollingMovementMethod());


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        geocoder = new Geocoder(Assessor_Atten.this);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        /*location.getLatitude();
                        location.getLongitude();*/
                        try {

                            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                            if (addresses != null && addresses.size() > 0) {
                                String address = addresses.get(0).getAddressLine(0);
                                String city = addresses.get(0).getLocality();
                                String streett = addresses.get(0).getSubLocality();
                                String country = addresses.get(0).getCountryName();
                               String  aaa = address + "," + streett + "," + city + "," + country;
                                System.out.println("location is"+aaa);

                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                        }
                    }
                });

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rgroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                RadioButton rb=(RadioButton)findViewById(checkedId);
                if (rb.getText().equals("Yes")){
                    System.out.println("yes clicked");
                    uploadphotoo.setVisibility(View.VISIBLE);
                    uploadid.setVisibility(View.VISIBLE);
                    currentlocation.setVisibility(View.VISIBLE);
                    tclocation.setVisibility(View.VISIBLE);
                    loginbutton.setVisibility(View.VISIBLE);
                }else {
                    System.out.println("No clicked");
                    attendancealert();
                    uploadphotoo.setVisibility(View.GONE);
                    uploadid.setVisibility(View.GONE);
                    currentlocation.setVisibility(View.GONE);
                    tclocation.setVisibility(View.GONE);
                    loginbutton.setVisibility(View.GONE);
                }

            }
        });

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii=new Intent(Assessor_Atten.this, Photo_navigation.class);
                startActivity(ii);
            }
        });



    }

    @Override
    protected int getMenuId() {
        return R.menu.main;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_assessor__atten;
    }

}
