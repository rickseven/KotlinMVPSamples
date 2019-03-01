package id.digisys.android.kotlinmvpsamples.utility

import id.digisys.android.kotlinmvpsamples.utility.DateHelper.formatDateToString
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import java.text.SimpleDateFormat
import java.util.*

class DateHelperTest {

    private lateinit var dateFormat: SimpleDateFormat
    private lateinit var date: Date

    @Before
    fun setUp() {
        dateFormat = SimpleDateFormat("dd/MM/yyyy")
        date = dateFormat.parse("04/11/2018")
    }

    @Test
    fun `format date to string, when date given, should return date format string`() {
        assertEquals("Sunday, 04 November 2018", formatDateToString(date))
    }

    @Test
    fun `format date to string, when null given, should return empty string`() {
        assertEquals("", formatDateToString(null))
    }
}