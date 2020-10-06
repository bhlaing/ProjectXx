package com.x.contentlibrary.impl.model

import com.x.contentlibrary.domain.ContentType
import com.x.contentlibrary.domain.SecurityLevel
import java.util.*

class ContentDO(
    val valueRef: String? = null,
    val name: String? = "",
    val description: String? = "",
    val createdDate: Date? = null,
    val securityLevel: SecurityLevel? = null,
    val type: ContentType? = null
)
