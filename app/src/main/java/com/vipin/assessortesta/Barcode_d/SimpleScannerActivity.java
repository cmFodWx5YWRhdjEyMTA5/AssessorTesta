package com.vipin.assessortesta.Barcode_d;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.text.Html;
import android.util.Log;
import android.util.Xml;
import android.view.ViewGroup;
import android.widget.Toast;

import com.vipin.assessortesta.Ass_Registration.AssRegActivity;
import com.vipin.assessortesta.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class SimpleScannerActivity extends BaseScannerActivity implements ZBarScannerView.ResultHandler {
    private ZBarScannerView mScannerView;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_simple_scanner);
        setupToolbar();
        ViewGroup contentFrame = (ViewGroup) findViewById(R.id.content_frame);
        mScannerView = new ZBarScannerView(this);
        contentFrame.addView(mScannerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void handleResult(Result rawResult) {
        String str=rawResult.getContents();
        String[] ss=str.split("\" ");
        //Toast.makeText(this, "dataa is" + ss[0]+"  "+ss[1]+"  "+ss[2]+" "+ss[3], Toast.LENGTH_LONG).show();
        if (ss!=null) {
            Intent intent = new Intent();
            intent.putExtra("ss", ss);
            setResult(2, intent); //Here 2 result code
            //startActivity(intent);
            finish();
        }



        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mScannerView.resumeCameraPreview(SimpleScannerActivity.this);
            }
        }, 2000);
    }


}
