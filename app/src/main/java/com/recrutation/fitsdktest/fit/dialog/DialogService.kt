package com.recrutation.fitsdktest.fit.dialog

import androidx.appcompat.app.AppCompatActivity
import com.recrutation.fitsdktest.fit.abstr.DialogInterface
import javax.inject.Inject


class DialogService @Inject constructor(val activity: AppCompatActivity): DialogInterface {

    override fun showPermissionDialog() {
    }

    override fun showGooglePermissionDialog() {
    }

    override fun showExitDialog() {
    }

    override fun showErrorDialog() {
        
    }
}