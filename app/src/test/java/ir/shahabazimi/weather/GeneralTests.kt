package ir.shahabazimi.weather

import weather.data.utils.convertToDayDate
import weather.data.utils.convertToReadableDate
import weather.data.utils.dateIsToday
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 06
 **/
@RunWith(JUnit4::class)
class GeneralTests {

    @Test
    fun `test convert iso8601 to simple english`() {
        val date = "2023-09-05"
        val result = date.convertToReadableDate()
        val expected = "Tuesday, Sep 05"
        assertEquals(expected, result)
    }

    @Test
    fun `test convert iso8601 to only day name`() {
        val date = "2023-09-05"
        val result = date.convertToDayDate()
        val expected = "Tuesday"
        assertEquals(expected, result)
    }

    @Test
    fun `check if date is today`(){
        val date = "2023-09-05"
        assertTrue(dateIsToday(date))
    }
}