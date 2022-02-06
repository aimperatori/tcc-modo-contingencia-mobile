package com.example.homeassistantoff.Settings

import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.example.homeassistantoff.R
import com.example.homeassistantoff.utils.Constants.APP_REQUEST_SETTING

class SettingsActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

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
        val listener = OnSharedPreferenceChangeListener { prefs, key ->
            Log.d("Settings", "Settings mudeou")
            if (key == APP_REQUEST_SETTING) {
                //todo criar logica para habilitar ou desabilitar requisições para o home assistant
                Log.d("Settings", "Preference value was updated to: " + prefs.getBoolean(key, false))
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

        Log.d("Settings", "Settings mudeou")

        if (key == APP_REQUEST_SETTING) {
            Log.d("Settings", "Preference value was updated to: " + sharedPreferences.getString(key, ""))
        }
    }
}