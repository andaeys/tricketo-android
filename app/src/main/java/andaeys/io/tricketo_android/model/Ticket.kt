package andaeys.io.tricketo_android.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import java.util.Date

@IgnoreExtraProperties
data class Ticket(
    var enterTime: Long = Date().time,
    var licenseNumber: String = "",
    var driverName: String = "",
    var inboundWeight: Int = 0,
    var outboundWeight: Int = 0
) {
    @Exclude
    fun getEnterTimeDate(): Date = Date(enterTime)
}
