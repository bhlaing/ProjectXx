package com.x.projectxx.application.extensions

import android.view.View.GONE
import android.widget.TextView

/**
 * Convenient extension function which set the text if given text is not null or empty
 * else hides the text view
 */
fun TextView.setTextOrGone(text: String?) = if(text.isNullOrBlank()) {
    this.visibility = GONE
} else {
    this.text = text
}