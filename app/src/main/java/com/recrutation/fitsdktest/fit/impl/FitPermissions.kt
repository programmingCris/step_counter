package com.recrutation.fitsdktest.fit.impl

import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.fitness.FitnessOptions
import com.recrutation.fitsdktest.fit.abstr.FitPermissionsInterface

class FitPermissions(
    private val activity: AppCompatActivity, private val account: GoogleSignInAccount,
    private val fitnessOptions: FitnessOptions, private val delegate: PermissionDelegate?) : FitPermissionsInterface {
    private val GOOGLE_FIT_PERMISSIONS_REQUEST_CODE =  103

    override fun checkPermissions() :Boolean {
        return if (!GoogleSignIn.hasPermissions(account, fitnessOptions)) {
            askForPermission()
            false
        } else {
            true
        }
    }

    override fun askForPermission() {
        GoogleSignIn.requestPermissions(
            activity, // your activity
            GOOGLE_FIT_PERMISSIONS_REQUEST_CODE, // e.g. 1
            account,
            fitnessOptions)
    }

    override fun onPermissionsResult(requestCode: Int, resultCode: Int) {
        delegate?.onPermissionsSuccess()
    }

    interface PermissionDelegate{
        fun onPermissionsSuccess()
        fun onPermissionsDenied()
    }
}