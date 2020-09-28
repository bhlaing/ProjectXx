package com.x.contentlibrary.domain

import java.util.*

class UserContent(
    val contentRef: String, // docId of this user content
    val valueRef: String, // docId of this user content value
    val name: String?,
    val description: String?,
    val createdDate: Date,
    val securityLevel: SecurityLevel,
    val type: ContentType
)