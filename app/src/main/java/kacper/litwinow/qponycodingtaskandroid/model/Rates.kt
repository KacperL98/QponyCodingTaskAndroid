package kacper.litwinow.qponycodingtaskandroid.model

import kacper.litwinow.qponycodingtaskandroid.constants.AUD
import kacper.litwinow.qponycodingtaskandroid.constants.CAD
import kacper.litwinow.qponycodingtaskandroid.constants.PLN
import kacper.litwinow.qponycodingtaskandroid.constants.USD
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Rates(
    @Json(name = "AUD")
    val aud: Double?,
    @Json(name = "CAD")
    val cad: Double?,
    @Json(name = "PLN")
    val pln: Double?,
    @Json(name = "USD")
    val usd: Double?
) {

    fun createRatesHashMap(): LinkedHashMap<String, Double?> {
        return LinkedHashMap<String, Double?>().apply {
            put(AUD, aud)
            put(CAD, cad)
            put(PLN, pln)
            put(USD, usd)
        }
    }
}
