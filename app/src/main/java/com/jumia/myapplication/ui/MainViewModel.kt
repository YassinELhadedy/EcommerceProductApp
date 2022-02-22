package com.jumia.myapplication.ui

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jumia.myapplication.ui.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

enum class NavigationJourney(val value: Int) {
    STORY_DETAIL(0),
    PRODUCTDETAIL(1);

    companion object {
        fun valueOf(value: Int) = values().find { it.value == value }
    }
}

class Navigator(val navigationJourney: NavigationJourney, val bundle: Bundle? = null)

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    val navigationJourney = MutableLiveData<Event<Navigator>>(null)

}