package com.icostel.facerecognition.di.component

import com.icostel.facerecognition.di.FaceRecognitionApp
import com.icostel.facerecognition.di.modules.AppModule
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent : AndroidInjector<FaceRecognitionApp> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<FaceRecognitionApp>()
}
