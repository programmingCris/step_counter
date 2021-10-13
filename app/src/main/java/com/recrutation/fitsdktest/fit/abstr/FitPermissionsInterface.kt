package com.recrutation.fitsdktest.fit.abstr

interface FitPermissionsInterface {
    fun checkPermissions() : Boolean
    fun askForPermission()
    fun onPermissionsResult(requestCode: Int, resultCode: Int)
}