package com.x.projectxx.domain.content

import com.x.projectxx.R
import com.x.projectxx.data.content.model.Content
import com.x.projectxx.data.content.model.ContentType
import com.x.projectxx.data.content.model.ContentWrapperDO
import com.x.projectxx.data.content.model.SecurityLevel
import com.x.projectxx.domain.content.exception.InvalidContentException
import com.x.projectxx.domain.content.model.SecurityLevel as DomainSecurityLevel
import com.x.projectxx.domain.content.model.UserContent.PAYID
import com.x.projectxx.domain.content.model.UserContent.WIFI
import com.x.projectxx.domain.content.model.UserContent.MOBILE

fun List<ContentWrapperDO>.toUserContents() = mapContents(this)

private fun mapContents(contents: List<ContentWrapperDO>) = contents.map {
    when (it.content.type) {
        ContentType.PAY_ID -> mapPayId(it)
        ContentType.WIFI_INFO -> mapWifiInfo(it)
        ContentType.MOBILE -> mapMobile(it)
        else -> throw InvalidContentException(R.string.content_error_message)
    }
}

private fun mapMobile(content: ContentWrapperDO) = content.runCatching {
    MOBILE(
        reference,
        this.content.name ?: "",
        this.content.description ?: "",
        this.content.createdDate!!,
        mapSecurityLevel(this.content.securityLevel!!),
        this.content.value.toString()
    )
}.getOrElse { throw InvalidContentException(R.string.content_error_message) }

private fun mapWifiInfo(content: ContentWrapperDO) = content.runCatching {
    WIFI(
        this.reference,
        this.content.name ?: "",
        this.content.description ?: "",
        this.content.createdDate!!,
        mapSecurityLevel(this.content.securityLevel!!),
        this.content.value.toString()
    )
}.getOrElse { throw InvalidContentException(R.string.content_error_message) }

private fun mapPayId(content: ContentWrapperDO) = content.runCatching {
    PAYID(
        this.reference,
        this.content.name ?: "",
        this.content.description ?: "",
        this.content.createdDate!!,
        mapSecurityLevel(this.content.securityLevel!!),
        this.content.value as String
    )
}.getOrElse { throw InvalidContentException(R.string.content_error_message) }

private fun mapSecurityLevel(securityLevel: SecurityLevel) =
    when (securityLevel) {
        SecurityLevel.NONE -> DomainSecurityLevel.NONE
        SecurityLevel.DEFAULT -> DomainSecurityLevel.DEFAULT
        SecurityLevel.HIGH -> DomainSecurityLevel.HIGH
        SecurityLevel.XHIGH -> DomainSecurityLevel.XHIGH
    }