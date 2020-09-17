package com.x.projectxx.domain.content.model

import java.util.*

sealed class UserContent(
    val referenceId: String,
    val name: String,
    val description: String,
    val createdDate: Date,
    val securityLevel: SecurityLevel
) {
    class PAYID(
        referenceId: String,
        name: String = "",
        description: String = "",
        createdDate: Date,
        securityLevel: SecurityLevel,
        val value: String
    ) : UserContent(referenceId, name, description, createdDate, securityLevel)

    class WIFI(
        referenceId: String,
        name: String = "",
        description: String = "",
        createdDate: Date,
        securityLevel: SecurityLevel,
        val value: String
    ) : UserContent(referenceId, name, description, createdDate, securityLevel)

    class MOBILE(
        referenceId: String,
        name: String = "",
        description: String = "",
        createdDate: Date,
        securityLevel: SecurityLevel,
        val number: String
    ) : UserContent(referenceId, name, description, createdDate, securityLevel)
}