package com.jumia.myapplication.domain

class Store (val id: String,val categoryDisc: String,val logo: String,val name: String,val status:StoreStatus,val openingHours:String,val address:Address)
enum class StoreStatus(val value: Int) {
    CLOSED(0),
    OPEN(1),
    BUSY(2),
    CLOSE_SOON(3);
    companion object {
        fun fromInt(value: Int) = StoreStatus.values().first { it.value == value }
    }
}