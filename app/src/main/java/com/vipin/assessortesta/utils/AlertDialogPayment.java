package com.vipin.assessortesta.utils;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.vipin.assessortesta.R;

/**
 * Created by senzec on 1/12/17.
 */

public class AlertDialogPayment {

    private static AlertDialogPayment alertClass = null;
    private Dialog dialog;

    public AlertDialogPayment()
    {

    }
    public static AlertDialogPayment getInstance()
    {
        if(alertClass == null)
        {
            alertClass = new AlertDialogPayment();
        }
//        return alertClass = new AlertDialogSingleClick();
        return alertClass ;

    }

    public void showDialog(Activity activity){

        if (dialog != null && dialog.isShowing()) {
            return;
        }

            dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.layout_payment_dialog);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            ImageView dialogBtn_cancel = dialog.findViewById(R.id.ivCloseDialog);
            dialogBtn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(getApplicationContext(),"Cancel" ,Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });

        dialog.show();

        }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }



    }