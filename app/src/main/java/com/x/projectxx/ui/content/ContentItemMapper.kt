package com.x.projectxx.ui.content

import com.x.projectxx.R
import com.x.projectxx.domain.content.model.SecurityLevel
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
        mapContentDescription(this),
        mapIcon(securityLevel)
    )
}

private fun mapToWifiItem(mobileNumberContent: UserContent.WIFI) = with(mobileNumberContent) {
    UserContentItem.WifItem(this.referenceId,
        this.name,
        this.description,
        ymdFormat.format(this.createdDate),
        securityLevel,
        value,
        mapContentDescription(this),
        mapIcon(securityLevel)
    )
}


private fun mapToMobileItem(mobileNumberContent: UserContent.MOBILE) = with(mobileNumberContent) {
    UserContentItem.MobileItem(this.referenceId,
        this.name,
        this.description,
        ymdFormat.format(this.createdDate),
        securityLevel,
        number,
        mapContentDescription(this),
        mapIcon(securityLevel)
    )
}

private fun mapContentDescription(content: UserContent) =
    "${content.name} " +
            "${content.description} " +
            "created on: ${ymdFormat.format(content.createdDate)}" +
            "security level set to: ${content.securityLevel}"


private fun mapIcon(securityLevel: SecurityLevel) =
    when(securityLevel) {
        SecurityLevel.NONE -> R.drawable.ic_baseline_tag_faces_24
        SecurityLevel.DEFAULT -> R.drawable.ic_baseline_tag_faces_24
        SecurityLevel.HIGH -> R.drawable.ic_baseline_tag_faces_24
        else -> R.drawable.ic_baseline_tag_faces_24
    }

//class MOBILEItem(
//    referenceId: String,
//    name: String = "",
//    description: String = "",
//    createdDate: String,
//    securityLevel: SecurityLevel,
//    val number: String,
//    contentDescription: String