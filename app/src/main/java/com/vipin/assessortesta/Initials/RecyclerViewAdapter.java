package com.vipin.assessortesta.Initials;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.vipin.assessortesta.Attendance.Assessor_Atten;
import com.vipin.assessortesta.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {


    //may be we have declare this page seprately

    Context mContext;
    //list of data
    List<Upcoming1>mData;
CardView cardviewupcoming;



    //constructor of recyclerview for upcoming Batch

    public RecyclerViewAdapter(Context mContext, List<Upcoming1> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v;

        //adding the layout
        v= LayoutInflater.from(mContext).inflate(R.layout.item_upcoming,null,false);
        cardviewupcoming=v.findViewById(R.id.cardviewupcoming);
        MyViewHolder vholder = new MyViewHolder(v);


        return vholder;








    }






    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        //bind the data add in holder

        holder.batchname.setText(mData.get(position).getBatchname());
        holder.totalstudent.setText(mData.get(position).getTotalstudent());
        holder.assessmentda.setText(mData.get(position).getAssessmentdate());
        holder.tcname.setText(mData.get(position).getTcName());

        cardviewupcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "data is"+position, Toast.LENGTH_SHORT).show();
                Intent ii=new Intent(mContext, Assessor_Atten.class);
                mContext.startActivity(ii);
            }
        });






    }

    @Override
    public int getItemCount() {

        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public static View holder;


        //Declare the variable

        private TextView batchname;
        private TextView  totalstudent ;
        private TextView assessmentda;
        private TextView tcname;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


              //add the id

            batchname = itemView.findViewById(R.id.batchnameid);
            totalstudent=itemView.findViewById(R.id.totalstudentid);
            assessmentda = itemView.findViewById(R.id.assessmentid);
            tcname=itemView.findViewById(R.id.tcnameid);


//
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent ii=new Intent(getA,Annexure.class);
//                    mContext.startActivity(ii);

                    v.getContext().startActivity(new Intent(v.getContext(),Annexure.class));

                }
            });








        }
    }


}
