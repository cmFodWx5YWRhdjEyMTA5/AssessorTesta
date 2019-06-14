package com.vipin.assessortesta.Ass_Registration.pojo.certificate;

import com.google.firebase.abt.FirebaseABTesting;

public class JobrolesItem {

    public JobrolesItem(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    String name;
    int id;

}
