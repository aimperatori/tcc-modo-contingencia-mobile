package com.example.homeassistantoff

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.homeassistantoff.HARequest.HARequestAlarmReceiver
import com.example.homeassistantoff.HARequest.HARequestBroadcastListener
import com.example.homeassistantoff.databinding.ActivityMainBinding


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

    private fun setupHARequestAlarm() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(this, HARequestAlarmReceiver::class.java)

        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)

        alarmManager.setInexactRepeating(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime(),
            (1000 * 5).toLong(), // a cada 5 segundos
            pendingIntent)
    }

    private fun registerReceiver() {

        haRequest = object : HARequestAlarmReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val text = intent?.getStringExtra("text")

                Log.d("Alarme", "TESTEEE MAIN TÀ ON - $text")

                updateUI("Online !!!", true)

            }
        }
        registerReceiver(haRequest, IntentFilter("online"))


        haRequestOff = object : HARequestAlarmReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val text = intent?.getStringExtra("text")

                Log.d("Alarme", "TESTEEE MAIN TÀ OFF - $text")

                updateUI("OFFline !!!", false)

            }
        }
        registerReceiver(haRequestOff, IntentFilter("offline"))
    }


    ///

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
                val intent = Intent(this, HARequestAlarmReceiver::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun updateUI(text: String?, status: Boolean) {
        val statusTextView = this.findViewById<TextView>(R.id.statusText)
        statusTextView.text = text

        val statusImg = this.findViewById<ImageView>(R.id.statusImg)
        if (status) {
            statusImg.setImageResource(R.mipmap.online)
        }
        else {
            statusImg.setImageResource(R.mipmap.offline)
        }
    }
}
