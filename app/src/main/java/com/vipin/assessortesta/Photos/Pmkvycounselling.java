package com.vipin.assessortesta.Photos;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vipin.assessortesta.Global.BaseActivity;
import com.vipin.assessortesta.R;

import java.io.ByteArrayOutputStream;

public class Pmkvycounselling extends BaseActivity {
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    ImageView pmkvycounseling;
    TextView clickmessage_pmkvycounseling;
    Button   submit__pmkvycounseling;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pmkvycounseling=findViewById(R.id.pmkvycounselling);
        clickmessage_pmkvycounseling=findViewById(R.id.clickpmkvycounselling);
        submit__pmkvycounseling=findViewById(R.id.nextbutton_pmkvycounselling);
        //click drawble right of textview
        clickmessage_pmkvycounseling.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    if(event.getX() >= (clickmessage_pmkvycounseling.getRight() - clickmessage_pmkvycounseling.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        captureevent();
                        Toast.makeText(getApplicationContext(),"drawable clicked",Toast.LENGTH_LONG).show();
                        return true;
                    }
                }
                return false;
            }
        });


        submit__pmkvycounseling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pmkvycounseling==null){
                   // System.out.print("enn" +encoded);
                   // Toast.makeText(getApplicationContext(),"photo   lo",Toast.LENGTH_LONG).show();
//
//                    Intent intent = new Intent();
//                    intent.putExtra("encode",j);
//                    setResult(RESULT_OK,intent);
//                    Pmkvysignane.this.finish();



                }
                else {

                    //System.out.print("enn" +encoded);
                   // Toast.makeText(getApplicationContext(),"photo  lo",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    //intent.putExtra("encode",encoded);
                    setResult(2,intent);
                    Pmkvycounselling.this.finish();



                }
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pmkvycounselling;
    }


    /*@Override
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
                if(data.getExtras()==null || (data.getExtras().get("data")==null ||  !(data.getExtras().get("data") instanceof Bitmap))){
                    //todo - show error
                    Toast.makeText(getApplicationContext(),"The file picked is invalid.Please use default camera to click Photos",Toast.LENGTH_LONG).show();
                    return;
                }
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                pmkvycounseling.setImageBitmap(photo);
                submit__pmkvycounseling.setVisibility(View.VISIBLE);
                int currentBitmapWidth = photo.getWidth();
                int currentBitmapHeight = photo.getHeight();
                int ivWidth = pmkvycounseling.getWidth();
                int ivHeight = pmkvycounseling.getHeight();
                int newWidth = ivWidth;
                int newHeight = (int) Math.floor((double) currentBitmapHeight *( (double) ivWidth / (double) currentBitmapWidth));
                Bitmap newbitMap = Bitmap.createScaledBitmap(photo, newWidth, newHeight, true);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream .toByteArray();
                String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
