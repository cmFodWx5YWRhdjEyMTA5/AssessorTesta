package com.vipin.assessortesta.practical_student_assign.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
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
import com.vipin.assessortesta.pojo.stu_list.StudentDetailsItem;
import com.vipin.assessortesta.pojo.stu_list.StudentListResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NonSelectedRcAdapter  extends RecyclerView.Adapter<NonSelectedRcAdapter.MyViewHolder> {

    Context _context;
    StudentListResponse response;
    Map<String, Boolean> statusMap;

    public NonSelectedRcAdapter(Context _context, StudentListResponse response, Map<String, Boolean> statusMap){
        this._context = _context;
        this.response = response;
        this.statusMap = statusMap;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        FrameLayout frameSelectBg;
        ImageView ivTick;
        TextView tvStudentName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            frameSelectBg = (FrameLayout)itemView.findViewById(R.id.frameSelectBg);
            ivTick = (ImageView) itemView.findViewById(R.id.ivTick);
            tvStudentName = (TextView) itemView.findViewById(R.id.tvStudentName);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(_context).inflate(R.layout.row_non_selected_st_layout, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.setIsRecyclable(false);
        List<StudentDetailsItem> itemList = response.getStudentDetails();
        myViewHolder.tvStudentName.setText(itemList.get(i).getName());
        myViewHolder.ivTick.setTag("None");

        try {
            if (statusMap.get(itemList.get(i).getStudentId()) == true) {
                myViewHolder.frameSelectBg.setBackgroundColor(_context.getResources().getColor(R.color.colorSelectBg));
                Drawable d = (Drawable) _context.getResources().getDrawable(R.drawable.ic_check_circle_24dp);
                myViewHolder.ivTick.setImageDrawable(d);
                myViewHolder.ivTick.setTag("Selected");
            }
        }catch (Exception e){
            Log.e("NonSelectAdapter", " #Error : "+e, e);
        }

    }

    @Override
    public int getItemCount() {
        return response.getStudentDetails().size();
    }

}
