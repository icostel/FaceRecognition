package com.icostel.facerecognition.di.modules

import androidx.lifecycle.ViewModel
import com.icostel.facerecognition.di.ViewModelKey
import com.icostel.facerecognition.ui.viewmodels.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindTermsViewModel(termsViewModel: MainViewModel): ViewModel
}
