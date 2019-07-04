package com.vipin.assessortesta.student_group;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.vipin.assessortesta.R;
import com.vipin.assessortesta.pojo.practical_que.PracticalQuesResponse;
import com.vipin.assessortesta.practical_student_assign.StudentAssignActivity;

public class StudentGroupAdapter extends RecyclerView.Adapter<StudentGroupAdapter.MyViewHolder>{

    private Context _context;
    PracticalQuesResponse response;
    StudentGroupAdapter(Context _context, PracticalQuesResponse response){
    this._context = _context;
    this.response = response;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView ivGmailLetter;
        TextView tvGroupName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            tvGroupName = (TextView) itemView.findViewById(R.id.tvGroupName);
            ivGmailLetter = (ImageView)itemView.findViewById(R.id.ivGmailLetter);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(_context).inflate(R.layout.row_student_group, null);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        if (i == -1) {
            myViewHolder.cardView.setCardBackgroundColor(_context.getResources().getColor(R.color.BBlue));
            myViewHolder.tvGroupName.setTextColor(_context.getResources().getColor(R.color.white));

            TextDrawable textDrawable = TextDrawable.builder().beginConfig().textColor(Color.BLACK).endConfig().buildRound("" + (i + 1), Color.WHITE);
            myViewHolder.ivGmailLetter.setImageDrawable(textDrawable);

        }else {
            TypedArray ta = _context.getResources().obtainTypedArray(R.array.arr_group_color);
            int colorToUse = ta.getColor(i, Color.RED);

            TextDrawable textDrawable = TextDrawable.builder().buildRound("" + (i + 1), colorToUse);
            myViewHolder.ivGmailLetter.setImageDrawable(textDrawable);
        }
    }

    @Override
    public int getItemCount() {

        if (response.getTotalQuestions() != null){

            return response.getTotalQuestions();
        }else {
            return 0;
        }
    }

}
