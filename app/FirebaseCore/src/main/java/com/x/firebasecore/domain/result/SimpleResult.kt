package com.x.firebasecore.domain.result

import java.lang.Exception

sealed class SimpleResult {
    object Success: SimpleResult()
    class Fail(val exception: Exception): SimpleResult()
}