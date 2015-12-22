package com.jackbbb95.globe.countr.Fragments;


import android.content.Context;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import com.jackbbb95.globe.countr.Countr;
import com.jackbbb95.globe.countr.R;


public class CreateCountrDialogFrag extends DialogFragment {

    //runs onFinishCountrDialog when creation is done
    public interface CreateCountrDialogListener{
        void onFinishCreateCountr(Countr newCountr);
    }

    private EditText name; //the EditText box where the user inpts the name of the Countr
    private EditText startNum; //the EditText where the user chooses the startNum number
    private EditText interval; //the EditText that gives choices of intervals to count by


    public CreateCountrDialogFrag(){} //default constructor for CreateCountrDialogFrag

    //sets the dialog size on start
    @Override
    public void onStart(){
        super.onStart();
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setLayout(width,height);

    }

    /*
    Brings up the dialog that asks the user to input parameters that are fed to a new Countr Object
    which will appear in the List view in the Main Activity
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.create_countr_dialog_frag,container);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        //for the name of the countr
        name = (EditText) view.findViewById(R.id.countr_name);
        TextInputLayout nameTil = (TextInputLayout) view.findViewById(R.id.countr_name_til);
        nameTil.setHint("Countr Name");
        name.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY); //show keyboard

        //for the startNum number
        startNum = (EditText)view.findViewById(R.id.countr_start_num);
        TextInputLayout startNumTil = (TextInputLayout) view.findViewById(R.id.countr_start_til);
        startNumTil.setHint("Starting Number");
        startNum.setInputType(InputType.TYPE_CLASS_NUMBER);
        imm.showSoftInput((startNum), InputMethodManager.SHOW_IMPLICIT);

        //for the interval to count by
        interval = (EditText)view.findViewById(R.id.countr_interval);
        TextInputLayout intervalTil = (TextInputLayout) view.findViewById(R.id.countr_interval_til);
        intervalTil.setHint("Count Interval");
        interval.setInputType(InputType.TYPE_CLASS_NUMBER);
        imm.showSoftInput((interval), InputMethodManager.SHOW_IMPLICIT);

        //button to actually create the Countr
        Button create = (Button) view.findViewById(R.id.create_button);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.length() < 1) {
                    Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
                    name.startAnimation(shake);
                } else if (!TextUtils.isDigitsOnly(startNum.getText())) {
                    Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
                    startNum.startAnimation(shake);
                } else if (!TextUtils.isDigitsOnly(interval.getText())) {
                    Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
                    interval.startAnimation(shake);
                } else {
                    int startAndCurNumber;
                    int intervalNumber;
                    String countrName = name.getText().toString();
                    if (startNum.getText().length() == 0) {
                        startAndCurNumber = 0;
                    } else {
                        startAndCurNumber = Integer.parseInt(startNum.getText().toString());
                    }
                    if (interval.getText().length() == 0) {
                        intervalNumber = 1;
                    } else {
                        intervalNumber = Integer.parseInt(interval.getText().toString());
                    }
                    CreateCountrDialogListener activity = (CreateCountrDialogListener) getActivity();
                    activity.onFinishCreateCountr(new Countr(countrName, startAndCurNumber, intervalNumber, startAndCurNumber, 0));
                    getDialog().dismiss();
                }

            }
        });


        // button to cancel the dialog and thus not create the Countr
        Button cancel = (Button) view.findViewById(R.id.cancel_button);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        return view;
    }

}
