package com.icostel.facerecognition.ui.screens

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.icostel.facerecognition.ui.utils.setImmersive
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

@SuppressLint("Registered")
open class BaseActivity: DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setImmersive()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        setImmersive()
    }
}