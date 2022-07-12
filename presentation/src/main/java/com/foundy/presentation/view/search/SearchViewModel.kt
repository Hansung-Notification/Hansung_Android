package com.foundy.presentation.view.search

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.foundy.domain.model.Query
import com.foundy.domain.usecase.notice.SearchNoticeListUseCase
import com.foundy.domain.usecase.query.AddRecentQueryUseCase
import com.foundy.domain.usecase.query.GetRecentQueryListUseCase
import com.foundy.domain.usecase.query.RemoveRecentQueryUseCase
import com.foundy.domain.usecase.query.UpdateRecentQueryUseCase
import com.foundy.presentation.model.NoticeUiState
import com.foundy.presentation.view.common.FavoriteViewModelDelegateFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class SearchViewModel @Inject constructor(
    getRecentQueryListUseCase: GetRecentQueryListUseCase,
    private val addRecentQueryUseCase: AddRecentQueryUseCase,
    private val removeRecentQueryUseCase: RemoveRecentQueryUseCase,
    private val updateRecentQueryUseCase: UpdateRecentQueryUseCase,
    private val searchNoticeListUseCase: SearchNoticeListUseCase,
    favoriteDelegateFactory: FavoriteViewModelDelegateFactory,
    @Named("Main") private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    private val favoriteDelegate = favoriteDelegateFactory.create(viewModelScope, dispatcher)

    val recentQueries = getRecentQueryListUseCase().map { list ->
        list.map { it.content }
    }.flowOn(dispatcher).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    fun addOrUpdateRecent(query: String) {
        val hasQuery = recentQueries.value.contains(query)
        Query(content = query).let {
            viewModelScope.launch(dispatcher) {
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

    fun searchNotices(query: String): Flow<PagingData<NoticeUiState>> {
        return searchNoticeListUseCase(query).cachedIn(viewModelScope).map { pagingData ->
            pagingData.map { favoriteDelegate.createNoticeUiState(it) }
        }
    }
}