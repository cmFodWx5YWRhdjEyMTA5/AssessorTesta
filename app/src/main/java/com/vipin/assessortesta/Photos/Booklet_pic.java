package com.vipin.assessortesta.Photos;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vipin.assessortesta.Global.BaseActivity;
import com.vipin.assessortesta.R;

import java.io.ByteArrayOutputStream;

public class Booklet_pic extends BaseActivity {
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    ImageView pmkvybooklet;
    TextView clickmessage_pmkvybooklet;
    Button submit_pmkvybooklet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pmkvybooklet=findViewById(R.id.pmkvybooklet);
        clickmessage_pmkvybooklet=findViewById(R.id.clickpmkvybooklet);
        submit_pmkvybooklet =findViewById(R.id.nextbutton_pmkvybooklet);


        //click drawble right of textview
        clickmessage_pmkvybooklet.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    if(event.getX() >= (clickmessage_pmkvybooklet.getRight() - clickmessage_pmkvybooklet.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        captureevent();
                        Toast.makeText(getApplicationContext(),"drawable clicked",Toast.LENGTH_LONG).show();
                        return true;
                    }
                }
                return false;
            }
        });


        submit_pmkvybooklet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pmkvybooklet==null){
                    //System.out.print("enn" +encoded);
                    Toast.makeText(getApplicationContext(),"photo   lo",Toast.LENGTH_LONG).show();
//
//                    Intent intent = new Intent();
//                    intent.putExtra("encode",j);
//                    setResult(RESULT_OK,intent);
//                    Pmkvysignane.this.finish();



                }
                else {

                   // System.out.print("enn" +encoded);
                   // Toast.makeText(getApplicationContext(),"photo  lo",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    //intent.putExtra("encode",encoded);
                    setResult(3,intent);
                    Booklet_pic.this.finish();



                }
            }
        });


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_booklet_pic;
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
                pmkvybooklet.setImageBitmap(photo);
                submit_pmkvybooklet.setVisibility(View.VISIBLE);
                int currentBitmapWidth = photo.getWidth();
                int currentBitmapHeight = photo.getHeight();
                int ivWidth = pmkvybooklet.getWidth();
                int ivHeight = pmkvybooklet.getHeight();
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

    @Override
    protected int getMenuId() {
        return R.menu.main;
    }
}
