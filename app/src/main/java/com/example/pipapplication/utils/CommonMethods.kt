package com.example.pipapplication.utils

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.pipapplication.R

class CommonMethods {
    companion object {
        fun isDeviceOnlineStatus(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }

/*    fun internetAlert(context: Context): Boolean {
        var connected = false
        if (!isDeviceOnlineStatus(context)) {
            val alertDialogBuilder: AlertDialog.Builder =
                AlertDialog.Builder(context)
            alertDialogBuilder.setTitle(context.getString(R.string.app_name))
            alertDialogBuilder.setMessage("Please check your internet connection and try again!")
            alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Okay") { dialog, id ->
                    context.finishAffinity()
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
                            context,
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
    }*/
    }
}