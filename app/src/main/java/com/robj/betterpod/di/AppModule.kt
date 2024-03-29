package com.robj.betterpod.di

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.SavedStateHandle
import com.robj.betterpod.DispatcherProvider
import com.robj.betterpod.DispatcherProviderImpl
import com.robj.betterpod.ui.compose.screens.details.DetailViewModel
import com.robj.betterpod.ui.compose.screens.home.HomeViewModel
import com.robj.betterpod.ui.compose.screens.myShows.MyShowsViewModel
import com.robj.betterpod.ui.compose.screens.search.SearchViewModel
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

    viewModel { HomeViewModel(get(), get(), get()) }
    viewModel { SearchViewModel(get(), get(), get()) }
    viewModel { MyShowsViewModel(get(), get()) }
    viewModel { (savedStateHandle: SavedStateHandle) ->
        DetailViewModel(
            savedStateHandle,
            get(),
            get()
        )
    }
}
