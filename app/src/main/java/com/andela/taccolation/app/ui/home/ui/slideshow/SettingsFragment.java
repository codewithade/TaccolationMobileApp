package com.andela.taccolation.app.ui.home.ui.slideshow;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.andela.taccolation.R;
import com.google.android.material.snackbar.Snackbar;

import dagger.hilt.android.AndroidEntryPoint;

// https://developer.android.com/guide/topics/ui/settings
@AndroidEntryPoint
public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    private SwitchPreferenceCompat mThemeSwitch;

    /*public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentSettingsBinding binding = FragmentSettingsBinding.inflate(inflater);
        return binding.getRoot();
    }*/

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings, rootKey);
        initPreferences();
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.theme_key)))
            displaySnackBar("Theme changed");
        else if (key.equals(getString(R.string.animation_key)))
            displaySnackBar("Animation changed");
    }

    private void initPreferences() {
        mThemeSwitch = findPreference(getString(R.string.theme_key));
        if (mThemeSwitch != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) // disable change theme for android 9 and above. responds to system default settings
                mThemeSwitch.setEnabled(false);
            else {
                mThemeSwitch.setOnPreferenceClickListener(preference -> {
                    uiMode();
                    return true;
                });
            }
        }
    }

    // enable/disable dark theme
    private void switchTheme() {
        SwitchPreferenceCompat themeSwitch = findPreference(getString(R.string.theme_key));

        if (themeSwitch != null) {
            if (themeSwitch.isEnabled()) {
                // enable dark theme:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                themeSwitch.setIcon(R.drawable.ic_baseline_brightness_4);
            } else {
                // forcefully disable dark theme:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                themeSwitch.setIcon(R.drawable.ic_baseline_brightness_high);
            }
        } else
            // set app theme based on mobile settings of dark mode,
            // i.e. if dark mode is enabled then the theme will be set to a dark theme,
            // if not then the default theme, but this will only work in version >= Android version Q
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }

    private void uiMode() {
        // check for ui configuration mode
        int nightModeFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
            // Night mode active, switch to Day mode
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            mThemeSwitch.setIcon(R.drawable.ic_baseline_brightness_high);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            mThemeSwitch.setIcon(R.drawable.ic_baseline_brightness_4); // Day mode active
        }
    }

    private void displaySnackBar(String message) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show();
    }
}