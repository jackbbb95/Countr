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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;

import com.jackbbb95.globe.countr.Countr;
import com.jackbbb95.globe.countr.R;

import org.w3c.dom.Text;

public class CreateCountrDialogFrag extends DialogFragment {

    public interface CreateCountrDialogListener{
        void onFinishCreateCountr(Countr newCountr);
    } //runs onFinishCountrDialog when creation is done

    private EditText name; //the EditText box where the user inpts the name of the Countr
    private EditText startNum; //the EditText where the user chooses the startNum number
    private EditText interval; //the EditText that gives choices of intervals to count by
    private Button create; //the button to create the Countr
    private Button cancel; //the button to close the dialog without creating the Countr
    private InputMethodManager imm;
    private TextInputLayout nameTil;
    private TextInputLayout startNumTil;
    private TextInputLayout intervalTil;
    private Toolbar tb;


    public CreateCountrDialogFrag(){} //default constructor for CreateCountrDialogFrag


    @Override
    public void onStart(){
        super.onStart();
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setLayout(width,height);

    } //On the startNum of the dialog creation, sets the size of the dialog box

    /*
    Brings up the dialog that asks the user to input parameters that are fed to a new Countr Object
    which will appear in the List view in the Main Activity
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.create_countr_dialog_frag,container);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        tb = (Toolbar) view.findViewById(R.id.create_countr_toolbar);
        tb.setTitle("Create Countr");

        //for the name of the countr
        name = (EditText) view.findViewById(R.id.countr_name);
        nameTil = (TextInputLayout) view.findViewById(R.id.countr_name_til);
        nameTil.setHint("Countr Name");
        name.requestFocus();
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY); //show keyboard


        //for the startNum number
        startNum = (EditText)view.findViewById(R.id.countr_start_num);
        startNumTil =(TextInputLayout) view.findViewById(R.id.countr_start_til);
        startNumTil.setHint("Starting Number");
        startNum.setInputType(InputType.TYPE_CLASS_NUMBER);
        imm.showSoftInput((startNum), InputMethodManager.SHOW_IMPLICIT);


        //for the interval to count by
        interval = (EditText)view.findViewById(R.id.countr_interval);
        intervalTil = (TextInputLayout) view.findViewById(R.id.countr_interval_til);
        intervalTil.setHint("Count Interval");
        interval.setInputType(InputType.TYPE_CLASS_NUMBER);
        imm.showSoftInput((interval), InputMethodManager.SHOW_IMPLICIT);

        //button to actually create the Countr
        create = (Button)view.findViewById(R.id.create_button);
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


        // button to cancel the dailog and thus not create the Countr
        cancel = (Button)view.findViewById(R.id.cancel_button);
        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        return view;
    }

}
