package com.robj.betterpod.di

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.SavedStateHandle
import com.robj.betterpod.DetailsViewModel
import com.robj.betterpod.DispatcherProvider
import com.robj.betterpod.DispatcherProviderImpl
import com.robj.betterpod.MainActivityPresenter
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    factory<SharedPreferences> {
        androidContext().getSharedPreferences(
            "defaults",
            Context.MODE_PRIVATE
        )
    }
    single<DispatcherProvider> { DispatcherProviderImpl }

//    factory { MainActivityPresenter(get()) }

    viewModel { MainActivityPresenter(get()) }
    viewModel { (savedStateHandle: SavedStateHandle) ->
        DetailsViewModel(
            savedStateHandle,
            get(),
            get()
        )
    }
}