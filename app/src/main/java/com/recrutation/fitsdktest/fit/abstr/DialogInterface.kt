package com.recrutation.fitsdktest.fit.abstr

import android.view.View

interface DialogInterface {
    fun showPermissionDialog(onOkClickListener: View.OnClickListener,
                             onCancelClickListener: View.OnClickListener)
    fun showGooglePermissionDialog()
    fun showExitDialog()
    fun showErrorDialog()
}