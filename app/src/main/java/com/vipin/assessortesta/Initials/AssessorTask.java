package com.vipin.assessortesta.Initials;

import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;



import com.vipin.assessortesta.R;

public class AssessorTask extends AppCompatActivity implements Upcoming.OnFragmentInteractionListener, Complete.OnFragmentInteractionListener,
        Overdue.OnFragmentInteractionListener{

    Toolbar toolbar;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessor_task);
        toolbar=findViewById(R.id.toolbar);
        toolbar.setEnabled(true);
        toolbar.setTitle("Home");
        TabLayout tabLayout = findViewById(R.id.tablelayout);
        tabLayout.addTab(tabLayout.newTab().setText("Upcoming"));
        tabLayout.addTab(tabLayout.newTab().setText("Complete"));
        tabLayout.addTab(tabLayout.newTab().setText("OverDue"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final  ViewPager viewPager = findViewById(R.id.pager);
        final  PageAdapter adapter = new PageAdapter(getSupportFragmentManager(),tabLayout.getTabCount());

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




    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
