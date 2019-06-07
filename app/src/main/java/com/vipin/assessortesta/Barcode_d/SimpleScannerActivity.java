package com.vipin.assessortesta.Barcode_d;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import com.vipin.assessortesta.R;

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

    @Override
    public void handleResult(Result rawResult) {
        Toast.makeText(this, "Contents = " + rawResult.getContents() +
                ", Format = " + rawResult.getBarcodeFormat().getName(), Toast.LENGTH_SHORT).show();

        String[] uid=rawResult.getContents().split("uid");
        String[] name=rawResult.getContents().split("name");
        String[] gender=rawResult.getContents().split("gender");
        String[] yob=rawResult.getContents().split("yob");
        String[] co=rawResult.getContents().split("co");
        String[] house=rawResult.getContents().split("house");
        String[] street=rawResult.getContents().split("street");
        String[] vtc=rawResult.getContents().split("vtc");
        String[] dist=rawResult.getContents().split("dist");
        String[] state=rawResult.getContents().split("state");
        String[] pc=rawResult.getContents().split("pc");
        //System.out.println("uid is"+uid[0]+"name  "+name[0]);
        Log.d("cccccc",name[0]);
        // Note:
        // * Wait 2 seconds to resume the preview.
        // * On older devices continuously stopping and resuming camera preview can result in freezing the app.
        // * I don't know why this is the case but I don't have the time to figure out.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mScannerView.resumeCameraPreview(SimpleScannerActivity.this);
            }
        }, 2000);
    }
}
