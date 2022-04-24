package com.example.solitairecollection

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Insets
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowInsets
import android.view.WindowMetrics
import androidx.appcompat.app.AppCompatActivity


abstract class SuperActivity(
    var statusBarHeight: Int = 0,
    var screenWidth: Int = 0,
    var screenHeight: Int = 0
) : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 액티비티에서 방향을 설정하면 시작하고 잠시 뒤에 방향이 바뀜
//        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        calcStatusBarHeight()
        calcScreenSize()
    }

    private fun calcStatusBarHeight() {
        val screenSizeType =
            (resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK)
        var height = 0
        if (screenSizeType != Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            val resourceId = applicationContext.resources.getIdentifier(
                "status_bar_height",
                "dimen",
                "android"
            )
            if (resourceId > 0) {
                height = applicationContext.resources.getDimensionPixelSize(resourceId)
            }
        }
        statusBarHeight = height
    }

    private fun calcScreenSize() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics: WindowMetrics = windowManager.currentWindowMetrics
            val insets: Insets = windowMetrics.windowInsets
                .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            screenWidth = windowMetrics.bounds.width() - insets.left - insets.right
            Log.i("screenWidth", "${windowMetrics.bounds.width()} ${insets.left} ${insets.right}")
            screenHeight = windowMetrics.bounds.height() - insets.top - insets.bottom
            Log.i("screenHeight", "${windowMetrics.bounds.height()} ${insets.top} ${insets.bottom}")
        } else {
            val displayMetrics = DisplayMetrics()
            @Suppress("DEPRECATION")
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            screenWidth = displayMetrics.widthPixels
            screenHeight = displayMetrics.heightPixels
            Log.i("screenSize", "$screenWidth $screenHeight")
        }
    }
}