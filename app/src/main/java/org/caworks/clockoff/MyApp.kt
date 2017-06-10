package org.caworks.clockoff

import android.app.Application

/**
 * Created by Gallon on 2017/6/10.
 */

class MyApp : Application() {

    companion object {
        lateinit var APP: Application
        var SCREEN_WIDTH: Int = 0
        var SCREEN_HEIGHT: Int = 0
    }

    override fun onCreate() {
        super.onCreate()
        APP = this

        initSettings()
    }

    private fun initSettings() {
        SCREEN_WIDTH = resources.displayMetrics.widthPixels
        SCREEN_HEIGHT = resources.displayMetrics.heightPixels
    }


}