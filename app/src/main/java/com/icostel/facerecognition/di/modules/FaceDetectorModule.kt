package com.icostel.facerecognition.di.modules

import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FaceDetectorModule {

    @Singleton
    @Provides
    fun providesFaceDetector(): FirebaseVisionFaceDetector {
        val options = FirebaseVisionFaceDetectorOptions.Builder()
            .build()
        return FirebaseVision.getInstance()
            .getVisionFaceDetector(options)
    }
}