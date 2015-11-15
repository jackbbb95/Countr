package com.jackbbb95.globe.countr;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by jackb on 11/13/2015.
 */
public class CreateCountrDialogFrag extends DialogFragment {


    public interface CreateCountrDialogListener{
        Countr onFinishCreateCountr(Countr newCountr);
    }

    private EditText name;
    private NumberPicker start;
    private Spinner interval;
    private Button create;
    private Button cancel;



    public CreateCountrDialogFrag(){}

    @Override
    public void onStart(){
        super.onStart();
        int width = ViewGroup.LayoutParams.WRAP_CONTENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setLayout(width,height);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.create_countr_dialog_frag,container);
        getDialog().setTitle("Create Countr");



        //for the name of the countr
        name = (EditText) view.findViewById(R.id.countr_name);
        name.setHint("Enter name");
        name.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        //for the start number
        start = (NumberPicker)view.findViewById(R.id.start_number_picker);
        start.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                start.requestFocus();
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
                    int intervalNumber = Integer.parseInt(interval.getSelectedItem().toString());
                    CreateCountrDialogListener activity = (CreateCountrDialogListener) getActivity();
                    activity.onFinishCreateCountr(new Countr(countrName, startNumber, intervalNumber, 0));
                    getDialog().dismiss();
                }

            }
        });



        cancel = (Button)view.findViewById(R.id.cancel_button);
        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        return view;
    }



    /*public boolean onEditorAction(TextView v, int actionId, KeyEvent event){
        if(EditorInfo.IME_ACTION_DONE == actionId){
            //set the name to a variable
            String countrName = name.getText().toString();
            int startNumber = start.getValue();
            CreateCountrDialogListener activity = (CreateCountrDialogListener) getActivity();


            activity.onFinishCreateCountr(new Countr(countrName, startNumber, 1, 0));

            this.dismiss();
            return true;
        }
        return false;
    }*/

}
