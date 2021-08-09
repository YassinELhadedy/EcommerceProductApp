package com.jumia.myapplication.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.ExperimentalPagingApi
import com.jumia.myapplication.infrastructure.ConfigurationRepo
import com.jumia.myapplication.infrastructure.dto.JumConfiguration
import com.jumia.myapplication.infrastructure.dto.JumCurrency
import com.jumia.myapplication.infrastructure.dto.JumSupport
import com.jumia.myapplication.ui.splash.ConfigurationViewModel
import com.jumia.myapplication.ui.util.state.Resource
import com.mindorks.kotlinFlow.utils.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.lang.RuntimeException

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ConfigurationViewModelTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()
    @Mock
    private lateinit var apiHelper: ConfigurationRepo
    @Mock
    private lateinit var apiUsersObserver: Observer<Resource<JumConfiguration>>
    @Before
    fun setUp() {
        // do something if required
    }
    @ExperimentalPagingApi
    @Test
    fun givenServerResponse200_whenFetch_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            Mockito.doReturn(
                JumConfiguration(
                    JumCurrency("s","s",1,1,"S","S"), emptyList(),JumSupport("s",true,"s")
                )
            )
                .`when`(apiHelper)
                .get(ArgumentMatchers.anyInt())
            val viewModel = ConfigurationViewModel(apiHelper)
            viewModel.configurationData.observeForever(apiUsersObserver)
            Mockito.verify(apiHelper).get(ArgumentMatchers.anyInt())
            Mockito.verify(apiUsersObserver).onChanged(Resource.success(JumConfiguration(
                JumCurrency("s","s",1,1,"S","S"), emptyList(),JumSupport("s",true,"s"))))
            viewModel.configurationData.removeObserver(apiUsersObserver)
        }
    }

    @ExperimentalPagingApi
    @Test
    fun givenServerResponse200_whenFetch_And_Null_Data_shouldReturnSuccess2() {
        testCoroutineRule.runBlockingTest {
            Mockito.doReturn(
                null
            )
                .`when`(apiHelper)
                .get(ArgumentMatchers.anyInt())
            val viewModel = ConfigurationViewModel(apiHelper)
            viewModel.configurationData.observeForever(apiUsersObserver)
            Mockito.verify(apiHelper).get(ArgumentMatchers.anyInt())
            Mockito.verify(apiUsersObserver).onChanged(Resource.success(null))
            viewModel.configurationData.removeObserver(apiUsersObserver)
        }
    }

    @ExperimentalPagingApi
    @Test
    fun givenServerResponseError_whenFetch_shouldReturnError() {
        testCoroutineRule.runBlockingTest {
            val errorMessage = "Error Occurred!"
            Mockito.doThrow(RuntimeException())
                .`when`(apiHelper)
                .get(ArgumentMatchers.anyInt())
            val viewModel = ConfigurationViewModel(apiHelper)
            viewModel.configurationData.observeForever(apiUsersObserver)
            Mockito.verify(apiHelper).get(ArgumentMatchers.anyInt())
            Mockito.verify(apiUsersObserver).onChanged(
                Resource.error(
                    errorMessage,
                    null
                )
            )
            viewModel.configurationData.removeObserver(apiUsersObserver)
        }
    }
}