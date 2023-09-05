package ir.shahabazimi.eliqweather

import eliqweather.data.utils.convertToReadableDate
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class GeneralTests {

    @Test
    fun `test convert iso8601 to simple english`() {
        val date = "2023-09-05"
        val result = date.convertToReadableDate()
        val expected = "Tuesday, Sep 05"
        assertEquals(expected, result)
    }
}