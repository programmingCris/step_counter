package com.recrutation.fitsdktest.fit.dagger

import com.recrutation.fitsdktest.fit.abstr.DialogInterface
import com.recrutation.fitsdktest.fit.dialog.DialogService
import com.recrutation.fitsdktest.fit.impl.FitClient
import com.recrutation.fitsdktest.fit.ui.MainActivity
import dagger.Module
import dagger.Provides


@Module
class AppModule(val activity: MainActivity) {

    @Provides
    fun provideFitClient(): FitClient {
        return FitClient(activity, activity) // implementation of ImageAssistant
    }

    @Provides
    fun provideDialogService(): DialogInterface{
        return DialogService(activity)
    }
}