package com.recrutation.fitsdktest.fit.impl

import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataSource
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.Field
import com.google.android.gms.fitness.request.DataReadRequest
import com.google.android.gms.fitness.request.SensorRequest
import com.recrutation.fitsdktest.fit.abstract.FitClientInterface
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.concurrent.TimeUnit

class FitClient(private val activity: AppCompatActivity) : FitClientInterface,
    FitPermissions.PermissionDelegate {
    private val TAG = "FitClient"
    private val fitnessOptions = FitnessOptions.builder()
        .addDataType(DataType.TYPE_STEP_COUNT_CUMULATIVE, FitnessOptions.ACCESS_READ)
        .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
        .build()
    private val account = GoogleSignIn.getAccountForExtension(activity, fitnessOptions)
    val fitPermissions = FitPermissions(activity, account, fitnessOptions, this)
    private var totalCount = 0
    val mutableLiveData: MutableLiveData<Int> = MutableLiveData(totalCount)

    override fun getTotalCount() : Int {
        if(fitPermissions.checkPermissions()){
            accessGoogleFitData()
        }
        return 0
    }

    override fun accessGoogleFitData() {
        val startTime = LocalDate.now().atStartOfDay(ZoneId.systemDefault())
        val endTime = LocalDateTime.now().atZone(ZoneId.systemDefault())

        val datasource = DataSource.Builder()
            .setAppPackageName("com.google.android.gms")
            .setDataType(DataType.TYPE_STEP_COUNT_DELTA)
            .setType(DataSource.TYPE_DERIVED)
            .setStreamName("estimated_steps")
            .build()

        val request = DataReadRequest.Builder()
            .aggregate(datasource)
            .bucketByTime(1, TimeUnit.DAYS)
            .setTimeRange(startTime.toEpochSecond(), endTime.toEpochSecond(), TimeUnit.SECONDS)
            .build()

        Fitness.getHistoryClient(activity, account)
            .readData(request)
            .addOnSuccessListener { response ->
                val totalSteps = response.buckets
                    .flatMap { it.dataSets }
                    .flatMap { it.dataPoints }
                    .sumOf<T>({ it.getValue(Field.FIELD_STEPS).asInt() })
                totalCount = totalSteps
                mutableLiveData.postValue(totalCount)
                Log.i(TAG, "Total steps: $totalSteps")
                Fitness.getSensorsClient(activity, account)
                    .add(SensorRequest.Builder()
                        .setDataType(DataType.TYPE_STEP_COUNT_DELTA)
                        .setSamplingRate(10, TimeUnit.SECONDS)
                        .build()
                    ) {
                        Log.d(TAG, "working")
                        for (item in it.dataType.fields) {
                            val value = it.getValue(item)
                            Log.d(TAG, "field ${item.name}")
                            Log.d(TAG, "field count  $value")
                            totalCount.plus(value.asInt())
                            mutableLiveData.postValue(totalCount)
                            Toast.makeText(activity, "AAAA $value", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .addOnSuccessListener {
                        Log.d(TAG, "getSensorsClientSuccessListener")
                    }
                    .addOnFailureListener{
                        Log.d(TAG, "getSensorsClientFailureListener")
                    }
            }
            .addOnFailureListener{
                Log.d(TAG, "addOnFailureListener")
            }
        /*Fitness.getRecordingClient(activity, account)
            .subscribe(DataType.TYPE_STEP_COUNT_CUMULATIVE)
            .addOnSuccessListener {
                Log.i(TAG,"Subscription was successful!")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "There was a problem subscribing ", e)
            }*/
    }

    override fun onPermissionsSuccess() {
        accessGoogleFitData()
    }

    override fun onPermissionsDenied() {
        //show dialog
    }
}