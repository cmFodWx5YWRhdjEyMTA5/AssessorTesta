package com.vipin.assessortesta.practical_student_list.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vipin.assessortesta.R;
import com.vipin.assessortesta.feedback.AssessorFeedbackActivity;
import com.vipin.assessortesta.pojo.feedback_stu_list.PracticalStuListResponse;
import com.vipin.assessortesta.pojo.feedback_stu_list.StudentDetailsItem;

import java.util.List;
import java.util.Map;

public class PracticalStuListRcAdapter extends RecyclerView.Adapter<PracticalStuListRcAdapter.MyViewHolder> {

    Context _context;
    PracticalStuListResponse response;

    public PracticalStuListRcAdapter(Context _context, PracticalStuListResponse response){
        this._context = _context;
        this.response = response;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        FrameLayout frameSelectBg;
        ImageView ivTick;
        TextView tvStudentName, tvStudentId;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            frameSelectBg = (FrameLayout)itemView.findViewById(R.id.frameSelectBg);
            ivTick = (ImageView) itemView.findViewById(R.id.ivTick);
            tvStudentName = (TextView) itemView.findViewById(R.id.tvStudentName);
            tvStudentId = (TextView) itemView.findViewById(R.id.tvStudentId);

        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(_context).inflate(R.layout.row_practical_stu_list_layout, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        List<StudentDetailsItem> itemList = response.getStudentDetails();
        myViewHolder.tvStudentName.setText(itemList.get(i).getName());
        myViewHolder.tvStudentId.setText("ID : "+itemList.get(i).getStudentId());

        int status = itemList.get(i).getExamStatus();

        try {
                if (status == 1) {
                    Drawable d = (Drawable) _context.getResources().getDrawable(R.drawable.ic_check_circle_outline);
                    myViewHolder.ivTick.setImageDrawable(d);
                    myViewHolder.ivTick.setColorFilter(ContextCompat.getColor(_context, R.color.colorExp2), android.graphics.PorterDuff.Mode.SRC_IN);
                    myViewHolder.ivTick.setVisibility(View.VISIBLE);
                }else {
                    myViewHolder.ivTick.setVisibility(View.GONE);
                }
        }catch (Exception e){
            Log.e("NonSelectAdapter", " #Error : "+e, e);
        }

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (status == 1) {

                    Toast.makeText(_context, "Feedback already submitted", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(_context, AssessorFeedbackActivity.class);
                    intent.putExtra("stu_id", itemList.get(i).getStudentId());
                    _context.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return response.getStudentDetails().size();
    }

}
