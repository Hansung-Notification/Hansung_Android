package com.foundy.presentation.view.search

import androidx.lifecycle.*
import androidx.paging.cachedIn
import androidx.paging.map
import com.foundy.domain.model.Query
import com.foundy.domain.usecase.notice.SearchNoticeListUseCase
import com.foundy.domain.usecase.query.AddRecentQueryUseCase
import com.foundy.domain.usecase.query.GetRecentQueryListUseCase
import com.foundy.domain.usecase.query.RemoveRecentQueryUseCase
import com.foundy.domain.usecase.query.UpdateRecentQueryUseCase
import com.foundy.presentation.model.SearchUiState
import com.foundy.presentation.view.common.NoticeItemUiStateCreatorFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
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
    noticeItemUiStateCreatorFactory: NoticeItemUiStateCreatorFactory,
    @Named("Main") private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    private val noticeUiStateCreator = noticeItemUiStateCreatorFactory.create(
        viewModelScope,
        dispatcher
    )

    init {
        viewModelScope.launch(dispatcher) {
            getRecentQueryListUseCase().map { list ->
                list.map { it.content }
            }.collect { queries ->
                _uiState.update { it.copy(recentQueries = queries) }
            }
        }
    }

    fun addOrUpdateRecent(query: String) {
        val hasQuery = _uiState.value.recentQueries.contains(query)
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

    private var searchingJob: Job? = null

    fun searchNotices(query: String) {
        searchingJob?.cancel()
        searchingJob = viewModelScope.launch(dispatcher) {
            searchNoticeListUseCase(query).cachedIn(viewModelScope).map { pagingData ->
                pagingData.map(noticeUiStateCreator::create)
            }.collectLatest { pagingData ->
                _uiState.update {
                    it.copy(searchedNoticePagingData = pagingData)
                }
            }
        }
    }
}