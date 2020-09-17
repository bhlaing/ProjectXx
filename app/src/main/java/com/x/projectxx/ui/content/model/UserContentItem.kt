package com.x.projectxx.ui.content.model

import androidx.annotation.DrawableRes
import com.x.projectxx.domain.content.model.SecurityLevel

sealed class UserContentItem(
    val referenceId: String,
    val name: String,
    val description: String,
    val createdDate: String,
    val securityLevel: SecurityLevel,
    val contentDescription: String,
    @DrawableRes val icon: Int
) {
    class PayIdItem(
        referenceId: String,
        name: String = "Pay Id",
        description: String = "",
        createdDate: String,
        securityLevel: SecurityLevel,
        val value: String,
        contentDescription: String,
        @DrawableRes icon: Int
    ) : UserContentItem(
        referenceId,
        name,
        description,
        createdDate,
        securityLevel,
        contentDescription,
        icon
    )

    class WifItem(
        referenceId: String,
        name: String = "Wifi",
        description: String = "",
        createdDate: String,
        securityLevel: SecurityLevel,
        val value: String,
        contentDescription: String,
        @DrawableRes icon: Int
    ) : UserContentItem(
        referenceId,
        name,
        description,
        createdDate,
        securityLevel,
        contentDescription,
        icon
    )

    class MobileItem(
        referenceId: String,
        name: String = "Mobile",
        description: String = "",
        createdDate: String,
        securityLevel: SecurityLevel,
        val number: String,
        contentDescription: String,
        @DrawableRes icon: Int
    ) : UserContentItem(
        referenceId,
        name,
        description,
        createdDate,
        securityLevel,
        contentDescription,
        icon
    )
}