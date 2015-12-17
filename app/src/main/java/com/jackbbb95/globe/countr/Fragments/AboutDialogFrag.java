package com.jackbbb95.globe.countr.Fragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.jackbbb95.globe.countr.R;

public class AboutDialogFrag extends DialogFragment {

    private Toolbar tb;

    @Override
    public void onStart(){
        super.onStart();
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setLayout(width,height);
    } //On the startNum of the dialog creation, sets the size of the dialog box

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_about_dialog, container);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        tb = (Toolbar) view.findViewById(R.id.about_countr_toolbar);
        tb.setTitle("About Countr");






        return view;
    }
}
