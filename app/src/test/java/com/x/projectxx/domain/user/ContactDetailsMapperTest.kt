package com.x.projectxx.domain.user

import com.x.projectxx.data.identity.model.UserProfile
import com.x.projectxx.domain.user.mappers.toContactDetails
import org.junit.Test
import java.io.InvalidObjectException

class ContactDetailsMapperTest {
    @Test(expected = InvalidObjectException::class)
    fun `when user id is null throw InvalidObjectException` () {
        UserProfile().toContactDetails()
    }
}