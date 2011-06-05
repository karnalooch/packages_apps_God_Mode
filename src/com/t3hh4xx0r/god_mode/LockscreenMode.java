package com.t3hh4xx0r.god_mode;


import com.t3hh4xx0r.god_mode.R;
import com.t3hh4xx0r.god_mode.R.xml;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.EditTextPreference;
import android.preference.PreferenceScreen;
import android.widget.Toast;
import android.util.Log;
import android.provider.Settings;


public class LockscreenMode extends PreferenceActivity
implements SharedPreferences.OnSharedPreferenceChangeListener {

	private static final String CARRIER_CAP = "carrier_caption";
	private static final String TRACKPAD_WAKE_SCREEN = "trackpad_wake_screen";
	private static final String VOLUME_WAKE_SCREEN = "volume_wake_screen";
	private static final String TRACKPAD_UNLOCK_SCREEN = "trackpad_unlock_screen";
	private static final String MENU_UNLOCK_SCREEN = "menu_unlock_screen";
	private static final String LOCKSCREEN_ROTARY_LOCK = "use_rotary_lockscreen";
	private static final String LOCKSCREEN_SHORTCUTS = "lockscreen_shortcuts";
	private static final String LOCKSCREEN_ALWAYS_BATTERY = "lockscreen_always_battery";
	
	private EditTextPreference mCarrierCaption;
	private CheckBoxPreference mTrackpadWakeScreen;
	private CheckBoxPreference mVolumeWakeScreen;
	private CheckBoxPreference mTrackpadUnlockScreen;
	private CheckBoxPreference mMenuUnlockScreen;
	private CheckBoxPreference mLockscreenShortcuts;
	//TODO Create music controls in rotary layouts.
	//private CheckBoxPreference mUseRotaryLockPref;
	private CheckBoxPreference mLockscreenAlwaysBattery;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.lockscreen_prefs);
		PreferenceScreen prefSet = getPreferenceScreen();
		
		mCarrierCaption = (EditTextPreference)prefSet.findPreference(CARRIER_CAP);
		mTrackpadWakeScreen = (CheckBoxPreference) prefSet.findPreference(TRACKPAD_WAKE_SCREEN);
		mTrackpadWakeScreen.setChecked(Settings.System.getInt(getContentResolver(), Settings.System.TRACKPAD_WAKE_SCREEN, 0) != 0);
		mVolumeWakeScreen = (CheckBoxPreference) prefSet.findPreference(VOLUME_WAKE_SCREEN);
		mVolumeWakeScreen.setChecked(Settings.System.getInt(getContentResolver(), Settings.System.VOLUME_WAKE_SCREEN, 0) != 0);
		mTrackpadUnlockScreen = (CheckBoxPreference) prefSet.findPreference(TRACKPAD_UNLOCK_SCREEN);
		mTrackpadUnlockScreen.setChecked(Settings.System.getInt(getContentResolver(), Settings.System.TRACKPAD_UNLOCK_SCREEN, 0) != 0);
		mMenuUnlockScreen = (CheckBoxPreference) prefSet.findPreference(MENU_UNLOCK_SCREEN);
		mMenuUnlockScreen.setChecked(Settings.System.getInt(getContentResolver(), Settings.System.MENU_UNLOCK_SCREEN, 0) != 0);
	        //TODO Create music controls in rotary layouts.
		//mUseRotaryLockPref = (CheckBoxPreference)prefSet.findPreference(LOCKSCREEN_ROTARY_LOCK);
		//mUseRotaryLockPref.setChecked(Settings.System.getInt(getContentResolver(), Settings.System.USE_ROTARY_LOCKSCREEN, 0) != 0);
		mLockscreenShortcuts = (CheckBoxPreference) prefSet.findPreference(LOCKSCREEN_SHORTCUTS);
		mLockscreenShortcuts.setChecked (Settings.System.getInt(getContentResolver(), Settings.System.LOCKSCREEN_SHORTCUTS, 0) != 0);
		mLockscreenAlwaysBattery = (CheckBoxPreference) prefSet.findPreference(LOCKSCREEN_ALWAYS_BATTERY);
		mLockscreenAlwaysBattery.setChecked(Settings.System.getInt(getContentResolver(), Settings.System.LOCKSCREEN_ALWAYS_BATTERY, 0) == 0);
		
		getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }	
	
	public boolean onDialogClosed() {
		return false;
	}

    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        boolean value;
        //TODO Create music controls in rotary layouts.
        //if (preference == mUseRotaryLockPref) {
        //    value = mUseRotaryLockPref.isChecked();
        //    Settings.System.putInt(getContentResolver(), Settings.System.USE_ROTARY_LOCKSCREEN, value ? 1 : 0);
        //    //Temporary hack to fix Phone FC's when swapping styles.
        //   ActivityManager am = (ActivityManager)getSystemService(
        //            Context.ACTIVITY_SERVICE);
        //   am.forceStopPackage("com.android.phone");
        //} else 
	if (preference == mLockscreenAlwaysBattery) {
		    value = mLockscreenAlwaysBattery.isChecked();
		    Settings.System.putInt(getContentResolver(), Settings.System.LOCKSCREEN_ALWAYS_BATTERY, value ? 1 : 0);
	} else if (preference == mLockscreenShortcuts) {
		    value = mLockscreenShortcuts.isChecked();
		    Settings.System.putInt(getContentResolver(), Settings.System.LOCKSCREEN_SHORTCUTS, value ? 1 : 0);
	} else if (preference == mTrackpadWakeScreen) {
		    value = mTrackpadWakeScreen.isChecked();
		    Settings.System.putInt(getContentResolver(), Settings.System.TRACKPAD_WAKE_SCREEN, value ? 1 : 0);
	} else if (preference == mVolumeWakeScreen) {
		    value = mVolumeWakeScreen.isChecked();
		    Settings.System.putInt(getContentResolver(), Settings.System.VOLUME_WAKE_SCREEN, value ? 1 : 0);
	} else if (preference == mTrackpadUnlockScreen) {
		    value = mTrackpadUnlockScreen.isChecked();
		    Settings.System.putInt(getContentResolver(), Settings.System.TRACKPAD_UNLOCK_SCREEN, value ? 1 : 0);
	} else if (preference == mMenuUnlockScreen) {
		    value = mMenuUnlockScreen.isChecked();
		    Settings.System.putInt(getContentResolver(), Settings.System.MENU_UNLOCK_SCREEN, value ? 1 : 0);
	}
        return true;
    }
	
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		if (CARRIER_CAP.equals(key)) {
			Settings.System.putString(getContentResolver(),CARRIER_CAP, sharedPreferences.getString(CARRIER_CAP, ""));
            ActivityManager am = (ActivityManager)getSystemService(
                    Context.ACTIVITY_SERVICE);
            am.forceStopPackage("com.android.phone");
		}
	}
}
