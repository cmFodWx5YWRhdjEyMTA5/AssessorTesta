package com.vipin.assessortesta.student_group;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.GridLayoutAnimationController;
import android.widget.Button;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rx2androidnetworking.Rx2AndroidNetworking;
import com.vipin.assessortesta.Batch_Student.Batch_instruction;
import com.vipin.assessortesta.Global.BaseActivity;
import com.vipin.assessortesta.R;
import com.vipin.assessortesta.pojo.practical_que.PracticalItem;
import com.vipin.assessortesta.pojo.practical_que.PracticalQuesResponse;
import com.vipin.assessortesta.practical_student_assign.StudentAssignActivity;
import com.vipin.assessortesta.utils.CommonUtils;
import com.vipin.assessortesta.utils.GridSpacingItemDecoration;
import com.vipin.assessortesta.utils.NetworkManager;
import com.vipin.assessortesta.utils.RecyclerItemClickListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import dmax.dialog.SpotsDialog;

import static com.vipin.assessortesta.utils.RecyclerItemClickListener.*;

public class StudentGroupActivity extends AppCompatActivity {

    private RecyclerView rcView;
    private android.app.AlertDialog progressDialog;

    SharedPreferences sharedpreferences;
    final String mypreference = "mypref";
    String assessor_id,batchid;
    Button btnProceed;
    static int totalCount = 0;
    static int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_group);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Practical Exam Group");
        progressDialog = new SpotsDialog(this, R.style.Custom);

        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);

        if (sharedpreferences.contains("user_name")) {
            assessor_id = sharedpreferences.getString("user_name", "");
            System.out.println("asessoriddd" + assessor_id);

        }


        if (sharedpreferences.contains("batch_id")) {
            batchid = sharedpreferences.getString("batch_id", "");
            System.out.println("asessoriddd" + batchid);

        }

        initView();
        manageView();





    }

    private void initView() {
        rcView = (RecyclerView)findViewById(R.id.rcView);
        btnProceed = (Button) findViewById(R.id.btnProceed);


    }

    private void manageView() {

//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.grid_item_anim);
//        GridLayoutAnimationController controller = new GridLayoutAnimationController(animation, .2f, .2f);
//        rcView.setLayoutAnimation(controller);

        if (NetworkManager.getInstance().isOnline(this) == true) {
            callApiForQueList();
        }else {
            showAlertMessageWithBack(R.drawable.ic_complain, "Alert", getResources().getString(R.string.net_error));
        }

        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count == totalCount) {
                    startActivity(new Intent(StudentGroupActivity.this, Batch_instruction.class));
                    finish();
                }else {
                    showAlertMessage(R.drawable.ic_complain, "Alert", "\nYou must assign maximum students to each group.");
                }
            }
        });
    }

    private void callApiForQueList() {
        show_progressbar();
        AndroidNetworking.post(CommonUtils.url+"get_practical_question.php")
                .addBodyParameter("key_salt", "UmFkaWFudEluZm9uZXRTYWx0S2V5")
                .addBodyParameter("batch_id",batchid)
//                .addBodyParameter("batch_id", "299")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hide_progressbar();


                        try {
                            if (response.getInt("status") == 1) {
                                funcConfigApiData(response);
                            }else {
                                showAlertMessageWithBack(R.drawable.ic_complain, "Alert", getResources().getString(R.string.api_error));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            showAlertMessageWithBack(R.drawable.ic_complain, "Alert", getResources().getString(R.string.api_error));
                        }

//                        funcConfigApiData(response);

                    }

                    @Override
                    public void onError(ANError anError) {
                        hide_progressbar();
                        showAlertMessageWithBack(R.drawable.ic_complain, "Alert", getResources().getString(R.string.api_error));
                    }
                });
    }

    private void funcConfigApiData(JSONObject jsonObject){
        Gson gson = new Gson();
        PracticalQuesResponse response = gson.fromJson(jsonObject.toString(), PracticalQuesResponse.class);
        setGridList(response, jsonObject);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void setGridList(PracticalQuesResponse response, JSONObject jsonObject) {

        rcView.setLayoutManager(new GridLayoutManager(StudentGroupActivity.this, 2));
        StudentGroupAdapter adapter = new StudentGroupAdapter(StudentGroupActivity.this, response);

        int spanCount = 2; // 2 columns
        int spacing = 8; // 50px
        boolean includeEdge = false;
        rcView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));

        rcView.setAdapter(adapter);

        rcView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, rcView,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // TODO Handle item click

                        Intent intent = new Intent(StudentGroupActivity.this, StudentAssignActivity.class);
                        intent.putExtra("que_data", jsonObject.toString());
                        intent.putExtra("position", position);

                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                }));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                startActivity(new Intent(this, Batch_instruction.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void show_progressbar(){
        progressDialog.show();
    }

    public void hide_progressbar(){
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, Batch_instruction.class));
        finish();
    }

    private void showAlertMessage(int icon, String title, String msg){
        new AlertDialog.Builder(StudentGroupActivity.this)
                .setIcon(icon)
                .setTitle(title)
                .setMessage(msg)
                .setCancelable(false)
                .setNegativeButton("Ok", null)
                .show();
    }
    private void showAlertMessageWithBack(int icon, String title, String msg){
        new AlertDialog.Builder(StudentGroupActivity.this)
                .setIcon(icon)
                .setTitle(title)
                .setMessage(msg)
                .setCancelable(false)
                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(StudentGroupActivity.this, Batch_instruction.class));
                        finish();
                    }
                })
                .show();
    }

}
