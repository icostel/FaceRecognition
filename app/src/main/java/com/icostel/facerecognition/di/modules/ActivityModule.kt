package com.icostel.facerecognition.di.modules

import com.icostel.facerecognition.ui.screens.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    internal abstract fun contributesMainActivity(): MainActivity
}
