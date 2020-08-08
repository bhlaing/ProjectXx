package com.x.projectxx.application.extensions

import android.content.Context
import android.widget.Toast

fun Context.showLongToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()

fun Context.showShortToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
