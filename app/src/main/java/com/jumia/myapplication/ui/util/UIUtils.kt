package com.jumia.myapplication.ui.util

import android.widget.HorizontalScrollView
import java.util.*


fun HorizontalScrollView.scrollToStart() {
    val direction = if (Locale.getDefault().language == "en") left else right
    smoothScrollTo(right, 0)
}
fun HorizontalScrollView.scrollToEnd() {
    val direction = if (Locale.getDefault().language == "en") left else right
    smoothScrollTo(left, 0)
}
