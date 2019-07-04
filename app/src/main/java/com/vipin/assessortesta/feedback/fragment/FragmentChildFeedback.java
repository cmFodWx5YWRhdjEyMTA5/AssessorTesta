package com.vipin.assessortesta.feedback.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.vipin.assessortesta.Assessor_Exam.GetStatusQue;
import com.vipin.assessortesta.R;
import com.vipin.assessortesta.feedback.AssessorFeedbackActivity;
import com.vipin.assessortesta.feedback.adapter.FeedbackRcAdapter;
import com.vipin.assessortesta.pojo.feedback.FeedbackResponse;

import java.util.HashMap;

public class FragmentChildFeedback extends Fragment implements View.OnClickListener {

    private RecyclerView rcFeedback;
    private TextView tvText;
    private ImageView ivScrollDown;
    private NestedScrollView scrollView;

    String quename;
    int pgnn;
    String query;
    HashMap<String,String> hm=new HashMap<>();
    private static GetStatusQue getStatusQue;
    FeedbackResponse feedbackResponse;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_child_feedback, container, false);
        Bundle bundle = getArguments();
        pgnn=bundle.getInt("pgno");
//        childname = bundle.getString("data");
//        quename =bundle.getString("daa");

//        hm.put(quename,childname);
        getIDs(view);
        manageView();
        System.out.println("abcc"+pgnn);

        tvText.setText(""+pgnn);
        return view;
    }

    private void getIDs(View view) {
        rcFeedback = (RecyclerView)view.findViewById(R.id.rcFeedback);
        tvText = (TextView) view.findViewById(R.id.tvText);
        scrollView = (NestedScrollView) view.findViewById(R.id.scrollView);
        ivScrollDown = (ImageView) view.findViewById(R.id.ivScrollDown);


    }
    private void manageView(){
        feedbackResponse = ((AssessorFeedbackActivity)getActivity()).getApiRespData();
        ivScrollDown.setOnClickListener(this::onClick);


        if (feedbackResponse != null){
            funcNonSelectedStudent();
        }
    }

    private void funcNonSelectedStudent() {

        rcFeedback.setLayoutManager(new LinearLayoutManager(getContext()));
        FeedbackRcAdapter adapter = new FeedbackRcAdapter(getContext(), feedbackResponse, pgnn);
        rcFeedback.setAdapter(adapter);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        scrollView.getViewTreeObserver()
                .addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged() {
                        if (scrollView.getChildAt(0).getBottom()
                                <= (scrollView.getHeight() + scrollView.getScrollY())) {
                            //scroll view is at bottom
                            ivScrollDown.setVisibility(View.GONE);
                        } else {
                            //scroll view is not at bottom
                            ivScrollDown.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }
    public static void aaa(GetStatusQue gettt){
        getStatusQue=gettt;
    }
    @Override
    public void setUserVisibleHint(boolean visible)
    {
        super.setUserVisibleHint(visible);
        if (visible && isResumed())
        {
            onResume();
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        String aab;
        if (!getUserVisibleHint())
        {
            return;
        }
        query=hm.get(quename);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ivScrollDown:
//                scrollView.fullScroll(View.FOCUS_DOWN);

                final int scrollViewHeight = scrollView.getHeight();

                if (scrollViewHeight > 0) {
//                    scrollView.getViewTreeObserver().removeOnGlobalLayoutListener(getActivity());

                    final View lastView = scrollView.getChildAt(scrollView.getChildCount() - 1);
                    final int lastViewBottom = lastView.getBottom() + scrollView.getPaddingBottom();
                    final int deltaScrollY = lastViewBottom - scrollViewHeight - scrollView.getScrollY();
                    /* If you want to see the scroll animation, call this. */
                    scrollView.smoothScrollBy(0, deltaScrollY);
                    /* If you don't want, call this. */
                    scrollView.scrollBy(0, deltaScrollY);
                }

                break;
        }
    }
}
