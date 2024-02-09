package com.gcode.study

import android.app.Application
import com.gcode.study.di.RetrofitConfig

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        RetrofitConfig.buildRetrofitWithHeaders()
    }
}