package com.andela.taccolation.app.ui.home.ui.slideshow;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.andela.taccolation.R;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class SettingsFragment extends PreferenceFragmentCompat {
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

    private void initPreferences() {
        mThemeSwitch = findPreference(getString(R.string.theme_key));
        mThemeSwitch.setOnPreferenceClickListener(preference -> {
            uiMode();
            return true;
        });
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

    /*private void checkForDarkModeCompatibility() {
        // This darkens my webViews even if I am using a light theme for my app.
        // How do I only enable this when using a dark theme?
        // Currently a night theme check around the FORCE_DARK code is the only option to do so.
        int nightModeFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
            Log.i(TAG, "stackWebSetup: FORCE_DARK Feature Supported");
            if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
                //Code to enable force dark and select force dark strategy
                WebSettingsCompat.setForceDark(mWebView.getSettings(), WebSettingsCompat.FORCE_DARK_ON);
            }
        } else Log.i(TAG, "stackWebSetup: FORCE_DARK Feature Not Supported");
    }*/
}