package com.jumia.myapplication.ui.util.progress

import android.app.Activity
import android.app.ProgressDialog
import android.content.ContextWrapper
import com.jumia.myapplication.R

class WaitingDialog(activity: Activity?) {

    private var alertDialog: ProgressDialog? = null

    private var activity: Activity? = null

    init {

        this.activity = activity

        alertDialog = activity?.let {
            ProgressDialog(it)
        }

        alertDialog?.setCancelable(false)
        alertDialog?.setMessage(activity?.getString(R.string.waiting_message))
    }

    fun showDialog() {
        if (activity?.isDestroyed == true) {
            return
        }

        if (alertDialog != null && !alertDialog!!.isShowing) {
            val context = (alertDialog!!.context as ContextWrapper).baseContext
            if (context is Activity) {
                if (!context.isFinishing) {
                    alertDialog?.show()
                }
            } else {
                alertDialog?.show()
            }
        }
    }

    fun dismissDialog() {
        if (activity?.isDestroyed == true) {
            return
        }

        if (alertDialog != null && alertDialog!!.isShowing) {
            val context = (alertDialog!!.context as ContextWrapper).baseContext
            if (context is Activity) {
                if (!context.isFinishing) {
                    alertDialog?.dismiss()
                }
            } else {
                alertDialog?.dismiss()
            }
        }
    }
}
