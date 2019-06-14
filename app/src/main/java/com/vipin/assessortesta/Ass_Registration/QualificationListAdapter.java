package com.vipin.assessortesta.Ass_Registration;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.obsez.android.lib.filechooser.ChooserDialog;
import com.vipin.assessortesta.Ass_Registration.db.AcademicDbModel;
import com.vipin.assessortesta.Ass_Registration.db.DBAdapterClass;
import com.vipin.assessortesta.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QualificationListAdapter extends BaseAdapter {

    private static final String TAG = "CustomAcademicAdapter";
    Context mContext;
    LayoutInflater inflater;
    EditText input_cert_no;
    TextView tvCertTitle;
    Spinner spnQualification;
    List<AllCollegeNameModel.Result> result;
    private ArrayList<String> mStringList;
    ArrayList<AcademicDbModel> academicDbModelArrayList ;
    private DBAdapterClass dbAdapterClass;

    int size;
    public QualificationListAdapter(Context mContext, int size, List<AllCollegeNameModel.Result> result){
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
        view = inflater.inflate(R.layout.row_qualification_layout, null);

        tvCertTitle = (TextView)view.findViewById(R.id.tvCertTitle);
        spnQualification = (Spinner)view.findViewById(R.id.spnQualification);
        LinearLayout actionUploadDoc = (LinearLayout) view.findViewById(R.id.actionUploadDoc);
        ImageView ivUpload = (ImageView)view.findViewById(R.id.ivUpload);
        tvCertTitle.setText("Qualification-"+(i+1));
        TextView tvDoc = (TextView) view.findViewById(R.id.tvDoc);
        TextView tvDocText = (TextView) view.findViewById(R.id.tvDocText);
/*

        if (certificateResponse != null) {
            List<JobrolesItem> jobrolesItemList = certificateResponse.getJobroles();

            List<String> list = new ArrayList<>();
            for (JobrolesItem jobrolesItem: jobrolesItemList){
                list.add(jobrolesItem.getName());
            }
            list.add(0, "Select Certificate");
            ArrayAdapter arrayAdapter =
                    new ArrayAdapter(mContext, android.R.layout.simple_list_item_1, list);
            spnr.setAdapter(arrayAdapter);
        }
*/


//        if (jobrolesItemList.size() > 0) {

            // OFFLINE DATA
            /*try {
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
            }*/
//        }else {

            // OFFLINE DATA
            /*try {
                if (!getOfflineData(view).isEmpty() && i < getOfflineData(view).size()) {
                    tvQualification.setText(academicDbModelArrayList.get(i).getProfile1());
                    //            tvDegree.setText(academicDbModelArrayList.get(i).getProfile2());
                    tvJoinYear.setText(academicDbModelArrayList.get(i).getProfile3());
                    tvFinalYear.setText(academicDbModelArrayList.get(i).getProfile4());

                }
            } catch (ArrayIndexOutOfBoundsException aiobe) {
                Log.e(TAG, " #Error : " + aiobe, aiobe);
            }*/
//        }

        actionUploadDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String path = Environment.getExternalStorageDirectory().toString()+ File.separator + Environment.DIRECTORY_DOWNLOADS;

//                File file = new File(path);

                new ChooserDialog().with(mContext)
                        .withFilter(false, false, "pdf", "doc", "docx", "jpeg", "png", "jpg")
                        .withStartFile(path)
                        .withResources(R.string.title_choose_file, R.string.title_choose, R.string.dialog_cancel)
                        .withChosenListener(new ChooserDialog.Result() {
                            @Override
                            public void onChoosePath(String path, File pathFile) {
//                                Toast.makeText(mContext, "FILE: " + path, Toast.LENGTH_SHORT).show();
//                                System.out.println(pathFile);

                                if (pathFile.length() > 0) {
                                    int fileSize = Integer.parseInt(String.valueOf(pathFile.length() / 1024));

                                    if (fileSize <= 500) {

                                        String sPath = path.substring(path.lastIndexOf("/") + 1);
                                        tvDoc.setText(path);
                                        tvDocText.setText(sPath);
                                        tvDocText.setTextColor(mContext.getResources().getColor(R.color.button2));
                                        ivUpload.setImageResource(R.drawable.ic_file_done);
                                        ivUpload.setColorFilter(ContextCompat.getColor(mContext, R.color.button2), android.graphics.PorterDuff.Mode.SRC_IN);

                                    } else {
                                        new AlertDialog.Builder(mContext)
                                                .setIcon(R.drawable.ic_complain)
                                                .setTitle("Warning")
                                                .setMessage("File size should be between 1-500KB")
                                                .setNegativeButton("OK", null)
                                                .show();
                                    }
                                }
                            }
                        })
                        .build()
                        .show();

            }
        });

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
