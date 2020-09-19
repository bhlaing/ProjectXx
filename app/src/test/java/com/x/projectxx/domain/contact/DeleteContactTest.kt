package com.x.projectxx.domain.contact

import com.x.projectxx.BaseTest
import com.x.projectxx.MockitoHelper
import com.x.projectxx.data.contact.ContactRepository
import com.x.projectxx.data.contact.model.SimpleResult
import com.x.projectxx.domain.contact.DeleteContact.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class DeleteContactTest : BaseTest() {

    @Mock
    private lateinit var contactRepository: ContactRepository

    private lateinit var deleteContact: DeleteContact

    @Before
    override fun setUp() {
        super.setUp()

        deleteContact = DeleteContact(contactRepository)
    }

    @Test
    fun `when contact is successfully canceled then return success result`() {
        runBlocking {
            whenever(contactRepository.deleteContact(MockitoHelper.anyObject())).thenReturn(
                SimpleResult.Success
            )

            val result = deleteContact.invoke(Param("userId", "contactId"))

            Assert.assertTrue(result is DeleteResult.Success)
        }
    }

    @Test
    fun `when cancel contact fail then return fail result`() {
        runBlocking {
            whenever(contactRepository.deleteContact(MockitoHelper.anyObject())).thenReturn(
                SimpleResult.Fail(Exception("fail"))
            )

            val result = deleteContact.invoke(Param("1234", "contactId"))

            Assert.assertTrue(result is DeleteResult.Fail)
        }
    }
}