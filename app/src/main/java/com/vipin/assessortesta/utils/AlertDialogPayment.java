package com.vipin.assessortesta.utils;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vipin.assessortesta.R;

import static android.content.Context.CLIPBOARD_SERVICE;

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

        TextView tvBankAcNO = (TextView)dialog.findViewById(R.id.tvBankAcNO);
        TextView tvIFSC = (TextView)dialog.findViewById(R.id.tvIFSC);

        tvBankAcNO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Bank Account Number", "180401000010000");
                clipboard.setPrimaryClip(clip);
                Toast.makeText(activity, "Bank Account Number Copied", Toast.LENGTH_SHORT).show();
            }
        });

        tvIFSC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("IFSC/RTGS code", "IOBA0001804");
                clipboard.setPrimaryClip(clip);
                Toast.makeText(activity, "IFSC/RTGS code Copied", Toast.LENGTH_SHORT).show();
            }
        });



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