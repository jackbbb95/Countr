package com.jackbbb95.globe.countr;


import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
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

import org.w3c.dom.Text;

public class CreateCountrDialogFrag extends DialogFragment {

    public interface CreateCountrDialogListener{
        void onFinishCreateCountr(Countr newCountr);
    } //runs onFinishCountrDialog when creation is done

    private EditText name; //the EditText box where the user inpts the name of the Countr
    private NumberPicker start; //the Number Picker where the user chooses the start number
    private Spinner interval; //the Spinner that gives choices of intervals to count by
    private Button create; //the button to create the Countr
    private Button cancel; //the button to close the dialog without creating the Countr
    private InputMethodManager imm;
    private TextInputLayout nameTil;
    private Toolbar tb;


    public CreateCountrDialogFrag(){} //default constructor for CreateCountrDialogFrag


    @Override
    public void onStart(){
        super.onStart();
        int width = ViewGroup.LayoutParams.WRAP_CONTENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setLayout(width,height);

    } //On the start of the dialog creation, sets the size of the dialog box

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


        //for the start number
        start = (NumberPicker)view.findViewById(R.id.start_number_picker);
        start.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                getDialog().findViewById(R.id.focus_dummy).requestFocusFromTouch();
                imm.hideSoftInputFromWindow(name.getWindowToken(),0);
            }
        });
        start.setMinValue(0);
        start.setMaxValue(9999);
        start.setWrapSelectorWheel(false);


        //for the interval to count by
        interval = (Spinner)view.findViewById(R.id.interval_spinner);
        Integer[] intervalChoices = new Integer[]{1,2,5,10,15,20,100,1000};
        ArrayAdapter<Integer> intervalAdapter = new ArrayAdapter<Integer>(getContext(),android.R.layout.simple_spinner_item,intervalChoices);
        intervalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        interval.setAdapter(intervalAdapter);


        //button to actually create the Countr
        create = (Button)view.findViewById(R.id.create_button);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.length() < 1) {
                    Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
                    name.startAnimation(shake);
                } else {
                    String countrName = name.getText().toString();
                    int startNumber = start.getValue();
                    int currentNumber = start.getValue();
                    int intervalNumber = Integer.parseInt(interval.getSelectedItem().toString());
                    CreateCountrDialogListener activity = (CreateCountrDialogListener) getActivity();
                    activity.onFinishCreateCountr(new Countr(countrName, startNumber, intervalNumber, currentNumber, 0));
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
