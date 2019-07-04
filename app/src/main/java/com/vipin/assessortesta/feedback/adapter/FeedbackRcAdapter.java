package com.vipin.assessortesta.feedback.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.vipin.assessortesta.R;
import com.vipin.assessortesta.pojo.feedback.FeedbackResponse;

import java.util.ArrayList;
import java.util.List;

public class FeedbackRcAdapter extends RecyclerView.Adapter<FeedbackRcAdapter.MyViewHolder> {

    Context _context;
    FeedbackResponse feedbackResponse;
    int pageNo;
    public FeedbackRcAdapter(Context _context, FeedbackResponse feedbackResponse, int pageNo){
        this._context = _context;
        this.feedbackResponse = feedbackResponse;
        this.pageNo = pageNo;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        FrameLayout frameSelectBg;
        TextView tvQuesNo;
        TextView tvStudentName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            frameSelectBg = (FrameLayout)itemView.findViewById(R.id.frameSelectBg);
            tvQuesNo = (TextView) itemView.findViewById(R.id.tvQuesNo);
            tvStudentName = (TextView) itemView.findViewById(R.id.tvStudentName);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(_context).inflate(R.layout.row_feedback_layout, viewGroup, false);
            return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        List<String> list = new ArrayList<>();
        if (pageNo == 0){
            list = feedbackResponse.getPractical().getJsonMember().getRubrics().getProficient();
        }else if (pageNo == 1){
            list = feedbackResponse.getPractical().getJsonMember().getRubrics().getException();
        }else if (pageNo == 2){
            list = feedbackResponse.getPractical().getJsonMember().getRubrics().getAverage();
        }else if (pageNo == 3){
            list = feedbackResponse.getPractical().getJsonMember().getRubrics().getPoor();
        }


        myViewHolder.tvQuesNo.setText(""+(i+1)+")");
        myViewHolder.tvStudentName.setText(list.get(i));
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {

        if (pageNo == 0){
            List<String> veryGoodList = feedbackResponse.getPractical().getJsonMember().getRubrics().getProficient();
            return veryGoodList.size();
        }else if (pageNo == 1){
            List<String> goodList = feedbackResponse.getPractical().getJsonMember().getRubrics().getException();
            return goodList.size();
        }else if (pageNo == 2){

            List<String> averageList = feedbackResponse.getPractical().getJsonMember().getRubrics().getAverage();
            return  averageList.size();
        }else if (pageNo == 3){
            List<String> poorList = feedbackResponse.getPractical().getJsonMember().getRubrics().getPoor();
            return poorList.size();
        }else {
            return 0;
        }

    }

}
