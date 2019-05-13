package com.vipin.assessortesta.Global;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toolbar;

import com.vipin.assessortesta.R;

public abstract class BaseActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private android.app.AlertDialog progressDialog;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
       // progressDialog = new SpotsDialog(BaseActivity.this, R.style.Custom);
        //setupToolbar();
        //bindViews();
        initView();
    }

    public void attendancealert(){
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(BaseActivity.this);

        // Set the message show for the Alert time
        builder.setMessage("You Can mark the Attendance only after You reach the Training Centre");


        // Set Cancelable false
        // for when the user clicks on the outside
        // the Dialog Box then it will remain show
        builder.setCancelable(false);

        // Set the positive button with yes name
        // OnClickListener method is use of
        // DialogInterface interface.

        builder
                .setPositiveButton(
                        "Ok",
                        new DialogInterface
                                .OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which)
                            {

                                // When the user click yes button
                                // then app will close
                                dialog.cancel();
                            }
                        });
    }

    public void show_progressbar(){
       // progressDialog.show();

    }

    public void hide_progressbar(){
      /*  if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }*/
    }

    /* *//**
     * Use this method to initialize view components. This method is called after {@link
     * BaseActivity#()}
     */
    public void initView() {
    }

    /**
     * Its common use a toolbar within activity, if it exists in the
     * layout this will be configured
     */
   /* public void setupToolbar() {
        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
    }*/
    /*
     *//**
     * Every object annotated with {@link butterknife.Bind} its gonna injected trough butterknife
     *//*
    private void bindViews() {
        ButterKnife.bind(this);
    }

    @Nullable public Toolbar getToolbar() {
        return mToolbar;
    }*/

    /**
     * @return The layout id that's gonna be the activity view.
     */
    protected abstract int getLayoutId();
}

