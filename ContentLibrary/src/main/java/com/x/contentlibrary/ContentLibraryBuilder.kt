package com.x.contentlibrary

import com.google.firebase.firestore.FirebaseFirestore
import com.x.contentlibrary.impl.ContentService

object ContentLibraryBuilder {
    fun init(firebaseFirestore: FirebaseFirestore): ContentRepository = ContentService(firebaseFirestore)
}