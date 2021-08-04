package com.jumia.myapplication.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import com.jumia.myapplication.infrastructure.ConfigurationRepo
import com.jumia.myapplication.ui.util.state.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class ConfigurationViewModel @Inject constructor(private val configurationRepo: ConfigurationRepo) :
    ViewModel() {
    private val _configurationData = MutableLiveData<Resource<Any>?>(null)
    val configurationData: LiveData<Resource<Any>?> = _configurationData

    fun configurationInfo(configurationId: Int) = viewModelScope.launch {
        _configurationData.postValue(Resource.loading(null))
        kotlin.runCatching {
            _configurationData.postValue(
                Resource.success(
                    data = configurationRepo.get(
                        configurationId
                    )
                )
            )
        }.getOrElse { ex ->
            _configurationData.postValue(
                Resource.error(
                    ex.message ?: "Error Occurred!",
                    null
                )
            )
        }
    }

}