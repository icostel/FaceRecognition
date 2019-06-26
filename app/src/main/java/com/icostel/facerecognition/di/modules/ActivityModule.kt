package com.icostel.facerecognition.di.modules

import com.icostel.facerecognition.ui.screens.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    internal abstract fun contributesMainActivity(): MainActivity
}
