package andaeys.io.tricketo_android.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.toFormattedDateString(): String {
    val date = Date(this)
    val format = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    return format.format(date)
}

fun Long.toFormattedTimeString(): String {
    val date = Date(this)
    val format = SimpleDateFormat("HH:mm", Locale.getDefault())
    return format.format(date)
}

fun Long.toFormattedFullString(): String {
    val date = Date(this)
    val format = SimpleDateFormat("MMM dd, yyyy - HH:mm", Locale.getDefault())
    return format.format(date)
}

fun dateStringToLong(dateString: String, format: String = "MMM dd, yyyy - HH:mm"): Long? {
    return try {
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        val date = sdf.parse(dateString)
        date?.time
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
