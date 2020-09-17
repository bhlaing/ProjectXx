package com.x.projectxx.data.contact.model

import java.lang.Exception

sealed class SimpleResult {
    object Success: SimpleResult()
    class Fail(val exception: Exception): SimpleResult()
}