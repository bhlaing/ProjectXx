package com.x.projectxx.domain.contact

import com.x.projectxx.BaseTest
import com.x.projectxx.MockitoHelper
import com.x.projectxx.data.identity.IdentityRepository
import com.x.projectxx.data.identity.model.UserProfile
import com.x.projectxx.domain.contact.RetrieveContactList.*
import com.x.projectxx.domain.contact.RetrieveUserContactsStatus.*
import com.x.projectxx.domain.user.model.ContactStatus
import com.x.projectxx.domain.user.model.User
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mock

class RetrieveContactListTest : BaseTest() {
    @Mock
    lateinit var identityRepository: IdentityRepository

    @Mock
    lateinit var retrieveUserContactsStatus: RetrieveUserContactsStatus

    private lateinit var retrieveContactList: RetrieveContactList

    private val mockUserContactsStatusSuccess =
        RetrieveContactResult.Success(listOf(buildContactStatus(userId = "confirmed",status = ContactStatus.CONFIRMED),
            buildContactStatus(userId = "request",status = ContactStatus.REQUEST)))

    override fun setUp() {
        super.setUp()

        retrieveContactList = RetrieveContactList(retrieveUserContactsStatus, identityRepository)
    }

    @Test
    fun `when retrieving detail of a user that doesn't exit, then skips the user id`() {
        runBlocking {
            whenever(identityRepository.getUserProfile(MockitoHelper.anyObject())).thenReturn(null)
            whenever(retrieveUserContactsStatus.invoke(MockitoHelper.anyObject())).thenReturn(
                mockUserContactsStatusSuccess
            )

            val result = retrieveContactList.invoke(RetrieveContactList.Param("aa"))

            assertTrue(result is Result.Success)
        }
    }

    @Test
    fun `when retrieving detail of a contact, populates contact status`() {
        runBlocking {
            whenever(identityRepository.getUserProfile("confirmed")).thenReturn(buildUserProfile(userId = "confirmed"))
            whenever(identityRepository.getUserProfile("request")).thenReturn(buildUserProfile(userId = "request"))
            whenever(retrieveUserContactsStatus.invoke(MockitoHelper.anyObject())).thenReturn(
                mockUserContactsStatusSuccess
            )

            val result = retrieveContactList.invoke(RetrieveContactList.Param("aa"))

            assertTrue(result is Result.Success)

            val successResult = result as Result.Success

            assertEquals(ContactStatus.CONFIRMED,successResult.contacts.component1().contactStatus)
            assertEquals(ContactStatus.REQUEST,successResult.contacts.component2().contactStatus)
        }
    }


    private fun buildContactStatus(userId: String = "11", status: ContactStatus = ContactStatus.CONFIRMED) =
        User.Contact(userId, status)

    private fun buildUserProfile(
        userId: String = "11",
        displayName: String = "displayName",
        status: String? = "hahaha",
        profileImage: String? = null
    ) =
        UserProfile(userId, displayName, status, profileImage)
}