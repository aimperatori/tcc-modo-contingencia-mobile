package com.example.homeassistantoff

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.homeassistantoff.HARequest.HARequestAlarmReceiver
import com.example.homeassistantoff.Settings.SettingsActivity
import com.example.homeassistantoff.databinding.ActivityMainBinding
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

        setupHARequestAlarm()
        registerReceiver()
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

    // !!! REQUEST HOME ASSISTANT !!!

    private fun setupHARequestAlarm() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(this, HARequestAlarmReceiver::class.java)

        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)

        alarmManager.setInexactRepeating(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime(),
            (1000 * 5).toLong(), // each 5 sec
            pendingIntent)
    }

    private fun registerReceiver() {
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