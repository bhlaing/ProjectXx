package com.x.projectxx.ui.content

import com.x.projectxx.ui.content.model.UserContentItem
import com.x.projectxx.domain.content.model.UserContent
import java.text.SimpleDateFormat
import java.util.*

val ymdFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

fun UserContent.toUserContentItem() = mapToContentItem(this)

private fun mapToContentItem(userContent: UserContent) = userContent.let {
    when (it) {
        is UserContent.MOBILE -> mapToMobileItem(it)
        is UserContent.PAYID -> mapToPayIdItem(it)
        is UserContent.WIFI -> mapToWifiItem(it)
    }
}

private fun mapToPayIdItem(mobileNumberContent: UserContent.PAYID) = with(mobileNumberContent) {
    UserContentItem.PayIdItem(this.referenceId,
        this.name,
        this.description,
        ymdFormat.format(this.createdDate),
        securityLevel,
        value,
        mapContentDescription(this)
    )
}

private fun mapToWifiItem(mobileNumberContent: UserContent.WIFI) = with(mobileNumberContent) {
    UserContentItem.WifItem(this.referenceId,
        this.name,
        this.description,
        ymdFormat.format(this.createdDate),
        securityLevel,
        value,
        mapContentDescription(this)
    )
}


private fun mapToMobileItem(mobileNumberContent: UserContent.MOBILE) = with(mobileNumberContent) {
    UserContentItem.MobileItem(this.referenceId,
        this.name,
        this.description,
        ymdFormat.format(this.createdDate),
        securityLevel,
        number,
        mapContentDescription(this)
    )
}

private fun mapContentDescription(content: UserContent) =
    "${content.name} " +
            "${content.description} " +
            "created on: ${ymdFormat.format(content.createdDate)}" +
            "security level set to: ${content.securityLevel}"
