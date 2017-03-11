package com.iamshekhargh.realmexample.StaticClasses;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;

/**
 * Created by <<-- iamShekharGH -->>
 * on 10/03/17.
 */

public class StaticFunctions {

    static boolean showLog = true;

    public static void alerDialog(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public static void alertDialogTwoButtons(Context context,
                                             DialogInterface.OnClickListener positive,
                                             String positiveText,
                                             DialogInterface.OnClickListener negative,
                                             String negetiveText,
                                             String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (positive != null) {
            builder.setPositiveButton(positiveText, positive);
        }
        if (negative != null) {
            builder.setNegativeButton(negetiveText, negative);
        }
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public static void logDotI(String tag, String message) {
        if (showLog) {
            Log.i(tag, message);
        }

    }

}
