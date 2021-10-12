package com.recrutation.fitsdktest.fit.abstract

interface FitPermissionsInterface {
    fun checkPermissions() : Boolean
    fun askForPermission()
    fun onPermissionsResult(requestCode: Int, resultCode: Int)
}