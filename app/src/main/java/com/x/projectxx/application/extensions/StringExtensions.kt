package com.x.projectxx.application.extensions

import android.net.Uri

fun String.toAndroidUri() = Uri.parse(this)