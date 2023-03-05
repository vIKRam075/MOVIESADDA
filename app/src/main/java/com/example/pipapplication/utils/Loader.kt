package com.example.pipapplication.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.example.pipapplication.R

class Loader {
    companion object {
        private var alertDialog: Dialog? = null
        fun showLoading(context: Context) {
            if (alertDialog?.isShowing == true) {
                alertDialog?.dismiss()
                alertDialog = null
            } else {
                alertDialog = Dialog(context)
                alertDialog?.setContentView(R.layout.dialog_loader)
                alertDialog?.setCanceledOnTouchOutside(false)
                alertDialog?.setCancelable(false)
                alertDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                alertDialog?.show()
            }
        }

        fun stopLoading() {
            alertDialog?.let {
                if (it.isShowing) {
                    it.dismiss()
                }
            }
        }
    }

}