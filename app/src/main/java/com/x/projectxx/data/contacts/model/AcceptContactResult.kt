package com.x.projectxx.data.contacts.model

import java.lang.Exception

sealed class AcceptContactResult {
    object Success: AcceptContactResult()
    class Fail(val exception: Exception): AcceptContactResult()
}