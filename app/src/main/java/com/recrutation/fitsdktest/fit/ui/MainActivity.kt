package com.recrutation.fitsdktest.fit.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.recrutation.fitsdktest.R
import com.recrutation.fitsdktest.databinding.ActivityMainBinding
import com.recrutation.fitsdktest.fit.abstr.DialogInterface
import com.recrutation.fitsdktest.fit.dagger.AppModule
import com.recrutation.fitsdktest.fit.dagger.DaggerAppComponent
import com.recrutation.fitsdktest.fit.dialog.DialogService
import com.recrutation.fitsdktest.fit.impl.FitClient
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private val  MY_PERMISSIONS_REQUEST_ACTIVITY_RECOGNITION = 102
    @Inject
    lateinit var client: FitClient
    @Inject
    lateinit var dialogService: DialogInterface
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build().inject(this)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        setContentView(binding.root)
        binding.vm = viewModel
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
                    dialogService.showPermissionDialog()
                    }
                    else -> {
                        dialogService.showExitDialog()
                    }
                }
            }
        }
    }
}