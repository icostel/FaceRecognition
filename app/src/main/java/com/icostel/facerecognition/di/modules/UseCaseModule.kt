package com.icostel.facerecognition.di.modules

import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector
import com.icostel.facerecognition.domain.FaceDetectionUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCaseModule {

    @Singleton
    @Provides
    fun provideFaceDetectionUseCase(faceDetector: FirebaseVisionFaceDetector): FaceDetectionUseCase {
        return FaceDetectionUseCase(faceDetector)
    }
}