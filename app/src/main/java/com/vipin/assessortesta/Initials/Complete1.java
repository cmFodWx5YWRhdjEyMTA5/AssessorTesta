package com.vipin.assessortesta.Initials;

public class Complete1
{
    //declare the variable
    private String Batchname;
        private String Totalstudent;
        private String Assessmentdate;
        private String TcName;
    private String Centeridd;


    public Complete1(String batchname, String totalstudent, String assessmentdate, String tcName, String centeridd) {
            Batchname = batchname;
            Totalstudent = totalstudent;
            Assessmentdate = assessmentdate;
            TcName = tcName;
        Centeridd=centeridd;

    }




        //getter

        public String getBatchname() {
            return Batchname;
        }

        public String getTotalstudent() {
            return Totalstudent;
        }

        public String getAssessmentdate() {
            return Assessmentdate;
        }

        public String getTcName() {
            return TcName;
        }


    public String getcenterid(){
        return Centeridd;
    }


        //setter

        public void setBatchname(String batchname) {
            Batchname = batchname;
        }

        public void setTotalstudent(String totalstudent) {
            Totalstudent = totalstudent;
        }

        public void setAssessmentdate(String assessmentdate) {
            Assessmentdate = assessmentdate;
        }

        public void setTcName(String tcName) {
            TcName = tcName;
        }

    public void setcenterid(String centeridd){
        centeridd=centeridd;


    };


}
