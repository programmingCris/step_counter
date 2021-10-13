package com.recrutation.fitsdktest.fit.dialog

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.recrutation.fitsdktest.fit.abstr.DialogInterface
import javax.inject.Inject


class DialogService @Inject constructor(val activity: AppCompatActivity): DialogInterface {

    override fun showPermissionDialog(onOkClickListener: View.OnClickListener,
                                      onCancelClickListener: View.OnClickListener) {
        AppPermissionDialog(activity, onOkClickListener, onCancelClickListener).showDialog()
    }

    override fun showGooglePermissionDialog() {
        FitPermissionDialog(activity).showDialog()
    }

    override fun showExitDialog() {
        ExitDialog(activity).showDialog()
    }

    override fun showErrorDialog() {
        FitErrorDialog(activity).showDialog()
    }
}