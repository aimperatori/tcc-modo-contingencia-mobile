package com.example.homeassistantoff

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.preference.PreferenceManager
import com.example.homeassistantoff.HARequest.HARequestAlarmReceiver
import com.example.homeassistantoff.Messaging.Notification
import com.example.homeassistantoff.Settings.SettingsActivity
import com.example.homeassistantoff.databinding.ActivityMainBinding
import com.example.homeassistantoff.utils.Constants
import com.example.homeassistantoff.utils.Constants.OFFLINE
import com.example.homeassistantoff.utils.Constants.ONLINE


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private lateinit var haRequest: HARequestAlarmReceiver
    private lateinit var haRequestOff: HARequestAlarmReceiver


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        updateDesign()

        setupHARequestAlarm()
    }

    private fun updateDesign() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val appRequestActive = sharedPreferences.getBoolean(Constants.APP_REQUEST_SETTING, false)

        if (!appRequestActive) {

            var statusTextView = findViewById<TextView>(R.id.statusText)
            if (statusTextView != null) {
                statusTextView.text = getString(R.string.disabled_ha_request)
            }
            val statusImg = findViewById<ImageView>(R.id.statusImg)
            if (statusImg != null) {
                statusImg.visibility = View.INVISIBLE

            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // ### REQUEST HOME ASSISTANT ###

    private fun setupHARequestAlarm() {

        HARequestAlarmReceiver.manageHARequests(this)

        haRequest = object : HARequestAlarmReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                updateUI(getText(R.string.text_home_assistant_online), true)
            }
        }
        registerReceiver(haRequest, IntentFilter(ONLINE))


        haRequestOff = object : HARequestAlarmReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                updateUI(getText(R.string.text_home_assistant_offline), false)
            }
        }
        registerReceiver(haRequestOff, IntentFilter(OFFLINE))
    }

    private fun updateUI(text: CharSequence?, status: Boolean) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val appRequestActive = sharedPreferences.getBoolean(Constants.APP_REQUEST_SETTING, false)

        if (appRequestActive) {
            var statusTextView = findViewById<TextView>(R.id.statusText)
            if (statusTextView != null) {
                statusTextView.text = text
            }

            val statusImg = findViewById<ImageView>(R.id.statusImg)
            if (statusImg != null) {
                if (status) {
                    statusImg.setImageResource(R.mipmap.online)
                } else {
                    statusImg.setImageResource(R.mipmap.offline)
                }
            }
        }
    }
}