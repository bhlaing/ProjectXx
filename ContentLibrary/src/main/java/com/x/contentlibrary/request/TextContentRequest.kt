package com.x.contentlibrary.request

import com.x.contentlibrary.domain.SecurityLevel

data class TextContentRequest(
    val text: String,
    val name: String? = "",
    val description: String? = "",
    val securityLevel: SecurityLevel
)