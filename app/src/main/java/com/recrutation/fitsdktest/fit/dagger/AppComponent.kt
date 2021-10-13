package com.recrutation.fitsdktest.fit.dagger

import com.recrutation.fitsdktest.fit.ui.MainActivity
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {

    // Classes that can be injected by this Component
    fun inject(activity: MainActivity)
}