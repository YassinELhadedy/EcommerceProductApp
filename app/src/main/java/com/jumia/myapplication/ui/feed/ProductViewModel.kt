package com.jumia.myapplication.ui.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jumia.myapplication.domain.Product
import com.jumia.myapplication.infrastructure.ProductRepo
import com.jumia.myapplication.ui.feed.model.VideoDrop
import com.jumia.myapplication.ui.util.state.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class ProductViewModel @Inject constructor(private val productRepo: ProductRepo) : ViewModel() {
    private val _productData = MutableLiveData<Resource<Any>?>(null)
    val productData: LiveData<Resource<Any>?> = _productData

    private val _videoData = MutableLiveData<VideoDrop>(VideoDrop("https://media.geeksforgeeks.org/wp-content/uploads/20201217163353/Screenrecorder-2020-12-17-16-32-03-350.mp4","dads","30 Min"))
    val videoData: LiveData<VideoDrop> = _videoData

     fun productInfo(productId: Int) = viewModelScope.launch {
        _productData.postValue(Resource.loading(null))
        kotlin.runCatching {
            _productData.postValue(Resource.success(data = productRepo.get(productId)))
        }.getOrElse { ex ->
            _productData.postValue(
                Resource.error(
                    ex.message ?: "Error Occurred!",
                    ex
                )
            )
        }
    }

    fun getProducts(): Flow<PagingData<Product>> = productRepo.getAllWithPagination().cachedIn(viewModelScope)
}

class FeedStoryModel(
    var id: String = "",
    var imageUrl: String = "",
    var isOpened: Boolean = false,
    var name: String = ""
)