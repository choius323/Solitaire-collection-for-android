package com.example.solitairecollection

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class SuperActivity : AppCompatActivity() {
    var statusBarHeight = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 액티비티에서 방향을 설정하면 시작하고 잠시 뒤에 방향이 바뀜
//        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        getStatusBarHeight()
    }

    fun getStatusBarHeight() {
        val context = applicationContext
        val screenSizeType =
            (resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK)
        var height = 0
        if (screenSizeType != Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            val resourceId = context.resources.getIdentifier(
                "status_bar_height",
                "dimen",
                "android"
            )
            if (resourceId > 0) {
                height = context.resources.getDimensionPixelSize(resourceId)
            }
        }
        this.statusBarHeight = height;
    }

}