package com.vipin.assessortesta.Initials;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.vipin.assessortesta.Attendance.Assessor_Atten;
import com.vipin.assessortesta.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    final String mybatch = "myybatch";
    final String mypreference = "mypref";
    Context mContext;
    List<UpcomingModel> mData;
    CardView cardviewupcoming;
    String batchidd;
    SharedPreferences sharedpreferences;
    String strDate;

    public RecyclerViewAdapter(Context mContext, List<UpcomingModel> mData) {
        this.mContext = mContext;
        this.mData = mData;


        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
        strDate = sdf.format(c.getTime());
        System.out.print(strDate + " " +" lkjlglkjg");





    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_upcoming, viewGroup, false);
        cardviewupcoming = v.findViewById(R.id.cardviewupcoming);

        MyViewHolder vholder = new MyViewHolder(v);

        return vholder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        //bind the data add in holder
        holder.batchname_header.setText("Batch Name");
        holder.totalstudent_header.setText("Total Students");
        holder.assessmentda_header.setText("Assessment Date");
        holder.tcname_header.setText("Tc Name");
        holder.batchname.setText(mData.get(position).getBatchname());
        holder.totalstudent.setText(mData.get(position).getTotalstudent());
        holder.assessmentda.setText(mData.get(position).getAssessmentdate());
        holder.tcname.setText(mData.get(position).getTcName());
        sharedpreferences = mContext.getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        cardviewupcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!mData.get(position).getBatchname().trim().equalsIgnoreCase("NA")) {


                    if (mData.get(position).getAssessmentdate().trim().equalsIgnoreCase(strDate)) {

                        Toast.makeText(mContext, "data is" + mData.get(position).getcenterid(), Toast.LENGTH_SHORT).show();
                        Intent ii = new Intent(mContext, Assessor_Atten.class);
                        ii.putExtra("centerid", mData.get(position).getcenterid());
                        ii.putExtra("Batchid", mData.get(position).getBatchid());
                        ii.putExtra("exam_date", mData.get(position).getAssessmentdate());
                        ii.putExtra("batch_name", mData.get(position).getBatchname());

                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("ccc", mData.get(position).getBatchid());
                        editor.apply();

                        mContext.startActivity(ii);
                    }
                    else
                    {

                        Toast.makeText(mContext, "The Batch you are choosing is of Different date.", Toast.LENGTH_SHORT).show();
                    }



                }else {
                    Toast.makeText(mContext, "No batch assigned currently.", Toast.LENGTH_SHORT).show();
                }
            }
        });



        int progressPerc = mData.get(position).getProgressPerc();

        if (progressPerc > 0) {
            holder.btnResume.setText("" + progressPerc + "%");
            holder.btnResume.setVisibility(View.VISIBLE);
        }






    }

    @Override
    public int getItemCount() {

        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        //Declare the variable
        private TextView batchname, batchname_header;
        private TextView totalstudent, totalstudent_header;
        private TextView assessmentda, assessmentda_header;
        private TextView tcname, tcname_header;
        private Button btnResume;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            batchname = itemView.findViewById(R.id.batchnameid_upcoming);
            totalstudent = itemView.findViewById(R.id.totalstudentid_upcoming);
            assessmentda = itemView.findViewById(R.id.assessmentid_upcoming);
            tcname = itemView.findViewById(R.id.tcnameid_upcoming);

            batchname_header = itemView.findViewById(R.id.batchname_upcoming);
            totalstudent_header = itemView.findViewById(R.id.totalstudent_upcoming);
            assessmentda_header = itemView.findViewById(R.id.assessment_upcoming1);
            tcname_header = itemView.findViewById(R.id.tcname_upcoming);
            btnResume = itemView.findViewById(R.id.btnResume);
        }
    }
}
