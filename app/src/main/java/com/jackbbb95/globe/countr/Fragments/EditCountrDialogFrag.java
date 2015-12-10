package com.jackbbb95.globe.countr.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.jackbbb95.globe.countr.Activities.MainCountrActivity;
import com.jackbbb95.globe.countr.Countr;
import com.jackbbb95.globe.countr.R;


public class EditCountrDialogFrag extends DialogFragment {

    public interface CreateCountrDialogListener{
        void onFinishCreateCountr(Countr newCountr);
    } //runs onFinishCountrDialog when creation is done

    private EditText name; //the EditText box where the user inpts the name of the Countr
    private EditText curNum; //the Number Picker where the user chooses the start number
    private EditText interval; //the Spinner that gives choices of intervals to count by
    private Button create; //the button to create the Countr
    private Countr curCountr;



    public EditCountrDialogFrag(){} //default constructor for CreateCountrDialogFrag


    @Override
    public void onStart(){
        super.onStart();
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setLayout(width,height);

    } //On the start of the dialog creation, sets the size of the dialog box

    /*
    Brings up the dialog that asks the user to input parameters that are fed to a new Countr Object
    which will appear in the List view in the Main Activity
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.edit_countr_dialog_frag,container);
        Context context = getContext();
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        Toolbar tb = (Toolbar) view.findViewById(R.id.create_countr_toolbar);
        tb.setTitle("Edit Countr");

        Bundle bundle = getArguments();
        curCountr = (Countr) bundle.getSerializable("Countr");

        /*
        for the name of the countr
         */
        name = (EditText) view.findViewById(R.id.edit_name);
        TextInputLayout nameTil = (TextInputLayout) view.findViewById(R.id.edit_name_til);
        nameTil.setHint("Name");
        //configure keyboard
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY); //show keyboard
        //hint config
        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            name.setHint(curCountr.getName());
                        }
                    },200);

                }
                else
                    name.setHint("");
            }
        });

        /*
        for the current number
        */
        curNum = (EditText) view.findViewById(R.id.curNumET);
        TextInputLayout curNumTil = (TextInputLayout) view.findViewById(R.id.cur_num_til);
        curNumTil.setHint("Value");
        //configure keyboard
        curNum.setInputType(InputType.TYPE_CLASS_NUMBER);
        imm.showSoftInput((curNum), InputMethodManager.SHOW_IMPLICIT);
        //configure hint
        curNum.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            curNum.setHint(String.valueOf(curCountr.getCurrentNumber()));
                        }
                    }, 200);

                } else
                    curNum.setHint("");
            }
        });

        /*
        for the interval to count by
         */
        interval = (EditText)view.findViewById(R.id.edit_interval);
        TextInputLayout intervalTil = (TextInputLayout) view.findViewById(R.id.edit_interval_til);
        intervalTil.setHint("Interval");
        //configure keyboards
        interval.setInputType(InputType.TYPE_CLASS_NUMBER);
        imm.showSoftInput((interval), InputMethodManager.SHOW_IMPLICIT);
        //configure hint
        interval.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            interval.setHint(String.valueOf(curCountr.getCountBy()));
                        }
                    },200);

                }
                else
                    interval.setHint("");
            }
        });

        //button to actually create the Countr
        create = (Button)view.findViewById(R.id.create_button);
        create.setEnabled(false);
        //makes it only able to save if there is text present in the name edittext
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                create.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (name.getText().toString().length() > 0 || curNum.getText().toString().length() > 0) {
                    create.setEnabled(true);
                } else {
                    create.setEnabled(false);
                }
            }
        });
        //makes it only able to save if there is text present in the curNum editext
        curNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                create.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (name.getText().toString().length() > 0 || curNum.getText().toString().length() > 0) {
                    create.setEnabled(true);
                } else {
                    create.setEnabled(false);
                }
            }
        });
        //makes it only able to save if the interval is different than the current
        interval.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                create.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (interval.getText().toString().length() > 0 || curNum.getText().toString().length() > 0) {
                    create.setEnabled(true);
                } else {
                    create.setEnabled(false);
                }
            }
        });


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isDigitsOnly(curNum.getText().toString())) {
                    Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
                    curNum.startAnimation(shake);

                } else {
                    if (name.getText().toString().length() > 0)
                        if (curCountr != null) {
                            curCountr.setName(name.getText().toString());
                        }
                    if (curNum.getText().toString().length() > 0)
                        if (curCountr != null) {
                            curCountr.setCurrentNumber(Integer.parseInt(curNum.getText().toString()));
                        }
                    if (interval.getText().toString().length() > 0) {
                        if (curCountr != null) {
                            curCountr.setCountBy(Integer.parseInt(interval.getText().toString()));
                        }
                    }
                    ((MainCountrActivity)getActivity()).updateList();
                    getDialog().dismiss();
                }
            }
        });


        // button to cancel the dailog and thus not create the Countr
        Button cancel = (Button)view.findViewById(R.id.cancel_button);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });


        return view;
    }

}
