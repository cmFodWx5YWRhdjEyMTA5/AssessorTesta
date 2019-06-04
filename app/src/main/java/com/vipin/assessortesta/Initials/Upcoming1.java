package com.vipin.assessortesta.Initials;

public class Upcoming1 {

    private String Batchname;
    private String Totalstudent;
    private String Assessmentdate;
    private String TcName;
    private String Centeridd;
    private String Batchid;

    public Upcoming1(String batchname, String totalstudent, String assessmentdate, String tcName, String centeridd, String batchid) {
        Batchname = batchname;
        Totalstudent = totalstudent;
        Assessmentdate = assessmentdate;
        TcName = tcName;
        Centeridd = centeridd;
        Batchid = batchid;
        System.out.println("batchname is" + Batchname);
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

    public String getcenterid() {
        return Centeridd;
    }

    public String getBatchid() {
        return Batchid;
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

    public void setcenterid(String centeridd) {
        centeridd = centeridd;


    }

    ;

    public void setBatchid(String batchid) {
        Batchid = batchid;
    }
}
