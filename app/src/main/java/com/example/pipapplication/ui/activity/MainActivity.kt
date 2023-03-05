package com.example.pipapplication.ui.activity

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.pipapplication.R
import com.example.pipapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.actionbar.toolbarContainer)
        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    fun isDeviceOnlineStatus(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    fun internetAlert(): Boolean {
        var connected = false
        if (!isDeviceOnlineStatus(this)) {
            val alertDialogBuilder: AlertDialog.Builder =
                AlertDialog.Builder(this)
            alertDialogBuilder.setTitle(getString(R.string.app_name))
            alertDialogBuilder.setMessage("Please check your internet connection and try again!")
            alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Okay") { dialog, id ->
                    finishAffinity()
                    dialog.dismiss()
                }
//                // negative button text and action
//                .setNegativeButton("No") { dialog, id ->
//                    finishAffinity()
//                    dialog.dismiss()
//                }

            val alertDialog: AlertDialog = alertDialogBuilder.create()

            alertDialog.setOnShowListener {
                alertDialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE)
                    .setTextColor(
                        ContextCompat.getColor(
                            applicationContext,
                            R.color.blue
                        )
                    )
                alertDialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE).isAllCaps = false

            }

            alertDialog.show()
        } else {
            connected = true
        }
        return connected
    }


}
