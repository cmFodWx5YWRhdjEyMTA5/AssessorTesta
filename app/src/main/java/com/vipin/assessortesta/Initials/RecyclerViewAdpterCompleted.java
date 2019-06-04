package com.vipin.assessortesta.Initials;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vipin.assessortesta.R;

import java.util.List;

public class RecyclerViewAdpterCompleted extends RecyclerView.Adapter<RecyclerViewAdpterCompleted.MyViewHolder> {

    Context mContext;
    //list of data
    List<Complete1> mData;
    CardView cardviewComplete;


    public RecyclerViewAdpterCompleted(Context mContext, List<Complete1> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v;

        //adding the layout

        v = LayoutInflater.from(mContext).inflate(R.layout.item_complete, null, false);
        cardviewComplete = v.findViewById(R.id.cardviewComplete);
        MyViewHolder vholder = new MyViewHolder(v);
        return vholder;


    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.batchname_header.setText("Batch Name");
        holder.totalstudent_header.setText("Total Students");
        holder.assessmentda_header.setText("Assessment Data");
        holder.tcname_header.setText("Tc Name");

        holder.batchname.setText(mData.get(position).getBatchname());
        holder.totalstudent.setText(mData.get(position).getTotalstudent());
        holder.assessmentda.setText(mData.get(position).getAssessmentdate());
        holder.tcname.setText(mData.get(position).getTcName());


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {


        private TextView batchname, batchname_header;
        private TextView totalstudent, totalstudent_header;
        private TextView assessmentda, assessmentda_header;
        private TextView tcname, tcname_header;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            //add the id

            batchname = itemView.findViewById(R.id.batchnameidcomp);
            totalstudent = itemView.findViewById(R.id.totalstudentidcomp);
            assessmentda = itemView.findViewById(R.id.assessmentidcomp);
            tcname = itemView.findViewById(R.id.tcnameidcomp);


            batchname_header = itemView.findViewById(R.id.batchname1);
            totalstudent_header = itemView.findViewById(R.id.totalstudent1);
            assessmentda_header = itemView.findViewById(R.id.assessment1);
            tcname_header = itemView.findViewById(R.id.tcname1);


        }
    }
}