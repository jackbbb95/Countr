package com.jackbbb95.globe.countr.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
                intent.putExtra("Countr", countr);
                intent.putExtra("Position",position);
                startActivityForResult(intent, 1);
            }
        });

        final View header = inflater.inflate(R.layout.header,null);

        //Action on the long click of an item in the listview
        countrListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TextView theHeader = (TextView) header.findViewById(R.id.header);
                final Countr countr = (Countr) parent.getAdapter().getItem(position);
                theHeader.setText(countr.getName());
                ArrayAdapter<String> editOrDeleteAdapter = new ArrayAdapter<String>(getActivity(),R.layout.edit_delete_dialog_list_item);
                editOrDeleteAdapter.add("Edit");
                editOrDeleteAdapter.add("Reset");
                editOrDeleteAdapter.add("Delete");

                //setup the dialog for when the user longclicks on an item
                editOrDeleteDialog = DialogPlus.newDialog(getContext())
                        .setHeader(header)
                        .setGravity(Gravity.BOTTOM)
                        .setAdapter(editOrDeleteAdapter)
                        .setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                                //For when the user clicks the edit option
                                //Creates popup where the user can edit the countr
                                if(position == 1){
                                    showEditCountrDialog(countr);

                                    //Snackbar.make(getActivity().findViewById(R.id.listview_countr), "Edit " + countr.getName(), Snackbar.LENGTH_SHORT).show();
                                    //dialog.dismiss();
                                }
                                //For when the user clicks reset
                                //Sets the countr to 0
                                else if(position == 2){
                                    resetCountr(countr);
                                }
                                //For if the User clicks Delete
                                //Popup Dialo asking if sure
                                else if(position == 3){
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
                if(scrollState == SCROLL_STATE_IDLE){
                    ((MainCountrActivity)getActivity()).getAddCountr().show();
                }else if(scrollState == SCROLL_STATE_TOUCH_SCROLL && countrArrayList.size()>9
                        || scrollState == SCROLL_STATE_FLING && countrArrayList.size()>9){
                    ((MainCountrActivity)getActivity()).getAddCountr().hide();
                }


            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });


        return rootView; //inflates CountrList
    }

    /*
    Retrieves the new countr and sets it to the correct position in the saveArray
     */
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


    //reset the countr to 0, with warning dialog
    private void resetCountr(final Countr resetThis) {
        AlertDialog confirmReset = new AlertDialog.Builder(getActivity()).create();
        confirmReset.setTitle("Reset?");
        confirmReset.setMessage("Are you sure you want to reset " + resetThis.getName() + " ?");
        confirmReset.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        resetThis.setCurrentNumber(0); //RESETS COUNTR TO 0
                        mCountrAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                        editOrDeleteDialog.dismiss();
                        Snackbar.make(getActivity().findViewById(R.id.listview_countr), resetThis.getName() + " has been reset", Snackbar.LENGTH_SHORT).show();
                    }
                });
        confirmReset.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editOrDeleteDialog.dismiss();
                        dialog.dismiss();
                    }
                });
        confirmReset.show();
    }
    //delete the countr, with warning dialog
    public void deleteCountr(final Countr deleteThis){
        AlertDialog confirmDelete = new AlertDialog.Builder(getActivity()).create();
        confirmDelete.setTitle("Delete?");
        confirmDelete.setMessage("Are you sure you want to delete " + deleteThis.getName() + " ?");
        confirmDelete.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((MainCountrActivity) getActivity()).getSaveArray().remove(deleteThis);
                        mCountrAdapter.remove(deleteThis);
                        mCountrAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                        editOrDeleteDialog.dismiss();
                        Snackbar.make(getActivity().findViewById(R.id.listview_countr), deleteThis.getName() + " has been deleted", Snackbar.LENGTH_SHORT).show();
                    }
                });
        confirmDelete.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editOrDeleteDialog.dismiss();
                        dialog.dismiss();
                    }
                });
        confirmDelete.show();
    }

    /*
    Shows the dialog that allows the user to edit
     */
    public void showEditCountrDialog(Countr countr){
        EditCountrDialogFrag ec = new EditCountrDialogFrag();
        Bundle bundle = new Bundle();
        bundle.putSerializable("Countr", countr);
        ec.setArguments(bundle);
        ec.show(getFragmentManager(), "edit_countr_dialog");
        editOrDeleteDialog.dismiss();
    }
}
