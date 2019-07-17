package com.vipin.assessortesta.Initials;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.vipin.assessortesta.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//
///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link Upcoming.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link Upcoming#newInstance} factory method to
// * create an instance of this fragment.
// */
public class Upcoming extends Fragment {

    ArrayList<String> batchid=new ArrayList<>();
    ArrayList<String> batchname=new ArrayList<>();
    ArrayList<String> totalstudents=new ArrayList<>();
    ArrayList<String> centername=new ArrayList<>();
    ArrayList<String> centerid=new ArrayList<>();
    ArrayList<String> startdate=new ArrayList<>();

    LinearLayout upcomingfragment;

    Context ctx;

    SharedPreferences sharedpreferences;
    final String mypreference = "mypref";
    String assessor_id;

    View v;
    ShimmerFrameLayout c;
    private RecyclerView myrecyclerview;
    private List<UpcomingModel> lstBatch = new ArrayList<>();


    public Upcoming() { }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

         v = inflater.inflate(R.layout.fragment_upcoming,container,false);
         myrecyclerview = v.findViewById(R.id.Upcoming_recyclerview);
         upcomingfragment=v.findViewById(R.id.upcomingfragment);
        ctx=container.getContext();
        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));


        JSONObject mainJObj = ((AssessorTask)getActivity()).getApiData();
        setData1(mainJObj);


        return v;

    }


    private void setData1(JSONObject jsonObject){
        JSONArray jsonArray = null;
        try {
            jsonArray = jsonObject.getJSONArray("upcoming_batch");


            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject c = jsonArray.getJSONObject(i);

                String batchId = c.getString("batchid");
                String batchName = c.getString("batch_name");
                String numOfStudents = c.getString("number_of_students");
                String examCenterName = c.getString("exam_center_name");
                String startDate = c.getString("startdate");
                String examCenterId = c.getString("exam_center_id");

                int feedbackPerc = c.getInt("feedback_percentage");
                int alignStudentPerc = c.getInt("align_student_percentage");
                int annexureMPhotoPerc = c.getInt("annexure_m_photo_percentage");
                int annexureMStatusPerc = c.getInt("annexure_m_status_percentage");
                int groupPhoto = c.getInt("group_photo");
                int attendancePerc = c.getInt("attendance_percentage");


                int perc = (feedbackPerc + alignStudentPerc + annexureMPhotoPerc + annexureMStatusPerc + groupPhoto + attendancePerc)/6;

                lstBatch.add(new UpcomingModel(batchName, numOfStudents, startDate, examCenterName, examCenterId, batchId, perc));

            }
            RecyclerViewAdapter recyclerViewAdapter = new
                    RecyclerViewAdapter(getContext(), lstBatch);
            myrecyclerview.setAdapter(recyclerViewAdapter);

            myrecyclerview.setAdapter(recyclerViewAdapter);
/*
            for (int i = 0; i <= batchname.size() - 1; i++) {
                lstBatch.add(new UpcomingModel(batchname.get(i), totalstudents.get(i), startdate.get(i), centername.get(i), centerid.get(i),batchid.get(i)));
            }*/

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }





//    private void getBatches() {
//
//        progressDialog.show();
//
//
//        String serverURL = CommonUtils.url+"get_assigned_batch.php";
//
//        StringRequest request = new StringRequest(Request.Method.POST, serverURL, new Response.Listener<String>() {
//            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//            @Override
//            public void onResponse(String response) {
//                System.out.println("response is" + response);
//                try {
//                    JSONObject jobj = new JSONObject(response);
//                    String status = jobj.getString("status");
//
//                    if (status.equals("1")) {
//
//                        JSONObject jobb = jobj.getJSONObject("batch_details");
//                        JSONArray jsonArray = jobb.getJSONArray("upcoming_batch");
//
//
//
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            JSONObject c = jsonArray.getJSONObject(i);
//
//                            if (batchid.size() <= jsonArray.length() - 1) {
//                                batchid.add(c.getString("batchid"));
//                                System.out.println("batchidd  " +batchid);
//                            }
//                            if (batchname.size() <= jsonArray.length() - 1) {
//                                batchname.add(c.getString("batch_name"));
//
//                            }
//                            if (totalstudents.size() <= jsonArray.length() - 1) {
//                                totalstudents.add(c.getString("number_of_students"));
//                            }
//                            if (centername.size() <= jsonArray.length() - 1) {
//                                centername.add(c.getString("exam_center_name"));
//                            }
//                            if (startdate.size() <= jsonArray.length() - 1) {
//                                startdate.add(c.getString("startdate"));
//                            }
//                            if (centerid.size() <= jsonArray.length() - 1) {
//                                centerid.add(c.getString("exam_center_id"));
//                            }
//
//                        }
//                        if(batchid.size()==0){
//                            upcomingfragment.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.no_assess));
//                        }
//
//                        RecyclerViewAdapter recyclerViewAdapter = new
//                                RecyclerViewAdapter(getContext(), lstBatch);
//                        myrecyclerview.setAdapter(recyclerViewAdapter);
//
//                        for (int i =0;i<=batchname.size()-1; i++ ){
//                            lstBatch.add(new UpcomingModel(batchname.get(i),totalstudents.get(i), startdate.get(i),centername.get(i), centerid.get(i),batchid.get(i)));
//                        }
////c.stopShimmer();
//                    } else {
//                        Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
//                    }
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                if (progressDialog.isShowing()) {
//                    progressDialog.dismiss();
//                }
//
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                   if (progressDialog.isShowing()) {
//                   progressDialog.dismiss();
//                }
//              //  Toast.makeText(getContext(), "Error: Please try again Later"+error, Toast.LENGTH_LONG).show();
//
//                System.out.println("ccccccccccc"+error);
//            }
//        }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                super.getHeaders();
//                Map<String, String> map = new HashMap<>();
//
//                return map;
//            }
//
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                super.getParams();
//                Map<String, String> map = new HashMap<>();
//                map.put("key_salt", "UmFkaWFudEluZm9uZXRTYWx0S2V5");
//                map.put("user_name", assessor_id);
//                System.out.println("ddd" + map);
//                return map;
//            }
//        };
//        request.setRetryPolicy(new DefaultRetryPolicy(20000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        MyNetwork.getInstance(getContext()).addToRequestQueue(request);
//    }
}


