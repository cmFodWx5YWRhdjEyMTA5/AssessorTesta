package com.vipin.assessortesta.Batch_Student;


import android.content.Context;
import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vipin.assessortesta.R;

public class Students_list  extends AppCompatActivity {
    RecyclerView meet_rc;
    final Context myContext = Students_list.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_list);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        meet_rc = (RecyclerView) findViewById(R.id.meet_rc);
        meet_rc.setLayoutManager(new LinearLayoutManager(myContext));
        meet_rc.setAdapter(new MeetAdapter());
    }

    private class MeetAdapter extends RecyclerView.Adapter<MeetAdapter.ViewHolder> {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(myContext);
            View view = inflater.inflate(R.layout.student_adapter, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.maccountName.setText("Student1");
            holder.mcontactperson.setText("9015363586");
            holder.mlocation.setText("Delhi");
            holder.mstartDate.setText("Batch no. 1");
        }

        @Override
        public int getItemCount() {
            return 8;
            // return accountName.size();
        }






        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
            TextView maccountName, mcontactperson, mlocation, mstartDate, mendDate, meetStatus, textMenu;
            AlertDialog dialog;
            double desLat, desLng;
            Geocoder geocoder;

            ViewHolder(@NonNull View itemView) {
                super(itemView);


                itemView.setOnClickListener(this);
                maccountName = itemView.findViewById(R.id.studentlist_batchnameid);
                mcontactperson = (TextView) itemView.findViewById(R.id.studentlist_totalstudentid);
                mlocation = (TextView) itemView.findViewById(R.id.studentlist_assessmentid);
                mstartDate = (TextView) itemView.findViewById(R.id.studentlist_tcnameid);
                mendDate = (TextView) itemView.findViewById(R.id.studentlist_namee);


            }

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return false;
            }

            @Override
            public void onClick(View v) {
                Intent ii=new Intent(Students_list.this,Batch_detail.class);
                startActivity(ii);
            }

        }
    }
}
