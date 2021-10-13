package com.recrutation.fitsdktest.fit.dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog

open class BaseDialog(context: Context, layoutId: Int = -1, okButton: Int = -1, cancelButton: Int = -1,
                      onOkClickListener: View.OnClickListener? = null,
                      onCancelClickListener: View.OnClickListener? = null
) {
    private var alertDialog: AlertDialog? = null

    init {
        val builder = AlertDialog.Builder(context)
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(layoutId, null)
        builder.setView(view)
        builder.setCancelable(false)
        if(okButton != -1){
            val button = view.findViewById<Button>(okButton)
                button.setOnClickListener {
                    onOkClickListener?.onClick(it)
                    hideDialog()
                }
        }
        if(cancelButton != -1){
            val button = view.findViewById<Button>(cancelButton)
            button.setOnClickListener {
                onCancelClickListener?.onClick(it)
                hideDialog()
            }
        }
        alertDialog = builder.create()
    }

    fun showDialog(){
        alertDialog?.show()
    }

    fun hideDialog(){
        alertDialog?.dismiss()
    }
}