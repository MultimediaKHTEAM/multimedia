package com.example.trungnguyen.mymusicplayer;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by Trung Nguyen on 11/16/2016.
 */
public class AlertDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);
        Context myContext = getActivity();
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(myContext);
        builder.setTitle("Oops! Sorry!").setMessage("There was no network connection! please try again.")
                .setPositiveButton("OK", null);
        Dialog dialog = builder.create();
        return dialog;
    }
}
