package com.recrutation.fitsdktest.fit.ui

import androidx.lifecycle.ViewModel
import com.recrutation.fitsdktest.fit.config.FitConfig

class MainViewModel: ViewModel() {
    var actualCount = 0
    var maxCount = FitConfig.MAX_COUNT
}