package com.icostel.facerecognition.di.modules

import android.content.Context
import com.icostel.facerecognition.di.FaceRecognitionApp
import com.icostel.facerecognition.di.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.android.support.AndroidSupportInjectionModule

@Module(
    includes = [
        AndroidSupportInjectionModule::class,
        ActivityModule::class,
        ViewModelFactory::class,
        ViewModelModule::class,
        FaceDetectorModule::class
    ]
)
abstract class AppModule {

    @Binds
    abstract fun bindContext(application: FaceRecognitionApp): Context
}
