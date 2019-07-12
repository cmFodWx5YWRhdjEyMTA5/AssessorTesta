package com.vipin.assessortesta.feedback.feedback_dialog;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.vipin.assessortesta.R;
import com.vipin.assessortesta.feedback.AssessorFeedbackActivity;
import com.vipin.assessortesta.practical_student_assign.StudentAssignActivity;
import com.vipin.assessortesta.practical_student_list.PracticalStuListActivity;
import com.vipin.assessortesta.student_group.StudentGroupActivity;
import com.vipin.assessortesta.utils.CommonUtils;
import com.vipin.assessortesta.utils.NetworkManager;

import org.json.JSONException;
import org.json.JSONObject;

import dmax.dialog.SpotsDialog;

public class FeedbackDialogActivity extends Activity implements View.OnClickListener {

    private LinearLayout llExp1, llExp2, llExp3, llExp4;
    private ImageView ivExp1, ivExp2, ivExp3,ivExp4;
    private TextView tvExp1, tvExp2, tvExp3, tvExp4;
    private EditText etComment;
    private Button btnCancel, btnSubmit;
    String stu_id, ques_id, sRate = null;
    private android.app.AlertDialog progressDialog;


    SharedPreferences sharedpreferences;
    final String mypreference = "mypref";
    String assessor_id,batchid;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setExitTransition(new Explode());
        setContentView(R.layout.activity_feedback_dialog);

        stu_id = getIntent().getExtras().getString("stu_id");
        ques_id = getIntent().getExtras().getString("ques_id");

//        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        initView();
        manageView();


        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);

        if (sharedpreferences.contains("user_name")) {
            assessor_id = sharedpreferences.getString("user_name", "");
            System.out.println("asessoriddd" + assessor_id);

        }


        if (sharedpreferences.contains("batch_id")) {
            batchid = sharedpreferences.getString("batch_id", "");
            System.out.println("asessoriddd" + batchid);

        }

    }

    private void initView() {
        progressDialog = new SpotsDialog(FeedbackDialogActivity.this, R.style.Custom);
        llExp1 = (LinearLayout)findViewById(R.id.llExp1);
        llExp2 = (LinearLayout)findViewById(R.id.llExp2);
        llExp3 = (LinearLayout)findViewById(R.id.llExp3);
        llExp4 = (LinearLayout)findViewById(R.id.llExp4);

        ivExp1 = (ImageView)findViewById(R.id.ivExp1);
        ivExp2 = (ImageView)findViewById(R.id.ivExp2);
        ivExp3 = (ImageView)findViewById(R.id.ivExp3);
        ivExp4 = (ImageView)findViewById(R.id.ivExp4);

        tvExp1 = (TextView) findViewById(R.id.tvExp1);
        tvExp2 = (TextView)findViewById(R.id.tvExp2);
        tvExp3 = (TextView)findViewById(R.id.tvExp3);
        tvExp4 = (TextView) findViewById(R.id.tvExp4);

        etComment = (EditText) findViewById(R.id.etComment);

        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);



    }
    private void manageView() {

        llExp1.setOnClickListener(this::onClick);
        llExp2.setOnClickListener(this::onClick);
        llExp3.setOnClickListener(this::onClick);
        llExp4.setOnClickListener(this::onClick);
        btnCancel.setOnClickListener(this::onClick);
        btnSubmit.setOnClickListener(this::onClick);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.llExp1:
                funcSetExp(ivExp1, tvExp1, R.color.colorExp1, "Excellent");
                break;
            case R.id.llExp2:
                funcSetExp(ivExp2, tvExp2, R.color.colorExp2, "Very Good");
                break;
            case R.id.llExp3:
                funcSetExp(ivExp3, tvExp3, R.color.colorExp3, "Good");
                break;
            case R.id.llExp4:
                funcSetExp(ivExp4, tvExp4, R.color.colorExp4, "Poor");
                break;
            case R.id.btnCancel:
                finish();
                break;
                case R.id.btnSubmit:
                    if (isValid()) {

                        if (NetworkManager.getInstance().isOnline(this) == true) {
                            funcUploadFeedbackApi();
                        }else {
                            showAlertMessageWithBack(R.drawable.ic_complain, "Alert", getResources().getString(R.string.net_error));
                        }
                    }
                break;

                default:
                    Toast.makeText(this, "Invalid Selection", Toast.LENGTH_SHORT)
                            .show();
                    break;
        }
    }

    private void funcUploadFeedbackApi() {
        show_progressbar();
        AndroidNetworking.post(CommonUtils.url+"save_student_feedback.php")
                .addBodyParameter("key_salt", "UmFkaWFudEluZm9uZXRTYWx0S2V5")
                .addBodyParameter("batch_id", batchid)
                .addBodyParameter("assessor_id",assessor_id)
                .addBodyParameter("question_id", ques_id)
                .addBodyParameter("student_id", stu_id)
                .addBodyParameter("performance", sRate)
                .addBodyParameter("feedback", etComment.getText().toString())
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hide_progressbar();
                        try {
                            int respCode = response.getInt("status");
                            String msg = response.getString("msg");

                            if (respCode == 1) {
                                new AlertDialog.Builder(FeedbackDialogActivity.this, R.style.DialogTheme)
                                        .setTitle("Success")
                                        .setMessage(msg)
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                              startActivity(new Intent(FeedbackDialogActivity.this, PracticalStuListActivity.class));
                                              finish();
                                            }
                                        })
                                        .show();
                            }else {
                                new AlertDialog.Builder(FeedbackDialogActivity.this, R.style.DialogTheme)
                                        .setTitle("Failed")
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
                        Toast.makeText(FeedbackDialogActivity.this, "Upload failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void funcSetExp(ImageView iv, TextView tv, int color, String rate){
        ivExp1.setColorFilter(ContextCompat.getColor(this, R.color.black), android.graphics.PorterDuff.Mode.SRC_IN);
        tvExp1.setTextColor(getResources().getColor(R.color.colorGray));
        ivExp2.setColorFilter(ContextCompat.getColor(this, R.color.black), android.graphics.PorterDuff.Mode.SRC_IN);
        tvExp2.setTextColor(getResources().getColor(R.color.colorGray));
        ivExp3.setColorFilter(ContextCompat.getColor(this, R.color.black), android.graphics.PorterDuff.Mode.SRC_IN);
        tvExp3.setTextColor(getResources().getColor(R.color.colorGray));
        ivExp4.setColorFilter(ContextCompat.getColor(this, R.color.black), android.graphics.PorterDuff.Mode.SRC_IN);
        tvExp4.setTextColor(getResources().getColor(R.color.colorGray));

        sRate = rate;

        // Fade the button out and back in
        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(iv,
                View.ALPHA, 0);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(ValueAnimator.REVERSE);
        alphaAnimation.start();

        iv.setColorFilter(ContextCompat.getColor(this, color), android.graphics.PorterDuff.Mode.SRC_IN);
        tv.setTextColor(getResources().getColor(color));

    }

    private boolean isValid(){
        if (sRate == null){
            Toast.makeText(this, "Please, rate the student", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public void show_progressbar(){
        progressDialog.show();

    }

    public void hide_progressbar(){
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private void showAlertMessageWithBack(int icon, String title, String msg){
        new android.support.v7.app.AlertDialog.Builder(this)
                .setIcon(icon)
                .setTitle(title)
                .setMessage(msg)
                .setCancelable(false)
                .setNegativeButton("Ok", null)
                .show();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        startActivity(new Intent(this, AssessorFeedbackActivity.class));
        finish();
    }
}
