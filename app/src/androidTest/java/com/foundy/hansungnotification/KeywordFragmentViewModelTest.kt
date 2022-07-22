package com.foundy.hansungnotification

import com.foundy.domain.exception.KeywordInvalidException
import com.foundy.domain.exception.NoSearchResultException
import com.foundy.domain.usecase.messaging.SubscribeToUseCase
import com.foundy.domain.usecase.messaging.UnsubscribeFromUseCase
import com.foundy.domain.usecase.keyword.AddKeywordUseCase
import com.foundy.domain.usecase.keyword.ReadKeywordListUseCase
import com.foundy.domain.usecase.keyword.RemoveKeywordUseCase
import com.foundy.domain.usecase.keyword.ValidateKeywordUseCase
import com.foundy.domain.usecase.notice.HasSearchResultUseCase
import com.foundy.hansungnotification.fake.FakeKeywordRepositoryImpl
import com.foundy.hansungnotification.fake.FakeMessagingRepositoryImpl
import com.foundy.hansungnotification.fake.FakeNoticeRepositoryImpl
import com.foundy.presentation.view.keyword.KeywordFragmentViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class KeywordFragmentViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    private val fakeKeywordRepository = FakeKeywordRepositoryImpl()
    private val fakeMessagingRepository = FakeMessagingRepositoryImpl()
    private val fakeNoticeRepository = FakeNoticeRepositoryImpl()

    private val viewModel = KeywordFragmentViewModel(
        ReadKeywordListUseCase(fakeKeywordRepository),
        AddKeywordUseCase(fakeKeywordRepository),
        RemoveKeywordUseCase(fakeKeywordRepository),
        SubscribeToUseCase(fakeMessagingRepository),
        UnsubscribeFromUseCase(fakeMessagingRepository),
        HasSearchResultUseCase(fakeNoticeRepository),
        ValidateKeywordUseCase(),
        testDispatcher
    )

    @Test
    fun callsOnFailureWithKeywordInvalidException_IfKeywordIsInvalid() {
        var onSuccessCount = 0
        lateinit var exception: Exception

        viewModel.checkKeywordSubmit(
            "@장학금",
            onSuccess = { onSuccessCount++ },
            onFailure = { exception = it },
            onFinally = {}
        )

        assertEquals(0, onSuccessCount)
        assertTrue(exception is KeywordInvalidException)
    }

    @Test
    fun callsOnFailureWithNoSearchResultException_IfKeywordHasNoSearchResult() {
        fakeNoticeRepository.setHasSearchResult(Result.success(false))

        var onSuccessCount = 0
        lateinit var exception: Exception

        viewModel.checkKeywordSubmit(
            "장학금",
            onSuccess = { onSuccessCount++ },
            onFailure = { exception = it },
            onFinally = {}
        )

        assertEquals(0, onSuccessCount)
        assertTrue(exception is NoSearchResultException)
    }

    @Test
    fun callsOnFailureWithHttpException_IfSearchResultIsFailure() {
        val responseBody = ResponseBody.create(MediaType.get("text/html; charset=UTF-8"), "")
        val response = Response.error<ResponseBody>(404, responseBody)
        fakeNoticeRepository.setHasSearchResult(Result.failure(HttpException(response)))

        var onSuccessCount = 0
        lateinit var exception: Exception

        viewModel.checkKeywordSubmit(
            "장학금",
            onSuccess = { onSuccessCount++ },
            onFailure = { exception = it },
            onFinally = {}
        )

        assertEquals(0, onSuccessCount)
        assertTrue(exception is HttpException)
    }

    @Test
    fun callsOnSuccess_IfKeywordPassesChecks() {
        fakeNoticeRepository.setHasSearchResult(Result.success(true))

        var onSuccessCount = 0
        var exception: Exception? = null

        viewModel.checkKeywordSubmit(
            "장학금",
            onSuccess = { onSuccessCount++ },
            onFailure = { exception = it },
            onFinally = {}
        )

        assertEquals(1, onSuccessCount)
        assertTrue(exception == null)
    }

    @Test
    fun callsOnFinally_Whenever() {
        fakeNoticeRepository.setHasSearchResult(Result.failure(Exception()))

        var onFinallyCount = 0

        viewModel.checkKeywordSubmit(
            "2o3ij$@!",
            onSuccess = {},
            onFailure = {},
            onFinally = { onFinallyCount++ }
        )

        assertEquals(1, onFinallyCount)
    }
}