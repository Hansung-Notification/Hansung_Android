package com.foundy.presentation.view.search

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.*
import com.foundy.domain.model.Query
import com.foundy.domain.usecase.query.AddRecentQueryUseCase
import com.foundy.domain.usecase.query.GetRecentQueryListUseCase
import com.foundy.domain.usecase.query.RemoveRecentQueryUseCase
import com.foundy.domain.usecase.query.UpdateRecentQueryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class SearchViewModel @Inject constructor(
    getRecentQueryListUseCase: GetRecentQueryListUseCase,
    private val addRecentQueryUseCase: AddRecentQueryUseCase,
    private val removeRecentQueryUseCase: RemoveRecentQueryUseCase,
    private val updateRecentQueryUseCase: UpdateRecentQueryUseCase,
    @VisibleForTesting @Named("RecentQuery")
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    val recentQueries = getRecentQueryListUseCase().asLiveData().map { list ->
        list.map { it.content }
    }

    fun addOrUpdateRecent(query: String) {
        viewModelScope.launch(dispatcher) {
            Query(content = query).let {
                val hasQuery = recentQueries.value?.contains(query) == true
                if (hasQuery) {
                    updateRecentQueryUseCase(it)
                } else {
                    addRecentQueryUseCase(it)
                }
            }
        }
    }

    fun removeRecent(query: String) {
        viewModelScope.launch(dispatcher) {
            removeRecentQueryUseCase(Query(content = query))
        }
    }
}