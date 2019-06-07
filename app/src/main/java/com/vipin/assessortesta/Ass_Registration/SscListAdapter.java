package com.vipin.assessortesta.Ass_Registration;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.vipin.assessortesta.Ass_Registration.db.AcademicDbModel;
import com.vipin.assessortesta.Ass_Registration.db.DBAdapterClass;
import com.vipin.assessortesta.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SscListAdapter extends BaseAdapter {

    private static final String TAG = "CustomAcademicAdapter";
    Context mContext;
    LayoutInflater inflater;
    EditText input_cert_no;
    Spinner spnJobRole;
    List<AllCollegeNameModel.Result> result;
    private ArrayList<String> mStringList;
    ArrayList<AcademicDbModel> academicDbModelArrayList ;
    private DBAdapterClass dbAdapterClass;

    int size;
    public SscListAdapter(Context mContext, int size, List<AllCollegeNameModel.Result> result){
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        this.size = size;
        this.result = result;

        mStringList = new ArrayList<>(Arrays.asList(groupName(result)));
    }
    @Override
    public int getCount() {
        return size;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.row_ssc_layout, null);

        spnJobRole = (Spinner)view.findViewById(R.id.spnJobRole);
        input_cert_no = (EditText) view.findViewById(R.id.input_cert_no);


        /*
        if (mStringList.size() > 0) {
            ArrayAdapter arrayAdapter =
                    new ArrayAdapter(mContext, android.R.layout.simple_list_item_1, mStringList);
            spnCollege.setAdapter(arrayAdapter);
            // OFFLINE DATA
            try {
                if (!getOfflineData(view).isEmpty() && i < getOfflineData(view).size()) {
                    tvQualification.setText(academicDbModelArrayList.get(i).getProfile1());
                    //            tvDegree.setText(academicDbModelArrayList.get(i).getProfile2());
                    tvJoinYear.setText(academicDbModelArrayList.get(i).getProfile3());
                    tvFinalYear.setText(academicDbModelArrayList.get(i).getProfile4());

                    int spinnerPosition =
                            arrayAdapter.getPosition(academicDbModelArrayList.get(i).getProfile2());
                    spnCollege.setSelection(spinnerPosition);
                }
            } catch (ArrayIndexOutOfBoundsException aiobe) {
                Log.e(TAG, " #Error : " + aiobe, aiobe);
            }
        }else {

            // OFFLINE DATA
            try {
                if (!getOfflineData(view).isEmpty() && i < getOfflineData(view).size()) {
                    tvQualification.setText(academicDbModelArrayList.get(i).getProfile1());
                    //            tvDegree.setText(academicDbModelArrayList.get(i).getProfile2());
                    tvJoinYear.setText(academicDbModelArrayList.get(i).getProfile3());
                    tvFinalYear.setText(academicDbModelArrayList.get(i).getProfile4());

                }
            } catch (ArrayIndexOutOfBoundsException aiobe) {
                Log.e(TAG, " #Error : " + aiobe, aiobe);
            }
        }
        */

        return view;
    }


    private String[] groupName(List<AllCollegeNameModel.Result> result){
        /*List<AllCollegeNameModel.Result> resource=result;
        String[] groupName = new String[resource.size()];
        if(resource!=null){
            for(int i=0;i<resource.size();i++){
                AllCollegeNameModel.Result result1 =resource.get(i);
                groupName[i]=result1.colleges_name;
            }
            return groupName;
        }
        return groupName;*/
        return new String[]{"ABC", "XYZ"};
    }

    public ArrayList<AcademicDbModel> getOfflineData(View view){
        dbAdapterClass = new DBAdapterClass(view.getContext());
        dbAdapterClass.createDatabase();
        dbAdapterClass.open();  // --- open database connection
        academicDbModelArrayList = new ArrayList<>();
        academicDbModelArrayList = dbAdapterClass.retrieveAcademicData();
        return academicDbModelArrayList;
    }

}
