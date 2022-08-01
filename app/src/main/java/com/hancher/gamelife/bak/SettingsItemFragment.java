package com.hancher.gamelife.bak;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.hancher.gamelife.R;

public class SettingsItemFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}