package com.vipin.assessortesta.feedback;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rx2androidnetworking.Rx2AndroidNetworking;
import com.vipin.assessortesta.Ass_Registration.AssRegActivity;
import com.vipin.assessortesta.R;
import com.vipin.assessortesta.feedback.adapter.FeedbackVpAdapter;
import com.vipin.assessortesta.feedback.camera.Camera2VideoFragment;
import com.vipin.assessortesta.feedback.extra.FeedbackShowButton;
import com.vipin.assessortesta.feedback.feedback_dialog.FeedbackDialogActivity;
import com.vipin.assessortesta.feedback.fragment.FragmentChildFeedback;
import com.vipin.assessortesta.pojo.feedback.FeedbackResponse;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dmax.dialog.SpotsDialog;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class AssessorFeedbackActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvQuesNO, tvQues;
    private Button btnProceed;
    private TabLayout tbFeedback;
    private ViewPager vpFeedback;
    private FeedbackVpAdapter vpAdapter;
    private android.app.AlertDialog progressDialog;

    int pageno,pagenoo;
    private static FeedbackShowButton showbuttonn;
    String[] arrTitle = {"Excellent", "Very Good", "Good", "Poor"};
    FeedbackResponse feedbackRes;
    int quesId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessor_feedback);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (null == savedInstanceState) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, Camera2VideoFragment.newInstance())
                    .commit();
        }

        initView();
       manageView();

        funcCallApi();

    }

    private void funcCallApi(){
        show_progressbar();
        Rx2AndroidNetworking.post("https://www.skillassessment.org/sdms/android_connect1/assessor/get_student_ques_detail.php")
                .addBodyParameter("key_salt", "UmFkaWFudEluZm9uZXRTYWx0S2V5")
                .addBodyParameter("student_id", "UKJK003")
                .build()
                .getObjectObservable(FeedbackResponse.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FeedbackResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(FeedbackResponse feedbackResponse) {
                        hide_progressbar();
                        Toast.makeText(AssessorFeedbackActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        feedbackRes = feedbackResponse;

                        String sQues = feedbackResponse.getPractical().getJsonMember().getQuestion();
                        quesId = feedbackResponse.getPractical().getJsonMember().getQuestionId();


                        tvQues.setText(sQues);
                        funcConfigViewPager();

                        for (int i = 0; i < arrTitle.length; i++){
                            addPage(arrTitle[i], "Ques-"+i, i);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        hide_progressbar();
                        Toast.makeText(AssessorFeedbackActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        hide_progressbar();
                    }
                });
    }


    private void initView() {
        progressDialog = new SpotsDialog(AssessorFeedbackActivity.this, R.style.Custom);
        tvQuesNO = (TextView)findViewById(R.id.tvQuesNO) ;
        tvQues = (TextView)findViewById(R.id.tvQues) ;
        btnProceed = (Button) findViewById(R.id.btnProceed);
        tbFeedback = (TabLayout) findViewById(R.id.tbFeedback);
        vpFeedback = (ViewPager) findViewById(R.id.vpFeedback);

    }
    private void manageView() {

        tvQues.setText("Read each sentence to find out whether there is any grammatical error in it. The error, if any will be in one part of the sentence.");
        btnProceed.setOnClickListener(this::onClick);
        tbFeedback.setupWithViewPager(vpFeedback);

    }

    private void funcConfigViewPager(){
        vpAdapter = new FeedbackVpAdapter(getSupportFragmentManager());
        vpFeedback.setAdapter(vpAdapter);
        pagenoo=vpFeedback.getCurrentItem();

        vpFeedback.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            Boolean first = true;
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (!first && positionOffset == 0 && positionOffsetPixels == 0){
                    onPageSelected(0);
                    first = false;

                }
            }
            @Override
            public void onPageSelected(int position) {

                pageno=vpFeedback.getCurrentItem();

                if (pageno == vpAdapter.getCount()-1){
                    //ttv.setVisibility(View.VISIBLE);
//                    showbuttonn.getData(1);
                    System.out.println("fragment on last page");

                }else{
//                    showbuttonn.getData(0);
                    System.out.println("not true");

                }


            }

            @Override
            public void onPageScrollStateChanged(int i) {
                System.out.println("page scrolled");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Toast.makeText(this, "Back button clicked", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnProceed:
                Intent i = new Intent(AssessorFeedbackActivity.this, FeedbackDialogActivity.class);
                i.putExtra("ques_id", ""+quesId);
                i.putExtra("stu_id", "UKJK003");

                startActivity(i);
                break;
        }
    }

    public void addPage(String pagename, String que, int pgn) {
        Bundle bundle = new Bundle();
        bundle.putString("data", pagename);
        bundle.putInt("pgno",pgn);

        FragmentChildFeedback fragmentChild = new FragmentChildFeedback();
        fragmentChild.setArguments(bundle);
        vpAdapter.addFrag(fragmentChild, pagename,que);
        vpAdapter.notifyDataSetChanged();
        if (vpAdapter.getCount() > 0)
            vpFeedback.setCurrentItem(0);
    }

    public FeedbackResponse getApiRespData(){
        return feedbackRes;
    }


    public void show_progressbar(){
        progressDialog.show();

    }

    public void hide_progressbar(){
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }



}