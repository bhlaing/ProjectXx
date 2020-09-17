package com.x.projectxx.data.content.model

import java.util.*

class ContentWrapperDO(val reference: String,
                       val content: Content)

class Content(
    val value: Any? = null,
    val name: String? = "",
    val description: String? = "",
    val createdDate: Date? = null,
    val securityLevel: SecurityLevel? = null,
    val type: ContentType? = null
)

