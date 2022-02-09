package com.example.homeassistantoff.Settings

import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.example.homeassistantoff.HARequest.HARequestAlarmReceiver
import com.example.homeassistantoff.R
import com.example.homeassistantoff.utils.Constants.APP_REQUEST_SETTING

class SettingsActivity : AppCompatActivity(), OnSharedPreferenceChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val prefs = PreferenceManager.getDefaultSharedPreferences(this);
        val listener = OnSharedPreferenceChangeListener { _, key ->
            Log.d("Settings", "Settings mudou")
            if (key == APP_REQUEST_SETTING) {
                HARequestAlarmReceiver.manageHARequests(this)
            }
        }
        prefs.registerOnSharedPreferenceChangeListener(listener)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        Log.d("Settings", "Settings changed")
    }
}