package com.jumia.myapplication.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel@Inject constructor(): ViewModel(){
    val fragmentMenuSelected = MutableLiveData(Menu.FEED)

}
enum class Menu(val value: Int) {
    FEED(0),
    RELEAEDDROBS(1),
    FUTUREDROBS(2),
    TOPRATEDDROBS(3);
    companion object {
        fun fromInt(value: Int) = Menu.values().first { it.value == value }
    }
}