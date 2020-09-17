package com.x.projectxx.domain.contact

import com.x.projectxx.BaseTest
import com.x.projectxx.MockitoHelper
import com.x.projectxx.data.contact.ContactRepository
import com.x.projectxx.data.contact.model.SimpleResult
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class AcceptContactTest : BaseTest() {

    @Mock
    lateinit var contactRepository: ContactRepository

    lateinit var acceptContact: AcceptContact

    @Before
    override fun setUp() {
        super.setUp()

        acceptContact = AcceptContact(contactRepository)
    }


    @Test
    fun `when contact is successfully accepted then return success result`() {
        runBlocking {
            whenever(contactRepository.acceptContactRequest(MockitoHelper.anyObject())).thenReturn(
                SimpleResult.Success
            )

            val result = acceptContact.invoke(AcceptContact.Param("1234"))

            assertTrue(result is AcceptContact.AcceptResult.Success)
        }
    }

    @Test
    fun `when accept contact fail then return fail result`() {
        runBlocking {
            whenever(contactRepository.acceptContactRequest(MockitoHelper.anyObject())).thenReturn(
                SimpleResult.Fail(Exception("fail"))
            )

            val result = acceptContact.invoke(AcceptContact.Param("1234"))

            assertTrue(result is AcceptContact.AcceptResult.Fail)
        }
    }
}