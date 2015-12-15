package com.jackbbb95.globe.countr.Activities;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.jackbbb95.globe.countr.Fragments.CountrListFragment;
import com.jackbbb95.globe.countr.Handlers.MyApplication;
import com.jackbbb95.globe.countr.R;

import java.util.List;

public class SettingsActivity extends AppCompatPreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle(R.string.action_settings);
    }

    public static class GeneralPreferenceFragment extends PreferenceFragment {
        SharedPreferences mPrefs;
        Context context = (MainCountrActivity)getActivity();

        @Override
        public void onStart(){
            super.onStart();
            mPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_general);
            SharedPreferences countrPrefs = getActivity().getSharedPreferences("countrPrefs", Activity.MODE_PRIVATE);
            final SharedPreferences.Editor editor = countrPrefs.edit();

            //for the setting indicating whether the user wants to use hardware buttons
            SwitchPreference useHardwareButtons = (SwitchPreference) findPreference("hardware_buttons");
            useHardwareButtons.setDefaultValue(true);
            useHardwareButtons.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    if ((Boolean) newValue) {
                        editor.remove("useHB");
                        editor.putInt("useHB", 2);
                        editor.apply();
                    } else {
                        editor.remove("useHB");
                        editor.putInt("useHB", 1);
                        editor.apply();
                    }
                    return true;
                }
            });

            SwitchPreference useVibration = (SwitchPreference) findPreference("vibrate");
            useVibration.setDefaultValue(true);
            useVibration.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    if((Boolean) newValue){
                        editor.remove("vibrate");
                        editor.putBoolean("vibrate",true);
                        editor.apply();
                    } else{
                        editor.remove("vibrate");
                        editor.putBoolean("vibrate",false);
                        editor.apply();
                    }
                    return true;
                }
            });










/*            ListPreference theme = (ListPreference) findPreference("theme");
            theme.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    if(newValue.equals("0")){
                        MyApplication.getAppContext().setTheme(R.style.AppTheme_Light_NoActionBar);
                        getActivity().recreate();
                    }
                    else if(newValue.equals("1")){
                        MyApplication.getAppContext().setTheme(R.style.AppTheme_Dark_NoActionBar);
                        getActivity().recreate();
                    }
                    getActivity().finish();
                    return true;
                }
            });
            //TODO Work through Theme Setting

*/        }

    }


}
