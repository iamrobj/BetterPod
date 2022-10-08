package com.robj.betterpod.ui.compose.screens.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robj.betterpod.DispatcherProvider
import com.robj.betterpod.networking.ApiRepo
import com.robj.betterpod.networking.models.Category
import com.robj.betterpod.networking.models.Podcast
import kotlinx.coroutines.launch

class HomeViewModel(
    private val apiRepo: ApiRepo,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    val state: MutableState<State> = mutableStateOf(State.Loading)
    val categoryState: MutableState<CategoryState> = mutableStateOf(CategoryState.Loading)

    init {
        viewModelScope.launch(context = dispatcherProvider.io) {
            apiRepo.getTrendingPodcasts()
                .onSuccess { podcasts ->
                    state.value = if (podcasts.isEmpty()) {
                        State.Empty
                    } else {
                        State.Data(podcasts)
                    }
                }.onFailure {
                    it.printStackTrace()
                    state.value = State.Error("Something went wrong")
                }
        }
        viewModelScope.launch(context = dispatcherProvider.io) {
            apiRepo.getAllCategories()
                .onSuccess { categories ->
                    categoryState.value = CategoryState.Data(categories)
                }.onFailure {
                    it.printStackTrace()
                    categoryState.value = CategoryState.Error("Something went wrong")
                }
        }
    }

    fun loadByCategory(categories: List<Category>) {
        state.value = State.Loading
        viewModelScope.launch(context = dispatcherProvider.io) {
            apiRepo.getPodcastsByCategories(categories = categories)
                .onSuccess { podcasts ->
                    state.value = if (podcasts.isEmpty()) {
                        State.Empty
                    } else {
                        State.Data(podcasts)
                    }
                }.onFailure {
                    it.printStackTrace()
                    state.value = State.Error("Something went wrong")
                }
        }
    }

    sealed class State {
        object Loading : State()
        object Empty : State()
        data class Error(val errorMsg: String) : State()
        data class Data(val podcasts: List<Podcast>) : State()
    }

    sealed class CategoryState {
        object Loading : CategoryState()
        object Empty : CategoryState()
        data class Error(val errorMsg: String) : CategoryState()
        data class Data(val categories: List<Category>) : CategoryState()
    }


}