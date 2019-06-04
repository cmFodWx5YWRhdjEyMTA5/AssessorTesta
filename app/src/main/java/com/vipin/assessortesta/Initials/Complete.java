package com.vipin.assessortesta.Initials;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.vipin.assessortesta.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dmax.dialog.SpotsDialog;


///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link Complete.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link Complete#newInstance} factory method to
// * create an instance of this fragment.
// */
public class Complete extends Fragment {


    ArrayList<String> batchid = new ArrayList<>();
    ArrayList<String> batchname = new ArrayList<>();
    ArrayList<String> totalstudents = new ArrayList<>();
    ArrayList<String> centername = new ArrayList<>();
    ArrayList<String> centerid = new ArrayList<>();
    ArrayList<String> startdate = new ArrayList<>();

    Context ctx;


    View v;
    private RecyclerView myrecyclerview;
    private List<Complete1> lstBatch;
    private android.app.AlertDialog progressDialog;


    //calling the page adapter

    public Complete() {


    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //get the view of fragment

        v = inflater.inflate(R.layout.fragment_complete, container, false);
        ctx = container.getContext();
        //get the view of recyclerview which  is in fragment

        myrecyclerview = v.findViewById(R.id.Complete_recyclerview);


        RecyclerViewAdpterCompleted recyclerViewAdapter = new
                RecyclerViewAdpterCompleted(getContext(), lstBatch);

        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        //setadapter in recyclerview
        myrecyclerview.setAdapter(recyclerViewAdapter);

        return v;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    //Create the cardview


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new SpotsDialog(getActivity(), R.style.Custom);

        lstBatch = new ArrayList<>();
        getBatches();


    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void getBatches() {


        progressDialog.show();

        String serverURL = "https://www.skillassessment.org/sdms/android_connect1/assessor/get_assigned_batch.php";

        StringRequest request = new StringRequest(Request.Method.POST, serverURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("response is" + response);
                try {
                    JSONObject jobj = new JSONObject(response);
                    String status = jobj.getString("status");

                    if (status.equals("1")) {

                        JSONObject jobb = jobj.getJSONObject("batch_details");
                        JSONArray jsonArray = jobb.getJSONArray("previous_batch");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject c = jsonArray.getJSONObject(i);

                            if (batchid.size() <= jsonArray.length() - 1) {
                                batchid.add(c.getString("batchid"));
                            }
                            if (batchname.size() <= jsonArray.length() - 1) {
                                batchname.add(c.getString("batch_name"));
                            }
                            if (totalstudents.size() <= jsonArray.length() - 1) {
                                totalstudents.add(c.getString("number_of_students"));
                            }
                            if (centername.size() <= jsonArray.length() - 1) {
                                centername.add(c.getString("exam_center_name"));
                            }
                            if (startdate.size() <= jsonArray.length() - 1) {
                                startdate.add(c.getString("startdate"));
                            }
                            if (centerid.size() <= jsonArray.length() - 1) {
                                centerid.add(c.getString("exam_center_id"));
                            }

                        }
                        RecyclerViewAdpterCompleted recyclerViewAdapter = new
                                RecyclerViewAdpterCompleted(getContext(), lstBatch);

                        myrecyclerview.setAdapter(recyclerViewAdapter);

                        for (int i = 0; i <= batchname.size() - 1; i++) {
                            lstBatch.add(new Complete1(batchname.get(i), totalstudents.get(i), startdate.get(i), centername.get(i), centerid.get(i)));
                        }
//c.stopShimmer();
                    } else {
                        Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }


             /*   if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }*/
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Toast.makeText(getContext(), "Error: Please try again Later", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                super.getHeaders();
                Map<String, String> map = new HashMap<>();

                return map;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                Map<String, String> map = new HashMap<>();
                map.put("key_salt", "UmFkaWFudEluZm9uZXRTYWx0S2V5");
                map.put("user_name", "pbharti@radiantinfonet.com");
                System.out.println("ddd" + map);
                return map;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(20000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyNetwork.getInstance(getContext()).addToRequestQueue(request);
    }


}
