package com.recrutation.fitsdktest.fit.dagger

import androidx.appcompat.app.AppCompatActivity
import com.recrutation.fitsdktest.fit.abstr.DialogInterface
import com.recrutation.fitsdktest.fit.dialog.DialogService
import dagger.Module
import com.recrutation.fitsdktest.fit.impl.FitClient
import dagger.Provides


@Module
class AppModule(val activity: AppCompatActivity) {

    @Provides
    fun provideFitClient(): FitClient {
        return FitClient(activity) // implementation of ImageAssistant
    }

    @Provides
    fun provideDialogService(): DialogInterface{
        return DialogService(activity)
    }
}