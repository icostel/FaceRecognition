package com.icostel.facerecognition.di


import com.icostel.facerecognition.BuildConfig
import com.icostel.facerecognition.di.component.DaggerAppComponent
import com.icostel.facerecognition.di.injector.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

class FaceRecognitionApp : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        AppInjector.init(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }
}
