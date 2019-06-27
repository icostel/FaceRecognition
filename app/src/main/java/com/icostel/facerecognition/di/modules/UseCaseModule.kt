package com.icostel.facerecognition.di.modules

import com.icostel.facerecognition.domain.BaseUseCase
import com.icostel.facerecognition.domain.FaceDetectionUseCase
import dagger.Binds
import dagger.Module

@Module
abstract class UseCaseModule {

    @Binds
    abstract fun bindFaceDetectorUseCase(faceDetectionUseCase: FaceDetectionUseCase): BaseUseCase
}