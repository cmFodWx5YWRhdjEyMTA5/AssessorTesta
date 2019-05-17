package com.vipin.assessortesta.Initials;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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


    View v;

    private RecyclerView myrecyclerview;
    private List<Upcoming1>lstBatch;

    public Upcoming(){


    }
    public  View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){

         v = inflater.inflate(R.layout.fragment_upcoming,container,false);
         myrecyclerview = v.findViewById(R.id.Upcoming_recyclerview);
         RecyclerViewAdapter recyclerViewAdapter = new
                 RecyclerViewAdapter(getContext(),lstBatch);

         myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
         myrecyclerview.setAdapter(recyclerViewAdapter);



         return v;





    }








    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lstBatch = new ArrayList<>();
        getBatches();
        for (int i =0;i<=10; i++ ){
            lstBatch.add(new Upcoming1("Tc01","34","24/5/2019","Noida"));


        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void getBatches() {

        String serverURL = "https://www.skillassessment.org/sdms/android_connect1/assessor/get_assigned_batch.php";

        StringRequest request = new StringRequest(Request.Method.POST, serverURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("response is"+response);
                try {
                    JSONObject jobj = new JSONObject(response);
                    String status= jobj.getString("status");
                    float aab=jobj.getLong("theory_time");
                    System.out.println("response is"+response);
                    if (status.equals("1")){
                      //  alreadyExecuted = true;
                        JSONArray jsonArray=jobj.getJSONArray("batch_details");
                      //  arraysize=jsonArray.length();
                      //  timee=arraysize*60*1000;
                       // System.out.println("bsdfsdf"+timee+"   "+START_TIME_IN_MILLIS);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject c = jsonArray.getJSONObject(i);
                         /*   if (qnooo.size()<=jsonArray.length()-1){qnooo.add(i+1);}
                            if (qnooo1.size()<=jsonArray.length()-1){ qnooo1.add(Integer.toString(i+1));
                            }
                            if (aa.size()<=jsonArray.length()-1){aa.add(c.getString("question_id"));}
                            if (bb.size()<=jsonArray.length()-1){bb.add(c.getString("question"));}
                            if (queid.size()<=jsonArray.length()-1){ queid.add(c.getString("question_id"));}
                            if (options1.size()<=jsonArray.length()-1){options1.add(c.getString("option1"));}
                            if (options2.size()<=jsonArray.length()-1){options2.add(c.getString("option2"));}
                            if (options3.size()<=jsonArray.length()-1){ options3.add(c.getString("option3"));}
                            if (options4.size()<=jsonArray.length()-1){ options4.add(c.getString("option4"));}*/

                        }
                     //   System.out.println("bbbb"+aa);
                       /* for (int ii=0;ii<=aa.size()-1;ii++) {
                            if (dbAutoSave.getDataOfSingleClientstatus(qnooo1.get(ii))==null){
                                dbAutoSave.insertDataunanswered(studentid,qnooo1.get(ii),"3");}
                            fragmentParent.addPage(aa.get(ii) + "", bb.get(ii), qnooo.get(ii), options1.get(ii), options2.get(ii), options3.get(ii), options4.get(ii));
                        }*/




                    }
                    else {
                        Toast.makeText(getContext(),"Error",Toast.LENGTH_LONG).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


             /*   if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }*/
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               /* if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }*/
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
                System.out.println("ddd"+map);
                return map;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(20000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyNetwork.getInstance(getContext()).addToRequestQueue(request);
    }
}


