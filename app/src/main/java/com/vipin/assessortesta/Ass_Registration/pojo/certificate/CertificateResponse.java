package com.vipin.assessortesta.Ass_Registration.pojo.certificate;

import java.util.List;

public class CertificateResponse {

    public CertificateResponse(String msg, List<JobrolesItem> jobroles, int status) {
        this.msg = msg;
        this.jobroles = jobroles;
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<JobrolesItem> getJobroles() {
        return jobroles;
    }

    public void setJobroles(List<JobrolesItem> jobroles) {
        this.jobroles = jobroles;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    String msg;
    List<JobrolesItem> jobroles;
    int status;

}
