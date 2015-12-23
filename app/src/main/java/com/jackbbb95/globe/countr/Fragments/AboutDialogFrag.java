package com.jackbbb95.globe.countr.Fragments;

import android.app.DialogFragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.jackbbb95.globe.countr.R;

public class AboutDialogFrag extends DialogFragment {

    @Override
    public void onStart(){
        super.onStart();
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setLayout(width,height);
    } //On the startNum of the dialog creation, sets the size of the dialog box to match the screen

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_about_dialog, container);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        //setup toolbar
        Toolbar tb = (Toolbar) view.findViewById(R.id.about_countr_toolbar);
        tb.setTitle("");
        //sets up globe text
        TextView globe = (TextView) view.findViewById(R.id.globe);
        Typeface robotoLight = Typeface.createFromAsset(getActivity().getAssets(),"fonts/roboto.light.ttf");
        globe.setTypeface(robotoLight);
        //sets up Dialog Plus link
        TextView dialogPlus = (TextView) view.findViewById(R.id.license_dialog_plus);
        dialogPlus.setMovementMethod(LinkMovementMethod.getInstance());
        //sets up GSON library link
        TextView gson = (TextView) view.findViewById(R.id.license_GSON);
        gson.setMovementMethod(LinkMovementMethod.getInstance());


        return view;
    }
}
