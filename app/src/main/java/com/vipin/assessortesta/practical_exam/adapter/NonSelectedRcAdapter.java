package com.vipin.assessortesta.practical_exam.adapter;

import android.content.Context;
import android.graphics.Color;
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

public class NonSelectedRcAdapter  extends RecyclerView.Adapter<NonSelectedRcAdapter.MyViewHolder> {

    Context _context;
    public NonSelectedRcAdapter(Context _context){
        this._context = _context;
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

        myViewHolder.tvStudentName.setText("Student Name - "+(i+1));
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myViewHolder.frameSelectBg.setBackgroundColor(_context.getResources().getColor(R.color.colorSelectBg));
                Drawable d = (Drawable)_context.getResources().getDrawable(R.drawable.ic_check_circle_24dp);
                myViewHolder.ivTick.setImageDrawable(d);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

}
