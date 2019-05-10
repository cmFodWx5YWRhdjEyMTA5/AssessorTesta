package com.vipin.assessortesta.Initials;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vipin.assessortesta.R;

import java.util.ArrayList;
import java.util.List;

///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link Complete.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link Complete#newInstance} factory method to
// * create an instance of this fragment.
// */
public class Complete extends Fragment {

    View v;
    private RecyclerView myrecyclerview;
    private List<Complete1> lstBatch;




    //calling the page adapter

    public Complete(){


    }




    public  View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){

        //get the view of fragment

        v = inflater.inflate(R.layout.fragment_complete,container,false);

        //get the view of recyclerview which  is in fragment

        myrecyclerview = v.findViewById(R.id.Complete_recyclerview);

        RecyclerViewAdpterCompleted recyclerViewAdapter = new
                RecyclerViewAdpterCompleted(getContext(),lstBatch);

        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        //setadapter in recyclerview
        myrecyclerview.setAdapter(recyclerViewAdapter);

        return v;

    }



    //Create the cardview


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {


        //Create the cardview(put the item in cardview)

        lstBatch = new ArrayList<>();
        for (int i =0;i<=10; i++ ){
            lstBatch.add(new Complete1("Tc01","34","24/5/2019","Up"));


        }

        super.onCreate(savedInstanceState);
    }



    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

















}
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    private OnFragmentInteractionListener mListener;
//
//    public Complete() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment Complete.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static Complete newInstance(String param1, String param2) {
//        Complete fragment = new Complete();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_complete, container, false);
//    }
//
//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
//}
