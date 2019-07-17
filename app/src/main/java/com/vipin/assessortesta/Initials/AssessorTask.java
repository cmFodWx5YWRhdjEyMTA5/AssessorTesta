package com.vipin.assessortesta.Initials;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.vipin.assessortesta.R;
import com.vipin.assessortesta.utils.CommonUtils;
import com.vipin.assessortesta.utils.PrefsManager;

import org.json.JSONException;
import org.json.JSONObject;

import dmax.dialog.SpotsDialog;

public class AssessorTask extends AppCompatActivity implements Upcoming.OnFragmentInteractionListener, Complete.OnFragmentInteractionListener,
        Overdue.OnFragmentInteractionListener, NavigationView.OnNavigationItemSelectedListener {

    final String mypreference = "mypref";
    Toolbar toolbar;
    JSONObject mainJObject;
    SharedPreferences sharedpreferences;
    String assessor_id = null;
    TabLayout tabLayout;
    int itemid, ii;
    PrefsManager prefs;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToogle;
    private TextView headertext;


    private android.app.AlertDialog progressDialog;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessor_task);
        prefs = new PrefsManager(this);

        initView();
        manageView();
    }

    private void initView() {
//        ivLogout = findViewById(R.id.ivLogout);


        mDrawerLayout = findViewById(R.id.drawer);
        NavigationView navigationView = findViewById(R.id.nav_view);

        mToogle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToogle);
        mToogle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Batch List");

        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        headertext = header.findViewById(R.id.textview2);

        tabLayout = findViewById(R.id.tablelayout);

        tabLayout.addTab(tabLayout.newTab().setText("Upcoming"));
        tabLayout.addTab(tabLayout.newTab().setText("Complete"));
        tabLayout.addTab(tabLayout.newTab().setText("OverDue"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        progressDialog = new SpotsDialog(AssessorTask.this, R.style.Custom);
    }

    private void manageView() {


        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);

        if (prefs.getString("user_name") != null) {
            assessor_id = prefs.getString("user_name");
//            System.out.println("asessoriddd" + assessor_id);

            callWebApi();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_logout, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        itemid = item.getItemId();
        switch (itemid) {
            case R.id.logout:
                Intent j = new Intent(AssessorTask.this, SplashScreen.class);
                startActivity(j);
                finish();
                break;
            default:
                break;

        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToogle.onOptionsItemSelected(item)) {
            return true;
        }

        if (item.getItemId() == R.id.idLogout){
            showAlertMessageWithBack(R.drawable.ic_complain, "Alert", "\nDo you want to logout?");
        }

        return super.onOptionsItemSelected(item);
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        exitByBackKey();
    }


    protected void exitByBackKey() {

        new AlertDialog.Builder(this, R.style.DialogTheme)
                .setIcon(R.drawable.ic_complain)
                .setTitle("Alert")
                .setMessage("Are you sure you want to exit")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        finishAffinity();
                    }
                })
                .setNegativeButton("No", null)
                .show();

    }

    private void callWebApi() {

        progressDialog.show();
        String serverURL = CommonUtils.url + "get_assigned_batch.php";

        AndroidNetworking.post(serverURL)
                .addBodyParameter("key_salt", "UmFkaWFudEluZm9uZXRTYWx0S2V5")
                .addBodyParameter("user_name", assessor_id.trim())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jobj) {

                        progressDialog.dismiss();
                        try {
//                            JSONObject jobj = new JSONObject(response);
                            int status = jobj.getInt("status");

                            if (status == 1) {
                                mainJObject = jobj.getJSONObject("batch_details");

                                final ViewPager viewPager = findViewById(R.id.pager);
                                final PageAdapter adapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

                                viewPager.setAdapter(adapter);
                                viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

                                tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
                                    @Override
                                    public void onTabSelected(TabLayout.Tab tab) {

                                        viewPager.setCurrentItem(tab.getPosition());
                                    }

                                    @Override
                                    public void onTabUnselected(TabLayout.Tab tab) {

                                    }

                                    @Override
                                    public void onTabReselected(TabLayout.Tab tab) {

                                    }
                                });
                            } else {
                                Toast.makeText(AssessorTask.this, "Error", Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {

                        progressDialog.dismiss();
                        Toast.makeText(AssessorTask.this, "Failed to connect server", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public JSONObject getApiData() {
        return mainJObject;
    }

    private void showAlertMessageWithBack(int icon, String title, String msg) {
        new AlertDialog.Builder(this, R.style.DialogTheme)
                .setIcon(icon)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        prefs.removeAll();
                        startActivity(new Intent(AssessorTask.this, SplashScreen.class));
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

}


