package util

import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat

object Util {
    fun Long.getFormattedDateFromUnixTime(format:String):String{
        val timeStamp = Timestamp(this)
        val date = Date(timeStamp.time)

        return SimpleDateFormat(format).format(date)
    }
}