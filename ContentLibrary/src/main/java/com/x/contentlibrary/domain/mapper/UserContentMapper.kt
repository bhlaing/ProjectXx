package com.x.contentlibrary.domain.mapper

import com.x.contentlibrary.domain.UserContent
import com.x.contentlibrary.domain.exception.GenericContentException
import com.x.contentlibrary.impl.model.ContentDO

fun ContentDO.toUserContent(docId: String) =
    runCatching { mapToUserContent(docId, this) }.getOrElse {
        throw GenericContentException()
    }

fun mapToUserContent(docId: String, contentDO: ContentDO) =
    UserContent(docId,
        contentDO.valueRef!!,
        contentDO.name,
        contentDO.description,
        contentDO.createdDate!!,
        contentDO.securityLevel!!,
        contentDO.type!!
    )
