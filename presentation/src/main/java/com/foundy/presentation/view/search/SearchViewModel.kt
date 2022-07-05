package com.foundy.presentation.view.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.foundy.domain.model.Query
import com.foundy.domain.usecase.query.AddRecentQueryUseCase
import com.foundy.domain.usecase.query.GetRecentQueryListUseCase
import com.foundy.domain.usecase.query.RemoveRecentQueryUseCase
import com.foundy.domain.usecase.query.UpdateRecentQueryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    getRecentQueryListUseCase: GetRecentQueryListUseCase,
    private val addRecentQueryUseCase: AddRecentQueryUseCase,
    private val removeRecentQueryUseCase: RemoveRecentQueryUseCase,
    private val updateRecentQueryUseCase: UpdateRecentQueryUseCase
) : ViewModel() {

    val recentQueries = getRecentQueryListUseCase().asLiveData().map { list ->
        list.map { it.content }
    }

    fun addOrUpdateRecent(query: String) {
        viewModelScope.launch {
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
        viewModelScope.launch {
            removeRecentQueryUseCase(Query(content = query))
        }
    }
}