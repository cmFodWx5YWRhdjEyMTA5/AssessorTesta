package com.vipin.assessortesta.utils;

public interface CommonUtils {


    int VIDEO_TIMER = 10000;

     String  url = "https://www.skillassessment.org/sdms/android_connect1/assessor/";
     String url2="https://www.skillassessment.org/sdms/android_connect1/assessor/exam/";

     String MAIN_URL = CommonUtils.url2;

    String serverURL_batchquestions = MAIN_URL+"batch_questions.php";
    String serverURL2_saveproctoring = MAIN_URL+"save_proctoring.php";
    String serverURL_batchlanguage = MAIN_URL+"get_batch_language.php";
    String serverURL_saveanswer = MAIN_URL+"save_answers.php";
    String serverURL_savelog = MAIN_URL+"save_logs.php";

}
