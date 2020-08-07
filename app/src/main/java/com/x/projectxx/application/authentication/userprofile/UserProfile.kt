package com.x.projectxx.application.authentication.userprofile

import android.net.Uri

data class UserProfile(val userId: String,
                       val displayName: String,
                       val uri: Uri?)