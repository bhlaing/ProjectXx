package com.x.projectxx.ui.content.model

import com.x.contentlibrary.domain.ContentType
import com.x.contentlibrary.domain.SecurityLevel


class UserContentItem(
    val contentRef: String,
    val valueRef: String,
    val name: String,
    val description: String,
    val createdDate: String,
    val securityLevel: SecurityLevel,
    val contentDescription: String,
    val type: ContentType
)