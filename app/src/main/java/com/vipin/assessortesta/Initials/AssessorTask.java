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
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.vipin.assessortesta.R;
import com.vipin.assessortesta.utils.CommonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class AssessorTask extends AppCompatActivity implements Upcoming.OnFragmentInteractionListener, Complete.OnFragmentInteractionListener,
        Overdue.OnFragmentInteractionListener ,NavigationView.OnNavigationItemSelectedListener
{

    Toolbar toolbar;
    JSONObject mainJObject;
    SharedPreferences sharedpreferences;
    final String mypreference = "mypref";
    String assessor_id;
    TabLayout tabLayout;


    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToogle;
    int itemid,ii;



    private android.app.AlertDialog progressDialog;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_assessor_task);
//       toolbar = findViewById(R.id.toolbar);
//        toolbar.setEnabled(true);
//       toolbar.setTitle("Batch List");
        tabLayout = findViewById(R.id.tablelayout);
        tabLayout.addTab(tabLayout.newTab().setText("Upcoming"));
        tabLayout.addTab(tabLayout.newTab().setText("Complete"));
        tabLayout.addTab(tabLayout.newTab().setText("OverDue"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        mDrawerLayout= findViewById(R.id.drawer);
        NavigationView  navigationView= findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        TextView headertext = header.findViewById(R.id.textview2);



        progressDialog = new SpotsDialog(AssessorTask.this, R.style.Custom);





        callWebApi();


        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);

        if (sharedpreferences.contains("user_name")) {
            assessor_id = sharedpreferences.getString("user_name", "");
            headertext.setText(sharedpreferences.getString("user_name", ""));

            System.out.println("asessoriddd" + assessor_id);

        }



        mToogle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToogle);
        mToogle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Batch List");



    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        itemid=item.getItemId();
        switch (itemid)

        {

            case R.id.logout:



                            Intent j = new Intent(AssessorTask.this,SplashScreen.class);
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
        if(mToogle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }





















    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
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

        String serverURL = CommonUtils.url+"get_assigned_batch.php";

        StringRequest request = new StringRequest(Request.Method.POST, serverURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("response is" + response);
                try {
                    JSONObject jobj = new JSONObject(response);
                    String status = jobj.getString("status");

                    if (status.equals("1")) {
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

                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // Toast.makeText(getContext(), "Error: Please try again Later", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                super.getHeaders();
                Map<String, String> map = new HashMap<>();

                return map;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                Map<String, String> map = new HashMap<>();
                map.put("key_salt", "UmFkaWFudEluZm9uZXRTYWx0S2V5");
                map.put("user_name", assessor_id);
//                map.put("user_name", "123");

                System.out.println("ddd" + map);
                return map;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(20000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyNetwork.getInstance(getApplicationContext()).addToRequestQueue(request);
    }


    public JSONObject getApiData(){
        return mainJObject;
    }



//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        itemid=item.getItemId();
//        switch (itemid)
//
//        {
//            case R.id.changepasw:
//
//                Intent D = new Intent(SecondActivity.this,Doctor.class);
//                startActivity(D);
//                break;
//
//            case R.id.logout:
//
//                Intent k = new Intent(AssessorTask.this,.class);
//                startActivity(k);
//                onBackPressed();
//                break;
//            case R.id.help:
//                Toast.makeText(getApplicationContext(),"fffffddddd",Toast.LENGTH_LONG).show();
//                break;
//            default:
//
//                break;
//
//        }
//        mDrawerLayout.closeDrawer(GravityCompat.START);
//        return true;
//    }




}


