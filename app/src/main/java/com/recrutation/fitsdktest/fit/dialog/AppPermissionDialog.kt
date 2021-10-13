package com.recrutation.fitsdktest.fit.dialog

import android.content.Context
import android.view.View
import com.recrutation.fitsdktest.R

class AppPermissionDialog(context: Context, onOkClickListener: View.OnClickListener,
                          onCancelClickListener: View.OnClickListener) : BaseDialog(context, R.layout.app_permission_dialog,
    R.id.app_permission_yes_button, R.id.app_permission_no_button, onOkClickListener, onCancelClickListener)