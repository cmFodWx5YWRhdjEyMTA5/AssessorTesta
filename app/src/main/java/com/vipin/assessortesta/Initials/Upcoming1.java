package com.vipin.assessortesta.Initials;

public class Upcoming1 {

    private String Batchname;
    private String Totalstudent;
    private String Assessmentdate;
    private String TcName;

    public Upcoming1(String batchname, String totalstudent, String assessmentdate, String tcName) {
        Batchname = batchname;
        Totalstudent = totalstudent;
        Assessmentdate = assessmentdate;
        TcName = tcName;
        System.out.println("batchname is"+Batchname);
    }

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
}
