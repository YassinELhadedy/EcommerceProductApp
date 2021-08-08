package com.jumia.myapplication.ui.products

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.ExperimentalPagingApi
import com.jumia.myapplication.domain.Product
import com.jumia.myapplication.infrastructure.ProductRepo
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

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ProductViewModelTest{
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()
    @Mock
    private lateinit var apiHelper: ProductRepo
    @Mock
    private lateinit var apiUsersObserver: Observer<Resource<Any>?>
    @Before
    fun setUp() {
        // do something if required
    }

    @ExperimentalPagingApi
    @Test
    fun givenServerResponse200_whenFetch_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            Mockito.doReturn(
                Product(
                  null,"S","S","S",1,1,1,null,1,null,null,null,null
                )
            )
                .`when`(apiHelper)
                .get(ArgumentMatchers.anyInt())
            val viewModel = ProductViewModel(apiHelper)
            viewModel.productInfo(1)
            viewModel.productData.observeForever(apiUsersObserver)
            Mockito.verify(apiHelper).get(ArgumentMatchers.anyInt())
            Mockito.verify(apiUsersObserver).onChanged(Resource.success( Product(
                null,"S","S","S",1,1,1,null,1,null,null,null,null
            )))
            viewModel.productData.removeObserver(apiUsersObserver)
        }
    }
}