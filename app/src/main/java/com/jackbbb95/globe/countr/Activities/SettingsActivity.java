package com.jackbbb95.globe.countr.Activities;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.jackbbb95.globe.countr.Fragments.AboutDialogFrag;
import com.jackbbb95.globe.countr.R;

public class SettingsActivity extends AppCompatPreferenceActivity {

    private static boolean keepAlive = false;
    private static MainCountrActivity ac = new MainCountrActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);
        //setup toolbar
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("");
    }

    //keeps settings activity alive on screen rotation
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus) keepAlive = false; //makes it so activity can finish if no dialogs are open
        if(!hasFocus && !keepAlive) finish();
    }

    //The fragment that handles allthe preferences and their catagories
    public static class GeneralPreferenceFragment extends PreferenceFragment {
        SharedPreferences mPrefs;

        @Override
        public void onStart(){
            super.onStart();
            mPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity()); //get the shared preferences to be changed
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_general);
            SharedPreferences countrPrefs = getActivity().getSharedPreferences("countrPrefs", Activity.MODE_PRIVATE);
            final SharedPreferences.Editor editor = countrPrefs.edit(); //to allow editing of the preferences

            //for the setting indicating whether the user wants to use hardware buttons
            final SwitchPreference useHardwareButtons = (SwitchPreference) findPreference("hardware_buttons");
            useHardwareButtons.setDefaultValue(false);
            useHardwareButtons.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    if ((Boolean) newValue) {
                        editor.remove("useHB");
                        editor.putBoolean("useHB", true);
                        editor.apply();
                        useHardwareButtons.setSummary(R.string.pref_hardware_buttons_disabled);
                    } else {
                        editor.remove("useHB");
                        editor.putBoolean("useHB", false);
                        editor.apply();
                        useHardwareButtons.setSummary(R.string.pref_hardware_buttons_enabled);
                    }
                    return true;
                }
            });

            //for the setting indicating whether the user wants to have vibrations on each count
            final SwitchPreference useVibration = (SwitchPreference) findPreference("vibrate");
            useVibration.setDefaultValue(false);
            useVibration.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    if ((Boolean) newValue) {
                        editor.remove("vibrate");
                        editor.putBoolean("vibrate", true);
                        editor.apply();
                        useVibration.setSummary(R.string.pref_vibrate_enabled);
                    } else {
                        editor.remove("vibrate");
                        editor.putBoolean("vibrate", false);
                        editor.apply();
                        useVibration.setSummary(R.string.pref_vibrate_disabled);
                    }
                    return true;
                }
            });

            //for the setting that will reset the count of all current countrs
            final Preference resetAll = findPreference("reset_all");
            resetAll.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    AlertDialog confirmReset = new AlertDialog.Builder(getActivity()).create();
                    confirmReset.setTitle("Reset?");
                    confirmReset.setMessage("Are you sure you want to reset all Countrs?");
                    confirmReset.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    for(int i = 0; i < ac.getCountrListFragment().getmCountrAdapter().getCount(); i++) {
                                        ac.getCountrListFragment().getmCountrAdapter().getItem(i).setCurrentNumber(0);
                                    }
                                    ac.getCountrListFragment().getmCountrAdapter().notifyDataSetChanged();
                                    dialog.dismiss();
                                    getActivity().finish();
                                    Toast.makeText(getActivity(), "All Countrs Reset", Toast.LENGTH_SHORT).show();
                                }
                            });
                    confirmReset.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    keepAlive = true; //keeps activity alive on rotation
                    confirmReset.show();
                    return true;
                }
            });

            //for the setting that would delete all countrs
            final Preference deleteAll = findPreference("delete_all");
            deleteAll.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    AlertDialog confirmDelete = new AlertDialog.Builder(getActivity()).create();
                    confirmDelete.setTitle("Delete?");
                    confirmDelete.setMessage("Are you sure you want to delete all Countrs?");
                    confirmDelete.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ac.getCountrListFragment().getmCountrAdapter().clear();
                                    ac.getCountrListFragment().getmCountrAdapter().notifyDataSetChanged();
                                    dialog.dismiss();
                                    getActivity().finish();
                                    ac.getCountrListFragment().getCreateText().setVisibility(View.VISIBLE);
                                    Toast.makeText(getActivity(), "All Countrs Deleted", Toast.LENGTH_SHORT).show();

                                }
                            });
                    confirmDelete.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    keepAlive = true; //keeps activity alive on rotation
                    confirmDelete.show();
                    return true;
                }
            });

            //for showing the about fragment that shows the info about the app
            final Preference about = findPreference("about");
            about.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    android.app.FragmentManager fm = getActivity().getFragmentManager();
                    AboutDialogFrag aboutDialog = new AboutDialogFrag();
                    keepAlive = true; //keeps activity alive on rotation
                    aboutDialog.show(fm, "fragment_about_countr");
                    return true;
                }
            });

        }

    }

}
