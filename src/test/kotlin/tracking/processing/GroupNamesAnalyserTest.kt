package tracking.processing

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import tracking.statuses.OfferStatusException
import tracking.statuses.Status

class GroupNamesAnalyserTest {
    private val subject = GroupNamesAnalyser()

    @Test
    fun `Every match must have the offer group`() {
        val exception = assertThrows<OfferStatusException> {
            subject.detectIn(listOf("StatusOpen" to "some_text"))
        }

        assertEquals("Did not detect offer", exception.requireMessage())
    }

    @Test
    fun `Every match must have the status group`() {
        val exception = assertThrows<OfferStatusException> {
            subject.detectIn(listOf("Commissions" to "some_text"))
        }

        assertEquals("Did not detect status", exception.requireMessage())
    }

    @Test
    fun `No match can have more than one status`() {
        val exception = assertThrows<OfferStatusException> {
            subject.detectIn(listOf(
                "StatusOpen" to "some_text_1",
                "StatusOpen" to "some_text_2",
                "Quotes" to "some_text_3",
            ))
        }

        assertEquals("Detected multiple statuses", exception.requireMessage())
    }

    @Test
    fun `No match can have the same offer captured more than once`() {
        val exception = assertThrows<OfferStatusException> {
            subject.detectIn(listOf(
                "Commissions" to "some_text_1",
                "Commissions" to "some_text_2",
                "StatusOpen" to "some_text_3",
            ))
        }

        assertEquals("The same offer has been matched multiple times", exception.requireMessage())
    }

    @Test
    fun detectIn() {
        val subject = GroupNamesAnalyser()

        val result = subject.detectIn(listOf(
            "StatusClosed" to "some_text_1",
            "CommissionsAndQuotes" to "some_text_2",
            "Projects" to "some_text_3",
        ))

        assertEquals(3, result.size)
        assertEquals(Status.CLOSED, result[0].status)
        assertEquals("COMMISSIONS", result[0].offer)
        assertEquals(Status.CLOSED, result[1].status)
        assertEquals("QUOTES", result[1].offer)
        assertEquals(Status.CLOSED, result[2].status)
        assertEquals("PROJECTS", result[2].offer)
    }
}
