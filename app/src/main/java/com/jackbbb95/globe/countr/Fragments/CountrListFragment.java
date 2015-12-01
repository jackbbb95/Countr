package com.jackbbb95.globe.countr.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jackbbb95.globe.countr.Activities.CountingActivity;
import com.jackbbb95.globe.countr.Countr;
import com.jackbbb95.globe.countr.Handlers.CountrAdapter;
import com.jackbbb95.globe.countr.Activities.MainCountrActivity;
import com.jackbbb95.globe.countr.R;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;

import java.util.ArrayList;

/**
 * A ListView fragment that displays the users created Countrs
 */
public class CountrListFragment extends Fragment {

    //the ArrayAdapter
    private ArrayList<Countr> countrArrayList; //the ArrayList in which each Countr item to be displayed, is placed
    private static CountrAdapter mCountrAdapter; //the adapter that the ArrayList is set to
    private static TextView createText; //the TextView that prompts the user to create a new Countr
    private int curCountrPos = -1;
    private String CUR_POS = "Current Countr Position";
    private boolean beforeCount = true;
    private DialogPlus editOrDeleteDialog;
    ListView countrListView;


    public CountrListFragment() {} //default constructor


    public CountrAdapter getmCountrAdapter(){return mCountrAdapter;} //returns the Adapter for the ArrayList
    public TextView getCreateText(){return createText;}
    public ArrayList<Countr> getCountrArrayList(){return countrArrayList;}
    public int getCurCountrPos(){return curCountrPos;}
    public ListView getCountrListView(){return countrListView;}

    /*
    Creates and inflates the ListView that contains the Countrs with an adapter providing the layout. Also shows the
    "Create a New Countr" message
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.countr_list_fragment, container, false); //sets the CountrList to inflate

        countrArrayList = ((MainCountrActivity)getActivity()).getCountrArrayList(); //creates the ArrayList for the Countrs

        mCountrAdapter = new CountrAdapter( //creates the adapter
                //current context
                getActivity(),
                //ID of the list item layout
                R.layout.list_item_countr,
                //countr object array
                countrArrayList);

        countrListView = (ListView) rootView.findViewById(R.id.listview_countr); //sets the layout of the ListView to be listview_countr
        countrListView.setAdapter(mCountrAdapter);//sets the adapter to the ListView
        countrListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        createText = (TextView) rootView.findViewById(R.id.create_counter_textview); //shows the "Create a New Countr" message for when there is no Countr




        //opens the new activity to count on user click of an item
        countrListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                curCountrPos = position;
                beforeCount = false;
                Countr countr = (Countr) parent.getAdapter().getItem(position);
                Intent intent = new Intent(getActivity(),CountingActivity.class);
                intent.putExtra("Countr",countr);
                intent.putExtra("Position",position);
                startActivityForResult(intent, 1);
            }
        });

        //Action on the long click of an item in the listview
        countrListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO Create Dialog that you choose to eit or delete the item
                final Countr countr = (Countr) parent.getAdapter().getItem(position);
                ArrayAdapter<String> editOrDeleteAdapter = new ArrayAdapter<String>(getActivity(),R.layout.edit_delete_dialog_list_item);
                editOrDeleteAdapter.add("Edit Countr");
                editOrDeleteAdapter.add("Reset Countr");
                editOrDeleteAdapter.add("Delete Countr");

                //setup the dialog for when the user longclicks on an item
                editOrDeleteDialog = DialogPlus.newDialog(getContext())
                        .setGravity(Gravity.BOTTOM)
                        .setAdapter(editOrDeleteAdapter)
                        .setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                                //For when the user clicks the edit option
                                //Creates popup where the user can edit the countr
                                if(position == 0){
                                    Snackbar.make(getActivity().findViewById(R.id.listview_countr), "Edit " + countr.getName(), Snackbar.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                                //For when the user clicks reset
                                //Sets the countr to 0
                                else if(position == 1){
                                    countr.setCurrentNumber(0);
                                    mCountrAdapter.notifyDataSetChanged();
                                    dialog.dismiss();
                                }
                                //For if the User clicks Delete
                                //Popup Dialo asking if sure
                                else if(position == 2){
                                    deleteCountr(countr);
                                }
                            }
                        })
                        .setExpanded(false, 400)  // This will enable the expand feature, (similar to android L share dialog)
                        .create();
                editOrDeleteDialog.show();
                return true;
            }
        });


        //make the fab disappear on scroll
        countrListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                ((MainCountrActivity) getActivity()).getAddCountr().show();
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem != 0){
                    ((MainCountrActivity)getActivity()).getAddCountr().hide();
                }
            }
        });


        return rootView; //inflates CountrList
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            Countr newCountr = (Countr) data.getSerializableExtra("NewCountr");
            int curPos = data.getIntExtra("Position",-1);
            ((MainCountrActivity) getActivity()).getSaveArray().set(curPos, newCountr);
            mCountrAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putInt(CUR_POS, curCountrPos);
        savedInstanceState.putBoolean("Before Count", beforeCount);
        super.onSaveInstanceState(savedInstanceState);
    }

    public void deleteCountr(final Countr deleteThis){
        AlertDialog confirmCancel = new AlertDialog.Builder(getActivity()).create();
        confirmCancel.setTitle("Delete?");
        confirmCancel.setMessage("Are you sure you want to delete " + deleteThis.getName() + " ?");
        confirmCancel.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((MainCountrActivity) getActivity()).getSaveArray().remove(deleteThis);
                        mCountrAdapter.remove(deleteThis);
                        mCountrAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                        editOrDeleteDialog.dismiss();
                    }
                });
        confirmCancel.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editOrDeleteDialog.dismiss();
                        dialog.dismiss();
                    }
                });
        confirmCancel.show();
    }

    public void editCountr(Countr countr){

    }

}
