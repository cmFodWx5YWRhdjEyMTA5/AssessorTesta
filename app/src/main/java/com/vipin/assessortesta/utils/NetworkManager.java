package com.vipin.assessortesta.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

public class NetworkManager {

    private static NetworkManager manager = null;

    public NetworkManager() { }
    public static NetworkManager getInstance()
    {
        if(manager == null)
        {
            manager = new NetworkManager();
        }
//        return alertClass = new AlertDialogSingleClick();
        return manager ;

    }

    public boolean isOnline(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }




    }