package com.vipin.assessortesta.Ass_Registration;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ravi on 24/10/17.
 */

public class AllCollegeNameModel implements Serializable {
    public String response_code;
    public String response_message;
    public List<Result> results;

   public class Result implements Serializable{
        public String _id;
        public String colleges_name;
    }
}
