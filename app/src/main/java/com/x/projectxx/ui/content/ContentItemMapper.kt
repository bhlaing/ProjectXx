package com.x.projectxx.ui.content

import com.x.contentlibrary.domain.UserContent
import com.x.projectxx.ui.content.model.UserContentItem
import java.text.SimpleDateFormat
import java.util.*

val ymdFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

fun UserContent.toUserContentItem() = mapToContentItem(this)

private fun mapToContentItem(userContent: UserContent) = userContent.let {
    UserContentItem(
        it.contentRef,
        it.valueRef,
        it.name ?: "",
        it.description ?: "",
        ymdFormat.format(it.createdDate),
        it.securityLevel,
        mapContentDescription(it),
        it.type
    )
}
private fun mapContentDescription(content: UserContent) =
    "${content.name} " +
            "${content.description} " +
            "created on: ${ymdFormat.format(content.createdDate)}" +
            "security level set to: ${content.securityLevel}"
