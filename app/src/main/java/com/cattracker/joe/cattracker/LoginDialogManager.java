package com.cattracker.joe.cattracker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by JOSEPH on 8/26/2017.
 */

class LoginDialogManager {

    public void showAlertDialog(Context context, String title, String message,
                                Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);

        if(status != null)
            alertDialog.setIcon((status) ? R.drawable.success : R.drawable.failure);

        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        alertDialog.show();
    }
}
