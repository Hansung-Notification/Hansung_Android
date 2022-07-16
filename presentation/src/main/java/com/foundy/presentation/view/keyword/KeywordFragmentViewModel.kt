package com.foundy.presentation.view.keyword

import androidx.lifecycle.*
import com.foundy.domain.exception.NoSearchResultException
import com.foundy.domain.model.Keyword
import com.foundy.domain.usecase.messaging.SubscribeToUseCase
import com.foundy.domain.usecase.messaging.UnsubscribeFromUseCase
import com.foundy.domain.usecase.keyword.AddKeywordUseCase
import com.foundy.domain.usecase.keyword.ReadKeywordListUseCase
import com.foundy.domain.usecase.keyword.RemoveKeywordUseCase
import com.foundy.domain.usecase.notice.HasSearchResultUseCase
import com.foundy.presentation.model.KeywordUiState
import com.foundy.presentation.utils.KeywordValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class KeywordFragmentViewModel @Inject constructor(
    readKeywordListUseCase: ReadKeywordListUseCase,
    private val addKeywordUseCase: AddKeywordUseCase,
    private val removeKeywordUseCase: RemoveKeywordUseCase,
    private val subscribeToUseCase: SubscribeToUseCase,
    private val unsubscribeFromUseCase: UnsubscribeFromUseCase,
    private val hasSearchResultUseCase: HasSearchResultUseCase,
    @Named("Main") private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    private val _uiState = MutableStateFlow(KeywordUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch(dispatcher) {
            readKeywordListUseCase().collect { result ->
                _uiState.update {
                    if (result.isSuccess) {
                        it.copy(keywordList = result.getOrNull()!!, isLoadingKeywords = false)
                    } else {
                        it.copy(error = result.exceptionOrNull()!!, isLoadingKeywords = false)
                    }
                }
            }
        }
    }

    fun addKeywordItem(keyword: Keyword) {
        addKeywordUseCase(keyword)
    }

    fun removeKeywordItem(keyword: Keyword) {
        removeKeywordUseCase(keyword)
    }

    fun subscribeTo(topic: String, onFailure: (Exception) -> Unit) {
        subscribeToUseCase(topic, onFailure)
    }

    fun unsubscribeFrom(topic: String, onFailure: (Exception) -> Unit) {
        unsubscribeFromUseCase(topic, onFailure)
    }

    /**
     * 키워드의 유효성 검사와 해당 키워드가 공지사항 검색결과에 존재하는지 확인한다.
     *
     * 검사에 성공한 경우 [onSuccess]가 호출되며, 실패한 경우 예외를 매개변수로하여 [onFailure]가 호출된다.
     * 최종적으로 [onFinally]가 호출된다.
     *
     * 예외에는 다음 경우가 존재한다.
     * - [KeywordValidator.KeywordInvalidException]: 유효성 검사에 실패한 경우
     * - [NoSearchResultException]: 검색 결과가 없는 경우
     * - [HttpException]: 검색에 실패한 경우
     * - [Exception]: 그외에 경우
     */
    fun checkKeywordSubmit(
        keyword: String,
        onSuccess: () -> Unit,
        onFailure: (e: Exception) -> Unit,
        onFinally: () -> Unit
    ) {
        viewModelScope.launch(dispatcher) {
            try {
                checkValid(keyword)
                checkKeywordHasSearchResult(keyword)

                onSuccess()
            } catch (e: Exception) {
                onFailure(e)
            } finally {
                onFinally()
            }
        }
    }

    /**
     * 키워드 문자의 유효성을 검사한다.
     *
     * 유효성 검사에 실패한 경우 [KeywordValidator.KeywordInvalidException]를 상속한 예외를 던진다.
     */
    fun checkValid(keyword: String) {
        KeywordValidator.check(keyword, uiState.value.keywordList)
    }

    private suspend fun checkKeywordHasSearchResult(keyword: String) {
        val result = hasSearchResultUseCase(keyword)
        if (result.isSuccess) {
            val hasSearchResult = result.getOrNull()!!
            if (!hasSearchResult) {
                throw NoSearchResultException()
            }
        } else {
            throw result.exceptionOrNull()!!
        }
    }
}