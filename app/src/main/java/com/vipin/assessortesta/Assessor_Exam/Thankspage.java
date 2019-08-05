package com.vipin.assessortesta.Assessor_Exam;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vipin.assessortesta.Initials.SignIn;
import com.vipin.assessortesta.Initials.SplashScreen;
import com.vipin.assessortesta.R;


public class Thankspage extends AppCompatActivity {


    Button ratebutton;
    TextView tvvv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thankspage);
        ratebutton =  findViewById(R.id.RateButton);
        tvvv=findViewById(R.id.skipTextView1);

        ratebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.radiant.rpl.testa");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });
        tvvv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii=new Intent(Thankspage.this, SplashScreen.class);
                startActivity(ii);
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //exitByBackKey();

            moveTaskToBack(true);

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }


}
