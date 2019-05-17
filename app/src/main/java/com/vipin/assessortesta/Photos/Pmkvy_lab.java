package com.vipin.assessortesta.Photos;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vipin.assessortesta.Batch_Student.Batch_detail;
import com.vipin.assessortesta.Global.BaseActivity;
import com.vipin.assessortesta.R;

import java.io.ByteArrayOutputStream;

public class Pmkvy_lab extends BaseActivity {
    ImageView pmkvy_labpic;
    TextView clickmessage_pmkvylab;
    Button submittbutton_pmkvylab;
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pmkvy_labpic=findViewById(R.id.labpmkvy_pic);
        clickmessage_pmkvylab=findViewById(R.id.clickmessagelabpmkvy);
        submittbutton_pmkvylab=findViewById(R.id.nextbutton_labpmkvy);
        //click drawble right of textview
        clickmessage_pmkvylab.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    if(event.getX() >= (clickmessage_pmkvylab.getRight() - clickmessage_pmkvylab.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        captureevent();
                        return true;
                    }
                }
                return false;
            }
        });


        submittbutton_pmkvylab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pmkvy_labpic==null){
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
                    Toast.makeText(getApplicationContext(),"photo mat lo",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    //intent.putExtra("encode",encoded);
                    setResult(RESULT_OK,intent);
                    Pmkvy_lab.this.finish();



                }
            }
        });



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
                 pmkvy_labpic.setImageBitmap(photo);
                  submittbutton_pmkvylab.setVisibility(View.VISIBLE);
                int currentBitmapWidth = photo.getWidth();
                int currentBitmapHeight = photo.getHeight();
                 int ivWidth = pmkvy_labpic.getWidth();
                 int ivHeight = pmkvy_labpic.getHeight();
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
    protected int getLayoutId() {
        return R.layout.activity_pmkvy_lab;
    }
    //click photo


   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(getMenuId(),menu);
        return true;
    }*/

    @Override
    protected int getMenuId() {
        return R.menu.main;
    }

}
