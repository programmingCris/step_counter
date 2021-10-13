package com.recrutation.fitsdktest

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.recrutation.fitsdktest.databinding.ActivityMainBinding
import com.recrutation.fitsdktest.fit.impl.FitClient

class MainActivity : AppCompatActivity() {
    private val  MY_PERMISSIONS_REQUEST_ACTIVITY_RECOGNITION = 102
    private lateinit var client: FitClient
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        setContentView(binding.root)
        binding.vm = viewModel
        client = FitClient(this)
        client.mutableLiveData.observe(this, {
            viewModel.actualCount = it
            binding.circularProgress.setCustomProgress(viewModel.actualCount)
            binding.stepCountMessage.text = getString(R.string.step_count_message, viewModel.actualCount, viewModel.maxCount)
        })
        checkPermission()
    }

    private fun checkPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACTIVITY_RECOGNITION),
                MY_PERMISSIONS_REQUEST_ACTIVITY_RECOGNITION)
        }
        else{
            client.getTotalCount()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        client.fitPermissions.onPermissionsResult(requestCode, resultCode)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == MY_PERMISSIONS_REQUEST_ACTIVITY_RECOGNITION){
            if(grantResults.isNotEmpty()){
                when {
                    ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACTIVITY_RECOGNITION
                    ) == PackageManager.PERMISSION_GRANTED -> {
                        client.getTotalCount()
                    }
                    shouldShowRequestPermissionRationale(Manifest.permission.ACTIVITY_RECOGNITION) -> {
                    // In an educational UI, explain to the user why your app requires this
                    // permission for a specific feature to behave as expected. In this UI,
                    // include a "cancel" or "no thanks" button that allows the user to
                    // continue using your app without granting the permission.
                    showInContextUI()
                }
                    else -> {
                        checkPermission()
                    }
                }
            }
        }
    }


    private fun showInContextUI() {

    }
}