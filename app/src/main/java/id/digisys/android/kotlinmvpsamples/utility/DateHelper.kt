package id.digisys.android.kotlinmvpsamples.utility

import java.text.SimpleDateFormat
import java.util.*

object DateHelper{
    fun formatDateToString(date: Date?, pattern: String = "EEEE, dd MMMM yyyy"): String {
       return if(date != null) SimpleDateFormat(pattern, Locale.US).format(date) else ""
    }

}